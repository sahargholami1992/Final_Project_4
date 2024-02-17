package com.example.final_project_4.service.impl;




import com.example.final_project_4.entity.BaseUser;
import com.example.final_project_4.repository.UserRepository;
import com.example.final_project_4.service.UserService;
import com.example.final_project_4.service.user.BaseUserServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Transactional(readOnly = true)
@Service
public class UserServiceImpl extends BaseUserServiceImpl<BaseUser, UserRepository>
        implements UserService {

    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }

}
