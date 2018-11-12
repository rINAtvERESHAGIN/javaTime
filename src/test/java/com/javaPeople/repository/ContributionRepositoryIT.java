package com.javaPeople.repository;

import com.javaPeople.domain.CircleResource;
import com.javaPeople.domain.Contribution;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

class ContributionRepositoryIT extends JpaDataConfigIT {

    @Autowired
    private CircleResourceRepository resourceRepository;
    @Autowired
    private ContributionRepository contributionRepository;
    @Autowired
    private TransactionTemplate transactionTemplate;



    @AfterEach
    void tearDown() {
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(status -> {
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
                .factor(9L)
                .build();

        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(status -> {
            resourceRepository.save(studyResource);
            contributionRepository.saveAll(Arrays.asList(readingBooks, watchingTutorials, coddingProject));
            status.flush();

            return status;
        });


        assertAll("assert set",
                () -> assertEquals(1, resourceRepository.findAll().size()),
                () -> assertEquals(3, contributionRepository.findAll().size())
        );

    }

}