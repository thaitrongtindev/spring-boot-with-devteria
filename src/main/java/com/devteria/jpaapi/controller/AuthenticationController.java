package com.devteria.jpaapi.controller;

import com.devteria.jpaapi.dto.request.ApiResponse;
import com.devteria.jpaapi.dto.request.AuthenticationRequest;
import com.devteria.jpaapi.dto.request.response.AuthenticationResponse;
import com.devteria.jpaapi.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        boolean result = authenticationService.authenticate(request);
        ApiResponse apiResponse = new ApiResponse();
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAuthenticated(result);
        apiResponse.setResult(authenticationResponse);
        return apiResponse;
    }

}
