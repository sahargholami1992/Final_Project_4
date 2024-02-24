package com.example.final_project_4.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubServiceSaveDto implements Serializable {
    @Pattern(regexp = "^[a-zA-Z\\s]+$",message = "invalid Service name")
    String serviceName;

    @Pattern(regexp = "^[a-zA-Z\\s]+$",message = "invalid subService name")
    private String subServiceName;
    @NotNull
    private double basePrice;
    @NotBlank
    private String description;
}
