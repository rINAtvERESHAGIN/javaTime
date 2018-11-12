
package com.javaPeople.logic.service;

import com.javaPeople.controller.dto.CircleResourceDto;
import com.javaPeople.domain.CircleResource;
import com.javaPeople.domain.Contribution;
import com.javaPeople.domain.Event;
import com.javaPeople.repository.CircleResourceRepository;
import com.javaPeople.repository.ContributionRepository;
import com.javaPeople.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetProgressService {

    @Autowired
    private CircleResourceRepository resourceRepository;
    @Autowired
    private ContributionRepository contributionRepository;
    @Autowired
    private EventRepository eventRepository;



    public List<CircleResourceDto> getAllProgressForAllTime(){

        List<CircleResourceDto> resultDto = new ArrayList<>();


        List<CircleResource> allResources = resourceRepository.findAll();

        for (CircleResource resource: allResources){

            long value = 0l;
            List<Contribution> contributions = resource.getContributions();

            for (Contribution contribution : contributions){
                    List<Event> eventsForCurrentContribution = contribution.getEvents();
//                List<Event> eventsForCurrentContribution = eventRepository.findByContributionId(contribution.getId());

                value = value + eventsForCurrentContribution.size();
//                    for (Event event: eventsForCurrentContribution){
//                        value += 1;
//                    }

            }

            resultDto.add(CircleResourceDto.builder()
                    .name(resource.getName())
                    .value(value)
                    .build());

            System.out.println("**** DTO name " + resource.getName() + " " + value);
        }

        return resultDto;
    }
}
