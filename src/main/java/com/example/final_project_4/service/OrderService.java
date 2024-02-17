package com.example.final_project_4.service;






import com.example.final_project_4.dto.OrderDto;
import com.example.final_project_4.entity.Customer;
import com.example.final_project_4.entity.Expert;
import com.example.final_project_4.entity.Order;
import com.example.final_project_4.entity.SubService;
import com.example.final_project_4.entity.enumaration.StatusOrder;

import java.util.Collection;

public interface OrderService  {
    Order addOrder(OrderDto dto, Customer customer, SubService subService);
    Collection<Order> getPendingOrdersForExpert(Expert expert);

    void UpdateStatus(Order order);
    void changeOrderStatus(Order order, StatusOrder statusOrder);
    Collection<Order> findAll();

    void update(Order order);

    boolean existById(Integer orderId);

    Order findById(Integer orderId);
}
