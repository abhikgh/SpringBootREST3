package com.example.SpringBootREST3;

import com.example.SpringBootREST3.service.HomeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class HomeServiceTest2 {

    private HomeService homeService;

    @BeforeEach
    public void init() {
        //set private field
        homeService = new HomeService();
        ReflectionTestUtils.setField(homeService, "value123", "123");
    }

    @Test
    void testValidateTestValue() {
        Assertions.assertEquals(300, homeService.validateTestValue());
        ReflectionTestUtils.setField(homeService, "value123", "200");
        Assertions.assertEquals(200, homeService.validateTestValue());
        ReflectionTestUtils.setField(homeService, "value123", "hdhdhdhd");
        Assertions.assertEquals(300, homeService.validateTestValue());
    }


}

