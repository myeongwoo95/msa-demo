package com.example.userservice.service;

import com.example.userservice.controller.dto.orders.OrderResponseDto;
import com.example.userservice.controller.dto.users.UserResponseDto;
import com.example.userservice.controller.dto.users.UsersSignUpRequestDto;
import com.example.userservice.domain.users.Users;
import com.example.userservice.domain.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final ModelMapper strictMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long createUser(UsersSignUpRequestDto requestDto){
        Users user = strictMapper.map(requestDto, Users.class);
        user.setEncryptedPwd(bCryptPasswordEncoder.encode(requestDto.getPwd()));
        usersRepository.save(user);
        return user.getUserId();
    }

    public UserResponseDto getUserById(Long userId){
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserResponseDto userResponseDto =  strictMapper.map(user, UserResponseDto.class);

        List<OrderResponseDto> orders = new ArrayList<>();
        userResponseDto.setOrders(orders);

        return userResponseDto;
    }

    public List<Users> getAllUsers(){
        return usersRepository.findAll();
    }
}
