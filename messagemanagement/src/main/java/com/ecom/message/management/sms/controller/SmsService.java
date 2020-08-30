package com.ecom.message.management.sms.controller;

import java.text.MessageFormat;
import java.util.Random;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ecom.message.management.sms.entity.OtpRequest;

@Service
public class SmsService {

	private static final String API_KEY = "0PM/GLFnBH4-ipmU8Kh2M0g4kZFW9TS9PlIv8kHUl4";

	private static final String SMS_GATEWAY_URL = "https://api.textlocal.in/send";

	private static final String SENDER = "TXTLCL";

	private static final MessageFormat FORMATTER = new MessageFormat(
			"{0} is your One Time Password for ECommerce Registration.");

	/**
	 * Method generate OTP and call Sms gateway service with the given mobile
	 * number.
	 * 
	 * @param mobileNo recipient mobile number.
	 * @return generated Otp if it able to reach sms gateway successfully else throw
	 *         exception.
	 */
	public String sendOtp(final String mobileNo) {
		final int otp = generateFiveDigitNumber();
		final OtpRequest otpRequest = buildOtpRequest(mobileNo);
		otpRequest.setMessage((FORMATTER.format(new Object[] { otp })));
		boolean isOtpSent = callSmsAPI(otpRequest);
		if (isOtpSent)
			return String.valueOf(otp);
		throw new RuntimeException("Unable to send OTP");
	}

	/**
	 * Restful call to third party SMS gateway service.
	 * 
	 * @param request {@link OtpRequest}
	 * @return true if otp sent else false.
	 */
	private boolean callSmsAPI(final OtpRequest request) {
		final Boolean[] isSuccess = { false };
		WebClient.create(SMS_GATEWAY_URL).get()
				.uri(uriBuilder -> uriBuilder.queryParam("apikey", API_KEY).queryParam("message", request.getMessage())
						.queryParam("sender", SENDER).queryParam("numbers", request.getMobileNo()).build())
				.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(String.class).doOnSuccess(success -> {
					isSuccess[0] = true;
				}).doOnError(error -> {
					isSuccess[0] = false;
				}).block();
		return isSuccess[0];
	}

	/**
	 * Method builds a OTP request
	 * 
	 * @param mobileNo recipient mobile number
	 * @return {@link OtpRequest}
	 */
	private OtpRequest buildOtpRequest(final String mobileNo) {
		final OtpRequest otpRequest = new OtpRequest();
		otpRequest.setMobileNo(mobileNo);
		return otpRequest;
	}

	/**
	 * Method create a five digit number based on current time stamp.
	 * 
	 * @return five digit number.
	 */
	private int generateFiveDigitNumber() {
		final Random randomNumber = new Random(System.currentTimeMillis());
		return (1 + randomNumber.nextInt(2)) * 10000 + randomNumber.nextInt(10000);
	}
}
