package com.example.final_project_4.service;





import com.example.final_project_4.entity.BasicService;

import java.util.Collection;

public interface BasicServiceService {
    boolean existByServiceName(String serviceName);

    BasicService findByServiceName(String serviceName);

    Collection<BasicService> loadAll();

    void saveOrUpdate(BasicService basicService);
}
