package com.example.userservice.service;

import com.example.userservice.controller.dto.orders.OrdersResponseDto;
import com.example.userservice.controller.dto.users.UsersResponseDto;
import com.example.userservice.controller.dto.users.UsersSignUpRequestDto;
import com.example.userservice.domain.users.Users;
import com.example.userservice.domain.users.UsersRepository;
import com.example.userservice.type.UsersRole;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    private final RestTemplate restTemplate;

    @Value("${url.order-service}")
    private String orderUrl;

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

    public UsersResponseDto getUserById(Long userId){
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UsersResponseDto userResponseDto =  strictMapper.map(user, UsersResponseDto.class);

        // RestTemplate restTemplate
        orderUrl = String.format(orderUrl, userId);
        System.out.println("orderUrl = " + orderUrl);

        ResponseEntity<List<OrdersResponseDto>> responseEntityOrders =
                restTemplate.exchange(
                        orderUrl,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<OrdersResponseDto>>() {}
                );

        List<OrdersResponseDto> orders = responseEntityOrders.getBody();

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
