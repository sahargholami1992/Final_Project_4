package com.example.final_project_4.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubServiceResponse implements Serializable {
    private Integer id;
    private String subServiceName;
    private double price;
    private String description;
}
