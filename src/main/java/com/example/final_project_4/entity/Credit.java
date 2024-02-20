package com.example.final_project_4.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Credit implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    private double balance;
    @OneToOne
    private BaseUser baseUser;




}
