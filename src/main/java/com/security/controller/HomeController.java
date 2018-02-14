package com.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping(value= {"/","/home","/index"})
	public String indexPage() {
		return "/index";
	}
	
	@GetMapping(value="/login")
	public String lognPage(String logout, String error) {
		
		return "/login";
	}
	
	
}
