package com.javaPeople.controller.dto;

import com.javaPeople.domain.Event;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class RawEventDtoConverter {


    public RawEventDto convertToDto(Event event) {
        return RawEventDto.builder()
                .id(event.getId())
                .eventDate(event.getEventDate().format(DateTimeFormatter.ofPattern("MM.dd.yyyy")))
                .comment(event.getComment())
                .name(event.getName())
                .build();

    }

    public Event convertToEvent(RawEventDto eventDto) {
        return Event.builder()
                .id(eventDto.getId())
                .eventDate(LocalDate.parse(eventDto.getEventDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")))
                .comment(eventDto.getComment())
                .name(eventDto.getName())
                .build();

    }
}
