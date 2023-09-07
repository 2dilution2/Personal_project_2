package com.spring.signalMate.service;

import com.spring.signalMate.dto.ResponseDto;
import com.spring.signalMate.dto.SignInDto;
import com.spring.signalMate.dto.SignInResponseDto;
import com.spring.signalMate.dto.SignUpDto;
import com.spring.signalMate.entity.UserEntity;
import com.spring.signalMate.security.TokenProvider;
import com.spring.signalMate.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AuthService {

    @Autowired
    UsersRepository userRepository;

    @Autowired
    TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public ResponseDto<?> signUp(SignUpDto dto) {
        String userEmail = dto.getEmail();
        String userPassword = dto.getPassword();
        String userPasswordCheck = dto.getPasswordCheck();

        // 이메일 중복확인
        try {
            if (userRepository.existsByEmail(userEmail)) {
                return ResponseDto.setFailed("이미 존재하는 이메일 입니다.");
            }
        } catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 에러.");
        }

        // 비밀번호 확인 일치여부 확인
        if (!userPassword.equals(userPasswordCheck)){
            return ResponseDto.setFailed("비밀번호가 일치하지 않습니다.");
        }

        // UserEntity생성
        UserEntity userEntity = new UserEntity(dto);

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userPassword);
        userEntity.setPassword(encodedPassword);

        // 회원정보 저장
        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 에러.");
        }

        // 회원가입 완료
        return ResponseDto.setSuccess("회원가입이 정상적으로 완료되었습니다.", null);
    }

    public ResponseDto<SignInResponseDto> signIn(SignInDto dto) {
        String userEmail = dto.getEmail();
        String userPassword = dto.getPassword();
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findByEmail(userEmail);
            if (userEntity == null) return ResponseDto.setFailed("이메일이 일치하지 않습니다.");
            if (!passwordEncoder.matches(userPassword, userEntity.getPassword()))
                return ResponseDto.setFailed("비밀번호가 일치하지 않습니다.");
        } catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 오류");
        }
        userEntity.setPassword("");

        String token = tokenProvider.create(userEmail);
        int expirTime = 3600000;

        SignInResponseDto signInResponseDto = new SignInResponseDto(token, expirTime, userEntity);
        return ResponseDto.setSuccess("로그인에 성공하였습니다.", signInResponseDto);
    }
}
