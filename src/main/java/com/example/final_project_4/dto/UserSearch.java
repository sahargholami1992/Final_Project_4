package com.example.final_project_4.dto;

import com.example.final_project_4.entity.enumaration.Roll;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSearch {
    private Roll role;
    private String firstName;
    private String lastName;
    private String email;
    private String expertiseField;
    private int minRating;
    private int maxRating;
}
