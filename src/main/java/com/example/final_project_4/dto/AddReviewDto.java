package com.example.final_project_4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddReviewDto implements Serializable {
    private Integer offerId;
    private int score;
    private String comment;

}
