package it.uniroma3.siw.config;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestControllerAdvice(basePackages = "it.uniroma3.siw.controller.rest")
public class RestExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);
	
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
    	logger.error("IllegalArgumentException");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("errore", ex.getMessage()));
        
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errori = new HashMap<>();
        logger.error("MethodArgumentNotValidException");
        ex.getBindingResult().getFieldErrors()
          .forEach(err -> errori.put(err.getField(), err.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errori);
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(RuntimeException ex) {
    	logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("errore", ex.getMessage()));
    }
    
}
