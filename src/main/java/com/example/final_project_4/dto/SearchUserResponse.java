package com.example.final_project_4.dto;


import com.example.final_project_4.entity.enumaration.Roll;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchUserResponse implements Serializable {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Roll roll;
    private String expertiseField;
    private int score;

}
