package com.michaelszymczak.speccare.specminer;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(glue = {"com.michaelszymczak.speccare.specminer"}, format = {"pretty", "html:target/cucumber", "junit:target/cucumber-junit.xml", "json:target/cucumber-json.json"} )
public class CucumberRunnerIT {

}
