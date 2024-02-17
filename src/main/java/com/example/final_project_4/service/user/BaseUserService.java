package com.example.final_project_4.service.user;






import com.example.final_project_4.dto.UserSearch;
import com.example.final_project_4.entity.BaseUser;

import java.util.Collection;
import java.util.List;

public interface BaseUserService<T extends BaseUser>  {
    T changePassword(String email, String newPassword);
    boolean existByEmail(String email);

    T findByEmail(String email);

    Collection<T> loadAll();
    List<T> searchUsers(UserSearch searchCriteria);


    T findById(Integer id);


    boolean existById(Integer id);
}
