package com.esgi.jee.basket;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class BasketApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void checkCircleCi() {

        org.junit.jupiter.api.Assertions.assertEquals("Test", "Test");
    }
}
