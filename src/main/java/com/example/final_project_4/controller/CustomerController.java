package com.example.final_project_4.controller;


import com.example.final_project_4.dto.*;
import com.example.final_project_4.entity.*;
import com.example.final_project_4.entity.enumaration.StatusOrder;
import com.example.final_project_4.mapper.CustomersMapper;
import com.example.final_project_4.mapper.OffersMapper;
import com.example.final_project_4.mapper.OrderMapper;
import com.example.final_project_4.mapper.SubServiceMapper;
import com.example.final_project_4.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        Customer customer = CustomersMapper.INSTANCE.convertToEntity(dto);
        Customer registerCustomer = customerService.registerCustomer(customer);
        return ResponseEntity.ok(
                CustomersMapper.INSTANCE.convertToDto(registerCustomer)
        );
    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("changePassword")
    public ResponseEntity<CustomerResponse> changePassword(@RequestBody @Valid ChangePasswordDto dto){
        Customer customer = customerService.changePassword(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(
                CustomersMapper.INSTANCE.convertToDto(customer)
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
                SubServiceMapper.INSTANCE.convertCollectionsToDto(subServices)
        );
    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/addOrder")
    public ResponseEntity<OrderResponse> addOrder(@RequestBody OrderDto dto){
        Order order = customerService.addOrder(dto);
        return ResponseEntity.ok(
                OrderMapper.INSTANCE.convertToResponse(order)
        );
    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/changeOrderStatusToStarted")
    public ResponseEntity<Void> changeOrderStatusToStarted(@RequestParam  Integer offerId){
        customerService.changeOrderStatusToStarted(offerId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/changeOrderStatus")
    public ResponseEntity<Void> changeOrderStatus(@RequestParam Integer offerId,@RequestParam StatusOrder statusOrder){
        customerService.changeOrderStatus(offerId,statusOrder);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/getOffersForOrder")
    public ResponseEntity<Collection<OfferResponse>> getOffersForOrder(@RequestParam Integer customerId, @RequestParam String sortBy) {
        Collection<Offer> offers = customerService.getOffersForOrder(customerId,sortBy);
        return ResponseEntity.ok(
                OffersMapper.INSTANCE.offersToDto(offers)
        );
    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/findAllByOrder")
    public ResponseEntity<Collection<OfferResponse>> findAllByOrder(@RequestParam Integer customerId){
        Collection<Offer> offers = customerService.findAllByOrder(customerId);
        return ResponseEntity.ok(
                OffersMapper.INSTANCE.offersToDto(offers)
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


}
