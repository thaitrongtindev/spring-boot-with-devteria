    package com.devteria.jpaapi.service;

    import com.devteria.jpaapi.dto.request.UserCreationRequest;
    import com.devteria.jpaapi.dto.request.UserUpdateRequest;
    import com.devteria.jpaapi.dto.request.response.UserResponse;
    import com.devteria.jpaapi.entity.User;
    import com.devteria.jpaapi.exception.ApiException;
    import com.devteria.jpaapi.exception.ErrorCode;
    import com.devteria.jpaapi.mapper.UserMapper;
    import com.devteria.jpaapi.repository.UserRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    public class UserService {

        @Autowired
        UserRepository userRepository;
        @Autowired
        UserMapper userMapper;
        public User createUser(UserCreationRequest request) {

            if (userRepository.existsByUsername(request.getUsername()))
                throw  new ApiException(ErrorCode.USER_EXSITED);
//            user.setUsername(request.getUsername());
//            user.setPassword(request.getPassword());
//            user.setFirstName(request.getFirstName());
//            user.setLastName(request.getLastName());
//            user.setDob(request.getDob());

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            User user = userMapper.toUser(request);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user);
            return  user;

        }

        public List<User> getUsers() {
            return userRepository.findAll();
        }

        public UserResponse getUserById(Long id) {
            return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
        }

        public UserResponse updateUserById(UserUpdateRequest request, Long userId) {
            User user = userRepository.findById(userId).orElseThrow();
            if (user != null) {
//                user.setFirstName(request.getFirstName());
//                user.setPassword(request.getPassword());
//                user.setDob(request.getDob());
//                user.setLastName(request.getLastName());
//                return userRepository.save(user);
                userMapper.updateUser(user, request);
                return userMapper.toUserResponse(userRepository.save(user));
            } else {
                return null;
            }
        }


        public void deleteUserById(Long id) {
            userRepository.deleteById(id);
        }
    }
