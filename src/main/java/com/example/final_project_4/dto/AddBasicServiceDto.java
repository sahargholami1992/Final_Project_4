package com.example.final_project_4.dto;

import jakarta.persistence.Column;
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
public class AddBasicServiceDto implements Serializable {
    @Column(unique = true)
    @Pattern(regexp = "^[a-zA-Z\\s]+$",message = "not valid format for service name")
    private String serviceName;
}
