package com.michaelszymczak.speccare.specminer.controller;

import com.michaelszymczak.speccare.specminer.core.ResultLocator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;

@Controller
@RequestMapping("/examples")
class ExamplesController {

    @Resource(name = "resultLocator") private ResultLocator featuresDir;

    @RequestMapping(value="/featuresPath", method = RequestMethod.GET)
    @ResponseBody
    public String featuresPath() throws IOException {
        return featuresDir.getFeaturesDirPath().toFile().getAbsolutePath();
    }

    @RequestMapping(value="/resultFilePath", method = RequestMethod.GET)
    @ResponseBody
    public String resultFilePath() throws IOException {
        return featuresDir.getResultFilePath().toFile().getAbsolutePath();
    }
}
