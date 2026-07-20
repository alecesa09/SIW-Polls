package it.uniroma3.siw.controller.rest;
import it.uniroma3.siw.Sondaggio;
import it.uniroma3.siw.dto.SondaggioDTO;
import it.uniroma3.siw.dto.VotazioneDTO;
import it.uniroma3.siw.dto.VotoDTO;
import it.uniroma3.siw.service.SondaggioService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class SondaggioController {
	private final SondaggioService ss;
	
	public SondaggioController(SondaggioService ss){
		this.ss=ss;
	}
	
	@GetMapping("rest/sondaggi/recenti")
	public List<SondaggioDTO> sondaggiRecenti() {
		return ss.getSondaggiRecenti();
	}
	
	@GetMapping("rest/sondaggio/{id}")
	public ResponseEntity<Sondaggio> getSondaggio(@PathVariable("id") Long id) {
	    Optional<Sondaggio> sondaggioOpt = ss.getSondaggioById(id);
	    if (sondaggioOpt.isPresent()) {
	        return ResponseEntity.ok(sondaggioOpt.get()); 
	    } else {
	        return ResponseEntity.notFound().build(); 
	    }
	}
	
	@PostMapping("/rest/sondaggio/voto") 
    public String salvaVoti(@RequestBody VotazioneDTO votazione, Principal principal) {
        
        Long idSondaggio = votazione.getSondaggioId();
        String visibilita = votazione.getVisibilita();
       
        
        ss.salvaVotazione(votazione,principal);
        
        return "Voti registrati con successo!";
    }
}
