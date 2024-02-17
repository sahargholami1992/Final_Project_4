package com.example.final_project_4.repository;


import com.example.final_project_4.entity.BasicService;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface BasicServiceRepository extends JpaRepository<BasicService,Integer> {

    boolean existsByServiceName(String serviceName);

    Optional<BasicService> findByServiceName(String serviceName);
}
