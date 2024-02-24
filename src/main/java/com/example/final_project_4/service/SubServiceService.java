package com.example.final_project_4.service;





import com.example.final_project_4.entity.BasicService;
import com.example.final_project_4.entity.Expert;
import com.example.final_project_4.entity.SubService;


import java.util.Collection;

public interface SubServiceService{
    void deleteByEXPERT(SubService subService, Expert expert);

    void saveExpert(SubService subService, Expert expert);
    SubService editSubService(String subServiceName,double price,String description);

    Collection<SubService> findByService(BasicService basicService);

    boolean existByName(String subServiceName);

    void saveOrUpdate(SubService subService);

    Collection<SubService> loadAll();
    SubService findBySubServiceName(String subServiceName);

    Collection<SubService> subServiceHistory(String email);
}
