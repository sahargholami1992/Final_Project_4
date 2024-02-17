package com.example.final_project_4.dto;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class PaymentRequest implements Serializable {
    @Pattern(regexp ="^[0-9]{16}$",message = "invalid card number")
    private String cardNumber;

    @Size(min = 3, max = 6, message = "Invalid ccv: Must be of 3 - 6 characters")
    private int cvv;
    @Size(min = 1, max = 12, message = "Invalid ccv: Must be of 1 - 12 characters")
    private String month;
    private String year;

    private String password;
    private Integer offerId;
//    @Transient
//    private String captcha;
//
//    @Transient
//    private String hiddenCaptcha;
//
//    @Transient
//    private String realCaptcha;
}
