package com.michaelszymczak.livingdocumentation.controller;

import com.michaelszymczak.livingdocumentation.specificationprovider.FeaturesConfigurableDirectory;
import com.michaelszymczak.livingdocumentation.specificationprovider.FeaturesDirectory;
import com.michaelszymczak.livingdocumentation.specificationprovider.FeaturesTemporaryDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

@Controller
@RequestMapping("/")
public class HelloController {


    @Autowired private WebApplicationContext wac;
    @Resource(name = "featuresDir") private FeaturesDirectory featuresDir;

	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		model.addAttribute("title", "LivingDocumentation " + wac.getEnvironment());
		model.addAttribute("environment", wac.getEnvironment());
		model.addAttribute("details", "@" + featuresDir.getPath());
		return "hello";
	}
}