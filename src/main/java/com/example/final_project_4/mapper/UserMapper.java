package com.example.final_project_4.mapper;


import com.example.final_project_4.dto.SearchUserResponse;
import com.example.final_project_4.entity.BaseUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    List<SearchUserResponse> convertToDto(List<BaseUser> users);

}
