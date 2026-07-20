package it.uniroma3.siw.controller.rest;
import it.uniroma3.siw.Sondaggio;
import it.uniroma3.siw.dto.SondaggioDTO;
import it.uniroma3.siw.service.SondaggioService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
	

}
