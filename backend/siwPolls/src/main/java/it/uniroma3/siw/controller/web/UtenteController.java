package it.uniroma3.siw.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.Credential;
import it.uniroma3.siw.Utente;
import it.uniroma3.siw.exception.EmailUtenteDuplicataException;
import it.uniroma3.siw.exception.UsernameDuplicatoException;
import it.uniroma3.siw.service.CredentialService;
import it.uniroma3.siw.service.UtenteService;
import jakarta.validation.Valid;

@Controller
public class UtenteController {
	private final UtenteService utenteService;
	private final CredentialService credentialService;

	
	public UtenteController(UtenteService utenteService,CredentialService credentialService) {
		this.utenteService = utenteService;
		this.credentialService =credentialService;	
	}
	
	@GetMapping("/register")
	public String iniziaRegistrazione(Model model) {
		model.addAttribute("utente", new Utente());
        model.addAttribute("credentials", new Credential());
		return "/utente/registrazione";
	}
	
	@PostMapping("/register")
	public String completaRegistrazione(
	        @Valid @ModelAttribute("utente") Utente utente, 
	        BindingResult utenteBindingResult,
	        @Valid @ModelAttribute("credentials") Credential credentials, 
	        BindingResult credentialsBindingResult,
	        Model model) {
		if(credentialsBindingResult.hasErrors() || utenteBindingResult.hasErrors()) {
			return "/utente/registrazione";
		}
		 try {
			 credentialService.saveCredentials(credentials,utente);
			 return "redirect:/login";
		 }
		 catch (EmailUtenteDuplicataException | UsernameDuplicatoException e) {
			 utenteBindingResult.reject("errore", e.getMessage());
			 return "/utente/registrazione";
		 }
		
	}
	@GetMapping("/admin")
    public String getmenu() {
    	return "/admin/index";
    }
}
