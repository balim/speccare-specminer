package com.michaelszymczak.speccare.specminer.controller;

import com.michaelszymczak.speccare.specminer.specificationprovider.ObjectScenarioRepository;
import com.michaelszymczak.speccare.specminer.domain.Scenario;
import com.michaelszymczak.speccare.specminer.specificationprovider.ScenarioTypeHttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/scenarios")
class ScenariosController {


    @Autowired private ObjectScenarioRepository repository;
    @Autowired private ScenarioTypeHttpStatus scenarioHttpStatus;

    @RequestMapping(value="/{scenarioNameFragment}", method=RequestMethod.GET,  produces = { "application/json"})
    @ResponseBody
	public ResponseEntity<String> find(@PathVariable String scenarioNameFragment) throws IOException {
        Scenario scenario = repository.find(scenarioNameFragment);

        return new ResponseEntity<>(scenario.toJson(), scenarioHttpStatus.getStatus(scenario));
	}
}