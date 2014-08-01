package com.michaelszymczak.speccare.specminer.controller;

import com.michaelszymczak.speccare.specminer.specificationprovider.FeaturesDirectory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;

@Controller
@RequestMapping("/examples")
class ExamplesController {

    @Resource(name = "featuresDir") private FeaturesDirectory featuresDir;

    @RequestMapping(value="/featuresPath", method = RequestMethod.GET)
    @ResponseBody
    public String featuresPath() throws IOException {
        return featuresDir.getPath().toFile().getAbsolutePath();
    }
}
