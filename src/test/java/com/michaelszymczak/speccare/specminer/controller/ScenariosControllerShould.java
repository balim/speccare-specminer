package com.michaelszymczak.speccare.specminer.controller;
// TODO: http://stackoverflow.com/questions/16170572/unable-to-mock-service-class-in-spring-mvc-controller-tests

import com.michaelszymczak.speccare.specminer.core.ScenarioFinalResult;
import com.michaelszymczak.speccare.specminer.core.ScenarioResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/applicationTestContext.xml")
public class ScenariosControllerShould {
    @Mock private ScenarioFinalResult finalResult;
    @Mock private ScenarioResponse scenarioResponse;
    @InjectMocks ScenariosController controllerUnderTest;
    private MockMvc mockMvc;

    @Test public void returnStatusProvidedByFinalResultResponse() throws Exception {
        givenFinalResultReturnsResponseWithStatusForScenarioFragment("foo", HttpStatus.OK);
        assertResponseStatus(HttpStatus.OK, "/scenarios/foo");
    }

    @Test public void returnStatusProvidedByFinalResultResponse2() throws Exception {
        givenFinalResultReturnsResponseWithStatusForScenarioFragment("bar", HttpStatus.NOT_FOUND);
        assertResponseStatus(HttpStatus.NOT_FOUND, "/scenarios/bar");
    }

    @Test public void returnContentProvidedByFinalResultResponse() throws Exception {
        givenFinalResultReturnsResponseWithContentForScenarioFragment("foo", "{\"foo\":\"FOO\"}");
        assertResponseContent("{\"foo\":\"FOO\"}", "/scenarios/foo");
    }

    @Test public void returnContentProvidedByFinalResultResponse2() throws Exception {
        givenFinalResultReturnsResponseWithContentForScenarioFragment("bar", "{\"bar\":\"BAZ\"}");
        assertResponseContent("{\"bar\":\"BAZ\"}", "/scenarios/bar");
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest).build();
    }

    private void givenFinalResultReturnsResponseWithContentForScenarioFragment(String scenarioNameFragment, String content) throws IOException {
        when(scenarioResponse.getHttpStatus()).thenReturn(HttpStatus.OK);
        when(scenarioResponse.getContent()).thenReturn(content);
        when(finalResult.createResponse(scenarioNameFragment)).thenReturn(scenarioResponse);
    }

    private void givenFinalResultReturnsResponseWithStatusForScenarioFragment(String scenarioNameFragment, HttpStatus returnedStatus) throws IOException {
        when(scenarioResponse.getHttpStatus()).thenReturn(returnedStatus);
        when(scenarioResponse.getContent()).thenReturn("");
        when(finalResult.createResponse(scenarioNameFragment)).thenReturn(scenarioResponse);
    }

    private void assertResponseStatus(HttpStatus expectedStatus, String url) throws Exception {
        Assert.assertEquals(expectedStatus.value(), request(url).getStatus());
    }

    private void assertResponseContent(String expectedContent, String url) throws Exception {
        Assert.assertEquals(expectedContent, request(url).getContentAsString());
    }

    private MockHttpServletResponse request(String url) throws Exception {
        return mockMvc.perform(get(url)).andReturn().getResponse();
    }
}
