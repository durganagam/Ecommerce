package com.ecom.webportal.registration.controller;

import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.reactive.function.client.WebClient;

import com.ecom.webportal.registration.entity.Name;
import com.ecom.webportal.registration.entity.OtpVerificationRequest;
import com.ecom.webportal.registration.entity.User;
import com.ecom.webportal.registration.entity.UserSignInRequest;
import com.ecom.webportal.registration.entity.UserSignUpRequest;

import io.netty.handler.codec.http.multipart.Attribute;

@Service
public class RegistrationService {

	/**
	 * Method is responsible to add {@link UserSignInRequest} attribute to model. so
	 * added {@link Attribute} further used by thymeleaf template engine to bind
	 * form data.
	 * 
	 * @param model {@link Model}
	 */
	public void signUp(final Model model) {
		UserSignUpRequest userSignUpRequest = new UserSignUpRequest();
		userSignUpRequest.setName(new Name());
		model.addAttribute("userSignUpRequest", userSignUpRequest);
	}

	/**
	 * Method is responsible to add {@link UserSignInRequest} attribute to model.
	 * 
	 * @param model {@link Model}
	 */
	public void signIn(final Model model) {
		UserSignInRequest userSignInRequest = new UserSignInRequest();
		userSignInRequest.setUserId("");
		userSignInRequest.setOtp("");
		model.addAttribute("userSignInRequest", userSignInRequest);
	}

	/**
	 * Method create a new user with the given input. It internally calls user
	 * management service to save the save the user details.
	 * 
	 * @param userSignUpRequest {@link UserSignUpRequest}
	 * @param model             {@link Model}
	 * @param session           {@link HttpSession}
	 * @return otp if user is successfully registered else return the appropriate
	 *         error page.
	 */
	public String createUser(final UserSignUpRequest userSignUpRequest, Model model, HttpSession session) {
		User user = callUserMgmtServiceToCreateUser(userSignUpRequest);
		if (Objects.nonNull(user)) {
			UserSignInRequest userSignInRequest = new UserSignInRequest();
			userSignInRequest.setMobileNo(user.getMobileNo());
			userSignInRequest.setUserId(user.getUserId());
			userSignInRequest.setOtp("");
			session.setAttribute("userId", user.getUserId());
			model.addAttribute("userSignInRequest", userSignInRequest);
			return "otp";
		}
		return "signin";
	}

	public String login(UserSignInRequest userSignInRequest, Model model, HttpSession session) {
		User user = callUserMgmtServiceToLoginUser(userSignInRequest);
		if (user != null) {
			session.setAttribute("userId", user.getUserId());
			return "otp";
		}
		return "signin";
	}

	/**
	 * Method is responsible to verify the user OTP. It internally call user
	 * management service to validate the entered otp.
	 * 
	 * @param userSignInRequest {@link UserSignInRequest}
	 * @param model             {@link Model}
	 * @param session           {@link HttpSession}
	 * @return home page if successful else remains on the same with error message.
	 */
	public String verifyOtp(final UserSignInRequest userSignInRequest, final Model model, final HttpSession session) {
		final OtpVerificationRequest otpVerificationRequest = new OtpVerificationRequest();
		otpVerificationRequest.setOtp(userSignInRequest.getOtp());
		otpVerificationRequest.setUserId((String) session.getAttribute("userId"));
		String response = callUserMgmtServiceToVerifyOtp(otpVerificationRequest);
		if (Objects.nonNull(response)) {
			model.addAttribute("userSignInRequest", userSignInRequest);
			return "home";
		}
		return "otp";
	}

	public void resendOTP(final HttpSession session) {
		String userId = (String) session.getAttribute("userId");
		WebClient.create("http://localhost:8080/users/resend").put().bodyValue(userId).header("website", "ecom")
				.accept(MediaType.APPLICATION_JSON).retrieve()
				.onStatus(httpStatus -> !HttpStatus.OK.equals(httpStatus),
						response -> response.bodyToMono(String.class).map(body -> null))
				.bodyToMono(String.class).block();
	}

	private User callUserMgmtServiceToCreateUser(final UserSignUpRequest userSignUpRequest) {
		return WebClient.create("http://localhost:8080/users/create").post().bodyValue(userSignUpRequest)
				.header("website", "ecom").accept(MediaType.APPLICATION_JSON).retrieve()
				.onStatus(httpStatus -> !HttpStatus.OK.equals(httpStatus),
						response -> response.bodyToMono(String.class).map(body -> null))
				.bodyToMono(User.class).block();
	}

	private User callUserMgmtServiceToLoginUser(final UserSignInRequest userSignInRequest) {
		return WebClient.create("http://localhost:8080/users/login").post().bodyValue(userSignInRequest.getMobileNo())
				.header("website", "ecom").accept(MediaType.APPLICATION_JSON).retrieve()
				.onStatus(httpStatus -> !HttpStatus.OK.equals(httpStatus),
						response -> response.bodyToMono(String.class).map(body -> null))
				.bodyToMono(User.class).block();
	}

	private String callUserMgmtServiceToVerifyOtp(final OtpVerificationRequest otpVerificationRequest) {
		return WebClient.create("http://localhost:8080/users/verify").post().bodyValue(otpVerificationRequest)
				.header("website", "ecom").accept(MediaType.APPLICATION_JSON).retrieve()
				.onStatus(httpStatus -> !HttpStatus.OK.equals(httpStatus),
						response -> response.bodyToMono(String.class).map(body -> null))
				.bodyToMono(String.class).block();
	}

}
