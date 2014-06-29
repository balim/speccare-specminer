package com.michaelszymczak.livingdocumentation.cucumberSteps;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration("/cucumber.xml")
@WebAppConfiguration
public class FeaturePresenceSteps {

    @Given("^documentation that reads:$")
    public void documentation_that_reads(String documentation) throws Throwable {
        driver.get(siteUrl + "/");
        System.out.println(driver.getPageSource());
    }

//    @Given("^feature file \"(.*?)\" in the developed application:$")
//    public void feature_file_in_the_developed_application(String featureFileName, String content) throws Throwable {
//    }
//
//    @Then("^\"(.*?)\" scenario should be marked as present$")
//    public void scenario_should_be_marked_as_present(String scenarioName) throws Throwable {
//    }
//
//    @Then("^\"(.*?)\" scenario should be marked as absent$")
//    public void scenario_should_be_marked_as_absent(String scenarioName) throws Throwable {
//    }

    private WebDriver driver;
    private String siteUrl;

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
