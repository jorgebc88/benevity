package org.benevity.server.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.Url;
import org.benevity.server.repository.PostRepository;
import org.benevity.server.repository.entity.Post;
import org.benevity.server.repository.entity.User;
import org.benevity.server.service.exception.ImageNotValidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    private static final String ID = "postId";
    private static final String USER_ID = "userId";
    @Mock
    private PostRepository postRepository;
    @Mock
    private UserService userService;
    @Mock
    private Cloudinary cloudinary;
    @Mock
    private Uploader uploader;
    @Mock
    private Url url;

    private PostService postService;

    @BeforeEach
    void setup() {
        postService = new PostService(postRepository, userService, cloudinary);
    }

    @Test
    void upload_new_image_to_post() throws IOException {
        Post post = createPost();
        given(postRepository.findById(ID)).willReturn(Optional.of(post));

        given(cloudinary.uploader()).willReturn(uploader);
        given(uploader.upload(any(), any())).willReturn(Map.of());

        given(cloudinary.url()).willReturn(url);
        given(url.transformation(any())).willReturn(url);
        given(url.generate(ID)).willReturn("URL");

        given(postRepository.save(any())).willReturn(post);

        String url = this.postService.uploadImage(ID, new MockMultipartFile("name", "content".getBytes()));
        Assertions.assertEquals("URL", url);
    }

    @Test
    void upload_new_image_to_post_cloudinary_error() {
        Post post = createPost();
        given(postRepository.findById(ID)).willReturn(Optional.of(post));

        given(cloudinary.uploader()).willAnswer(invocation -> {throw new IOException("IOException");});

        assertThrows(ImageNotValidException.class, () -> this.postService.uploadImage(ID, new MockMultipartFile("name", "content".getBytes())));
    }

    @Test
    void remove_image_from_post() {
        Post post = createPost();
        post.setImageUrl("imageUrl");
        given(postRepository.findById(ID)).willReturn(Optional.of(post));

        given(postRepository.save(post)).willReturn(post);

        this.postService.deletePostImage(ID, USER_ID);

        verify(postRepository).findById(ID);
        verify(postRepository).save(post);
    }

    private Post createPost() {
        Post post = new Post();
        post.setId(ID);
        post.setName("postName");
        post.setTitle("postTitle");
        post.setContent("postContent");
        post.setSlug("postTitle");
        post.setUser(createUser());
        post.setDate(LocalDateTime.of(2022, 11, 21, 20, 0));

        return post;
    }

    private User createUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email");

        return user;
    }
}
