package com.javaPeople.logic.service;

import com.javaPeople.controller.dto.RawEventDto;
import com.javaPeople.controller.dto.RawEventDtoConverter;
import com.javaPeople.domain.Event;
import com.javaPeople.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private RawEventDtoConverter converter;


    public List<RawEventDto> findEventsByContributionId(@NotNull Long contributionId) {
        List<RawEventDto> resultList = new ArrayList<>();

        List<Event> eventList = eventRepository.findByContributionId(contributionId);

        for ( Event event : eventList ) {
            resultList.add(converter.convertToDto(event));
        }

        return resultList;
    }


    public List<RawEventDto> findAllEvents() {
        List<RawEventDto> resultList = new ArrayList<>();

        List<Event> eventList = eventRepository.findAll();

        for ( Event event : eventList ) {
            resultList.add(converter.convertToDto(event));
        }

        return resultList;

    }

    public void deleteEvents(List<RawEventDto> eventDtoList) {

        List<Event> eventList = new ArrayList<>();
        for ( RawEventDto eventDto : eventDtoList ) {
            eventList.add(converter.convertToEvent(eventDto));
        }

        List<Long> ids = new ArrayList<>();
        for (Event event: eventList) {
            ids.add(event.getId());
        }

        eventRepository.deleteByIds(ids);

    }
}
