package com.michaelszymczak.speccare.specminer.controller;

import com.michaelszymczak.speccare.specminer.domain.ScenarioFinalResult;
import com.michaelszymczak.speccare.specminer.domain.ScenarioResponse;
import com.michaelszymczak.speccare.specminer.specificationprovider.ObjectScenarioRepository;
import com.michaelszymczak.speccare.specminer.domain.Scenario;
import com.michaelszymczak.speccare.specminer.specificationprovider.ScenarioTypeHttpStatus;
import com.michaelszymczak.speccare.specminer.view.ScenarioJson;
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
    @Autowired private ScenarioFinalResult finalResult;

    @RequestMapping(value="/{scenarioNameFragment}", method=RequestMethod.GET,  produces = { "application/json"})
    @ResponseBody
    public ResponseEntity<String> find(@PathVariable String scenarioNameFragment) throws IOException {
        Scenario scenario = repository.find(scenarioNameFragment);
//        ScenarioResponse response = finalResult.createResponse(scenarioNameFragment);
        return new ResponseEntity<>(new ScenarioJson(scenario).toString(), scenarioHttpStatus.getStatus(scenario));
    }
}