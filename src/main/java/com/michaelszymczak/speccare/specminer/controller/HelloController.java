package com.michaelszymczak.speccare.specminer.controller;

import com.michaelszymczak.speccare.specminer.domain.ResultLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

@Controller
@RequestMapping("/")
class HelloController {


    @Autowired private WebApplicationContext wac;
    @Resource(name = "resultLocator") private ResultLocator featuresDir;

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        model.addAttribute("environment", wac.getEnvironment());
        model.addAttribute("featuresDir", featuresDir.getFeaturesDirPath());
        model.addAttribute("resultFile", featuresDir.getResultFilePath());
        return "hello";
    }
}