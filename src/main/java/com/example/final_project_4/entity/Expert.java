package com.example.final_project_4.entity;



import com.example.final_project_4.entity.enumaration.ExpertStatus;
import com.example.final_project_4.utill.ValidImage;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Expert extends BaseUser {
    @Enumerated(EnumType.STRING)
    private ExpertStatus expertStatus;
    private int score;
    @Lob
    @ValidImage
    private byte[] profileImage;

    @ToString.Exclude
    @ManyToMany(mappedBy = "experts")
    private Set<SubService> subServices = new HashSet<>();


}
