package com.example.final_project_4.controller;


import com.example.final_project_4.dto.*;
import com.example.final_project_4.entity.*;
import com.example.final_project_4.entity.enumaration.StatusOrder;


import com.example.final_project_4.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Collection;



@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@CrossOrigin
@Validated
public class CustomerController {
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    @CrossOrigin
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/payment")
    public ResponseEntity<String> payment(@RequestBody @Valid PaymentRequest request){
        customerService.processOnlinePayment(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> register(@RequestBody @Valid CustomerRegisterDto dto){
        Customer customer = modelMapper.map(dto, Customer.class);
        Customer registerCustomer = customerService.registerCustomer(customer);
        return ResponseEntity.ok(
                modelMapper.map(registerCustomer,CustomerResponse.class)
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
        Collection<SubServiceResponse> responses = new ArrayList<>();
        for (SubService subService: subServices) {
            SubServiceResponse response = modelMapper.map(subService, SubServiceResponse.class);
            responses.add(response);
        }
        return ResponseEntity.ok(
               responses
        );
    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/addOrder")
    public ResponseEntity<OrderResponse> addOrder(@RequestBody OrderDto dto){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Order order = customerService.addOrder(dto,email);
        return ResponseEntity.ok(
                modelMapper.map(order,OrderResponse.class)
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
        Collection<OfferResponse> responses = new ArrayList<>();
        for (Offer offer:offers) {
            OfferResponse response = modelMapper.map(offer, OfferResponse.class);
            responses.add(response);
        }
        return ResponseEntity.ok(
                responses
        );
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/findAllByOrder")
    public ResponseEntity<Collection<OfferResponse>> findAllByOrder(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<Offer> offers = customerService.findAllByOrder(email);
        Collection<OfferResponse> responses = new ArrayList<>();
        for (Offer offer:offers) {
            OfferResponse response = modelMapper.map(offer, OfferResponse.class);
            responses.add(response);
        }
        return ResponseEntity.ok(
                responses
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
        Collection<OrderResponse> responses = new ArrayList<>();
        for (Order order:orders) {
            OrderResponse response = modelMapper.map(order, OrderResponse.class);
            responses.add(response);
        }
        return ResponseEntity.ok(
                responses
        );
    }
}
