package com.example.userservice.service;

import com.example.userservice.controller.dto.orders.OrderResponseDto;
import com.example.userservice.controller.dto.users.UserResponseDto;
import com.example.userservice.controller.dto.users.UsersSignUpRequestDto;
import com.example.userservice.domain.users.Users;
import com.example.userservice.domain.users.UsersRepository;
import com.example.userservice.type.UsersRole;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UsersService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final ModelMapper strictMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + ": not found"));

        return new User(user.getEmail(), user.getEncryptedPwd(), new ArrayList<>());
    }

    public Long createUser(UsersSignUpRequestDto requestDto){
        Users user = strictMapper.map(requestDto, Users.class);
        user.setEncryptedPwd(bCryptPasswordEncoder.encode(requestDto.getPwd()));

        // 권한을 여러 개 추가
        Set<UsersRole> roles = new HashSet<>();
        roles.add(UsersRole.USER);
        user.setRoles(roles);

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

    public Users getUserByEmail(String email){
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + ": not found"));
    }

    public List<Users> getAllUsers(){
        return usersRepository.findAll();
    }
}
