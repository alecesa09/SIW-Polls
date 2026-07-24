package it.uniroma3.siw.controller.rest;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.Commento;
import it.uniroma3.siw.service.CommentoService;

@RestController
public class CommentoController {
	private final CommentoService cs;
	

	public CommentoController(CommentoService cs) {
		this.cs = cs;
	}

	@PostMapping("rest/sondaggio/commento/{cod}")
	public ResponseEntity<String> aggiungiCommento(
	        @PathVariable("cod") String codiceAccesso, 
	        @RequestBody String testoCommento,
	        Principal principal) {
		
	     cs.salvaCommento(codiceAccesso, testoCommento, principal);
	    
	    return ResponseEntity.ok("Commento registrato con successo!");
	}
	
	@GetMapping("rest/sondaggio/commenti/{cod}")
	public List<Commento> getCommentiSondaggio(@PathVariable("cod") String codiceAccesso) {
	    return cs.getCommenti(codiceAccesso);
	}

}
