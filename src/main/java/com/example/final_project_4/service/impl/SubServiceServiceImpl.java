package com.example.final_project_4.service.impl;



import com.example.final_project_4.dto.UserSearch;
import com.example.final_project_4.entity.*;
import com.example.final_project_4.entity.enumaration.Roll;
import com.example.final_project_4.exceptions.NoMatchResultException;
import com.example.final_project_4.exceptions.NotFoundException;
import com.example.final_project_4.repository.SubServiceRepository;
import com.example.final_project_4.service.SubServiceService;

import com.example.final_project_4.service.UserService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SubServiceServiceImpl implements SubServiceService {
    protected final SubServiceRepository repository;
    protected final UserService userService;
    @Transactional
    @Override
    public void deleteByEXPERT(SubService subService, Expert expert) {
        subService.getExperts().remove(expert);
        repository.save(subService);
    }
    @Transactional
    @Override
    public void saveExpert(SubService subService, Expert expert) {
        Set<Expert> experts = subService.getExperts();
        experts.add(expert);
        subService.setExperts(experts);
        repository.save(subService);
    }
    @Transactional
    @Override
    public SubService editSubService(String subServiceName, double price, String description) {
        if (repository.existsBySubServiceName(subServiceName)) {
            SubService subService = repository.findBySubServiceName(subServiceName).orElseThrow
                    (() -> new NotFoundException("this sub service not found"));
            subService.setBasePrice(price);
            subService.setDescription(description);
            repository.save(subService);
            return subService;
        }else throw new NoMatchResultException("this subService name is not exist");
    }

    @Override
    public Collection<SubService> findByService(BasicService basicService) {
        return repository.findByBasicService(basicService);
    }

    @Override
    public boolean existByName(String subServiceName) {
        return repository.existsBySubServiceName(subServiceName);
    }
    @Transactional
    @Override
    public void saveOrUpdate(SubService subService) {
        repository.save(subService);
    }

    @Override
    public Collection<SubService> loadAll() {
        return repository.findAll();
    }

    @Override
    public SubService findBySubServiceName(String subServiceName) {
        return repository.findBySubServiceName(subServiceName).orElseThrow(
                () -> new NotFoundException("this object not found")
        );
    }

    @Override
    public Collection<SubService> subServiceHistory(String email) {
        if (email.equals("Admin@admin.com"))
            throw new NoMatchResultException("THIS ROLL IS NOT SubService");
        return repository.findAll(getUserSpecification(email));
    }
    private Specification<SubService> getUserSpecification(String email) {
        BaseUser user = userService.findByEmail(email);
        return (root, query, criteriaBuilder) -> {
            if (user.getRoll().equals(Roll.ROLE_EXPERT)) {
                Join<SubService, Expert> subServiceExpertJoin = root.join("experts", JoinType.INNER);
                return criteriaBuilder.equal(subServiceExpertJoin.get("email"), email);
            }else {
                Join<SubService, Order> subServiceOrderJoin = root.join("order", JoinType.INNER);
                Join<Order, Customer> customerJoin = subServiceOrderJoin.join("customer", JoinType.INNER);
                return criteriaBuilder.equal(customerJoin.get("email"), email);
            }
        };
    }


}
