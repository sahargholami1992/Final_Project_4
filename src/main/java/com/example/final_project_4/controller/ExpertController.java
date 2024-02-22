package com.example.final_project_4.controller;


import com.example.final_project_4.dto.*;
import com.example.final_project_4.entity.Expert;
import com.example.final_project_4.entity.Order;
import com.example.final_project_4.entity.enumaration.StatusOrder;


import com.example.final_project_4.service.ExpertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/api/expert")
@RequiredArgsConstructor
@Validated
public class ExpertController {
    private final ExpertService expertService;
    private final ModelMapper modelMapper;

    @PostMapping(value ="/register",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Void> register(@ModelAttribute @Valid ExpertRegisterDto dto) throws IOException {
        Expert expert = modelMapper.map(dto, Expert.class);
        expertService.registerExpert(expert,dto.getImageFile());
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
        Collection<OrderResponse> orderResponses = new ArrayList<>();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<Order> orders = expertService.getPendingOrdersForExpert(email);
        for (Order p: orders) {
            OrderResponse orderResponse = modelMapper.map(p, OrderResponse.class);
            orderResponses.add(orderResponse);
        }
        return ResponseEntity.ok(
                orderResponses
        );
    }

    @PreAuthorize("hasRole('EXPERT')")
    @GetMapping("/historyOrdersForExpert")
    public ResponseEntity<Collection<OrderResponse>> historyOrdersForExpert(@RequestParam StatusOrder statusOrder){
        Collection<OrderResponse> orderResponses = new ArrayList<>();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<Order> orders = expertService.historyOrdersForExpert(email,statusOrder);
        for (Order p: orders) {
            OrderResponse orderResponse = modelMapper.map(p, OrderResponse.class);
            orderResponses.add(orderResponse);
        }
        return ResponseEntity.ok(
                orderResponses
        );
    }
}
