package com.example.final_project_4.mapper;


import com.example.final_project_4.dto.SearchUserResponse;
import com.example.final_project_4.entity.BaseUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsersMapper {
    UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);
    List<SearchUserResponse> convertsToDto(List<BaseUser> users);

}
