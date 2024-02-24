package com.example.final_project_4.dto;

import com.example.final_project_4.entity.enumaration.StatusOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryDto implements Serializable {
    LocalDate startDate;
    LocalDate endDate;
    StatusOrder statusOrder;
    String serviceType;
    String subService;
}
