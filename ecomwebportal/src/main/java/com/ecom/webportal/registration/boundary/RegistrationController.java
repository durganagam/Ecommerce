package com.ecom.webportal.registration.boundary;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecom.webportal.registration.controller.RegistrationService;
import com.ecom.webportal.registration.entity.UserSignInRequest1;
import com.ecom.webportal.registration.entity.UserSignUpRequest;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/registration")
public class RegistrationController {

	@Autowired
	private RegistrationService registrationService;

	@RequestMapping("/signup")
	public String signup(final Model model) {
		registrationService.signUp(model);
		return "signup";
	}

	@RequestMapping("/signin")
	public String signin(final Model model) {
		registrationService.signIn(model);
		return "signin";
	}

	@PostMapping
	@RequestMapping("create")
	public String createUser(@ModelAttribute("userSignUpRequest") UserSignUpRequest userSignUpRequest,
			final Model model, final HttpSession session) {
		return registrationService.createUser(userSignUpRequest, model, session);
	}
	
	@PostMapping
	@RequestMapping("login")
	public String loginUser(@ModelAttribute("userSignInRequest") UserSignInRequest1 userSignUpRequest,
			final Model model, final HttpSession session) {
		return registrationService.login(userSignUpRequest, model, session);
	}

	@PostMapping
	@RequestMapping("otp/verify")
	public String verify(@ModelAttribute("userSignInRequest") UserSignInRequest1 userSignInRequest, final Model model,
			HttpSession session) {
		return registrationService.verifyOtp(userSignInRequest, model, session);
	}

	@PutMapping
	@RequestMapping("otp/resend")
	public String resend(HttpSession session) {
		registrationService.resendOTP(session);
		return "OTP sent successfully";
	}
}
