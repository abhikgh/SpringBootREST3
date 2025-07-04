package com.example.SpringBootREST3;

import com.example.SpringBootREST3.controller.NavController2;
import com.example.SpringBootREST3.exception.ErrorInfoMessageProperties;
import com.example.SpringBootREST3.exception.ErrorMessageProperties;
import com.example.SpringBootREST3.exception.ErrorProperties;
import com.example.SpringBootREST3.repository.MovieRepository;
import com.example.SpringBootREST3.service.HomeService;
import com.example.SpringBootREST3.util.OrderServiceUtil;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class NavController2Test {

    @Mock
    private MovieRepository movieRepository;

    private OrderServiceUtil orderServiceUtil;

    private HomeService homeService;

    private ErrorProperties errorProperties;

    private ErrorMessageProperties errorMessageProperties;

    private ErrorInfoMessageProperties errorInfoMessageProperties;

    private Validator validator;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        orderServiceUtil = new OrderServiceUtil();
        homeService = new HomeService();
        errorMessageProperties = new ErrorMessageProperties();
        errorInfoMessageProperties = new ErrorInfoMessageProperties();
        errorProperties = new ErrorProperties(errorMessageProperties, errorInfoMessageProperties);
        validator = jakarta.validation.Validation.buildDefaultValidatorFactory().getValidator();
        mockMvc = MockMvcBuilders.standaloneSetup(new NavController2(homeService, orderServiceUtil, errorProperties, validator)).build();
        ReflectionTestUtils.setField(homeService, "businessUnit", "CA,NA,LQ");
    }

    @Test
    public void testGetOrder3() throws Exception {
        String requestPath = "src/test/resources/request1.json";
        String request = readFromFile(requestPath);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/v3/rest/getOrder3/{month}/{colour}", "Feb", "Red")
                                                                    .header("Actor", "Microservice")
                                                                    .queryParam("parmRequestSource", "web")
                                                                    .queryParam("parmAudienceType","external")
                                                                    .content(request)
                                                                    .contentType(MediaType.APPLICATION_JSON)
                                                                    .accept(MediaType.APPLICATION_JSON))
                                      .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        String responsePath = "src/test/resources/response1.json";
        String expectedResponse = readFromFile(responsePath);
        JSONAssert.assertEquals(actualResponse, expectedResponse, JSONCompareMode.STRICT);
    }

    private String readFromFile(String requestPath) throws IOException {
        Path path = Paths.get(requestPath);
        return Files.readString(path);
    }

}
