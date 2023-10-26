package com.spring.signalMate.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider {
    // Jwt 생성 및 검증을 위한 키
    private static final String SECURITY_KEY = "jwtseckey!@";

    // Jwt 생성하는 메서드
    public String create(Long userId) {
        // 만료날짜를 현재날짜 + 1시간으로 설정
        Date expirDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));

        // Jwt를 생성
        return Jwts.builder()
                // 암호화에 사용될 알고리즘, 키
                .signWith(SignatureAlgorithm.HS512, SECURITY_KEY)
                // Jwt제목
                .setSubject(userId.toString())  // setSubject 메서드를 사용하여 userId 저장
                // Jwt생성일
                .setIssuedAt(new Date())
                // Jwt만료일
                .setExpiration(expirDate)
                // 생성
                .compact();
    }

    // Jwt 검증 및 userId 추출
    public Long extractUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECURITY_KEY).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

}
