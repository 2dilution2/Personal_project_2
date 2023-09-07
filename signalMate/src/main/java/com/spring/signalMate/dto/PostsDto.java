package com.spring.signalMate.dto;

import com.spring.signalMate.constant.Symptom;

import java.time.LocalDateTime;

public class PostsDto {

    private Long postId;
    private Long userId;
    private String title;
    private String context;
    private Symptom symptom;
    private LocalDateTime created;
    private LocalDateTime updated;
}
