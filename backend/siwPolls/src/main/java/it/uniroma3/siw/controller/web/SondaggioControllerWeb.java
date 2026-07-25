package it.uniroma3.siw.controller.web;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.Sondaggio;
import it.uniroma3.siw.dto.SondaggioDTO;
import it.uniroma3.siw.exception.SondaggioNonTrovatoException;
import it.uniroma3.siw.service.SondaggioService;

@Controller
public class SondaggioControllerWeb {
	private final SondaggioService ss;

	public SondaggioControllerWeb(SondaggioService ss) {
		this.ss = ss;
	}
	
	@GetMapping("/admin/lista/sondaggi")
	public String listaSondaggi(Model model) {
		List<Sondaggio> sondaggi =ss.findAll();
		model.addAttribute("sondaggi", sondaggi);
		return "admin/sondaggio/list";
	}
	@GetMapping("/admin/lista/sondaggi/filtro")
	public String getListaSondaggiFiltrata( 
	        @RequestParam(required = false) String titolo,
	        @RequestParam(required = false) String codiceAccesso,
	        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataCreazioneMin,
	        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataCreazioneMax,
	        Model model) {
	    
	    List<Sondaggio> sondaggi = ss.findByFiltri(titolo, codiceAccesso, dataCreazioneMin, dataCreazioneMax);
	    model.addAttribute("sondaggi", sondaggi);
	    return "admin/sondaggio/list";
	}
	
	@PostMapping("/admin/cancella/sondaggio")
	public String cancellaUtente(@RequestParam("id") Long idSondaggio, Model model) {
	    System.out.println("Cancellazione sondaggio ID: " + idSondaggio);
	    ss.cancellaSondaggio(idSondaggio);//eccezione che finisce  all handler
	    return "redirect:/admin/lista/sondaggi";
	}
}
