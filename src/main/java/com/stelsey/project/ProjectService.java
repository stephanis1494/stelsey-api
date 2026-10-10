package com.stelsey.project;

import java.util.List;

import com.stelsey.common.NotFoundException;
import com.stelsey.customer.Customer;
import com.stelsey.customer.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final CustomerRepository customerRepository;

    public ProjectService(ProjectRepository projectRepository, CustomerRepository customerRepository) {
        this.projectRepository = projectRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public List<ProjectResponse> listForCustomer(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new NotFoundException("Customer", customerId);
        }
        return projectRepository.findByCustomerIdOrderByCreatedAtDesc(customerId).stream()
                .map(ProjectResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProjectResponse get(Long id) {
        return ProjectResponse.from(findProject(id));
    }

    @Transactional
    public ProjectResponse create(Long customerId, CreateProjectRequest request) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer", customerId));

        Project project = new Project(customer, request.name().strip(), normalize(request.description()));

        return ProjectResponse.from(projectRepository.save(project));
    }

    @Transactional
    public ProjectResponse update(Long id, UpdateProjectRequest request) {
        Project project = findProject(id);

        if (request.name() != null) {
            project.rename(request.name().strip());
        }
        if (request.description() != null) {
            project.changeDescription(normalize(request.description()));
        }
        if (request.status() != null) {
            project.changeStatus(request.status());
        }

        projectRepository.flush();
        return ProjectResponse.from(project);
    }

    private Project findProject(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new NotFoundException("Project", id));
    }

    private static String normalize(String description) {
        if (description == null || description.isBlank()) {
            return null;
        }
        return description.strip();
    }
}
