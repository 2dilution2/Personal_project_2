package com.spring.signalMate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpddateDto {
    private String nickname;
    private String phoneNum;
    private String profile;
}