package com.example.final_project_4.controller;


import com.example.final_project_4.dto.*;
import com.example.final_project_4.entity.Customer;
import com.example.final_project_4.entity.Expert;
import com.example.final_project_4.entity.Order;
import com.example.final_project_4.entity.enumaration.StatusOrder;

import com.example.final_project_4.mapper.CustomerMapper;
import com.example.final_project_4.mapper.ExpertMapper;
import com.example.final_project_4.mapper.OrdersMapper;
import com.example.final_project_4.service.ExpertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/api/expert")
@RequiredArgsConstructor
@Validated
public class ExpertController {
    private final ExpertService expertService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid ExpertRegisterDto dto){
        Expert expert = ExpertMapper.INSTANCE.convertsToEntity(dto);
        expertService.registerExpert(expert,dto.getImagePath());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('EXPERT')")
    @PutMapping("saveImageToFile")
    public ResponseEntity<ResultDTO<Boolean>> saveImageToFile(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(
                new ResultDTO<>(
                        expertService.saveImageToFile(email)
                )
        );
    }

    @PreAuthorize("hasRole('EXPERT')")
    @PostMapping("/sendOffer")
    public ResponseEntity<Void> sendOffer(@RequestBody @Valid OfferDto dto){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        expertService.sendOffer(dto,email);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('EXPERT')")
    @GetMapping("/getReviewsForExpert")
    public ResponseEntity<List<ReviewProjection>> getReviewsForExpert(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(expertService.getReviewsForExpert(email));
    }
    @PreAuthorize("hasRole('EXPERT')")
    @GetMapping("/getPendingOrdersForExpert")
    public ResponseEntity<Collection<OrderResponse>> getPendingOrdersForExpert(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<Order> orders = expertService.getPendingOrdersForExpert(email);
        return ResponseEntity.ok(
                OrdersMapper.INSTANCE.convertToCollectionsDto(orders)
        );
    }

    @PreAuthorize("hasRole('EXPERT')")
    @GetMapping("/historyOrdersForExpert")
    public ResponseEntity<Collection<OrderResponse>> historyOrdersForExpert(@RequestParam StatusOrder statusOrder){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<Order> orders = expertService.historyOrdersForExpert(email,statusOrder);
        return ResponseEntity.ok(
                OrdersMapper.INSTANCE.convertToCollectionsDto(orders)
        );
    }
}
