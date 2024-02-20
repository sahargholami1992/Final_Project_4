package com.example.final_project_4.repository;



import com.example.final_project_4.entity.Customer;
import com.example.final_project_4.entity.Expert;
import com.example.final_project_4.entity.Order;
import com.example.final_project_4.entity.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


import java.util.Collection;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer>, JpaSpecificationExecutor<Order> {
   @Query("SELECT o FROM Order o JOIN o.subService s JOIN s.experts e WHERE e = :expert")
   Collection<Order> findOrderByForExpert(Expert expert);
   
   Collection<Order> findOrderByCustomer(Customer customer);



}



