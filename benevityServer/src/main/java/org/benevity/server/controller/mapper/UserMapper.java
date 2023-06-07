package org.benevity.server.controller.mapper;

import org.benevity.server.openapi.model.UserDTO;
import org.benevity.server.repository.entity.User;

import java.util.List;

public class UserMapper {
    public static User toUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());

        return user;
    }

    public static UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        Integer postsCreated = user.getPosts() != null ? user.getPosts().size() : 0;
        userDTO.setPostsPosted(postsCreated);

        return userDTO;
    }

    public static List<UserDTO> toUserDTOList(List<User> userList) {
        return userList.stream().map(UserMapper::toUserDTO).toList();
    }
}
