package com.example.final_project_4.mapper;

import com.example.final_project_4.dto.OrderResponse;
import com.example.final_project_4.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrdersMapper {
    OrdersMapper INSTANCE = Mappers.getMapper(OrdersMapper.class);

    OrderResponse convertsToResponse(Order order);
    Collection<OrderResponse > convertToCollectionsDto(Collection<Order> orders);
    List<OrderResponse > convertsToListDto(List<Order> orders);

}
