package com.spring.signalMate.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.spring.signalMate.constant.Symptom;
import com.spring.signalMate.dto.PostDto;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "post")
@Table(name = "post")
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
    @JsonManagedReference
    private UserEntity user;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Symptom symptom;

    @Column(updatable = false)
    private LocalDateTime created;

    private LocalDateTime updated;

    @PrePersist
    protected void onCreate() {
        this.created = LocalDateTime.now();
        this.updated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated = LocalDateTime.now();
    }

    public PostEntity(PostDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.symptom = dto.getSymptom();
    }
}
