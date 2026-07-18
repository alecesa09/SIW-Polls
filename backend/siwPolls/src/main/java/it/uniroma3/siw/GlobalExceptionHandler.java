package it.uniroma3.siw;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
	 
	 private static final Logger logger =LoggerFactory.getLogger(GlobalExceptionHandler.class);
	 
	 @ExceptionHandler(Exception.class)
	 @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
		public String handleUnexpectedException(Exception e, Model model) {
		model.addAttribute("errorMessage","errore interno");
		logger.error(e.getMessage());
		return "error/500";
	}
	
}
