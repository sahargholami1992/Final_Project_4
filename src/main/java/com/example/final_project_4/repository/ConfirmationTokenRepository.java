package com.example.final_project_4.repository;


import com.example.final_project_4.entity.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Integer> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);

    boolean existsByConfirmationToken(String confirmationToken);
}
