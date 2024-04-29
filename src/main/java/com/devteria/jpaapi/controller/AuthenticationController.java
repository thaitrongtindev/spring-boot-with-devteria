package com.devteria.jpaapi.controller;

import com.devteria.jpaapi.dto.request.ApiResponse;
import com.devteria.jpaapi.dto.request.AuthenticationRequest;
import com.devteria.jpaapi.dto.request.IntrospectRequest;
import com.devteria.jpaapi.dto.request.response.AuthenticationResponse;
import com.devteria.jpaapi.dto.request.response.IntrospectResponse;
import com.devteria.jpaapi.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        ApiResponse<AuthenticationResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(result);
        return apiResponse;
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        System.out.println("result token: " + result.isValid());

        ApiResponse<IntrospectResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(result);
        return apiResponse;
    }

}
