package com.ecom.webportal.registration.boundary;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
     
	@RequestMapping("/signup")
	public String signup() {
		return "signup";
		
	}
	
}
