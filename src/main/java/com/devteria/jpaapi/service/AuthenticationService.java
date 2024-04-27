package com.devteria.jpaapi.service;

import com.devteria.jpaapi.dto.request.AuthenticationRequest;
import com.devteria.jpaapi.exception.ApiException;
import com.devteria.jpaapi.exception.ErrorCode;
import com.devteria.jpaapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    UserRepository userRepository;
    public boolean authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new ApiException(ErrorCode.USER_NOT_EXSITED)
        );
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        //request.getPassword(): password nhap tu nguoi dung, user.getPassword(): lay password tu db -> dem so sanh
        return passwordEncoder.matches(request.getPassword(), user.getPassword());

    }
}
