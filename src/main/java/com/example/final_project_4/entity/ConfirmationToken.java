package com.example.final_project_4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue
    private long tokenId;


    private String confirmationToken;

    private LocalDate createdDate;

    @OneToOne(targetEntity = BaseUser.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private BaseUser user;

    public ConfirmationToken() {}

    public ConfirmationToken(BaseUser user) {
        this.user = user;
        createdDate = LocalDate.now();
        confirmationToken = UUID.randomUUID().toString();
    }

}