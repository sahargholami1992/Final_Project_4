package com.example.final_project_4.controller;


import com.example.final_project_4.dto.*;
import com.example.final_project_4.entity.BaseUser;
import com.example.final_project_4.entity.Customer;
import com.example.final_project_4.mapper.CustomerMapper;
import com.example.final_project_4.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAnyRole('EXPERT','CUSTOMER','ADMIN')")
    @PutMapping("changePassword")
    public ResponseEntity<String> changePassword(@RequestBody @Valid PasswordChangeDto dto){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.changePassword(email, dto.getPassword(),dto.getConfirmPassword());
        return ResponseEntity.ok(
                "password successfully changed"
        );
    }

    @PreAuthorize("hasAnyRole('EXPERT','CUSTOMER','ADMIN')")
    @GetMapping("showBalance")
    public ResponseEntity<CreditBalanceProjection> showBalance(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(
                userService.showBalance(email)
        );
    }



}
