package com.spring.signalMate.config;

import com.spring.signalMate.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // cors정책 (Application에서 작업)
                .cors().and()
                // csrf (Cross Site Request Forge) 사이트간 요청 위조 -> 비활성화
                .csrf().disable()
                // Basic인증 사용여부 -> Bearer token 인증사용으로 비활성화
                .httpBasic().disable()
                // 세션기반 인증 -> 현재는 세션기반 인증 사용하지 않음
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                //"/", "/api/auth/" 모듈에 대해서는 모두 허용
                .authorizeRequests().antMatchers("/", "/api/auth/**").permitAll()
                // 나머지 요청에 대해서는 모두 인증된 사용자만 사용가능하게 함
                .anyRequest().authenticated();

        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
