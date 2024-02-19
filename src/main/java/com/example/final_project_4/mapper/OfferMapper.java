package com.example.final_project_4.mapper;


import com.example.final_project_4.dto.OfferResponse;
import com.example.final_project_4.entity.Offer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface OfferMapper {
    OfferMapper INSTANCE = Mappers.getMapper(OfferMapper.class);
    Collection<OfferResponse> offerToDto(Collection<Offer> offers);
}
