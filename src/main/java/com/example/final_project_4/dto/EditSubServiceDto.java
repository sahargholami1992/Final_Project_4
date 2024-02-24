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
public class EditSubServiceDto implements Serializable {
    @Pattern(regexp = "^[a-zA-Z\\s]+$",message = "invalid subService name")
    private String subServiceName;
    @NotNull(message = "this filed not be null")
    private double price;
    @NotBlank(message = "this filed not be null")
    private String description;
}
