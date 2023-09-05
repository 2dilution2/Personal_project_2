package com.spring.signalMate.users.dto;

import com.spring.signalMate.users.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponseDto {
    public String token;
    public int expirTime;
    private UserEntity user;
}
