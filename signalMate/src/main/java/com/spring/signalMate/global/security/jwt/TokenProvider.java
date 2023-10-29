package com.spring.signalMate.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.spring.signalMate.global.security.jwt.model.RefreshToken;
import com.spring.signalMate.global.security.jwt.repository.RefreshTokenRepository;
import com.spring.signalMate.global.security.service.CustomUserDetailsService;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30L;            // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60L * 24 * 7;      // 5일

    // Jwt 생성 및 검증을 위한 키
    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final CustomUserDetailsService userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostConstruct
    protected void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    private static Map<String, Object> createJWTHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        return header;
    }

    // Jwt 생성하는 메서드
    public String createAccessToken(Long userId, String email) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId); // Custom claim으로 userId를 추가합니다.
        claims.setSubject(email);     // 이메일을 subject로 설정합니다.

        // 만료날짜를 현재날짜 + 1시간으로 설정
        Date now = new Date();

        // Jwt를 생성
        return Jwts.builder()
            // 암호화에 사용될 알고리즘, 키
            .signWith(key,SignatureAlgorithm.HS512)
            // Jwt제목
            .setSubject(userId.toString())  // setSubject 메서드를 사용하여 userId 저장
            // Jwt 헤더 설정
            .setHeader(createJWTHeader())
            // 토큰에 UserId와 email 넣기
            .setClaims(claims)
            // Jwt생성일
            .setIssuedAt(now)
            // Jwt만료일
            .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME))
            // 생성
            .compact();
    }

    // Jwt 검증 및 userId 추출
    public Long extractUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

    // Redis에 Refresh토큰 저장
    public void saveRefreshTokenInRedis(String token, String email) {
        RefreshToken refreshToken = RefreshToken.builder()
            .id(email)
            .token(token)
            .expiration(REFRESH_TOKEN_EXPIRE_TIME)
            .build();
        refreshTokenRepository.save(refreshToken);
    }

}
