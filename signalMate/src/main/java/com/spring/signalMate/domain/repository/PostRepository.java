package com.spring.signalMate.repository;

import com.spring.signalMate.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    public List<PostEntity> findByOrderByCreatedDesc();
    public List<PostEntity> findByTitleContains(String boardTitle);
    Page<PostEntity> findByTitleContains(String title, Pageable pageable);
}
