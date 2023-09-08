package com.spring.signalMate.controller;

import com.spring.signalMate.dto.PostDto;
import com.spring.signalMate.dto.ResponseDto;
import com.spring.signalMate.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/new")
    public ResponseDto<?> post(@RequestBody PostDto requestBody) {
        ResponseDto<?> result = postService.createPost(requestBody);
        return result;
    }

    @PostMapping("/update/{postId}")
    public ResponseDto<?> updatePost(@PathVariable Long postId, @RequestBody PostDto requestBody) {
        ResponseDto<?> result = postService.updatePost(postId, requestBody);
        return result;
    }

    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }
}
