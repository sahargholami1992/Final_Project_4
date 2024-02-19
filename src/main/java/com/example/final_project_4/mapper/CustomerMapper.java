package com.example.final_project_4.mapper;


import com.example.final_project_4.dto.CustomerRegisterDto;
import com.example.final_project_4.dto.CustomerResponse;
import com.example.final_project_4.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    Customer convertsToEntity(CustomerRegisterDto dto);
    CustomerResponse convertsToDto(Customer customer);
}
