package com.michaelszymczak.livingdocumentation.sandbox;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration("/cucumber.xml")
@WebAppConfiguration
public class AppScenario {
    @Given("^I am on the welcome page$")
    public void i_am_on_the_welcome_page() throws Throwable {
        driver.get("http://localhost:9999");
    }

    @Then("^I can see some text$")
    public void i_can_see_some_text() throws Throwable {
        Assert.assertTrue(driver.getPageSource().contains("LivingDocumentation"));
    }

    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new HtmlUnitDriver();
    }

    @After
    public void destroy() {
        if(driver != null) {
            driver.close();
        }
    }
}
