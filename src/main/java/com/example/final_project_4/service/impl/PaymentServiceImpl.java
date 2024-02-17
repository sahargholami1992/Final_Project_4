package com.example.final_project_4.service.impl;


import com.example.final_project_4.dto.PaymentRequest;
import com.example.final_project_4.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {
    @Override
    public void paymentDone(PaymentRequest request) {
        System.out.println("true");
    }
}
