package org.benevity.server.controller;

import org.benevity.server.config.security.MyUserDetails;
import org.benevity.server.openapi.api.PostsApi;
import org.benevity.server.openapi.model.PostRequest;
import org.benevity.server.openapi.model.PostResponse;
import org.benevity.server.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.benevity.server.controller.mapper.PostMapper.toPost;
import static org.benevity.server.controller.mapper.PostMapper.toPostResponse;
import static org.benevity.server.controller.mapper.PostMapper.toPostResponseList;

@RestController
public class PostController extends Controller implements PostsApi  {
    PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Override
    public ResponseEntity<PostResponse> addPost(PostRequest postRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toPostResponse(this.postService.addPost(toPost(postRequest), getUserLoggedId())));
    }

    @Override
    public ResponseEntity<Void> deletePost(String id) {
        this.postService.deletePost(id, getUserLoggedId());
        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<PostResponse> getPost(String id) {
        return ResponseEntity.ok(toPostResponse(this.postService.getPost(id)));
    }

    @Override
    public ResponseEntity<List<PostResponse>> getPostList() {
        return ResponseEntity.ok(toPostResponseList(this.postService.getPostList()));
    }

    @Override
    public ResponseEntity<String> uploadPostImage(String id, MultipartFile image) {
        return ResponseEntity.ok(this.postService.uploadImage(id, image));
    }

    @Override
    public ResponseEntity<Void> deletePostImage(String id) {
        this.postService.deletePostImage(id, getUserLoggedId());
        return ResponseEntity.ok(null);
    }
}

