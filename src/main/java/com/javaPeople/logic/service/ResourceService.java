package com.javaPeople.logic.service;

import com.javaPeople.domain.CircleResource;
import com.javaPeople.repository.CircleResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class ResourceService {

    @Autowired
    private CircleResourceRepository resourceRepository;


    public List<CircleResource> findAllResources() {
        return resourceRepository.findAll();
    }


    public void saveNewResource(@NotNull CircleResource resource) {
        resourceRepository.save(resource);
    }

    public void editResource(@NotNull CircleResource resource) {resourceRepository.save(resource);}
}
