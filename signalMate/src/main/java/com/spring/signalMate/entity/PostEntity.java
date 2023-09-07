package com.spring.signalMate.entity;

import javax.persistence.*;

import com.spring.signalMate.constant.Symptom;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Symptom symptom;

    private LocalDateTime created;

    private LocalDateTime updated;

}
