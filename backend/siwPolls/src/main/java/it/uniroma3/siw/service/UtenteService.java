package it.uniroma3.siw.service;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.Utente;

import it.uniroma3.siw.exception.UtenteNotFoundException;
import it.uniroma3.siw.repository.CredentialRepository;
import it.uniroma3.siw.repository.SondaggioRepository;
import it.uniroma3.siw.repository.UtenteRepository;

@Service
public class UtenteService {
	private final UtenteRepository utenteRepository;
	private final CredentialRepository credentialRepository;
	private final SondaggioRepository sr;

	
	
	public UtenteService(UtenteRepository utenteRepository, CredentialRepository credentialRepository,
			SondaggioRepository sr) {
		this.utenteRepository = utenteRepository;
		this.credentialRepository = credentialRepository;
		this.sr = sr;
	}
	@Transactional(readOnly=true)
	public Utente getUser(Long id) {
		return utenteRepository.findById(id).get();
	}
	@Transactional(isolation=Isolation.READ_COMMITTED)
	public Utente saveUser(Utente user) {
		return utenteRepository.save(user);
	}
	
	@Transactional(isolation=Isolation.READ_COMMITTED)
	public void cancellaUtente(Long idUtente) {
		Utente utente= utenteRepository.findById(idUtente).orElseThrow(()->  new UtenteNotFoundException());
		sr.setUtenteNullByUtenteId(utente.getId());
		utenteRepository.delete(utente);
		credentialRepository.deleteByUtenteId(idUtente);
	}
	@Transactional(readOnly=true)
	public List<Utente> findAll() {
		return utenteRepository.findAll();
		
	}
	@Transactional(readOnly=true)
	public List<Utente> findByParametri(String nome, String cognome, Long id) {
		String nomeParam = (nome != null && !nome.isBlank()) ? "%" + nome.trim().toLowerCase() + "%" : null;
	    String cognomeParam = (cognome != null && !cognome.isBlank()) ? "%" + cognome.trim().toLowerCase() + "%" : null;
		return utenteRepository.findByParametri(nomeParam,cognomeParam,id);
	}	

}
