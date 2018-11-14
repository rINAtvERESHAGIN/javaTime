package com.javaPeople.logic.service;

import com.javaPeople.controller.dto.CircleResourceDto;
import com.javaPeople.domain.CircleResource;
import com.javaPeople.domain.Contribution;
import com.javaPeople.repository.CircleResourceRepository;
import com.javaPeople.repository.ContributionRepository;
import com.javaPeople.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceService {

    @Autowired
    ContributionRepository contributionRepository;
    @Autowired
    CircleResourceRepository circleResourceRepository;
    @Autowired
    EventRepository eventRepository;


    public List<CircleResource> findAllResources() {
        return circleResourceRepository.findAll();
    }


    public void saveNewResource(@NotNull CircleResource resource) {
        circleResourceRepository.save(resource);
    }

    public void editResource(@NotNull CircleResource resource) {circleResourceRepository.save(resource);}

    public List<CircleResourceDto> resourceWithMaximumFactor() {
        List<CircleResourceDto> resultDtoList = new ArrayList<>();

        Contribution contribution = contributionRepository.findResourceByMaximumFactorFromComtribution();
        resultDtoList.add(CircleResourceDto
                .builder()
                .name(contribution.getResource().getName())
                .value(1L)
                .build()
        );
        return resultDtoList;
    }
    // реесурс с наибольшим колличеством событий Ринат
    public List<CircleResourceDto> resourceWithTheMostEvents(){
        List<CircleResourceDto> resultDtoList = new ArrayList<>();
        List<CircleResource> resources = circleResourceRepository.findAll();
        List<Contribution> contributionList;

        String nameResourceMax = resources.get(0).getName();
        int maxEvent = 0;

        for (CircleResource resource:resources) {
            contributionList = contributionRepository.findByResourceId(resource.getId());

            int allEventSize = 0;
            for (Contribution contribution:contributionList) {
                int eventSize = eventRepository.findEventCountByContributionId(contribution.getId());
                allEventSize+=eventSize;
            }

            if (maxEvent < allEventSize){
                nameResourceMax = resource.getName();
                maxEvent = allEventSize;
            }
        }
        resultDtoList.add(CircleResourceDto
                .builder()
                .name(nameResourceMax)
                .value(1L)
                .build()
        );

        return resultDtoList;
    }
}
