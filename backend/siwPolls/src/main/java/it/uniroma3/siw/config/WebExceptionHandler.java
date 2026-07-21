package it.uniroma3.siw.config;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;


@ControllerAdvice(basePackages = "it.uniroma3.siw.controller.web")
public class WebExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(WebExceptionHandler.class);
	 @ExceptionHandler(IllegalArgumentException.class)
	 public String handleIllegalArgument(IllegalArgumentException ex, Model model) {
	     model.addAttribute("errore", ex.getMessage());
	     logger.error(ex.getMessage());
	     return "errore/500";
	 }
		
	
	 @ExceptionHandler(IOException.class)
	 @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
		public String handleImageException(Exception e, Model model) {
		model.addAttribute("errorMessage","è sorto un problema con il salvataggio dell`immagine");
		logger.error(e.getMessage());
		return "error/500";
	}
	 
}
