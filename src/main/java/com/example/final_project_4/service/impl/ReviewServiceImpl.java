package com.example.final_project_4.service.impl;



import com.example.final_project_4.dto.ReviewProjection;
import com.example.final_project_4.entity.Review;
import com.example.final_project_4.repository.ReviewRepository;
import com.example.final_project_4.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    protected final ReviewRepository repository;

    @Override
    public List<ReviewProjection> findByExpert(String email) {
        return repository.findScoreByExpert(email);
    }

    @Transactional
    @Override
    public Review save(Review review) {
        return repository.save(review);
    }
}
