package com.spring.signalMate.service;

import com.spring.signalMate.entity.UserEntity;
import com.spring.signalMate.repository.UsersRepository;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity users = usersRepository.findByEmail(username).orElse(null);
        if (users == null) {
            throw new UsernameNotFoundException("회원정보를 찾을 수 없습니다.");
        }
        return new MyUserDetails(users);
    }
}
