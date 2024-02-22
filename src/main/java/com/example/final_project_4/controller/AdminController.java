package com.example.final_project_4.controller;



import com.example.final_project_4.dto.*;
import com.example.final_project_4.entity.*;



import com.example.final_project_4.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Validated
public class AdminController {
    protected final AdminService adminService;
    protected final ModelMapper modelMapper;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/saveBasicService")
    public ResponseEntity<Void> saveBasicService(@RequestBody @Valid AddBasicServiceDto dto){
        adminService.saveService(dto.getServiceName());
        return new ResponseEntity<>(HttpStatus.CREATED);

    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/saveSubService")
    public ResponseEntity<Void> saveSubService(@RequestBody @Valid SubServiceSaveDto dto){
        SubService subService= new SubService();
        subService.setSubServiceName(dto.getSubServiceName());
        subService.setBasePrice(dto.getBasePrice());
        subService.setDescription(dto.getDescription());
        adminService.saveSubService(dto.getServiceName(),subService);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteExpertOfSubService")
    public ResponseEntity<Void> deleteExpertFromSubService(@RequestParam String subServiceName,@RequestParam Integer expertId){
        adminService.deleteExpertFromSubService(subServiceName,expertId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/saveExpertForSubService")
    public ResponseEntity<Void> saveExpertForSubService(@RequestBody @Valid SaveExpertForSubService dto){
        adminService.saveExpertForSubService(dto.getSubServiceName(),dto.getExpertId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/showAllSubService")
    public ResponseEntity<Collection<SubServiceResponse>> showAllSubService(){
        Collection<SubService> subServices = adminService.ShowAllSubService();
        Collection<SubServiceResponse> responses = new ArrayList<>();
        for (SubService subService:subServices) {
            SubServiceResponse response = modelMapper.map(subService, SubServiceResponse.class);
            responses.add(response);
        }
        return ResponseEntity.ok(
                responses
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/showAllBasicService")
    public ResponseEntity<Collection<BasicService>> showAllBasicService(){
        return ResponseEntity.ok(adminService.showAllService());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/showAllExpert")
    public ResponseEntity<Collection<ExpertResponseDto>> showAllExpert(){
        Collection<Expert> experts = adminService.showAllExpert();
        Collection<ExpertResponseDto> responses = new ArrayList<>();
        for (Expert expert:experts) {
            ExpertResponseDto response = modelMapper.map(expert, ExpertResponseDto.class);
            responses.add(response);
        }
        return ResponseEntity.ok(
                responses
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/changeExpertStatus")
    public ResponseEntity<Void> changeExpertStatus(@RequestParam  Integer expertId){
        adminService.changeExpertStatus(expertId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/existByServiceName")
    public ResponseEntity<ResultDTO<Boolean>> existByServiceName(@RequestParam String serviceName){
        return ResponseEntity.ok(
                new ResultDTO<>(
                        adminService.existByServiceName(serviceName)
                )
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/editSubService")
    public ResponseEntity<Void> editSubService(@RequestBody @Valid EditSubServiceDto dto){
        adminService.editSubService(dto.getSubServiceName(), dto.getPrice(),dto.getDescription());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/search")
    public ResponseEntity<List<SearchUserResponse>> search(@RequestBody UserSearch dto){
        List<BaseUser> users = adminService.search(dto);
        List<SearchUserResponse> responses = new ArrayList<>();
        for (BaseUser user:users) {
            SearchUserResponse response = modelMapper.map(user, SearchUserResponse.class);
            responses.add(response);
        }
        return ResponseEntity.ok(
                responses
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/subServiceHistory")
    public ResponseEntity<Collection<SubService>> subServiceHistory(@RequestParam String email){
        return ResponseEntity.ok(
                adminService.subServiceHistory(email)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reported")
    public ResponseEntity<ReportForAdmin> reported(@RequestParam String email){
        return ResponseEntity.ok(
                adminService.reported(email)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/getFilteredOrderHistory")
    public ResponseEntity<List<OrderResponse>> getFilteredOrderHistory(OrderHistoryDto dto){
        List<Order> orderHistory = adminService.getFilteredOrderHistory(dto);
        List<OrderResponse> responses = new ArrayList<>();
        for (Order order:orderHistory) {
            OrderResponse response = modelMapper.map(order, OrderResponse.class);
            responses.add(response);
        }
        return ResponseEntity.ok(
                responses
        );
    }


}
