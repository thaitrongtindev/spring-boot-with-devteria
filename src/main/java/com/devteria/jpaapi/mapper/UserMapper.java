package com.devteria.jpaapi.mapper;

import com.devteria.jpaapi.dto.request.UserCreationRequest;
import com.devteria.jpaapi.dto.request.UserUpdateRequest;
import com.devteria.jpaapi.dto.request.response.UserResponse;
import com.devteria.jpaapi.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest  request);
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
    //@MappingTarget:sử dụng làm mục tiêu của ánh xạ.
//, dòng code này định nghĩa một phương thức để cập nhật thông tin của
// một đối tượng User dựa trên các thông tin mới từ một đối tượng UserUpdateRequest.
    UserResponse toUserResponse(User user); // map tu User(entity) ve UserResponse)
}
