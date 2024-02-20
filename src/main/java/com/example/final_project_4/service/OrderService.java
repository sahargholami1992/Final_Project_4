package com.example.final_project_4.service;






import com.example.final_project_4.dto.OrderDto;
import com.example.final_project_4.dto.OrderHistoryDto;
import com.example.final_project_4.entity.Customer;
import com.example.final_project_4.entity.Expert;
import com.example.final_project_4.entity.Order;
import com.example.final_project_4.entity.SubService;
import com.example.final_project_4.entity.enumaration.StatusOrder;

import java.util.Collection;
import java.util.List;

public interface OrderService  {
    Order addOrder(OrderDto dto, Customer customer, SubService subService);
    Collection<Order> getPendingOrdersForExpert(Expert expert);

    void UpdateStatus(Order order);
    void changeOrderStatus(Order order, StatusOrder statusOrder);
    Collection<Order> findAll();

    void update(Order order);

    boolean existById(Integer orderId);

    Order findById(Integer orderId);
    Collection<Order> getOrdersForExpert(Expert expert,StatusOrder statusOrder);
    Collection<Order> findOrderByCustomer(Customer customer,StatusOrder statusOrder);
    List<Order> getFilteredOrderHistory(OrderHistoryDto dto);
    Long numberOfOrders(String email);
}
