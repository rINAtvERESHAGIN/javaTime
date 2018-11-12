package com.javaPeople.logic.service;

import com.javaPeople.controller.dto.ContributionDto;
import com.javaPeople.domain.CircleResource;
import com.javaPeople.domain.Contribution;
import com.javaPeople.domain.Event;
import com.javaPeople.repository.CircleResourceRepository;
import com.javaPeople.repository.ContributionRepository;
import com.javaPeople.repository.EventRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Import({DetailingService.class})
class DetailingServiceIT extends JpaDataConfigIT {

    @Autowired
    private CircleResourceRepository resourceRepository;
    @Autowired
    private ContributionRepository contributionRepository;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private DetailingService detailingService;



    @Test
    void test() {
        // заполнение
        CircleResource resource = CircleResource.builder()
                .name("test resource")
                .build();
        Contribution contribution1 = Contribution.builder()
                .name("Good")
                .factor(12L)
                .resource(resource)
                .build();
        Contribution contribution2 = Contribution.builder()
                .name("Well")
                .factor(10L)
                .resource(resource)
                .build();
        Contribution contribution3 = Contribution.builder()
                .name("Bad")
                .factor(5L)
                .resource(resource)
                .build();
        Event event1 = Event.builder()
                .eventDate(LocalDate.now())
                .comment("SOOOOOO")
                .name("Soooo")
                .contribution(contribution1)
                .build();
        Event event2 = Event.builder()
                .eventDate(LocalDate.now())
                .comment("good work")
                .name("good")
                .contribution(contribution2)
                .build();
        Event event3 = Event.builder()
                .eventDate(LocalDate.now())
                .comment("few")
                .name("few-few")
                .contribution(contribution2)
                .build();
        // создает
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(status -> {
            resourceRepository.save(resource);
            contributionRepository.saveAll(Arrays.asList(contribution1,contribution2,contribution3));
            eventRepository.saveAll(Arrays.asList(event1,event2,event3));
            status.flush();
            return status;
        });

        List<ContributionDto> contributionDtoList = detailingService.detailsByResource(resource);
        assertAll("verify many asserts",
                () -> assertEquals(3, contributionDtoList.size()),
                () -> assertEquals("Good", contributionDtoList.get(0).getName()),
                () -> assertEquals(12, contributionDtoList.get(0).getValue().intValue()),
                () -> assertEquals("Well", contributionDtoList.get(1).getName()),
                () -> assertEquals(20, contributionDtoList.get(1).getValue().intValue()),
                () -> assertEquals("Bad", contributionDtoList.get(2).getName()),
                () -> assertEquals(0, contributionDtoList.get(2).getValue().intValue())
        );
    }

    @AfterEach
    void tearDown() {
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(status -> {
            eventRepository.deleteAll();
            contributionRepository.deleteAll();
            resourceRepository.deleteAll();
            status.flush();

            return status;
        });
    }

}