package com.example.final_project_4.service.impl;



import com.example.final_project_4.dto.OrderDto;
import com.example.final_project_4.dto.OrderHistoryDto;
import com.example.final_project_4.entity.*;
import com.example.final_project_4.entity.enumaration.Roll;
import com.example.final_project_4.entity.enumaration.StatusOrder;
import com.example.final_project_4.exceptions.NoMatchResultException;
import com.example.final_project_4.exceptions.NotFoundException;
import com.example.final_project_4.repository.OrderRepository;
import com.example.final_project_4.service.CustomerService;
import com.example.final_project_4.service.OrderService;
import com.example.final_project_4.service.UserService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    protected final OrderRepository repository;
    protected final CustomerService customerService;
    protected final UserService userService;

    public OrderServiceImpl(OrderRepository repository, @Lazy CustomerService customerService, UserService userService) {
        this.repository = repository;
        this.customerService = customerService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public Order addOrder(OrderDto dto, Customer customer, SubService subService) {
        if (dto.getRecommendedPrice() < subService.getBasePrice()
                && dto.getDateDoOrder().isBefore(LocalDate.now()))
            throw new NoMatchResultException(" time or price in not valid");
        Order order = getOrder(dto, customer, subService);
        return repository.save(order);
    }

    private static Order getOrder(OrderDto dto, Customer customer, SubService subService) {
        Order order = new Order();
        order.setCustomer(customer);
        order.setSubService(subService);
        order.setAddress(dto.getAddress());
        order.setRecommendedPrice(dto.getRecommendedPrice());
        order.setDescription(dto.getDescription());
        order.setDateDoOrder(dto.getDateDoOrder());
        order.setStatusOrder(dto.getStatusOrder());
        return order;
    }

    public Collection<Order> getPendingOrdersForExpert(Expert expert) {
        return repository.findOrderByForExpert(expert).stream()
                .filter(order -> order.getStatusOrder() == StatusOrder.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS ||
                        order.getStatusOrder() == StatusOrder.WAITING_FOR_EXPERT_SELECTION)
                .collect(Collectors.toList());
    }
    @Transactional
    @Override
    public void UpdateStatus(Order order) {
        repository.save(order);
    }
    @Transactional
    @Override
    public void changeOrderStatus(Order order,StatusOrder statusOrder) {
        order.setStatusOrder(statusOrder);
        repository.save(order);
    }

    @Override
    public Collection<Order> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public void update(Order order) {
        repository.save(order);
    }

    @Override
    public boolean existById(Integer orderId) {
        return repository.existsById(orderId);
    }

    @Override
    public Order findById(Integer orderId) {
        return repository.findById(orderId).orElseThrow(
                () -> new NotFoundException("THIS ORDER ID IS NULL")
        );
    }

    @Override
    public Collection<Order> getOrdersForExpert(Expert expert,StatusOrder statusOrder) {
        return repository.findOrderByForExpert(expert).stream()
                .filter(order -> order.getStatusOrder().equals(statusOrder) )
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Order> findOrderByCustomer(Customer customer,StatusOrder statusOrder) {
        return repository.findOrderByCustomer(customer).stream()
                .filter(order -> order.getStatusOrder().equals(statusOrder) )
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getFilteredOrderHistory(OrderHistoryDto dto) {
        return repository.findAll(getUserSpecification(dto));
    }
    private Specification<Order> getUserSpecification(OrderHistoryDto dto) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (dto.getStartDate() != null && dto.getEndDate() != null) {
                predicate = cb.and(predicate, cb.between(root.get("dateDoOrder"), dto.getStartDate(), dto.getEndDate()));
            }
            if (dto.getStatusOrder() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("statusOrder"), dto.getStatusOrder()));
            }
            if (dto.getServiceType() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("subService").get("basicService").get("serviceName"), dto.getServiceType()));
            }
            if (dto.getSubService() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("subService").get("subServiceName"), dto.getSubService()));
            }
            return predicate;
        };
    }

    @Override
    public Long numberOfOrders(String email) {
        if (email.equals("Admin@admin.com"))
            throw new NoMatchResultException("THIS EMAIL DOES NOT HAVE ORDER");
        return repository.count(countOrders(email));
    }

    private Specification<Order> countOrders(String email) {
        BaseUser user = userService.findByEmail(email);
        return (root, query, criteriaBuilder) -> {
            if (user.getRoll().equals(Roll.ROLE_EXPERT)) {
                Join<Order, Offer> orderOfferJoin = root.join("offer", JoinType.INNER);
                return criteriaBuilder.equal(orderOfferJoin.get("expert").get("email"), email);
            }else
                return criteriaBuilder.equal(root.get("customer").get("email"), email);
        };
    }
}
