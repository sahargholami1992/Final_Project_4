package com.example.final_project_4.repository;



import com.example.final_project_4.entity.BasicService;
import com.example.final_project_4.entity.Expert;
import com.example.final_project_4.entity.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SubServiceRepository extends JpaRepository<SubService,Integer> , JpaSpecificationExecutor<SubService> {

    Collection<SubService> findByBasicService(BasicService basicService);

    boolean existsBySubServiceName(String subServiceName);
    Optional<SubService> findBySubServiceName(String subServiceName);


}
