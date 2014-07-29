package com.michaelszymczak.livingdocumentation.controller;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/applicationTestContext.xml")
public class ExamplesControllerShouldIT {
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void returnPathToFeaturesDirectoryThatCanBeUsedInTests() throws Exception {
        String response = mockMvc.perform(get("/examples/featuresPath"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThatResponseContainsTemporaryPathToFeaturesDirectory(response);
    }

    private void assertThatResponseContainsTemporaryPathToFeaturesDirectory(String response) {
        Assert.assertTrue(response.startsWith(System.getProperty("java.io.tmpdir")));
    }
}