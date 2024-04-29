package com.devteria.jpaapi.service;

import com.devteria.jpaapi.dto.request.AuthenticationRequest;
import com.devteria.jpaapi.dto.request.IntrospectRequest;
import com.devteria.jpaapi.dto.request.response.AuthenticationResponse;
import com.devteria.jpaapi.dto.request.response.IntrospectResponse;
import com.devteria.jpaapi.exception.ApiException;
import com.devteria.jpaapi.exception.ErrorCode;
import com.devteria.jpaapi.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class AuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    @Autowired
    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected  String SIGNER_KEY;

    // verify token
    public IntrospectResponse introspect(IntrospectRequest request) {
        var token = request.getToken();
        try {
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);
            var verified = signedJWT.verify(verifier); // tra ve true/false
            // true: verify thanhf cong , noi dung token khong bi thay doi
            System.out.println("verified" + verifier);

            // kiem tra token co het han ?
            Date expirtyTime = signedJWT.getJWTClaimsSet().getExpirationTime();
//            IntrospectResponse introspectResponse = new IntrospectResponse();
//            introspectResponse.isValid(verifier && expirtyTime.after(new Date()));
//            return IntrospectResponse.builder()
//                    .valid(verified && expirtyTime.after(new Date()))// het han sau thoi diem hien tai
//
//                    .build();
            System.out.println("expirtyTime: " + expirtyTime);
            return new IntrospectResponse(verified && expirtyTime.after(new Date()));

        } catch (JOSEException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    // xu ly dang nhapp
    // xu ly dang nhap
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Retrieve user from the database
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_EXSITED));

        // Debugging: Print user details and input credentials
        System.out.println("USER: " + user.toString());
        System.out.println("Request: " + request.getUsername() + " Password: " + request.getPassword());
        System.out.println("User: " + user.getUsername() + " Password: " + user.getPassword());

        // Check if passwords match
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        // Debugging: Print authentication result
        System.out.println("Authentication result: " + authenticated);

        // If not authenticated, throw an exception
        if (!authenticated) {
            throw new ApiException(ErrorCode.UNAUTHENTICATED);

        } else {
            // If authenticated, generate and return token
            var token = generateToken(request.getUsername());
            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setAuthenticated(true);
            authenticationResponse.setToken(token);
            return authenticationResponse;
        }
    }

    // method create token
    private String generateToken(String username) {
        // create header : header chứa nội dung thuật toán mà ta sử dụng để tạo ra mã token
        // có thuật toán rồi thì chung ta cần cái body sẽ gửi đi cacs token
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        // create payload
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder().subject(username).issuer("devteria").issueTime(new Date()).expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli())).claim("customClain", "Custom").build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        // kí cho token
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e.getMessage());
            throw new RuntimeException(e);
        }

    }


}
