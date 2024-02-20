package com.example.final_project_4.service;





import com.example.final_project_4.dto.OfferDto;
import com.example.final_project_4.entity.Customer;
import com.example.final_project_4.entity.Offer;

import java.util.Collection;

public interface OfferService {
    Offer saveOffer(OfferDto dto,String email);
    Collection<Offer> getOffersForOrder(Customer customer, String sortBy);
    Collection<Offer> findAllByOrderOfCustomer(Customer customer);

    boolean existById(Integer offerId);

    Offer findById(Integer offerId);
}
