package it.uniroma3.siw.service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;



import it.uniroma3.siw.Commento;
import it.uniroma3.siw.Sondaggio;
import it.uniroma3.siw.Utente;
import it.uniroma3.siw.exception.SondaggioNonTrovatoException;
import it.uniroma3.siw.exception.UtenteNotFoundException;
import it.uniroma3.siw.repository.CommentoRepository;
import it.uniroma3.siw.repository.SondaggioRepository;
import it.uniroma3.siw.repository.UtenteRepository;

@Service
public class CommentoService {
	private final UtenteRepository ur;
	private final CommentoRepository cr;
	private final SondaggioRepository sr;
	
	
	public CommentoService(UtenteRepository ur, CommentoRepository cr, SondaggioRepository sr) {
		this.ur = ur;
		this.cr = cr;
		this.sr = sr;
	}

	private static final Logger logger = LoggerFactory.getLogger(CommentoService.class);
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void salvaCommento(Long idSondaggio, String testoCommento, Principal principal) {
		logger.debug("inizio salvataggio del commento: {}", testoCommento);
		Utente utente = ur.findByCredentialUsername(principal.getName()).orElseThrow(() -> new UtenteNotFoundException());
		Sondaggio sondaggio= sr.findById(idSondaggio).orElseThrow(() -> new SondaggioNonTrovatoException(idSondaggio));
		Commento commento = new Commento(sondaggio,utente,LocalDate.now(),testoCommento);
		cr.save(commento);
	}
	
	@Transactional(readOnly=true)
	public List<Commento> getCommenti(Long id) {
		return  cr.findBySondaggioId(id);
	}
}
