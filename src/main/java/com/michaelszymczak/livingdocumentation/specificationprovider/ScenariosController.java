package com.michaelszymczak.livingdocumentation.specificationprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/scenarios")
public class ScenariosController {


    @Autowired
    private ScenarioRepository repository;

    @RequestMapping(value="/{scenarioNameFragment}", method=RequestMethod.GET,  produces = { "application/json"})
    @ResponseBody
	public ResponseEntity<String> find(@PathVariable String scenarioNameFragment) throws IOException {
        try {

            Scenario scenario = repository.find(scenarioNameFragment);
            if (scenario instanceof NotFoundScenario) {
                return new ResponseEntity<>(scenario.toJson(), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(scenario.toJson(), HttpStatus.OK);

        } catch (TooManyScenariosFound e ) {
            return new ResponseEntity<>("{\"result\":\"toomany\",\"details\":\"" + e.getMessage() + "\"}", HttpStatus.BAD_REQUEST);
        }


	}

}