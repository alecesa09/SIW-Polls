package it.uniroma3.siw.controller.rest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
@RestController
public class AuthController {
	@GetMapping("/rest/auth")
	public ResponseEntity<Object> getLoggin(Authentication authentication) {
	    if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
	        return ResponseEntity.ok(null); 
	    }
	    Object utente = authentication.getPrincipal();

	    return ResponseEntity.ok(utente);
	}
}
