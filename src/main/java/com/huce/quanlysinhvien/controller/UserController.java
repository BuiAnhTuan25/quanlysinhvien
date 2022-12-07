package com.huce.quanlysinhvien.controller;

import com.huce.quanlysinhvien.constains.RoleEnum;
import com.huce.quanlysinhvien.model.dto.UsersDto;
import com.huce.quanlysinhvien.model.request.UserSearchRequest;
import com.huce.quanlysinhvien.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "role", required = false) RoleEnum role,
            @RequestParam(value = "sort-by", required = false) String sortBy,
            @RequestParam("page") int page,
            @RequestParam("page-size") int pageSize
    ) {
        return new ResponseEntity<>(userService.search(new UserSearchRequest(name, username, role, sortBy), page, pageSize), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam("page") int page,
            @RequestParam("page-size") int pageSize
    ) {
        return new ResponseEntity<>(userService.getAll(page, pageSize), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createUser(
            @RequestBody UsersDto user
    ) throws MessagingException {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @RequestBody UsersDto user,
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(userService.updateUser(user, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }
}
