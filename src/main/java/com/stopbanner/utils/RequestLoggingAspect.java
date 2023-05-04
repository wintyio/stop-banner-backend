package com.stopbanner.utils;

import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.base.Joiner;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
public class RequestLoggingAspect {
    private final ObjectMapper objectMapper;

    private String paramMapToString(Map<String, String[]> paramMap) {
        return paramMap.entrySet().stream()
                .map(entry -> String.format("%s -> (%s)",
                        entry.getKey(), Joiner.on(",").join(entry.getValue())))
                .collect(Collectors.joining(", "));
    }

    @Pointcut("within(com.stopbanner.src.controller..*)")
    public void onRequest() {}

    @Around("com.stopbanner.utils.RequestLoggingAspect.onRequest()")
    public Object doLogging(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;

        Map<String, String[]> paramMap = request.getParameterMap();
        String params = "";
        if (paramMap.isEmpty() == false) {
            params = " [" + paramMapToString(paramMap) + "]";
        }

        Object result = null;

        long start = System.currentTimeMillis();
        try {
            result = pjp.proceed(pjp.getArgs());
            return result;
        } finally {
            String host = request.getRemoteHost();
            long end = System.currentTimeMillis();
            String str = String.format("Request: %s %s%s < %s (%dms)", request.getMethod(), request.getRequestURI(), params, host, end - start);
            // log.info("Request: {} {}{} < {} ({}ms)", request.getMethod(), request.getRequestURI(), params, host, end - start);
            if (cachingRequest.getContentType() != null && cachingRequest.getContentType().contains("application/json")) {
                if (cachingRequest.getContentAsByteArray() != null && cachingRequest.getContentAsByteArray().length != 0){
                    str += " " + objectMapper.readTree(cachingRequest.getContentAsByteArray());
                }
            }
            log.info(str);
            try {
                log.info("Response: {} < {}", objectMapper.writeValueAsString(result), host);
            }
            catch (Exception exception) {
                log.error("in loggin aop, this reponse is not BaseResponse");
            }
        }
    }
}