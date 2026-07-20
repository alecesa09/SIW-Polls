package it.uniroma3.siw.controller.rest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.Credential;
import it.uniroma3.siw.Utente;
import it.uniroma3.siw.service.CredentialService;

import org.springframework.http.ResponseEntity;
@RestController
public class AuthController {
	private final CredentialService cs;
	
	public AuthController(CredentialService cs) {
		this.cs = cs;
	}

	@GetMapping("/rest/auth")
	public ResponseEntity<Object> getLoggin(Authentication authentication) {
	    if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
	        return ResponseEntity.ok(null); 
	    }
	    
	    String username = authentication.getName();
	    
	    Credential credential = cs.findByUsername(username); 
	    
	    if (credential == null || credential.getUtente() == null) {
	        return ResponseEntity.ok(null);
	    }

	    Utente utenteDettagli = credential.getUtente();

	    return ResponseEntity.ok(utenteDettagli);
	}
}
