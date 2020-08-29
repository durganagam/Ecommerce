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

	public String generateOtp(final OtpRequest request) {
		int otp = generateFiveDigitNumber();
		request.setMessage((FORMATTER.format(new Object[] { otp })));
		int attempt = 0;
		while (attempt < 3) {
			if (!callSmsAPI(request)) {
				attempt++;
			} else {
				attempt = 4;
				return String.valueOf(otp);
			}
		}

		throw new RuntimeException("Error While generating the exception.");
	}

	private boolean callSmsAPI(final OtpRequest request) {
		final Boolean[] isSuccess = { false };
		WebClient.create(SMS_GATEWAY_URL).get()
				.uri(uriBuilder -> uriBuilder.queryParam("apikey", API_KEY).queryParam("message", request.getMessage())
						.queryParam("sender", SENDER).queryParam("numbers", request.getMobileNo()).build())
				.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(String.class)
				.doOnSuccess(success -> {
					isSuccess[0] = true;
				}).doOnError(error -> {
					isSuccess[0] = false;
				}).block();

		return isSuccess[0];
	}

	public int generateFiveDigitNumber() {
		Random r = new Random(System.currentTimeMillis());
		return (1 + r.nextInt(2)) * 10000 + r.nextInt(10000);
	}

}
