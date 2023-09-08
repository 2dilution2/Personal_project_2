package com.spring.signalMate.service;

import com.spring.signalMate.dto.PostDto;
import com.spring.signalMate.dto.ResponseDto;
import com.spring.signalMate.entity.PostEntity;
import com.spring.signalMate.entity.UserEntity;
import com.spring.signalMate.repository.PostRepository;
import com.spring.signalMate.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UsersRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(PostService.class);

    private PostEntity convertDtoToEntity(PostDto postDto) {
        PostEntity postEntity = new PostEntity();

        UserEntity userEntity = userRepository.findByEmail(postDto.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("아이디가 " + postDto.getUserId() + "인 사용자를 찾을 수 없습니다."));

        postEntity.setUser(userEntity);
        postEntity.setTitle(postDto.getTitle());
        postEntity.setContent(postDto.getContent());
        postEntity.setSymptom(postDto.getSymptom());

        return postEntity;
    }

    private PostDto convertEntityToDto(PostEntity postEntity) {
        PostDto postDto = new PostDto();
        postDto.setTitle(postEntity.getTitle());
        postDto.setContent(postEntity.getContent());
        postDto.setSymptom(postEntity.getSymptom());
        return postDto;
    }

    public ResponseDto<List<PostDto>> getList() {
        List<PostEntity> postList = new ArrayList<>();
        List<PostDto> postDtos = new ArrayList<>();

        try {
            postList = postRepository.findByOrderByCreatedDesc();
            for (PostEntity postEntity : postList) {
                postDtos.add(convertEntityToDto(postEntity));
            }
        } catch (Exception e) {
            log.error("Error during getList process", e);
            return ResponseDto.setFailed("데이터베이스 오류");
        }
        return ResponseDto.setSuccess("Success", postDtos);
    }

    public ResponseDto<List<PostDto>> getSearchList(String title){
        List<PostEntity> boardList = new ArrayList<>();
        List<PostDto> postDtos = new ArrayList<>();

        try {
            boardList = postRepository.findByTitleContains(title);
            for (PostEntity postEntity : boardList) {
                postDtos.add(convertEntityToDto(postEntity));
            }
        } catch (Exception e) {
            log.error("Error during getSearchList process", e);
            return ResponseDto.setFailed("데이터베이스 오류");
        }

        return ResponseDto.setSuccess("Success", postDtos);
    }

    public ResponseDto<PostDto> createPost(PostDto postDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        postDto.setUserId(currentUserEmail);

        try {
            PostEntity postEntity = convertDtoToEntity(postDto);
            PostEntity createdPost = postRepository.save(postEntity);

            return ResponseDto.setSuccess("Post created successfully", convertEntityToDto(createdPost));
        } catch (Exception e) {
            log.error("Error during createPost process", e);
            return ResponseDto.setFailed("데이터베이스 오류");
        }
    }


    public ResponseDto<PostDto> updatePost(Long postId, PostDto postDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        postDto.setUserId(currentUserEmail);

        try {
            if (!postRepository.existsById(postId)) {
                return ResponseDto.setFailed("Post not found");
            }
            PostEntity postEntity = convertDtoToEntity(postDto);
            postEntity.setPostId(postId);
            PostEntity updatedPost = postRepository.save(postEntity);
            return ResponseDto.setSuccess("Post updated successfully", convertEntityToDto(updatedPost));
        } catch (Exception e) {
            log.error("Error during updatePost process", e);
            return ResponseDto.setFailed("데이터베이스 오류");
        }
    }

    public ResponseDto<String> deletePost(Long postId) {
        try {
            postRepository.deleteById(postId);
            return ResponseDto.setSuccess("Post deleted successfully", null);
        } catch (Exception e) {
            log.error("Error during deletePost process", e);
            return ResponseDto.setFailed("데이터베이스 오류");
        }
    }
}
