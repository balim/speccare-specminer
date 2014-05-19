package com.michaelszymczak.livingdocumentation;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@Cucumber.Options(glue = {"com.michaelszymczak.livingdocumentation"})
public class CucumberRunnerTest {

}
