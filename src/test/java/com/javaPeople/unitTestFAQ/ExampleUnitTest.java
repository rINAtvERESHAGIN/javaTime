package com.javaPeople.unitTestFAQ;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
@DisplayName("Name for the following tests")
class ExampleUnitTest {

    @Test
    @DisplayName("name for simple test")
    void test_example() {

        assertEquals(1, 1);

    }


    @Test
    @DisplayName("test for many asserts")
    void test_example_many_asserts() {

        assertAll("verify many asserts",
                () -> assertEquals(1, 1),
                () -> assertEquals(2, 2),
                () -> assertEquals(3, 3)
        );

    }
}