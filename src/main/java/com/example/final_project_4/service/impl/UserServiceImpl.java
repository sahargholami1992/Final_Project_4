package com.example.final_project_4.service.impl;




import com.example.final_project_4.dto.CreditBalanceProjection;
import com.example.final_project_4.entity.BaseUser;
import com.example.final_project_4.entity.Credit;
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

    public UserServiceImpl(UserRepository repository,BCryptPasswordEncoder passwordEncoder,  CreditService creditService) {
        super(repository,passwordEncoder);
        this.creditService = creditService;
    }

    @Override
    public double showCreditBalance(String email) {
        BaseUser baseUser = findByEmail(email);
        Credit credit = creditService.findByBaseUser(baseUser);
        return credit.getBalance();
    }

    @Override
    public CreditBalanceProjection showBalance(String email) {
        BaseUser baseUser = findByEmail(email);
        return creditService.findBalanceByBaseUser(baseUser);
    }
}
