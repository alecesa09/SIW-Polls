package it.uniroma3.siw.service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
import it.uniroma3.siw.exception.UtenteNotFoundException;
import it.uniroma3.siw.exception.VotazioneIncompletaException;
import it.uniroma3.siw.exception.VotoGiaEspressoException;
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

	public SondaggioService( SondaggioRepository sr, OpzioneRepository or, UtenteRepository ur, DomandaRepository dr,
			VotazioneRepository vr) {
		this.sr = sr;
		this.or = or;
		this.ur = ur;
		this.dr = dr;
		this.vr = vr;
	}

	public List<SondaggioDTO> getSondaggiRecenti(){
		Pageable pageable = PageRequest.of(0, 6);
		return sr.findTop6RecentiAttivi(Sondaggio.Visibilita.PUBBLICO,LocalDate.now(),pageable);
	}

	public Optional<Sondaggio> getSondaggioById(Long id) {
		return sr.findById(id);
	}
	
	@Transactional(isolation = Isolation.SERIALIZABLE,rollbackFor = Exception.class)
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
}
