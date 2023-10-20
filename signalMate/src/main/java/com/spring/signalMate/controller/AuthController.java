package com.spring.signalMate.controller;

import com.spring.signalMate.dto.*;
import com.spring.signalMate.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signUp")
    public ResponseDto<?> signUp(@RequestBody SignUpDto requestBody) {
        ResponseDto<?> result = authService.signUp(requestBody);
        return result;
    }

    @PostMapping("/signIn")
    public ResponseDto<SignInResponseDto> signIn (@RequestBody SignInDto requestBody) {
        ResponseDto<SignInResponseDto> result = authService.signIn(requestBody);
        return result;
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String request) {
        authService.logout(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserUpddateDto userUpddateDto) {
        authService.updateUser(userId, userUpddateDto);
        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        ResponseDto<String> response = authService.deleteUser(userId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
