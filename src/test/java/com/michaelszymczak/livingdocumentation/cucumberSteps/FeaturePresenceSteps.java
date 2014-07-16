package com.michaelszymczak.livingdocumentation.cucumberSteps;

import com.michaelszymczak.livingdocumentation.specificationprovider.FeaturesTemporaryDirectory;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Path;

@ContextConfiguration("/cucumber.xml")
@WebAppConfiguration
public class FeaturePresenceSteps {


    public static final String EXAMPLE_SCENARIOS_URL = "/examples/test.html";

    @Given("^documentation that reads:$")
    public void documentation_that_reads(final String documentation) throws Throwable {
        driver.get(siteUrl + "/examples/test.html");
        String html = driver.getPageSource();
        Assert.assertTrue(html.contains(documentation));
    }

    @Given("^feature file \"(.*?)\" in the developed application:$")
    public void feature_file_in_the_developed_application(String featureFileName, String content) throws Throwable {
        File featureFile = featuresDir.getPath().resolve(featureFileName).toFile();
        featureFile.createNewFile();
        featureFile.deleteOnExit();
        PrintWriter pw = new PrintWriter(featureFile);
        pw.println(content);
        pw.close();
    }

//    @Then("^scenario should be marked as present:$")
//    public void scenario_should_be_marked_as_present(String content) throws Throwable {
//        driver.get(siteUrl + EXAMPLE_SCENARIOS_URL);
//        String html = driver.getPageSource();
//        Assert.assertTrue(html.contains("<span class=\"scenario-present\">" + content + "</span>"));
//    }

    private WebDriver driver;
    private String siteUrl;

    @Autowired private FeaturesTemporaryDirectory featuresDir;

    @Before
    public void setUp() {
        String jettyPort = System.getProperty("jetty.port");
        if (null == jettyPort) {
            throw new RuntimeException("You must set the jetty.port property first");
        }
        siteUrl = "http://localhost:" + jettyPort;
        driver = new HtmlUnitDriver();
    }


    @After
    public void destroy() {
        if(driver != null) {
            driver.close();
        }
    }
}
