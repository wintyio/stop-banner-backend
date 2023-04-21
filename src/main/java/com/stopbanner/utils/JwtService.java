package com.stopbanner.utils;

import com.stopbanner.config.BaseResponseStatus;
import com.stopbanner.config.secret.Secret;
import io.jsonwebtoken.*;
import com.stopbanner.config.BaseException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;

@Component
public class JwtService {

    public String createJwt(String sub) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("sub", sub)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24*365*10)) //1*(1000*60*60*24*365)))
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_SECRET_KEY)
                .compact();
    }

    public String getJwt() {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("Authorization");
    }

    public String getUserSub() throws BaseException {
        // JWT 추출
        String accessToken = getJwt();
        if (accessToken == null || accessToken.length() == 0) {
            throw new BaseException(BaseResponseStatus.EMPTY_JWT);
        }
        try {
            accessToken = accessToken.split("Bearer ")[1];
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.REQUEST_JWT_ERROR);
        }

        // JWT parsing
        Jws<Claims> claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new BaseException(BaseResponseStatus.INVALID_JWT);
        }

        // userIdx 추출
        return claims.getBody().get("sub", String.class);  // jwt 에서 userIdx를 추출합니다.
    }
}
