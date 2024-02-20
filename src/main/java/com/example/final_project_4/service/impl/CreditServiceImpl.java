package com.example.final_project_4.service.impl;



import com.example.final_project_4.dto.CreditBalanceProjection;
import com.example.final_project_4.entity.BaseUser;
import com.example.final_project_4.entity.Credit;
import com.example.final_project_4.exceptions.NotFoundException;
import com.example.final_project_4.repository.CreditRepository;
import com.example.final_project_4.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {
    protected final CreditRepository repository;


    @Transactional
    @Override
    public void withdraw(Credit credit) {
        repository.save(credit);
    }

    @Transactional
    @Override
    public Credit saveCredit(Credit credit) {
        return repository.save(credit);
    }

    @Override
    public Credit findByBaseUser(BaseUser baseUser) {
        return repository.findByBaseUser(baseUser)
                .orElseThrow(() -> new NotFoundException("this id not found"));
    }

    @Override
    public CreditBalanceProjection findBalanceByBaseUser(BaseUser baseUser) {
        return repository.findBalanceByBaseUser(baseUser);
    }


}









