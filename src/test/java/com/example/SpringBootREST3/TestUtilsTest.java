package com.example.SpringBootREST3;

import com.example.SpringBootREST3.util.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SpringBootTest
public class TestUtilsTest {

    @InjectMocks
    @Spy
    private TestUtils testUtils;

    //test static method
    @Test
    @DisplayName("Test static method")
    public void testStaticMethod() {
        Mockito.mockStatic(TestUtils.class);
        Mockito.when(TestUtils.testMethod("aaa")).thenReturn("AAA");
    }

    //test private method with Reflections
    @Test
    public void testPrivateMethodUsingReflection() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = TestUtils.class.getDeclaredMethod("pvMethod", Integer.class);
        method.setAccessible(true);
        String actualResponse = (String) method.invoke(testUtils, 10);
        Assertions.assertEquals("20", actualResponse);
    }

    //test private method with Powermock
   /* @Test
    public void testPrivateMethodUsingPowerMock() throws Exception {
        String actualResponse = (String) Whitebox.invokeMethod(testUtils, "pvMethod", 10);
        Assertions.assertEquals("20",actualResponse);
    }*/

    //test void method
    @Test
    public void testCheckPet() {
        String heroNameInput = "Tintin";
        Mockito.doCallRealMethod()
                .when(testUtils)
                .checkPet(heroNameInput);
        testUtils.checkPet(heroNameInput);
        Mockito.verify(testUtils, Mockito.times(1)).checkPet(Mockito.anyString());
    }

    //test exception from method
    @Test
    public void checkPetThrowsException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            testUtils.checkPetThrowsException(Mockito.anyString());
        });
    }
}
