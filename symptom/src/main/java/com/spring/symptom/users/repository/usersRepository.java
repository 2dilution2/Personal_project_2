package com.spring.symptom.users.repository;

import com.spring.symptom.users.entity.users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface usersRepository extends JpaRepository<users, Long> {
    users findByEmail(String email);
    boolean existsByEmail(String email);
}
