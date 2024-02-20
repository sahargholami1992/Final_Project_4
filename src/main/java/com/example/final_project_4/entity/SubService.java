package com.example.final_project_4.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SubService implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;


    private String subServiceName;

    private double basePrice;

    private String description;

    @OneToMany(mappedBy = "subService")
    private List<Order> orders;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "subServices_expert",
            joinColumns = @JoinColumn(name = "subService_id"),
            inverseJoinColumns = @JoinColumn(name = "expert_id"))
    private Set<Expert> experts;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private BasicService basicService;


    @Override
    public String toString() {
        return "SubService{" +
                "id=" + id +
                ", basicService" + basicService +
                ", subServiceName='" + subServiceName + '\'' +
                ", basePrice=" + basePrice +
                ", description='" + description + '\'' +
                '}';
    }
}
