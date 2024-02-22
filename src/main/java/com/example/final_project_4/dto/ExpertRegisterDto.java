package com.example.final_project_4.dto;


import com.example.final_project_4.entity.enumaration.ExpertStatus;
import com.example.final_project_4.entity.enumaration.Permissions;
import com.example.final_project_4.entity.enumaration.Roll;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ExpertRegisterDto extends CustomerRegisterDto {

    private ExpertStatus expertStatus;
    private int score;
    private MultipartFile imageFile;

    public ExpertRegisterDto(String firstName, String lastName, String email, String password, MultipartFile imagePath) {
        super(firstName,lastName,email,password);
        super.setRoll(Roll.ROLE_EXPERT) ;
        super.setPermission(Permissions.WAITING);
        this.expertStatus = ExpertStatus.AWAITING_CONFIRMATION;
        this.score = 0;
        this.imageFile = imagePath;
    }

}

