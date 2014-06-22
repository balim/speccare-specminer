package com.michaelszymczak.livingdocumentation.sandbox;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import static org.junit.Assert.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@ContextConfiguration("file:src/test/resources/applicationTestContext.xml")
public class AppScenario {
    @Given("^I am on the welcome page$")
    public void i_am_on_the_welcome_page() throws Throwable {
        assertTrue(true);
    }

    @Then("^I can see some text$")
    public void i_can_see_some_text() throws Throwable {
        assertTrue(true);
    }
}
