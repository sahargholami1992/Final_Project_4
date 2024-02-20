package com.example.final_project_4.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReportForAdmin {

    LocalDate dateRegister;

    Long ordersRequest;

    Long ordersPlaced;

}