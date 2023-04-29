package com.stopbanner.src.security;

import com.stopbanner.config.BaseException;
import com.stopbanner.utils.JwtService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.stopbanner.config.BaseResponseStatus.*;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final SecurityUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    public JwtAuthenticationFilter(SecurityUserDetailsService customUserDetailsService, JwtService jwtService,HandlerExceptionResolver resolver) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtService = jwtService;
        this.resolver = resolver;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        String method = request.getMethod();
        String[] exclude = {"/user/login", "POST", "/forum", "GET"};
        for (int i=0; i<exclude.length; i+=2) {
            if (exclude[i].equals(path) && method.equals(exclude[i+1])) return true;
        }
        String[] exclude2 = {"/swagger-ui", "/swagger-resources", "/v3/api-docs", "/rank"};
        for (String ex : exclude2) {
            if (path.startsWith(ex)) return true;
        }
        return false;
    }

    // 나중에 provider로 바꾸면 좋음
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String path = request.getRequestURI();
            String sub = jwtService.getUserSub();
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(sub);

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (BaseException exception) {
            resolver.resolveException(request, response, null, exception);
            return;
        } catch (UsernameNotFoundException exception) {
            resolver.resolveException(request, response, null, new BaseException(USER_NOT_FOUND));
            return;
        } catch (Exception exception) {
            resolver.resolveException(request, response, null, exception);
            return;
        }
        filterChain.doFilter(request, response);
    }
}