package com.example.final_project_4.controller;


import com.example.final_project_4.dto.*;
import com.example.final_project_4.entity.BaseUser;
import com.example.final_project_4.entity.ConfirmationToken;


import com.example.final_project_4.repository.ConfirmationTokenRepository;
import com.example.final_project_4.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;
    private final ConfirmationTokenRepository confirmationTokenRepository;


    @PreAuthorize("hasAnyRole('EXPERT','CUSTOMER','ADMIN')")
    @PutMapping("changePassword")
    public ResponseEntity<String> changePassword(@RequestBody @Valid PasswordChangeDto dto){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.changePassword(email, dto.getPassword(),dto.getConfirmPassword());
        return ResponseEntity.ok(
                "password successfully changed"
        );
    }

    @PreAuthorize("hasAnyRole('EXPERT','CUSTOMER','ADMIN')")
    @GetMapping("showBalance")
    public ResponseEntity<CreditBalanceProjection> showBalance(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(
                userService.showBalance(email)
        );
    }


    @GetMapping("/register")
    public ModelAndView displayRegistration(ModelAndView modelAndView, BaseUser user)
    {
        modelAndView.addObject("user", user);
        modelAndView.setViewName("register");
        return modelAndView;
    }






    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null) {
            BaseUser user = userService.findByEmail(token.getUser().getEmail());
            user.setActive(true);
            userService.save(user);
            modelAndView.setViewName("accountVerified");
        }
        else
        {
            modelAndView.addObject("message","The link is invalid or broken!");
            modelAndView.setViewName("error");
        }

        return modelAndView;
    }

}
