package com.ecom.user.management.exceptions;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EcommerceExceptionHandler extends ResponseEntityExceptionHandler {

/*
 * public void ecommerceExcetion(HttpServletResponse response) throws
 * IOException { // TODO Auto-generated method stub
 * 
 * response.sendError(HttpStatus.BAD_REQUEST.value()); }
 */
	
@ExceptionHandler(EcommerceException.class)	
protected ResponseEntity<Object> handleExceptionInternal(Exception ex) {
	Map<String, Object> body1 = new LinkedHashMap<>();
    body1.put("timestamp", new Date());
    //body1.put("status", status.value());
    body1.put("errors", ((EcommerceException)ex).getError());
    return new ResponseEntity<>(body1, HttpStatus.BAD_REQUEST);
}

}
