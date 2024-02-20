package com.example.final_project_4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class CreditBalanceProjection implements Serializable {
    private double balance;

}
