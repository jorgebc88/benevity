package org.benevity.server.controller.mapper;

import org.benevity.server.openapi.model.PostRequest;
import org.benevity.server.openapi.model.PostResponse;
import org.benevity.server.repository.entity.Post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.Locale.ENGLISH;

public class PostMapper {

    public static final String DATE_TIME_FORMAT_PATTERN = "E, dd MMM yyyy, HH:mm:ss a"; //Sun, 04 Jun 2023, 20:24:18 PM

    public static Post toPost(PostRequest postRequest) {
        Post post = new Post();
        post.setName(postRequest.getName());
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setDate(LocalDateTime.now());

        return post;
    }

    public static PostResponse toPostResponse(Post post) {
        PostResponse postResponse = new PostResponse();
        postResponse.setId(post.getId());
        postResponse.setContent(post.getContent());
        postResponse.setName(post.getName());
        postResponse.setTitle(post.getTitle());
        postResponse.setSlug(post.getSlug());
        postResponse.setDateAdded(post.getDate());
        postResponse.setDateAddedFormatted(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_PATTERN).withLocale(ENGLISH).format(post.getDate()));
        postResponse.setImageUrl(post.getImageUrl());
        postResponse.setUser(UserMapper.toUserDTO(post.getUser()));

        return postResponse;
    }

    public static List<PostResponse> toPostResponseList(List<Post> postList) {
        return postList.stream().map(PostMapper::toPostResponse).toList();
    }
}
