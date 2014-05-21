package com.michaelszymczak.livingdocumentation;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@Cucumber.Options(glue = {"com.michaelszymczak.livingdocumentation"}, format = {"pretty", "html:target/cucumber", "junit:target/cucumber-junit.xml"} )
public class CucumberRunnerIT {

}
