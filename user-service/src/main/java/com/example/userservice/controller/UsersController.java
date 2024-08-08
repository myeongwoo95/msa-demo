package com.example.userservice.controller;

import com.example.userservice.controller.dto.users.UsersResponseDto;
import com.example.userservice.controller.dto.users.UsersSignUpRequestDto;
import com.example.userservice.domain.users.Users;
import com.example.userservice.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UsersController {

    private final UsersService usersService;
    private final ModelMapper modelMapper;

    @PostMapping("/users")
    public ResponseEntity<Long> createUser(@RequestBody UsersSignUpRequestDto requestDto) {
        Long userId = usersService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UsersResponseDto>> getAllUsers() {
        Iterable<Users> users = usersService.getAllUsers();

        List<UsersResponseDto> result = new ArrayList<>();
        users.forEach(v -> {
            result.add(modelMapper.map(v, UsersResponseDto.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UsersResponseDto> getUserById(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.getUserById(userId));
    }


}
