package org.benevity.server.controller;

import org.benevity.server.openapi.api.UsersApi;
import org.benevity.server.openapi.model.UserDTO;
import org.benevity.server.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.benevity.server.controller.PostController.getUserLoggedId;
import static org.benevity.server.controller.mapper.UserMapper.toUser;
import static org.benevity.server.controller.mapper.UserMapper.toUserDTO;
import static org.benevity.server.controller.mapper.UserMapper.toUserDTOList;

@RestController
public class UserController implements UsersApi {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<UserDTO> addUser(UserDTO user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toUserDTO(this.userService.addUser(toUser(user))));
    }

    @Override
    public ResponseEntity<UserDTO> getUser(String userId) {
        return ResponseEntity.ok(toUserDTO(this.userService.getUser(userId)));
    }

    @Override
    public ResponseEntity<List<UserDTO>> getUserList() {
        return ResponseEntity.ok(toUserDTOList(this.userService.getUserList()));
    }

    @Override
    public ResponseEntity<UserDTO> updateUser(String userId, UserDTO user) {
        return ResponseEntity.status(HttpStatus.OK).body(toUserDTO(this.userService.updateUser(userId, toUser(user))));
    }

    @Override
    public ResponseEntity<Void> deleteUser(String userId) {
        this.userService.deleteUser(userId, getUserLoggedId());
        return ResponseEntity.ok(null);
    }
}
