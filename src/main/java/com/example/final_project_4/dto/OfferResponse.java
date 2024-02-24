package com.example.final_project_4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfferResponse implements Serializable {
    private int DurationOfWork;
    private LocalDate SuggestedTimeToStartWork;
    private double recommendedPrice;
    private LocalDate dateRegisterOffer;
}
