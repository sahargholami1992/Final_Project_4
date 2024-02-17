package com.example.final_project_4.service;







import com.example.final_project_4.dto.AddReviewDto;
import com.example.final_project_4.dto.OrderDto;
import com.example.final_project_4.dto.PaymentRequest;
import com.example.final_project_4.entity.*;
import com.example.final_project_4.entity.enumaration.StatusOrder;
import com.example.final_project_4.service.user.BaseUserService;

import java.util.Collection;

public interface CustomerService extends BaseUserService<Customer> {
    Customer registerCustomer(Customer customer);
    Collection<BasicService> showAllService();
    Collection<SubService> showAllSubServiceByService(String basicServiceName);
    Order addOrder(OrderDto dto);
    void changeOrderStatusToStarted(Integer offerId);
    void changeOrderStatus(Integer offerId, StatusOrder statusOrder);
    Collection<Offer> findAllByOrder(Integer customerId);
    Collection<Offer> getOffersForOrder(Integer customerId,String sortBy);
    void paymentFromCredit(Integer offerId);
    void processOnlinePayment(PaymentRequest paymentRequest);
    void addReview(AddReviewDto dto);
}
