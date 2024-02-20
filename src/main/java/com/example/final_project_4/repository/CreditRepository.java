package com.example.final_project_4.repository;



import com.example.final_project_4.dto.CreditBalanceProjection;
import com.example.final_project_4.entity.BaseUser;
import com.example.final_project_4.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CreditRepository extends JpaRepository<Credit,Integer> {

    Optional<Credit> findByBaseUser(BaseUser baseUser);
    @Query(value = "select new com.example.final_project_4.dto.CreditBalanceProjection(c.balance) from Credit c where c.baseUser = :baseUser")
    CreditBalanceProjection findBalanceByBaseUser(BaseUser baseUser);

}
