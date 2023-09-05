package com.spring.signalMate.users.dto;

import com.spring.signalMate.users.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatchUserResponseDto {
    private UserEntity user;
}
