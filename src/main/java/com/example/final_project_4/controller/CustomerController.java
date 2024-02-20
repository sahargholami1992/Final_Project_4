package com.example.final_project_4.controller;


import com.example.final_project_4.dto.*;
import com.example.final_project_4.entity.*;
import com.example.final_project_4.entity.enumaration.StatusOrder;

import com.example.final_project_4.mapper.CustomerMapper;
import com.example.final_project_4.mapper.OfferMapper;
import com.example.final_project_4.mapper.OrdersMapper;
import com.example.final_project_4.mapper.SubServicesMapper;
import com.example.final_project_4.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;


@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@CrossOrigin
@Validated
public class CustomerController {
    private final CustomerService customerService;

    @CrossOrigin
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/payment")
    public ResponseEntity<String> payment(@RequestBody @Valid PaymentRequest request){
        customerService.processOnlinePayment(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> register(@RequestBody @Valid CustomerRegisterDto dto){
        Customer customer = CustomerMapper.INSTANCE.convertsToEntity(dto);
        Customer registerCustomer = customerService.registerCustomer(customer);
        return ResponseEntity.ok(
                CustomerMapper.INSTANCE.convertsToDto(registerCustomer)
        );
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/showAllBasicService")
    public ResponseEntity<Collection<BasicService>> showAllBasicService(){
        return ResponseEntity.ok(customerService.showAllService());
    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/showAllSubServiceByService")
    public ResponseEntity<Collection<SubServiceResponse>> showAllSubServiceByService(@RequestParam String basicServiceName){
        Collection<SubService> subServices = customerService.showAllSubServiceByService(basicServiceName);
        return ResponseEntity.ok(
                SubServicesMapper.INSTANCE.convertCollectionToDto(subServices)
        );
    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/addOrder")
    public ResponseEntity<OrderResponse> addOrder(@RequestBody OrderDto dto){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Order order = customerService.addOrder(dto,email);
        return ResponseEntity.ok(
                OrdersMapper.INSTANCE.convertsToResponse(order)
        );
    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/changeOrderStatusToStarted")
    public ResponseEntity<Void> changeOrderStatusToStarted(@RequestParam  Integer offerId){
        customerService.changeOrderStatusToStarted(offerId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/changeOrderStatusToComingExpert")
    public ResponseEntity<Void> changeOrderStatusToComingExpert(@RequestParam Integer offerId){
        customerService.changeOrderStatusToComingExpert(offerId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/changeOrderStatusToDone")
    public ResponseEntity<Void> changeOrderStatusToDone(@RequestParam Integer offerId){
        customerService.changeOrderStatusToDone(offerId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/getOffersForOrder")
    public ResponseEntity<Collection<OfferResponse>> getOffersForOrder(@RequestParam String sortBy) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<Offer> offers = customerService.getOffersForOrder(email,sortBy);
        return ResponseEntity.ok(
                OfferMapper.INSTANCE.offerToDto(offers)
        );
    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/findAllByOrder")
    public ResponseEntity<Collection<OfferResponse>> findAllByOrder(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<Offer> offers = customerService.findAllByOrder(email);
        return ResponseEntity.ok(
                OfferMapper.INSTANCE.offerToDto(offers)
        );
    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/paymentFromCredit")
    public ResponseEntity<Void> paymentFromCredit(Integer offerId){
        customerService.paymentFromCredit(offerId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/addReview")
    public ResponseEntity<Void> addReview(@RequestBody AddReviewDto dto){
        customerService.addReview(dto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/historyOrdersForCustomer")
    public ResponseEntity<Collection<OrderResponse>> historyOrdersForCustomer(@RequestParam StatusOrder statusOrder){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<Order> orders = customerService.historyOrdersForCustomer(email,statusOrder);
        return ResponseEntity.ok(
                OrdersMapper.INSTANCE.convertToCollectionsDto(orders)
        );
    }
}
