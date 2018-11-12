package com.javaPeople.logic.service;

import com.javaPeople.controller.dto.ContributionDto;
import com.javaPeople.domain.CircleResource;
import com.javaPeople.domain.Contribution;
import com.javaPeople.domain.Event;
import com.javaPeople.repository.ContributionRepository;
import com.javaPeople.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class DetailingService {

    @Autowired
    private ContributionRepository contributionRepository;
    @Autowired
    private EventRepository eventRepository;


    @NotNull
    public List<ContributionDto> detailsByResource(CircleResource resource){

        List<ContributionDto> resultDtoList = new ArrayList<>();

        List<Contribution> contributionList = contributionRepository.findByResource(resource);

        for (Contribution contribution : contributionList) {
//            List<Event> eventList = eventRepository.findByContribution(contribution);
            List<Event> eventList = contribution.getEvents();

            Long result =  contribution.getFactor() * eventList.size();

            resultDtoList.add(ContributionDto.builder()
                    .name(contribution.getName())
                    .value(result)
                    .build()
            );

        }
        return resultDtoList;

    }
}
