package it.uniroma3.siw.controller.rest;
import it.uniroma3.siw.Commento;
import it.uniroma3.siw.Sondaggio;
import it.uniroma3.siw.dto.SondaggioDTO;
import it.uniroma3.siw.dto.StatisticheDTO;
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
	
	@GetMapping("rest/sondaggio/partecipazione/{id}")
	public boolean getControlloPartecipazioneSondaggio(@PathVariable("id") Long id,Principal principal) {
	    return ss.controllaPartecipazione(id,principal);
	}
	
	@GetMapping("rest/sondaggio/statistiche/{id}")
	public List<StatisticheDTO> getStatisticheSondaggio(@PathVariable("id") Long id) {
	    return ss.getStatistiche(id);
	}
	
	@GetMapping("rest/sondaggio/commenti/{id}")
	public List<Commento> getCommentiSondaggio(@PathVariable("id") Long id) {
	    return ss.getCommenti(id);
	}
	
	@GetMapping("rest/sondaggio/search/{str}")
	public List<SondaggioDTO> getSondaggioByTitolo(@PathVariable("str") String str) {
	    return ss.searchSondaggio(str);
	}
	
	@GetMapping("rest/sondaggio/searchPriv/{str}")
	public SondaggioDTO getSondaggioByCodiceAcesso(@PathVariable("str") String str) {
	    return ss.searchSondaggiopriv(str);
	}
	
	@PostMapping("rest/sondaggio/commento/{id}")
	public ResponseEntity<String> aggiungiCommento(
	        @PathVariable("id") Long idSondaggio, 
	        @RequestBody String testoCommento,
	        Principal principal) {
		
	     ss.salvaCommento(idSondaggio, testoCommento, principal);
	    
	    return ResponseEntity.ok("Commento registrato con successo!");
	}
	
	@GetMapping("rest/sondaggio/utente")
	public List<SondaggioDTO> getSondaggiCreatiDaUtente(Principal principal) {
	     return ss.getSondaggiPerUtente(principal);
	}
	
	@GetMapping("rest/sondaggi/votati/utente")
	public List<SondaggioDTO> getSondaggiVotatiDaUtente(Principal principal) {
	     return ss.getSondaggiVotatiUtente(principal);//ancora attivi
	}
}
