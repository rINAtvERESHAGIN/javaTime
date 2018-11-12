package com.javaPeople.logic.service;

import com.javaPeople.controller.dto.CircleResourceDto;
import com.javaPeople.domain.CircleResource;
import com.javaPeople.domain.Contribution;
import com.javaPeople.domain.Event;
import com.javaPeople.repository.CircleResourceRepository;
import com.javaPeople.repository.ContributionRepository;
import com.javaPeople.repository.EventRepository;
import org.junit.Assert;
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

@Import({GetProgressService.class})
public class GetProgressServiceIT extends JpaDataConfigIT {

    @Autowired
    private CircleResourceRepository resourceRepository;
    @Autowired
    private ContributionRepository contributionRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private GetProgressService progressService;

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

    @Test
    void test() {
        CircleResource resource = CircleResource.builder()
                .name("test resource")
                .build();

        Contribution firstContribution = Contribution.builder()
                .resource(resource)
                .name("test contribution 1")
                .factor(1L)
                .build();

        Contribution secondContribution = Contribution.builder()
                .resource(resource)
                .name("test contribution 2")
                .factor(3L)
                .build();

        Event firstEventForFirstContribution = Event.builder()
                .contribution(firstContribution)
                .eventDate(LocalDate.now())
                .comment("eventOne")
                .name("first")
                .build();

        Event secondEventForFirstContribution = Event.builder()
                .contribution(firstContribution)
                .eventDate(LocalDate.now())
                .comment("eventTwo")
                .name("second")
                .build();

        Event thirdEventForFirstContribution = Event.builder()
                .contribution(firstContribution)
                .eventDate(LocalDate.now())
                .comment("eventOne")
                .name("third")
                .build();

        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(status -> {
            resourceRepository.save(resource);
            contributionRepository.saveAll(Arrays.asList(firstContribution, secondContribution));
            eventRepository.saveAll(
                    Arrays.asList(firstEventForFirstContribution, secondEventForFirstContribution, thirdEventForFirstContribution)
            );
            status.flush();

            return status;
        });


        List<CircleResourceDto> resultDTO = progressService.getAllProgressForAllTime();


        assertAll("assert set",
                () -> Assert.assertEquals(1, resultDTO.size()),
                () -> Assert.assertEquals("test resource", resultDTO.get(0).getName()),
                () -> Assert.assertEquals(3, resultDTO.get(0).getValue().intValue())
        );
    }


}