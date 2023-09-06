package com.spring.signalMate.entity;

import javax.persistence.*;

import com.spring.signalMate.dto.SignUpDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "User")
@Table(name = "User")
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

    private LocalDateTime created;

    private LocalDateTime updated;

    private String profile;

    public UserEntity(SignUpDto dto){
        this.email = dto.getUserEmail();
        this.password = dto.getUserPassword();
        this.nickname = dto.getUserNickname();
        this.phoneNum = dto.getUserPhoneNum();
    }
}
