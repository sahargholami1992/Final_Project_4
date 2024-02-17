package com.example.final_project_4.dto;



import com.example.final_project_4.entity.enumaration.Permissions;
import com.example.final_project_4.entity.enumaration.Roll;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class CustomerRegisterDto implements Serializable {
    @Pattern(regexp = "^[a-zA-Z\\s]+$",message = "invalid string")
    private String firstName;
    @Pattern(regexp = "^[a-zA-Z\\s]+$",message = "invalid string")
    private String lastName;
    @Column(unique = true)
    @NotBlank(message = "empty email")
    @Email(message = "Invalid email")
    private String email;
    @Column( nullable = false)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8}$",message = "invalid password format")
    private String password;
    private LocalDate dateRegister;
    private Roll roll;
    private Permissions permission;


    public CustomerRegisterDto(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.dateRegister = LocalDate.now();
        this.roll = Roll.CUSTOMER;
        this.permission = Permissions.ACCEPTED;
    }
}
