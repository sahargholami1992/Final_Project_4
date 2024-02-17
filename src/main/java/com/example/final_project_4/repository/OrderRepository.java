package com.example.final_project_4.repository;



import com.example.final_project_4.entity.Expert;
import com.example.final_project_4.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
   @Query("SELECT o FROM Order o JOIN o.subService s JOIN s.experts e WHERE e = :expert")
   List<Order> findOrderByForExpert(Expert expert);


}



