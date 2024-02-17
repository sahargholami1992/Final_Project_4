package com.example.final_project_4.service;


import com.example.final_project_4.entity.Credit;

public interface CreditService {

    void withdraw(Credit credit);

    Credit saveCredit(Credit credit);
}
