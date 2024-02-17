package com.example.final_project_4.service;




import com.example.final_project_4.dto.ReviewProjection;
import com.example.final_project_4.entity.Review;

import java.util.List;

public interface ReviewService {


    List<ReviewProjection> findByExpertId(Integer expertId);

    Review save(Review review);
}
