package com.michaelszymczak.livingdocumentation.specificationprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/scenarios")
public class ScenarioController {


    @Autowired
    private ScenarioRepository repository;

    @RequestMapping(value="/{scenarioNameFragment}", method=RequestMethod.GET,  produces = { "application/json"})
    @ResponseBody
	public String find(@PathVariable String scenarioNameFragment) {
        return "{\"foo\":\"bar\"}";
//		return "/scenarios/" + scenarioNameFragment + repository.getClass();
	}

}