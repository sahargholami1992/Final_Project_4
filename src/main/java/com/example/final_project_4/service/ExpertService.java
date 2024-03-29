package com.example.final_project_4.service;




import com.example.final_project_4.dto.OfferDto;
import com.example.final_project_4.dto.ReviewProjection;
import com.example.final_project_4.entity.Expert;
import com.example.final_project_4.entity.Offer;
import com.example.final_project_4.entity.Order;
import com.example.final_project_4.entity.enumaration.StatusOrder;
import com.example.final_project_4.service.user.BaseUserService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface ExpertService extends BaseUserService<Expert> {
    Expert registerExpert(Expert expert, MultipartFile imageFile) throws IOException;

    void changeExpertStatus(Expert expert);
    Offer sendOffer(OfferDto dto,String email);
    boolean saveImageToFile(String email);
    Collection<Order> getPendingOrdersForExpert(String email);
    Expert saveExpert(Expert expert);
    List<ReviewProjection> getReviewsForExpert(String email);
    Collection<Order> historyOrdersForExpert(String email, StatusOrder statusOrder);

}
