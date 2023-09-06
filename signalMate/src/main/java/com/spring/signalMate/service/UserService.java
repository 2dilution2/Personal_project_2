package com.spring.signalMate.service;

import com.spring.signalMate.dto.PatchUserDto;
import com.spring.signalMate.dto.PatchUserResponseDto;
import com.spring.signalMate.dto.ResponseDto;
import com.spring.signalMate.entity.UserEntity;
import com.spring.signalMate.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UsersRepository usersRepository;

    public ResponseDto<PatchUserResponseDto> patchUser(PatchUserDto dto, String userEmail){
        UserEntity userEntity;
        String userNickname = dto.getUserNickname();
        String userProfile = dto.getUserProfile();

        try {
            userEntity = usersRepository.findByEmail(userEmail);
            if (userEntity == null) return ResponseDto.setFailed("존재하지않는 이용자입니다.");

            userEntity.setNickname(userNickname);
            userEntity.setProfile(userProfile);

            usersRepository.save(userEntity);
        } catch (Exception e) {
            return ResponseDto.setFailed("데이터베이스 오류");
        }

        userEntity.setPassword("");

        PatchUserResponseDto patchUserResponseDto = new PatchUserResponseDto(userEntity);

        return ResponseDto.setSuccess("Success", patchUserResponseDto);
    }
}
