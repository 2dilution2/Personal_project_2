package com.spring.signalMate.users.service;

import com.spring.signalMate.users.entity.UserEntity;
import com.spring.signalMate.users.repository.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UserDetails loadUserByUsername(String username) {
        UserEntity users = usersRepository.findByEmail(username);
        if (users == null) {
            throw new UsernameNotFoundException("회원정보를 찾을 수 없습니다.");
        }

        return new MyUserDetails(users);
    }
}
