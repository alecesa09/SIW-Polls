package it.uniroma3.siw.service;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.Utente;
import it.uniroma3.siw.repository.UtenteRepository;

@Service
public class UtenteService {
	private final UtenteRepository utenteRepository;
	
	public UtenteService(UtenteRepository utenteRepository) {
		super();
		this.utenteRepository = utenteRepository;
	}

	public Utente getUser(Long id) {
		return utenteRepository.findById(id).get();
	}
	
	public Utente saveUser(Utente user) {
		return utenteRepository.save(user);
	}
}
