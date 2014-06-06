package com.michaelszymczak.livingdocumentation.sandbox;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/hello")
public class HelloController {
	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
        Foo foo = new Foo();
		model.addAttribute("message", "Hello world! It's com.michaelszymczak.livingdocumentation.sandbox.HelloController! " + foo.getBar());
		return "hello";
	}
}