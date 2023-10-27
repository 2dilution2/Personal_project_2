package com.spring.signalMate.domain.users.model.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.spring.signalMate.domain.users.model.dto.SignUpDto;
import com.spring.signalMate.domain.post.model.entity.PostEntity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue
    private Long userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    private String phoneNum;

    @Column(updatable = false)
    private LocalDateTime created;

    private LocalDateTime updated;

    private String profile;

    @PrePersist
    protected void onCreate() {
        this.created = LocalDateTime.now();
        this.updated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated = LocalDateTime.now();
    }

    @OneToMany(mappedBy="user", fetch=FetchType.LAZY)
    @JsonBackReference
    private List<PostEntity> posts;

    public UserEntity(SignUpDto dto){
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.nickname = dto.getNickname();
        this.phoneNum = dto.getPhoneNum();
    }
}
