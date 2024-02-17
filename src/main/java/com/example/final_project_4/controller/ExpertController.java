package com.example.final_project_4.controller;


import com.example.final_project_4.dto.*;
import com.example.final_project_4.entity.Expert;
import com.example.final_project_4.entity.Order;
import com.example.final_project_4.mapper.ExpertsMapper;
import com.example.final_project_4.mapper.OrderMapper;
import com.example.final_project_4.service.ExpertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        Expert expert = ExpertsMapper.INSTANCE.convertToEntity(dto);
        expertService.registerExpert(expert,dto.getImagePath());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyRole('ADMIN','EXPERT')")
    @PutMapping("saveImageToFile")
    public ResponseEntity<ResultDTO<Boolean>> saveImageToFile(Integer expertId){
        return ResponseEntity.ok(
                new ResultDTO<>(
                        expertService.saveImageToFile(expertId)
                )
        );
    }
    @PreAuthorize("hasRole('EXPERT')")
    @PutMapping("changePassword")
    public ResponseEntity<ExpertResponseDto> changePassword(@RequestBody @Valid ChangePasswordDto dto){
        Expert expert = expertService.changePassword(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(
                ExpertsMapper.INSTANCE.convertToResponse(expert)
        );
    }
    @PreAuthorize("hasRole('EXPERT')")
    @PostMapping("/sendOffer")
    public ResponseEntity<Void> sendOffer(@RequestBody @Valid OfferDto dto){
        expertService.sendOffer(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('EXPERT')")
    @GetMapping("/getReviewsForExpert")
    public ResponseEntity<List<ReviewProjection>> getReviewsForExpert(@RequestParam Integer expertId){
        return ResponseEntity.ok(expertService.getReviewsForExpert(expertId));
    }
    @PreAuthorize("hasRole('EXPERT')")
    @GetMapping("/getPendingOrdersForExpert")
    public ResponseEntity<Collection<OrderResponse>> getPendingOrdersForExpert(@RequestParam Integer expertId){
        Collection<Order> orders = expertService.getPendingOrdersForExpert(expertId);
        return ResponseEntity.ok(
                OrderMapper.INSTANCE.convertToCollectionDto(orders)
        );
    }
}
