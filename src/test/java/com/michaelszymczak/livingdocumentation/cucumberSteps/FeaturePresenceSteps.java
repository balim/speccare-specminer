package com.michaelszymczak.livingdocumentation.cucumberSteps;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.nio.file.Paths;

@ContextConfiguration("/cucumber.xml")
@WebAppConfiguration
public class FeaturePresenceSteps {

    private WebResponse response;

    @Given("^feature file \"(.*?)\" in the developed application:$")
    public void feature_file_in_the_developed_application(String featureFileName, String content) throws Throwable {
        temporaryFileCreator.createInDirWithContent(featuresDir, featureFileName, content);
    }

    @When("^I get \"(.*?)\"$")
    public void i_get(String uri) throws Throwable {
        response = client.getPage(siteUrl + uri).getWebResponse();
    }

    @Then("^the response should be OK$")
    public void the_response_should_be_OK() throws Throwable {
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Then("^the response content should be JSON:$")
    public void the_response_content_should_be_json(String expectedJsonString) throws Throwable {
        ScenarioJson expectedJson = ScenarioJson.createFromStringCorrectingFeaturesDir(expectedJsonString, featuresDir);
        ScenarioJson actualJson = ScenarioJson.createFromString(response.getContentAsString());

        Assert.assertEquals(expectedJson, actualJson);
    }



    private WebDriver driver;
    private String siteUrl;
    private WebClient client;

    private String featuresDir;

    @Autowired private TemporaryFileCreator temporaryFileCreator;

    @Before
    public void setUp() throws IOException {
        String jettyPort = System.getProperty("jetty.port");
        if (null == jettyPort) {
            throw new RuntimeException("You must set the jetty.port property first");
        }
        siteUrl = "http://localhost:" + jettyPort;
        driver = new HtmlUnitDriver();
        client = new WebClient();
        featuresDir = client.getPage(siteUrl + "/examples/featuresPath").getWebResponse().getContentAsString();
        Assert.assertTrue(Paths.get(featuresDir).toFile().listFiles().length == 0);
    }


    @After
    public void destroy() {
        if(driver != null) {
            driver.close();
        }
    }
}
