package com.example.final_project_4.service.user;





import com.example.final_project_4.dto.UserSearch;
import com.example.final_project_4.entity.BaseUser;
import com.example.final_project_4.entity.ConfirmationToken;
import com.example.final_project_4.exceptions.NoMatchResultException;
import com.example.final_project_4.exceptions.NotFoundException;
import com.example.final_project_4.repository.ConfirmationTokenRepository;
import com.example.final_project_4.repository.user.BaseUserRepository;
import com.example.final_project_4.service.impl.EmailService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RequiredArgsConstructor
@Transactional(readOnly = true)
public abstract class BaseUserServiceImpl<T extends BaseUser,R extends BaseUserRepository<T>>
        implements BaseUserService<T> {

    protected final R repository;
    protected final BCryptPasswordEncoder passwordEncoder;
    protected final ConfirmationTokenRepository confirmationTokenRepository;
    protected final EmailService emailService;



    @Transactional
    @Override
    public T changePassword(String email, String newPassword,String confirmPassword) {
        T user = repository.findByEmail(email).
                orElseThrow(() -> new NoMatchResultException("userName or password is wrong"));
        if (!Objects.equals(newPassword, confirmPassword))
            throw new NoMatchResultException("password not match");
        user.setPassword(passwordEncoder.encode(
                newPassword
        ));
        return repository.save(user);
    }

    @Override
    public boolean existByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public T findByEmail(String email) {
        return repository.findByEmail(email).
                orElseThrow(() -> new NotFoundException("this user not found"));
    }
    @Override
    public Optional<T> findByUsernameOptional(String email) {
        return repository.findByEmail(email);

    }

    @Override
    public Collection<T> loadAll() {
        return repository.findAll();
    }
    @Override
    public List<T> searchUsers(UserSearch searchCriteria) {
            return repository.findAll(getUserSpecification(searchCriteria));
        }

        private Specification<T> getUserSpecification(UserSearch searchCriteria) {
            return (root, query, cb) -> {
                Predicate predicate = cb.conjunction();


                if (searchCriteria.getRole() != null) {
                    predicate = cb.and(predicate, cb.equal(root.get("roll"), searchCriteria.getRole()));
                }

                if (searchCriteria.getFirstName() != null && !searchCriteria.getFirstName().isEmpty()) {
                    predicate = cb.and(predicate, cb.like(root.get("firstName"), "%" + searchCriteria.getFirstName() + "%"));
                }
                if (searchCriteria.getLastName() != null && !searchCriteria.getLastName().isEmpty()) {
                    predicate = cb.and(predicate, cb.like(root.get("lastName"), "%" + searchCriteria.getLastName() + "%"));
                }

                if (searchCriteria.getEmail() != null && !searchCriteria.getEmail().isEmpty()) {
                    predicate = cb.and(predicate, cb.like(root.get("email"), "%" +searchCriteria.getEmail()+ "%"));
                }

                if (searchCriteria.getExpertiseField() != null && !searchCriteria.getExpertiseField().isEmpty()) {
                    predicate = cb.and(predicate, cb.equal(root.get("subServices").get("subServiceName"), searchCriteria.getExpertiseField()));
                }

                if (searchCriteria.getMinRating() != 0) {
                    predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("score"), searchCriteria.getMinRating()));
                }

                if (searchCriteria.getMaxRating() != 0) {
                    predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("score"), searchCriteria.getMaxRating()));
                    query.orderBy(cb.desc(root.get("score")));
                }


                return predicate;
            };
        }
    @Override
    public T findById(Integer id){
       return repository.findById(id)
               .orElseThrow(() -> new NotFoundException("this id not found"));
    }

    @Override
    public boolean existById(Integer id){
        return repository.existsById(id);
    }
    @Override
    public void save(T user){
        repository.save(user);
    }

//    @Override
//    public double showCreditBalance(String email){
//        T t = findByEmail(email);
//        return t.getCredit().getBalance();
//
//    }
    @Override
    public void sendEmail(T user) {
    ConfirmationToken confirmationToken = new ConfirmationToken(user);
    confirmationTokenRepository.save(confirmationToken);
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setFrom("siavosh.arminrad@gmail.com");
    mailMessage.setTo(user.getEmail());
    mailMessage.setSubject("Complete Registration!");
    mailMessage.setText("To confirm your account, please click here : "
            +"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());

    emailService.sendEmail(mailMessage);
}

}











