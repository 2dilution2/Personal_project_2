package com.spring.signalMate.service;

import com.spring.signalMate.dto.ResponseDto;
import com.spring.signalMate.entity.PostEntity;
import com.spring.signalMate.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    public ResponseDto<List<PostEntity>> getList() {
        List<PostEntity> postList = new ArrayList<PostEntity>();

        try {
            postList = postRepository.findByOrderByCreatedDesc();
        } catch (Exception e) {
            log.error("Error during signIn process", e);
            return ResponseDto.setFailed("데이터베이스 오류");
        }
        return ResponseDto.setSuccess("Success", postList);
    }

    public ResponseDto<List<PostEntity>> getSearchList(String title){
        List<PostEntity> boardList = new ArrayList<PostEntity>();

        try {
            boardList = postRepository.findByTitleContains(title);
        } catch (Exception e) {
            log.error("Error during signIn process", e);
            return ResponseDto.setFailed("데이터베이스 오류");
        }

        return ResponseDto.setSuccess("Success", boardList);

    }

    public ResponseDto<PostEntity> createPost(PostEntity boardEntity) {
        try {
            PostEntity createdPost = postRepository.save(boardEntity);
            return ResponseDto.setSuccess("Post created successfully", createdPost);
        } catch (Exception e) {
            log.error("Error during signIn process", e);
            return ResponseDto.setFailed("Database Error");
        }
    }

    public ResponseDto<PostEntity> updatePost(Long postId, PostEntity boardEntity) {
        try {
            if (!postRepository.existsById(postId)) {
                return ResponseDto.setFailed("Post not found");
            }
            boardEntity.setPostId(postId);
            PostEntity updatedPost = postRepository.save(boardEntity);
            return ResponseDto.setSuccess("Post updated successfully", updatedPost);
        } catch (Exception e) {
            log.error("Error during signIn process", e);
            return ResponseDto.setFailed("Database Error");
        }
    }

    public ResponseDto<String> deletePost(Long postId) {
        try {
            postRepository.deleteById(postId);
            return ResponseDto.setSuccess("Post deleted successfully", null);
        } catch (Exception e) {
            log.error("Error during signIn process", e);
            return ResponseDto.setFailed("Database Error");
        }
    }
}
