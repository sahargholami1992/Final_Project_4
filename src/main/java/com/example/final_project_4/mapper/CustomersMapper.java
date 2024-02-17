package com.example.final_project_4.mapper;


import com.example.final_project_4.dto.CustomerRegisterDto;
import com.example.final_project_4.dto.CustomerResponse;
import com.example.final_project_4.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomersMapper {
    CustomersMapper INSTANCE = Mappers.getMapper(CustomersMapper.class);
    Customer convertToEntity(CustomerRegisterDto dto);
    CustomerResponse convertToDto(Customer customer);
}
