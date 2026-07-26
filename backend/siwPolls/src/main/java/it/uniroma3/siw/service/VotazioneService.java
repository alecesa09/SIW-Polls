package it.uniroma3.siw.service;

import org.springframework.stereotype.Service;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import it.uniroma3.siw.Domanda;
import it.uniroma3.siw.Opzione;
import it.uniroma3.siw.Sondaggio;
import it.uniroma3.siw.Utente;
import it.uniroma3.siw.Votazione;
import it.uniroma3.siw.Voto;
import it.uniroma3.siw.dto.SondaggioDTO;
import it.uniroma3.siw.dto.VotazioneDTO;
import it.uniroma3.siw.dto.VotoDTO;
import it.uniroma3.siw.exception.IllegalVoteException;
import it.uniroma3.siw.exception.ModificaVotoInesistenteException;
import it.uniroma3.siw.exception.SondaggioNonTrovatoException;
import it.uniroma3.siw.exception.SondaggioScadutoException;
import it.uniroma3.siw.exception.UtenteNotFoundException;
import it.uniroma3.siw.exception.VotazioneIncompletaException;
import it.uniroma3.siw.exception.VotazioneNonTrovataException;
import it.uniroma3.siw.exception.VotoGiaEspressoException;
import it.uniroma3.siw.repository.DomandaRepository;
import it.uniroma3.siw.repository.OpzioneRepository;
import it.uniroma3.siw.repository.SondaggioRepository;
import it.uniroma3.siw.repository.UtenteRepository;
import it.uniroma3.siw.repository.VotazioneRepository;
import it.uniroma3.siw.repository.VotoRepository;
@Service
public class VotazioneService {
	private final SondaggioRepository sr;
	private final UtenteRepository ur;
	private final DomandaRepository dr;
	private final VotazioneRepository votazioneR;
	private final VotoRepository votoR;

	public VotazioneService(SondaggioRepository sr, OpzioneRepository or, UtenteRepository ur, DomandaRepository dr,VotoRepository votoR,
			VotazioneRepository votazioneR) {
		this.sr = sr;
		this.ur = ur;
		this.dr = dr;
		this.votazioneR = votazioneR;
		this.votoR=votoR;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(VotazioneService.class);
	
	private Votazione costruisciVotazioneValidata(VotazioneDTO votazione, Sondaggio sondaggio, Utente utente) {
		logger.info("inizio costruzione votazione");
	    Votazione v = votazione.getVisibilita().equals(Votazione.registrazione.ANONIMA.toString())
	            ? new Votazione(sondaggio, null, Votazione.registrazione.ANONIMA, LocalDate.now().atStartOfDay())
	            : new Votazione(sondaggio, utente, Votazione.registrazione.NORMALE, LocalDate.now().atStartOfDay());
	    
	    Set<Long> domandeAttese = sondaggio.getDomande().stream()
	            .map(Domanda::getId).collect(Collectors.toSet());
	    
	    Set<Long> domandeRisposte = votazione.getVoti().stream()
	            .map(VotoDTO::getDomandaId).collect(Collectors.toSet());
	    
	    if (!domandeAttese.equals(domandeRisposte)) {
	        throw new VotazioneIncompletaException();
	    }
	    
	    for(VotoDTO votodto:votazione.getVoti()) {
    		Domanda domanda = sondaggio.getDomande().stream().filter( d -> d.getId().equals(votodto.getDomandaId())).findFirst().orElseThrow(IllegalVoteException::new);
    		Opzione opzione = domanda.getOpzioni().stream()	.filter(d -> d.getId().equals(votodto.getOpzioneId())).findFirst().orElseThrow(IllegalVoteException::new);	
    		
    		new Voto(domanda,opzione,v);
    	}
	    return v;
	}
	
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void salvaVotazione(VotazioneDTO votazione, Principal principal) {
		Utente utente = getUtente(principal);
		
	    Sondaggio sondaggio = caricaSondaggio(votazione);

	    if (sondaggio.getDataScadenza().isBefore(LocalDate.now())) throw new SondaggioScadutoException();
	    
	    if (ur.existsByIdAndPartecipazioniId(utente.getId(), sondaggio.getId())) throw new VotoGiaEspressoException();

	    Votazione v = costruisciVotazioneValidata(votazione, sondaggio, utente);
	    votazioneR.save(v);
	    utente.aggiungiPartecipazione(sondaggio);
	}
	
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void modificaVotazione(VotazioneDTO votazione, Principal principal) {
	    Sondaggio sondaggio = caricaSondaggio(votazione);
	    Utente utente = getUtente(principal);

	    if (sondaggio.getDataScadenza().isBefore(LocalDate.now())) throw new SondaggioScadutoException();

	    Votazione votazionePrecedente = votazioneR
	            .findBySondaggioCodiceAccessoAndUtenteId(sondaggio.getCodiceAccesso(), utente.getId())
	            .orElseThrow(()->new VotazioneNonTrovataException());

	    Votazione v = costruisciVotazioneValidata(votazione, sondaggio, utente);
	    
	    v.setId(votazionePrecedente.getId());
	    votazioneR.save(v);
	}
	
	@Transactional(readOnly=true)
	public boolean controllaPartecipazione(String cod, Principal principal) {
		Utente utente = getUtente(principal);
		return ur.existsByIdAndPartecipazioniCodiceAccesso(utente.getId(),cod);
	}
	
	@Transactional(readOnly=true)
	public List<SondaggioDTO> getSondaggiVotatiUtente(Principal principal) {
		logger.info("inizio ricerca sondaggi Votati utente e ancora modificabili");
		Utente utente = getUtente(principal);
		List<SondaggioDTO> sondaggi = sr.findSondaggiVotatiPerUtente(utente.getId());
		for(SondaggioDTO sondaggio: sondaggi) {
			logger.info("-" + sondaggio.getId().toString());
			}
		return sondaggi;
	}
	@Transactional(readOnly = true)
	public VotazioneDTO getVotazioneUtente(String cod, Principal principal) {
	    Utente utente = getUtente(principal);
	    
	    Votazione votOpt = votazioneR.findBySondaggioCodiceAccessoAndUtenteId(cod, utente.getId()).orElseThrow(()->new VotazioneNonTrovataException());
	    
	    List<VotoDTO> voti = votoR.getVotiSondaggio(cod, utente.getId());
	    
	    VotazioneDTO votazioneDTO = new VotazioneDTO(votOpt.getSondaggio().getId(), votOpt, voti);

	    return votazioneDTO;
	}
	
	
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void eliminaVotazione(String cod, Principal principal) {
	    Utente utente = getUtente(principal);
	    Votazione votazione = votazioneR.findBySondaggioCodiceAccessoAndUtenteId(cod, utente.getId()).orElseThrow(()->new VotazioneNonTrovataException());
	    Sondaggio sondaggio = votazione.getSondaggio();
	    utente.getPartecipazioni().remove(sondaggio);
	    votazioneR.delete(votazione);
	    ur.save(utente);
	}
	
	private Utente getUtente(Principal principal) {
		return ur.findByCredentialUsername(principal.getName()).orElseThrow(() -> new UtenteNotFoundException());
	}
	
	private Sondaggio caricaSondaggio(VotazioneDTO votazione) {
		Sondaggio sondaggio = sr.findCompletoById(votazione.getSondaggioId()).orElseThrow(() -> new SondaggioNonTrovatoException(votazione.getSondaggioId().toString()));
	    return sondaggio;
	}
	
}
