package com.example.final_project_4.service.impl;




import com.example.final_project_4.dto.CreditBalanceProjection;
import com.example.final_project_4.entity.BaseUser;
import com.example.final_project_4.repository.ConfirmationTokenRepository;
import com.example.final_project_4.repository.UserRepository;
import com.example.final_project_4.service.CreditService;
import com.example.final_project_4.service.UserService;
import com.example.final_project_4.service.user.BaseUserServiceImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Transactional(readOnly = true)
@Service
public class UserServiceImpl extends BaseUserServiceImpl<BaseUser, UserRepository>
        implements UserService {
    protected final CreditService creditService;


    public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder passwordEncoder,ConfirmationTokenRepository confirmationTokenRepository,EmailService emailService, CreditService creditService) {
        super(repository,passwordEncoder,confirmationTokenRepository,emailService);
        this.creditService = creditService;

    }


    @Override
    public CreditBalanceProjection showBalance(String email) {
        BaseUser baseUser = findByEmail(email);
        return creditService.findBalanceByBaseUser(baseUser);
    }

}
