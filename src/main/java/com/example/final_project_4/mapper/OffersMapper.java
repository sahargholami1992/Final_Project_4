package com.example.final_project_4.mapper;


import com.example.final_project_4.dto.OfferResponse;
import com.example.final_project_4.entity.Offer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface OffersMapper {
    OffersMapper INSTANCE = Mappers.getMapper(OffersMapper.class);
    Collection<OfferResponse> offersToDto(Collection<Offer> offers);
}
