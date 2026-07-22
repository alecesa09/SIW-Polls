package it.uniroma3.siw.service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.Domanda;
import it.uniroma3.siw.Opzione;
import it.uniroma3.siw.Sondaggio;
import it.uniroma3.siw.Utente;
import it.uniroma3.siw.dto.SondaggioDTO;
import it.uniroma3.siw.dto.StatisticheDTO;
import it.uniroma3.siw.exception.UtenteNotFoundException;
import it.uniroma3.siw.repository.SondaggioRepository;
import it.uniroma3.siw.repository.UtenteRepository;


@Service
public class SondaggioService {
	private final SondaggioRepository sr;
	private final UtenteRepository ur;

	public SondaggioService(SondaggioRepository sr,UtenteRepository ur) {
		this.sr = sr;
		this.ur = ur;

	}
	private static final Logger logger = LoggerFactory.getLogger(SondaggioService.class);
	
	@Transactional(readOnly=true)
	public List<SondaggioDTO> getSondaggiRecenti(){
		Pageable pageable = PageRequest.of(0, 6);
		return sr.findTop6RecentiAttivi(Sondaggio.Visibilita.PUBBLICO,LocalDate.now(),pageable);
	}
	@Transactional(readOnly=true)
	public Optional<Sondaggio> getSondaggioById(Long id) {
		return sr.findByIdPubblici(id);
	}
	
	
	@Transactional(readOnly=true)
	public List<StatisticheDTO> getStatistiche(Long id) {
		List<StatisticheDTO> statistiche = sr.getStatistiche(id);
		for(StatisticheDTO statistica : statistiche) {
		logger.info(statistica.toString());
		}
		return statistiche;
	}

	@Transactional(readOnly=true)
	public List<SondaggioDTO> searchSondaggio(String str) {
		List<SondaggioDTO> lista =sr.search(str, PageRequest.of(0, 5));
		for(SondaggioDTO sondaggio : lista) {
			logger.info("sondaggio"+sondaggio.getId().toString());
			}
		return lista;
	}
	@Transactional(readOnly=true)
	public SondaggioDTO searchSondaggiopriv(String str) {
		SondaggioDTO sondaggio = sr.findByCodiceAccesso(str);
		return sondaggio;
	}
	@Transactional(readOnly=true)
	public List<SondaggioDTO>  getSondaggiPerUtente(Principal principal) {
		Utente utente = ur.findByCredentialUsername(principal.getName()).orElseThrow(() -> new UtenteNotFoundException());
		return sr.findByUtente(utente);
	}
	@Transactional(isolation=Isolation.READ_COMMITTED)//bo controlla
	public void creaSondaggio(Sondaggio sondaggio, Principal principal) {
		//da implementare
	}
	

}
