package com.example.piwater;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"gpio.enable=false", "gpio.mock=true"})
class PiWaterApplicationTests {

    @Test
    void contextLoads() {
    }

}
