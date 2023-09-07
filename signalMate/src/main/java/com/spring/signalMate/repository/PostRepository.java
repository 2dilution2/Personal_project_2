package com.spring.signalMate.repository;

import com.spring.signalMate.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    public List<PostEntity> findByOrderByPostCreatedDesc();

    public List<PostEntity> findByBoardTitleContains(String boardTitle);
}
