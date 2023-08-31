package com.spring.signalMate.users.controller;

import com.spring.signalMate.security.JwtTokenProvider;
import com.spring.signalMate.security.payload.JwtAuthenticationResponse;
import com.spring.signalMate.security.payload.LoginRequest;
import com.spring.signalMate.users.dto.UsersDto;
import com.spring.signalMate.users.entity.Users;
import com.spring.signalMate.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public Users register(@RequestBody UsersDto usersDto) {
        return usersService.register(usersDto);
    }

    @PutMapping("/{userId}")
    public void update(@PathVariable Long userId, @RequestBody UsersDto userDto) {
        usersService.update(userId, userDto);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        usersService.delete(userId);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }
}
