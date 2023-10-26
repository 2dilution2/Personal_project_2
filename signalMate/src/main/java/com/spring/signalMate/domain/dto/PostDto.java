package com.spring.signalMate.domain.dto;

import com.spring.signalMate.domain.constant.Symptom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Long postId;
    private String userId;
    private String title;
    private String content;
    private Symptom symptom;
    private LocalDateTime created;
    private LocalDateTime updated;
}
