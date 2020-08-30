package com.ecom.message.management.sms.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.message.management.sms.controller.SmsService;

@RestController
@RequestMapping("sms")
public class SmsController {

	@Autowired
	SmsService smsService;

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public String generateOtp(@RequestBody final String mobileNo) {
		return smsService.sendOtp(mobileNo);
	}
}
