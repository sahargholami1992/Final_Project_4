package com.example.final_project_4.controller;


import com.example.final_project_4.dto.*;
import com.example.final_project_4.entity.BaseUser;
import com.example.final_project_4.entity.Expert;
import com.example.final_project_4.entity.Order;
import com.example.final_project_4.mapper.CustomersMapper;
import com.example.final_project_4.mapper.ExpertsMapper;
import com.example.final_project_4.mapper.OrderMapper;
import com.example.final_project_4.service.ExpertService;
import com.example.final_project_4.service.UserService;
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
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAnyRole('EXPERT','CUSTOMER','ADMIN')")
    @PutMapping("changePassword")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid ChangePasswordDto dto){
        BaseUser baseUser = userService.changePassword(dto.getEmail(), dto.getPassword());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('EXPERT','CUSTOMER','ADMIN')")
    @GetMapping("showBalance")
    public ResponseEntity<CreditBalanceProjection> showBalance(@RequestParam String email){
        return ResponseEntity.ok(
                userService.showBalance(email)
        );
    }



}
