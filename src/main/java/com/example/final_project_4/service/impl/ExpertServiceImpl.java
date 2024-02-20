package com.example.final_project_4.service.impl;



import com.example.final_project_4.dto.OfferDto;
import com.example.final_project_4.dto.ReviewProjection;
import com.example.final_project_4.entity.Credit;
import com.example.final_project_4.entity.Expert;
import com.example.final_project_4.entity.Offer;
import com.example.final_project_4.entity.Order;
import com.example.final_project_4.entity.enumaration.Permissions;
import com.example.final_project_4.entity.enumaration.StatusOrder;
import com.example.final_project_4.exceptions.DuplicateException;
import com.example.final_project_4.exceptions.NotFoundException;
import com.example.final_project_4.repository.ExpertRepository;
import com.example.final_project_4.service.*;
import com.example.final_project_4.service.user.BaseUserServiceImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.Collection;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ExpertServiceImpl extends BaseUserServiceImpl<Expert, ExpertRepository>
                              implements ExpertService {
    protected final OfferService offerService;
    protected final ReviewService reviewService;
    protected final CreditService creditService;
    protected final OrderService orderService;
    public ExpertServiceImpl(ExpertRepository repository,BCryptPasswordEncoder passwordEncoder, OfferService offerService, ReviewService reviewService, CreditService creditService, OrderService orderService) {
        super(repository,passwordEncoder);
        this.offerService=offerService;
        this.reviewService=reviewService;
        this.creditService = creditService;
        this.orderService = orderService;
    }
    @Transactional
    @Override
    public Expert registerExpert(Expert expert,String imagePath) {
       if (existByEmail(expert.getEmail()))
           throw new DuplicateException("this email existed");
       expert.setProfileImage(readsImage(imagePath));
       expert.setPermissions(Permissions.WAITING);
       expert.setPassword(passwordEncoder.encode(expert.getPassword()));
       Credit credit = new Credit();
       credit.setBalance(0);
       Expert save = repository.save(expert);
       credit.setBaseUser(save);
       creditService.saveCredit(credit);
       return save;
    }
    private static byte[] readsImage(String imageName)  {
        InputStream inputStream = ExpertServiceImpl.class.getClassLoader().getResourceAsStream(imageName);
        if (inputStream != null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int bytesRead;
            byte[] buffer = new byte[1024];

            while (true) {
                try {
                    if ((bytesRead = inputStream.read(buffer)) == -1) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        }else{
            return null;
        }
    }
    @Transactional
    @Override
    public void changeExpertStatus(Expert expert) {
        repository.save(expert);
    }
    @Transactional
    @Override
    public Offer sendOffer(OfferDto dto,String email) {
        return offerService.saveOffer(dto,email);
    }



    public boolean saveImageToFile(String email) {
        if (!existByEmail(email))throw new NotFoundException("this expert id is null");
        Expert expert = findByEmail(email);
        try (OutputStream outputStream = new FileOutputStream("output.jpg")) {
            outputStream.write(expert.getProfileImage());
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public Collection<Order> getPendingOrdersForExpert(String email) {
        if (!existByEmail(email))throw new NotFoundException("this expert id is null");
        Expert expert = findByEmail(email);
        return orderService.getPendingOrdersForExpert(expert);
    }

    @Override
    public Expert saveExpert(Expert expert) {
        return repository.save(expert);
    }

    @Override
    public List<ReviewProjection> getReviewsForExpert(String email) {
        return reviewService.findByExpert(email);
    }

    @Override
    public Collection<Order> historyOrdersForExpert(String email,StatusOrder statusOrder) {
        if (!existByEmail(email))throw new NotFoundException("this expert id is null");
        Expert expert = findByEmail(email);
        return orderService.getOrdersForExpert(expert,statusOrder);
    }

}
