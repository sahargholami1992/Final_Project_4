package com.example.final_project_4.mapper;


import com.example.final_project_4.dto.ExpertRegisterDto;
import com.example.final_project_4.dto.ExpertResponseDto;
import com.example.final_project_4.entity.Expert;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface ExpertMapper {
    ExpertMapper INSTANCE = Mappers.getMapper(ExpertMapper.class);
    Expert convertsToEntity(ExpertRegisterDto dto);
    ExpertResponseDto convertsToResponse(Expert expert);
    Collection<ExpertResponseDto> convertsToDto(Collection<Expert> experts);
}
