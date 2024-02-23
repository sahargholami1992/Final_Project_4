package com.example.final_project_4.service.user;






import com.example.final_project_4.dto.UserSearch;
import com.example.final_project_4.entity.BaseUser;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BaseUserService<T extends BaseUser>  {
    T changePassword(String email, String newPassword,String confirmPassword);
    boolean existByEmail(String email);

    T findByEmail(String email);
    Optional<T> findByUsernameOptional(String email);

    Collection<T> loadAll();
    List<T> searchUsers(UserSearch searchCriteria);


    T findById(Integer id);


    boolean existById(Integer id);

    void save(T user);
    void sendEmail(T user);
    void confirmEmail(String confirmationToken);
}
