package com.michaelszymczak.speccare.specminer.cucumberSteps;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.michaelszymczak.speccare.specminer.domain.JsonResult;
import com.michaelszymczak.speccare.specminer.domain.ResultStatus;
import com.michaelszymczak.speccare.specminer.specificationprovider.EncodingDetector;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@ContextConfiguration("/cucumber.xml")
@WebAppConfiguration
public class FeaturePresenceSteps {



    @Given("^feature file \"(.*?)\" in the developed application:$")
    public void feature_file_in_the_developed_application(String featureFileName, String content) throws Throwable {
        tempFilesToRemove.add(temporaryFileCreator.createInDirWithContent(featuresDir, featureFileName, content));
    }

    @When("^I get \"(.*?)\"$")
    public void i_get(String uri) throws Throwable {
        response = client.getPage(siteUrl + uri).getWebResponse();
    }

    @Then("^the response should be OK$")
    public void the_response_should_be_OK() throws Throwable {
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Then("^the response should be NOT FOUND$")
    public void the_response_should_be_NOT_FOUND() throws Throwable {
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Then("^the response should be UNPROCESSABLE ENTITY$")
    public void the_response_should_be_UNPROCESSABLE_ENTITY() throws Throwable {
        Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatusCode());
    }

    @Then("^the response content should be JSON:$")
    public void the_response_content_should_be_json(String expectedJsonString) throws Throwable {
        ScenarioJson expectedJson = ScenarioJson.createFromStringCorrectingFeaturesDir(expectedJsonString, featuresDir);
        ScenarioJson actualJson = ScenarioJson.createFromString(response.getContentAsString());

        Assert.assertEquals(expectedJson, actualJson);
    }

    @And("^result file with content from \"([^\"]*)\"$")
    public void result_file_with_content_from(String sourcePath) throws Throwable {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(sourcePath);
        tempFilesToRemove.add(temporaryFileCreator.createWithContent(resultFilePath, IOUtils.toString(stream, "UTF-8")));
    }

    @Given("^result file with one \"(.*?)\" passing scenario$")
    public void result_file_with_one_scenario_with_all_passing_steps(String scenarioName) throws Throwable {
        ResultStatus result = new JsonResult(new FileReader(resultFilePath)).getResult(scenarioName);
        Assert.assertEquals(ResultStatus.PASSED, result);
    }

    @Given("^OK$")
    public void gok() throws Throwable {
        Assert.assertTrue(true);
    }

    @Then("^FAIL$")
    public void tfail() throws Throwable {
        Assert.assertTrue(false);
    }




    private String siteUrl;
    private final WebClient client = new WebClient();

    private String featuresDir;
    private String resultFilePath;
    private WebResponse response;
    private final List<Path> tempFilesToRemove = new ArrayList<>();

    @Autowired private TemporaryFileCreator temporaryFileCreator;
    private final EncodingDetector encodingDetector = new EncodingDetector();

    @Before
    public void setUp() throws IOException {
        String jettyPort = System.getProperty("jetty.port");
        if (null == jettyPort) {
            throw new RuntimeException("You must set the jetty.port property first");
        }
        siteUrl = "http://localhost:" + jettyPort;
        featuresDir = client.getPage(siteUrl + "/examples/featuresPath").getWebResponse().getContentAsString();
        resultFilePath = client.getPage(siteUrl + "/examples/resultFilePath").getWebResponse().getContentAsString();
        client.getOptions().setThrowExceptionOnFailingStatusCode(false);
        assertNoFilesInFeaturesDir();
    }

    @After
    public void tearDown() throws IOException {
        removeAllTmpFiles();
        assertNoFilesInFeaturesDir();
    }


    private void assertNoFilesInFeaturesDir() {
        File[] files = Paths.get(featuresDir).toFile().listFiles();
        Assert.assertNotNull(files);
        Assert.assertTrue(files.length == 0);
    }

    private void removeAllTmpFiles() throws IOException {
        for (Path tmpFile : tempFilesToRemove) {
            Files.delete(tmpFile);
        }
        tempFilesToRemove.clear();
    }
}
