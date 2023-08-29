package com.spring.symptom.users.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UsersDto {

    private Long userId;
    private String email;
    private String password;
    private String name;
    private LocalDateTime created;
    private LocalDateTime updated;
}
