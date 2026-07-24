package it.uniroma3.siw.controller.rest;
import it.uniroma3.siw.Commento;
import it.uniroma3.siw.Sondaggio;
import it.uniroma3.siw.dto.SondaggioDTO;
import it.uniroma3.siw.dto.StatisticheDTO;
import it.uniroma3.siw.dto.VotazioneDTO;
import it.uniroma3.siw.dto.VotoDTO;
import it.uniroma3.siw.service.SondaggioService;
import jakarta.validation.Valid;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

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
	
	@GetMapping("rest/sondaggio/statistiche/{cod}")
	public List<StatisticheDTO> getStatisticheSondaggio(@PathVariable("cod") String cod) {
	    return ss.getStatistiche(cod);
	}
	
	@GetMapping("rest/sondaggio/search/{titolo}")
	public List<SondaggioDTO> getSondaggioByTitolo(@PathVariable("titolo") String titolo) {
	    return ss.searchSondaggio(titolo);
	}
	
	@GetMapping("rest/sondaggio/search/codiceAccesso/{cod}")
	public Sondaggio getSondaggioByCodiceAccesso(@PathVariable("cod") String cod) {
	    return ss.searchSondaggioByCodiceAcesso(cod);
	}
	
	@GetMapping("rest/sondaggio/utente")
	public List<SondaggioDTO> getSondaggiCreatiDaUtente(Principal principal) {
	     return ss.getSondaggiPerUtente(principal);
	}
	
	@PostMapping(value = "/rest/sondaggio", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> creaSondaggio(
    		@Valid @RequestPart("sondaggio") Sondaggio sondaggio,
            @RequestPart(value = "file", required = false) MultipartFile file ,Principal principal) throws IOException {
		ss.creaSondaggio(sondaggio, file, principal);
        return ResponseEntity.ok().build();
    }
	
}
