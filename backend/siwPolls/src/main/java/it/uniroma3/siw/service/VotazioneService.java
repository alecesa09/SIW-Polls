package it.uniroma3.siw.service;

import org.springframework.stereotype.Service;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
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
	private final OpzioneRepository or;
	private final UtenteRepository ur;
	private final DomandaRepository dr;
	private final VotazioneRepository votazioneR;
	private final VotoRepository votoR;

	public VotazioneService(SondaggioRepository sr, OpzioneRepository or, UtenteRepository ur, DomandaRepository dr,VotoRepository votoR,
			VotazioneRepository votazioneR) {
		this.sr = sr;
		this.or = or;
		this.ur = ur;
		this.dr = dr;
		this.votazioneR = votazioneR;
		this.votoR=votoR;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(VotazioneService.class);
	
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void salvaVotazione(VotazioneDTO votazione,Principal principal) {
		logger.info("Payload ricevuto: {}", votazione);
		logger.info("inzio salvataggio votazione");
		Sondaggio sondaggio = sr.findById(votazione.getSondaggioId()).orElseThrow(() -> new SondaggioNonTrovatoException(votazione.getSondaggioId()));
		Utente utente = ur.findByCredentialUsername(principal.getName()).orElseThrow(() -> new UtenteNotFoundException());
		
		if (sondaggio.getDataScadenza().isBefore(LocalDate.now())) {
		    throw new SondaggioScadutoException();
		}
		
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
    	votazioneR.save(v);
    	utente.aggiungiPartecipazione(sondaggio);
	}
	
	@Transactional(readOnly=true)
	public boolean controllaPartecipazione(Long id, Principal principal) {
		Utente utente = ur.findByCredentialUsername(principal.getName()).orElseThrow(() -> new UtenteNotFoundException());
		return ur.existsByIdAndPartecipazioniId(utente.getId(),id);
	}
	
	@Transactional(readOnly=true)
	public List<SondaggioDTO> getSondaggiVotatiUtente(Principal principal) {
		logger.info("inizio ricerca sondaggi Votati utente e ancora modificabili");
		Utente utente = ur.findByCredentialUsername(principal.getName()).orElseThrow(() -> new UtenteNotFoundException());
		List<SondaggioDTO> sondaggi = sr.findSondaggiVotatiPerUtente(utente.getId());
		for(SondaggioDTO sondaggio: sondaggi) {
			logger.info("-" + sondaggio.getId().toString());
			}
		return sr.findSondaggiVotatiPerUtente(utente.getId());
	}
	@Transactional(readOnly = true)
	public Optional<VotazioneDTO> getVotazioneUtente(Long idSondaggio, Principal principal) {
	    Utente utente = ur.findByCredentialUsername(principal.getName())
	            .orElseThrow(() -> new UtenteNotFoundException());

	    Optional<Votazione> votOpt = votazioneR.findBySondaggioIdAndUtenteId(idSondaggio, utente.getId());

	    if (votOpt.isEmpty()) {
	        return Optional.empty();
	    }

	    List<VotoDTO> voti = votoR.getVotiSondaggio(idSondaggio, utente.getId());
	    VotazioneDTO votazioneDTO = new VotazioneDTO(idSondaggio, votOpt.get(), voti);

	    return Optional.of(votazioneDTO);
	}

	public void modificaVotazione(VotazioneDTO votazione, Principal principal) {
		logger.info("inizio modifica votazione");
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void eliminaVotazione(Long idSondaggio, Principal principal) {
	    Utente utente = ur.findByCredentialUsername(principal.getName()).orElseThrow(() -> new UtenteNotFoundException());
	    Votazione votazione = votazioneR.findBySondaggioIdAndUtenteId(idSondaggio, utente.getId()).orElseThrow(()->new VotazioneNonTrovataException());
	    votazioneR.delete(votazione);
	}
}
