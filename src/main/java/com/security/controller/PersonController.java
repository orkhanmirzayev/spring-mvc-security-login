package com.security.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.security.model.Person;
import com.security.service.PersonService;

@Controller
@RequestMapping(value="/person")
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@GetMapping(value="/test")
	public String lognPage() {
		
		return "index";
	}
	@PostMapping(value="/add-person")
	public String registerPerson(Person p,HttpServletRequest request) {
		personService.createPerson(p);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(p.getUsername(), p.getPassword());
		SecurityContextHolder.getContext().setAuthentication(token);
		request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
		return "redirect:/person/test";
	}
	
}
