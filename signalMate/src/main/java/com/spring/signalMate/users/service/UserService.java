package com.spring.signalMate.users.service;

import com.spring.signalMate.users.dto.UsersDto;
import com.spring.signalMate.users.entity.Users;
import com.spring.signalMate.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public Users register(UsersDto usersDto) {
        if (usersRepository.existsByEmail(usersDto.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일 입니다.");
        }

        Users newusers = new Users();
        newusers.setEmail(usersDto.getEmail());
        newusers.setPassword(passwordEncoder.encode(usersDto.getPassword()));
        newusers.setName(usersDto.getName());

        return usersRepository.save(newusers);
    }

    // 회원정보수정
    public void update(Long id, UsersDto usersDto) {
        Users existUser = usersRepository.findById(id).orElseThrow(() -> new RuntimeException("회원정보를 찾을 수 없습니다."));

        existUser.setEmail(usersDto.getEmail());
        existUser.setName(usersDto.getName());

        usersRepository.save(existUser);
    }

    // 회원정보 삭제
    public void delete(Long id) {
        if (!usersRepository.existsById(id)) {
            throw new RuntimeException("회원정보를 찾을 수 없습니다.");
        }

        usersRepository.deleteById(id);
    }
}
