package com.example.final_project_4.mapper;


import com.example.final_project_4.dto.SubServiceResponse;
import com.example.final_project_4.entity.SubService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface SubServicesMapper {
    SubServicesMapper INSTANCE = Mappers.getMapper(SubServicesMapper.class);
    Collection<SubServiceResponse> convertCollectionToDto(Collection<SubService> subServices);
}
