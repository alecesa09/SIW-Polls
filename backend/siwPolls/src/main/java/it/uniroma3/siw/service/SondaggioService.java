package it.uniroma3.siw.service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.Commento;
import it.uniroma3.siw.Domanda;
import it.uniroma3.siw.Opzione;
import it.uniroma3.siw.Sondaggio;
import it.uniroma3.siw.Utente;
import it.uniroma3.siw.Votazione;
import it.uniroma3.siw.Voto;
import it.uniroma3.siw.config.RestExceptionHandler;
import it.uniroma3.siw.dto.SondaggioDTO;
import it.uniroma3.siw.dto.StatisticheDTO;
import it.uniroma3.siw.dto.VotazioneDTO;
import it.uniroma3.siw.dto.VotoDTO;
import it.uniroma3.siw.exception.IllegalVoteException;
import it.uniroma3.siw.exception.SondaggioNonTrovatoException;
import it.uniroma3.siw.exception.UtenteNotFoundException;
import it.uniroma3.siw.exception.VotazioneIncompletaException;
import it.uniroma3.siw.exception.VotoGiaEspressoException;
import it.uniroma3.siw.repository.CommentoRepository;
import it.uniroma3.siw.repository.CredentialRepository;
import it.uniroma3.siw.repository.DomandaRepository;
import it.uniroma3.siw.repository.OpzioneRepository;
import it.uniroma3.siw.repository.SondaggioRepository;
import it.uniroma3.siw.repository.UtenteRepository;
import it.uniroma3.siw.repository.VotazioneRepository;
import it.uniroma3.siw.repository.VotoRepository;


@Service
public class SondaggioService {
	private final SondaggioRepository sr;
	private final OpzioneRepository or;
	private final UtenteRepository ur;
	private final DomandaRepository dr;
	private final VotazioneRepository vr;
	private final CommentoRepository cr;


	public SondaggioService(SondaggioRepository sr, OpzioneRepository or, UtenteRepository ur, DomandaRepository dr,
			VotazioneRepository vr, CommentoRepository cr) {
		this.sr = sr;
		this.or = or;
		this.ur = ur;
		this.dr = dr;
		this.vr = vr;
		this.cr = cr;
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
	
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void salvaVotazione(VotazioneDTO votazione,Principal principal) {
		Sondaggio sondaggio = sr.findById(votazione.getSondaggioId()).orElseThrow(() -> new SondaggioNonTrovatoException(votazione.getSondaggioId()));
		Utente utente = ur.findByCredentialUsername(principal.getName()).orElseThrow(() -> new UtenteNotFoundException());
		
		if (ur.existsByIdAndPartecipazioniId(utente.getId(), sondaggio.getId())) {
		    throw new VotoGiaEspressoException();
		}
		Votazione v;
		if(votazione.getVisibilita().equals(Votazione.registrazione.ANONIMA.toString())){
			Utente utentenullo=null;
			v= new Votazione(sondaggio,utentenullo,Votazione.registrazione.ANONIMA,LocalDate.now().atStartOfDay());
		}else {
			v= new Votazione(sondaggio,utente,Votazione.registrazione.NORMALE,LocalDate.now().atStartOfDay());
		}
		
		Set<Long> domandeAttese = sondaggio.getDomande().stream()
			    .map(Domanda::getId)
			    .collect(Collectors.toSet());

		Set<Long> domandeRisposte = votazione.getVoti().stream()
		    .map(VotoDTO::getDomandaId)
		    .collect(Collectors.toSet());

		if (!domandeAttese.equals(domandeRisposte)) {
		    throw new VotazioneIncompletaException();
		}
		
    	for(VotoDTO votodto:votazione.getVoti()) {
    		if(!or.existsByIdAndDomandaIdAndDomandaSondaggioId(votodto.getOpzioneId(),votodto.getDomandaId(), votazione.getSondaggioId())) {
    			throw new IllegalVoteException();
    		}
    		else {
    			Domanda domanda = dr.findById(votodto.getDomandaId()).get();
    			Opzione opzione = or.findById(votodto.getOpzioneId()).get();
    			new Voto(domanda,opzione,v);
    		}
    	}
    	vr.save(v);
    	utente.aggiungiPartecipazione(sondaggio);
	}
	
	@Transactional(readOnly=true)
	public boolean controllaPartecipazione(Long id, Principal principal) {
		Utente utente = ur.findByCredentialUsername(principal.getName()).orElseThrow(() -> new UtenteNotFoundException());
		return ur.existsByIdAndPartecipazioniId(utente.getId(),id);
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
	public List<Commento> getCommenti(Long id) {
		return  cr.findBySondaggioId(id);
	}
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void salvaCommento(Long idSondaggio, String testoCommento, Principal principal) {
		logger.debug("inizio salvataggio del commento: {}", testoCommento);
		Utente utente = ur.findByCredentialUsername(principal.getName()).orElseThrow(() -> new UtenteNotFoundException());
		Sondaggio sondaggio= sr.findById(idSondaggio).orElseThrow(() -> new SondaggioNonTrovatoException(idSondaggio));
		Commento commento = new Commento(sondaggio,utente,LocalDate.now(),testoCommento);
		cr.save(commento);
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
	public List<SondaggioDTO> getSondaggiVotatiUtente(Principal principal) {
		Utente utente = ur.findByCredentialUsername(principal.getName()).orElseThrow(() -> new UtenteNotFoundException());
		logger.info(utente.getId().toString());
		return sr.findSondaggiVotatiPerUtente(utente.getId());
	}
	

}
