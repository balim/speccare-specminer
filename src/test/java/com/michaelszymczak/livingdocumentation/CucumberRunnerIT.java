package com.michaelszymczak.livingdocumentation;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(glue = {"com.michaelszymczak.livingdocumentation"}, format = {"pretty", "html:target/cucumber", "junit:target/cucumber-junit.xml"} )
public class CucumberRunnerIT {

}
