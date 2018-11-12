package com.javaPeople.repository;

import com.javaPeople.domain.CircleResource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;


public class CircleResourceRepositoryIT extends JpaDataConfigIT {

    @Autowired
    private CircleResourceRepository resourceRepository;
    @Autowired
    private TransactionTemplate transactionTemplate;



    @AfterEach
    void tearDown() {
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(status -> {
            resourceRepository.deleteAll();
            status.flush();

            return status;
        });
    }


    @Test
    void test() {
        CircleResource resource = CircleResource.builder()
                .name("Study")
                .build();

        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(status -> {
            resourceRepository.save(resource);
            status.flush();

            return status;
        });


        assertAll("assert set",
                () -> assertEquals(1, resourceRepository.findAll().size())
        );
    }


}
