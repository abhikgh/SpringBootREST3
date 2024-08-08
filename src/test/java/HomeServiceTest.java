import com.example.SpringBootREST3.service.HomeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith({SpringExtension.class})
class HomeServiceTest {

    @InjectMocks
    @Spy
    private HomeService homeService;

    @BeforeEach
    public void init() {
        //set private field
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

