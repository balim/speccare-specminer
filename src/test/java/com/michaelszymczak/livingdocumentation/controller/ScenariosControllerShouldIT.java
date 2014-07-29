package com.michaelszymczak.livingdocumentation.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.michaelszymczak.livingdocumentation.specificationprovider.FeaturesDirectory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/applicationTestContext.xml")
public class ScenariosControllerShouldIT {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;
    @Resource(name = "featuresDir") private FeaturesDirectory featuresDir;
    private MockMvc mockMvc;

    @Before
    public void setup() throws IOException {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void returnCorrectResponseContainingInfoAboutTheScenarioIfSearchedScenarioExists() throws Exception {
        givenFeatureFilesInFeatureDirectoryContainingScenariosNamed("Bar scenario of Foo feature", "", "", "");

        String response = mockMvc.perform(get("/scenarios/Bar"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        Map<String,String> expectedJsonParameters = new HashMap<>();
        expectedJsonParameters.put("name", "Bar scenario of Foo feature");
        expectedJsonParameters.put("result", "found");
        assertThatResponseIsValidJsonObjectWithParameters(response, expectedJsonParameters);
    }

    @Test
    public void recognizeSpaceInQuery() throws Exception {
        givenFeatureFilesInFeatureDirectoryContainingScenariosNamed("A b c d scenario", "", "", "");

        String response = mockMvc.perform(get("/scenarios/b c"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        Map<String,String> expectedJsonParameters = new HashMap<>();
        expectedJsonParameters.put("name", "A b c d scenario");
        expectedJsonParameters.put("result", "found");
        assertThatResponseIsValidJsonObjectWithParameters(response, expectedJsonParameters);
    }

    @Test
    public void returnNotFoundResponseIfSearchedScenarioDoesNotExist() throws Exception {
        givenFeatureFilesInFeatureDirectoryContainingScenariosNamed("Foo scenario", "", "", "");

        String response = mockMvc.perform(get("/scenarios/Bar"))
                .andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();

        Map<String,String> expectedJsonParameters = new HashMap<>();
        expectedJsonParameters.put("name", "Scenario not found");
        expectedJsonParameters.put("result", "notfound");
        assertThatResponseIsValidJsonObjectWithParameters(response, expectedJsonParameters);
    }

    @Test
    public void returnErrorIfAmbiguousSearchPhrase() throws Exception {
        givenFeatureFilesInFeatureDirectoryContainingScenariosNamed("Foo scenario", "", "Bar scenario", "");

        String response = mockMvc.perform(get("/scenarios/scenario"))
                .andExpect(status().isUnprocessableEntity()).andReturn().getResponse().getContentAsString();

        Map<String,String> expectedJsonParameters = new HashMap<>();
        expectedJsonParameters.put("result", "toomany");
        assertThatResponseIsValidJsonObjectWithParameters(response, expectedJsonParameters);
    }

    private void givenFeatureFilesInFeatureDirectoryContainingScenariosNamed(String... scenarioNames) throws FileNotFoundException {
        createFeatureFileInFeatureDirectory("A.feature",
                "Feature: feature 1",
                "",
                "In order to foo",
                "As bar",
                "I want to bar",
                "",
                "",
                "  Scenario: " + scenarioNames[0],
                "    Given sth in scenario 1 in feature 1",
                "    When  sth in scenario 1 in feature 1",
                "    Then  sth in scenario 1 in feature 1",
                "",
                "  Scenario: " + scenarioNames[1],
                "    Given sth in scenario 2 in feature 1",
                "    When  sth in scenario 2 in feature 1",
                "    Then  sth in scenario 2 in feature 1",
                ""
        );

        createFeatureFileInFeatureDirectory("B.feature",
                "Feature: feature 2",
                "",
                "In order to foo",
                "As bar",
                "I want to bar",
                "",
                "",
                "  Scenario: " + scenarioNames[2],
                "    Given sth in scenario 1 in2 feature 2",
                "    When  sth in scenario 1 in feature 2",
                "    Then  sth in scenario 1 in feature 2",
                "",
                "  Scenario: " + scenarioNames[3],
                "    Given sth in scenario 2 in feature 2",
                "    When  sth in scenario 2 in feature 2",
                "    Then  sth in scenario 2 in feature 2",
                ""
        );
    }

    private void assertThatResponseIsValidJsonObjectWithParameters(String jsonResponse, Map<String,String> expectedParameters) {
        JsonObject json = new JsonParser().parse(jsonResponse).getAsJsonObject();
        for (Map.Entry<String,String> entry : expectedParameters.entrySet()) {
            Assert.assertEquals(entry.getValue(), json.get(entry.getKey()).getAsString());
        }
    }



    private void createFeatureFileInFeatureDirectory(String filePath, String... content) throws FileNotFoundException {
        File featureFile = featuresDir.getPath().resolve(filePath).toFile();
        featureFile.deleteOnExit();
        PrintWriter writer = new PrintWriter(featureFile);
        for (String line : content) {
            writer.println(line);
        }
        writer.close();
    }

}
