package com.example.final_project_4.mapper;


import com.example.final_project_4.dto.SubServiceResponse;
import com.example.final_project_4.entity.SubService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface SubServiceMapper {
    SubServiceMapper INSTANCE = Mappers.getMapper(SubServiceMapper.class);
    Collection<SubServiceResponse> convertCollectionsToDto(Collection<SubService> subServices);
}
