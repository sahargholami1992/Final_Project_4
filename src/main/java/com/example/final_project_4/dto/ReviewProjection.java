package com.example.final_project_4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
@AllArgsConstructor
@Getter
public class ReviewProjection implements Serializable {
    private int score;
}
