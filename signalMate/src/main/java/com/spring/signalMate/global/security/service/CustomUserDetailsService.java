package com.spring.signalMate.global.security.service;

import com.spring.signalMate.domain.users.model.entity.UserEntity;
import com.spring.signalMate.domain.users.repository.UsersRepository;
import com.spring.signalMate.global.security.CustomUserDetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity users = usersRepository.findByEmail(username).orElse(null);
        if (users == null) {
            throw new UsernameNotFoundException("회원정보를 찾을 수 없습니다.");
        }
        return new CustomUserDetails(users);
    }
}
