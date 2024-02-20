package com.example.final_project_4.service.impl;



import com.example.final_project_4.dto.OfferDto;
import com.example.final_project_4.entity.Customer;
import com.example.final_project_4.entity.Expert;
import com.example.final_project_4.entity.Offer;
import com.example.final_project_4.entity.Order;
import com.example.final_project_4.entity.enumaration.StatusOrder;
import com.example.final_project_4.exceptions.NoMatchResultException;
import com.example.final_project_4.exceptions.NotFoundException;
import com.example.final_project_4.repository.OfferRepository;
import com.example.final_project_4.service.ExpertService;
import com.example.final_project_4.service.OfferService;
import com.example.final_project_4.service.OrderService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Transactional(readOnly = true)
@Service
public class OfferServiceImpl implements OfferService {
    protected final OfferRepository repository;
    protected final OrderService orderService;
    protected final ExpertService expertService;

    public OfferServiceImpl(OfferRepository repository, OrderService orderService,@Lazy ExpertService expertServic) {
        this.repository = repository;
        this.orderService = orderService;
        this.expertService = expertServic;
    }

    @Transactional
    @Override
    public Offer saveOffer(OfferDto dto,String email) {
        if (!orderService.existById(dto.getOrderId())
                && !expertService.existByEmail(email))
            throw new NotFoundException("order or expert not found");
        Order order = orderService.findById(dto.getOrderId());
        Expert expert = expertService.findByEmail(email);
        if (!order.getStatusOrder().equals(StatusOrder.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS)
                && !order.getStatusOrder().equals(StatusOrder.WAITING_FOR_EXPERT_SELECTION))
            throw new NoMatchResultException("Cannot send proposal for orders in the current status");
        if (dto.getRecommendedPrice() < order.getSubService().getBasePrice()
                || dto.getSuggestedTimeToStartWork().isBefore(LocalDate.now()))
            throw new NoMatchResultException("Invalid proposal conditions ");
        Offer offer = getOffer(dto, order, expert);
        Offer save = repository.save(offer);
        order.setStatusOrder(StatusOrder.WAITING_FOR_EXPERT_SELECTION);
        orderService.UpdateStatus(order);
        return save;
    }

    private static Offer getOffer(OfferDto dto, Order order, Expert expert) {
        Offer offer = new Offer();
        offer.setDurationOfWork(dto.getDurationOfWork());
        offer.setSuggestedTimeToStartWork(dto.getSuggestedTimeToStartWork());
        offer.setRecommendedPrice(dto.getRecommendedPrice());
        offer.setDateRegisterOffer(dto.getDateRegisterOffer());
        offer.setOrder(order);
        offer.setExpert(expert);
        return offer;
    }

    @Override
    public Collection<Offer> getOffersForOrder(Customer customer, String sortBy) {
        Collection<Offer> offers = new ArrayList<>();
        switch (sortBy) {
            case "price" -> offers =repository.findAllByOrderOrderByRecommendedPrice(customer);

            case "rating" -> offers = repository.findAllByOrderOrderByExpertPrice(customer);

        }
        return offers;
    }

    @Override
    public Collection<Offer> findAllByOrderOfCustomer(Customer customer) {
        return repository.findAllByOrderOfCustomer(customer);
    }

    @Override
    public boolean existById(Integer offerId) {
        return repository.existsById(offerId);
    }

    @Override
    public Offer findById(Integer offerId) {
        return repository.findById(offerId).orElseThrow(
                () -> new NotFoundException("this offer is null")
        );
    }
}
