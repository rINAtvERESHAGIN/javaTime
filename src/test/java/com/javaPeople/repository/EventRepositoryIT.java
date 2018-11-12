package com.javaPeople.repository;

import com.javaPeople.domain.CircleResource;
import com.javaPeople.domain.Contribution;
import com.javaPeople.domain.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

class EventRepositoryIT extends JpaDataConfigIT {

    @Autowired
    private CircleResourceRepository resourceRepository;
    @Autowired
    private ContributionRepository contributionRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private TransactionTemplate transactionTemplate;



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
        CircleResource studyResource = CircleResource.builder()
                .name("Study")
                .build();

        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(status -> {
            resourceRepository.save(studyResource);
            status.flush();

            return status;
        });

        Contribution readingBooks = Contribution.builder()
                .name("Reading books")
                .resource(studyResource)
                .factor(5L)
                .build();

        Contribution watchingTutorials = Contribution.builder()
                .name("Watching tutorials")
                .resource(studyResource)
                .factor(2L)
                .build();

        Contribution coddingProject = Contribution.builder()
                .name("Codding my project")
                .resource(studyResource)
                .factor(3L)
                .build();

        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(status -> {
            contributionRepository.saveAll(Arrays.asList(readingBooks, watchingTutorials, coddingProject));
            status.flush();

            return status;
        });

        Event firstReading = Event.builder()
                .contribution(readingBooks)
                .eventDate(LocalDate.now())
                .comment("20 minutes Effective Java book")
                .name("Java book")
                .build();

        Event firstCodding = Event.builder()
                .contribution(coddingProject)
                .eventDate(LocalDate.now())
                .comment("2 hours to add IT test in project")
                .name("w hours")
                .build();

        Event secondCodding = Event.builder()
                .contribution(coddingProject)
                .eventDate(LocalDate.now())
                .comment("1 hour to add services to project")
                .name("lalala")
                .build();

        Event thirdCodding = Event.builder()
                .contribution(coddingProject)
                .eventDate(LocalDate.now())
                .comment("1 hour to add new controller to project")
                .name("1 hour event")
                .build();

        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(status -> {
            eventRepository.saveAll(Arrays.asList(firstReading, firstCodding, secondCodding, thirdCodding));
            status.flush();

            return status;
        });



        assertAll("assert set",
                () -> assertEquals(1, resourceRepository.findAll().size()),
                () -> assertEquals(3, contributionRepository.findAll().size()),
                () -> assertEquals(4, eventRepository.findAll().size())
        );

    }

}