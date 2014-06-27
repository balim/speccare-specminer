package com.michaelszymczak.livingdocumentation.specificationprovider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;

public class JsonScenarioRepositoryShould {

    @Test public void findScenarioByName() throws IOException {
//        sc.scenarios.add(ScenarioBuilder.use().withContent("Scenario: Foo").build());
//        Assert.assertEquals("Foo", repository.find("Foo"));
    }

    private ScenariosCreatorStub sc;
    private JsonScenarioRepository repository;

    @Rule public ExpectedException thrown = ExpectedException.none();
    @Before public void setUp() {
        sc = new ScenariosCreatorStub();
        ObjectScenarioRepository objectRepository = new ObjectScenarioRepository(sc);
        repository = new JsonScenarioRepository(objectRepository);
    }
}
