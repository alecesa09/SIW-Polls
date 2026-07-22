package it.uniroma3.siw.controller.rest;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.dto.SondaggioDTO;
import it.uniroma3.siw.dto.VotazioneDTO;
import it.uniroma3.siw.exception.VotazioneNonTrovataException;
import it.uniroma3.siw.service.VotazioneService;

@RestController
public class VotazioneController {
	private final VotazioneService vs;
	
	public VotazioneController(VotazioneService vs) {
		this.vs = vs;
	}

	@PostMapping("/rest/sondaggio/votazione") 
    public String salvaVotazione(@RequestBody VotazioneDTO votazione, Principal principal) {
	    vs.salvaVotazione(votazione,principal);	    
	    return "Voti registrati con successo!";
    }
	
	@PutMapping("/rest/sondaggio/votazione") 
    public String modificaVotazione(@RequestBody VotazioneDTO votazione, Principal principal) {
	    vs.modificaVotazione(votazione,principal);	    
	    return "Voti registrati con successo!";
    }
	
	@GetMapping("rest/sondaggio/partecipazione/{id}")
	public boolean getControlloPartecipazioneSondaggio(@PathVariable("id") Long id,Principal principal) {
	    return vs.controllaPartecipazione(id,principal);
	}
	
	@GetMapping("rest/sondaggi/votati/utente")
	public List<SondaggioDTO> getSondaggiVotatiDaUtente(Principal principal) {
	     return vs.getSondaggiVotatiUtente(principal);
	}
	
	@GetMapping("rest/sondaggio/votazione/{idSondaggio}")
	public VotazioneDTO getVotazioneUtente(@PathVariable Long idSondaggio, Principal principal) {
		try {
	    VotazioneDTO votazione = vs.getVotazioneUtente(idSondaggio, principal).orElseThrow(()->new VotazioneNonTrovataException());
	    return votazione;
		}catch (VotazioneNonTrovataException e) {
	    	return null;
	    }
	}
	
	@DeleteMapping("rest/sondaggio/votazione/{idSondaggio}")
	public BodyBuilder eliminaVotazione(@PathVariable Long idSondaggio, Principal principal) {
		vs.eliminaVotazione(idSondaggio,principal);
		return ResponseEntity.status(HttpStatus.OK);
	}
	
}
