package com.stelsey.project;

import com.stelsey.common.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerProjectService {

    private final ProjectRepository projectRepository;

    public CustomerProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional(readOnly = true)
    public List<CustomerProjectResponse> list(Long customerId){
        return projectRepository.findByCustomerIdOrderByCreatedAtDesc(customerId).stream()
                .map(CustomerProjectResponse::from)
                .toList();
    }


    @Transactional(readOnly = true)
    public CustomerProjectResponse get(Long customerId, Long projectId){
        return projectRepository.findByIdAndCustomerId(projectId, customerId)
                .map(CustomerProjectResponse::from)
                .orElseThrow(() -> new NotFoundException("Project", projectId));
    }
}
