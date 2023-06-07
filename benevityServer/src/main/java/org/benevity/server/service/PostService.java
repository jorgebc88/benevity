package org.benevity.server.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.benevity.server.repository.PostRepository;
import org.benevity.server.repository.entity.Post;
import org.benevity.server.service.exception.ImageNotValidException;
import org.benevity.server.service.exception.OperationNotAllowedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

@Service
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final Cloudinary cloudinary;

    @Autowired
    PostService(PostRepository postRepository, UserService userService, Cloudinary cloudinary) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.cloudinary = cloudinary;
    }

    public Post getPost(String id) {
        return this.postRepository.findById(id).orElseThrow();
    }

    public List<Post> getPostList() {
        return this.postRepository.findByOrderByDateDesc();
    }

    public Post addPost(Post post, String userId) {
        post.setUser(this.userService.getUser(userId));
        post.setSlug(this.slugGenerator(post.getTitle()));
        return this.postRepository.save(post);
    }

    public void deletePost(String postId, String userId) {
        Post postToDelete = getPost(postId);
        if (!postToDelete.getUser().getId().equals(userId)) {
            throw new OperationNotAllowedException(format("User %s not allowed to delete post %s", userId, postId));
        }
        this.postRepository.deleteById(postId);
    }

    public String uploadImage(String postId, MultipartFile image) {
        Post post = this.getPost(postId);
        try {
            cloudinary.uploader().upload(image.getBytes(), ObjectUtils.asMap("public_id", postId));
        } catch (IOException exception) {
            throw new ImageNotValidException(exception.getMessage());
        }
        String url = cloudinary.url().transformation(new Transformation().width(500).height(300).crop("fill")).generate(postId);
        post.setImageUrl(url);
        this.postRepository.save(post);
        return url;
    }

    public void deletePostImage(String postId, String userId) {
        Post post = this.getPost(postId);
        if (!post.getUser().getId().equals(userId)) {
            throw new OperationNotAllowedException(format("User %s not allowed to delete post %s", userId, postId));
        }
        post.setImageUrl(null);
        this.postRepository.save(post);
    }

    private String slugGenerator(String name) {
        return name.replace(" ", "-").replace("'", "");
    }
}
