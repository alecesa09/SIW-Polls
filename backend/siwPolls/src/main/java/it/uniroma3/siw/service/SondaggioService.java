package it.uniroma3.siw.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import it.uniroma3.siw.Sondaggio;
import it.uniroma3.siw.Utente;
import it.uniroma3.siw.dto.SondaggioDTO;
import it.uniroma3.siw.dto.StatisticheDTO;
import it.uniroma3.siw.exception.DataScadenzaNelPassatoException;
import it.uniroma3.siw.exception.SalvataggioImmagineException;
import it.uniroma3.siw.exception.SondaggioNonTrovatoException;
import it.uniroma3.siw.exception.UtenteNotFoundException;
import it.uniroma3.siw.repository.SondaggioRepository;
import it.uniroma3.siw.repository.UtenteRepository;


@Service
public class SondaggioService {
	private final SondaggioRepository sr;
	private final UtenteRepository ur;

	
	public SondaggioService(SondaggioRepository sr, UtenteRepository ur) {
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
	public List<StatisticheDTO> getStatistiche(String id) {
		List<StatisticheDTO> statistiche = sr.getStatistiche(id);
		for(StatisticheDTO statistica : statistiche) {
		logger.info(statistica.toString());
		}
		return statistiche;
	}

	@Transactional(readOnly=true)
	public List<SondaggioDTO> searchSondaggiByTitolo(String str) {
		List<SondaggioDTO> lista =sr.findByTitoloAndPubblico(str, PageRequest.of(0, 5));
		for(SondaggioDTO sondaggio : lista) {
			logger.info("sondaggio"+sondaggio.getId().toString());
			}
		return lista;
	}
	@Transactional(readOnly=true)//
	public Sondaggio searchSondaggioByCodiceAccesso(String str) {
	    logger.info("ricerca sondaggio con codice accesso: " + str);
	    Sondaggio sondaggio = sr.findCompletoByCod(str).orElseThrow(() -> new SondaggioNonTrovatoException(str));
	    return sondaggio;
	}
	
	@Transactional(readOnly=true)
	public List<SondaggioDTO>  getSondaggiPerUtente(Principal principal) {
		Utente utente = ur.findByCredentialUsername(principal.getName()).orElseThrow(() -> new UtenteNotFoundException());
		return sr.findByUtente(utente);
	}
	
	
	@Transactional(isolation=Isolation.READ_COMMITTED)
	public void creaSondaggio(Sondaggio sondaggio, MultipartFile file, Principal principal) {
		Utente utente = ur.findByCredentialUsername(principal.getName()).orElseThrow(() -> new UtenteNotFoundException());
		
		sondaggio.inizializza(utente);
		
		if(sondaggio.getDataScadenza().isBefore(LocalDate.now())) {
			throw new DataScadenzaNelPassatoException(sondaggio.getDataScadenza().toString());
		}
		
		try {
		String nomeFileGenerato = salvaImmagineSuDisco(file);
		sondaggio.setImmagine(nomeFileGenerato);
		} catch (IOException e){
			throw new SalvataggioImmagineException();
		}
		
		sr.save(sondaggio);
	}
	
	@Value("${app.upload.dir}")
	private String uploadDir;
	
	private String salvaImmagineSuDisco(MultipartFile file) throws IOException {
	    if (file == null || file.isEmpty()) {
	        return null;
	    }

	    // 1. Prendo il nome originale per estrarre l'estensione
	    String nomeOriginale = StringUtils.cleanPath(file.getOriginalFilename());
	    String estensione = "";
	    int dotIndex = nomeOriginale.lastIndexOf('.');
	    
	    if (dotIndex >= 0) {
	        estensione = nomeOriginale.substring(dotIndex);
	    }

	    // 2. Genero un nome completamente nuovo e univoco
	    String nomeFileUnivoco =UUID.randomUUID().toString() + estensione;

	    Path cartellaUpload = Paths.get(uploadDir);
	    
	    if (!Files.exists(cartellaUpload)) {
	        Files.createDirectories(cartellaUpload);
	    }
	    
	    // 3. Salvo il file usando il NUOVO nome
	    Path percorsoCompleto = cartellaUpload.resolve(nomeFileUnivoco);
	    Files.copy(file.getInputStream(), percorsoCompleto, StandardCopyOption.REPLACE_EXISTING);
	    
	    // Restituisco il nuovo nome (es. "123e4567-e89b-12d3-a456-426614174000.png")
	    return nomeFileUnivoco; 
	}
	@Transactional(readOnly=true)
	public List<Sondaggio> findAll() {
		return sr.findAll();
	}
	@Transactional(readOnly=true)
	public List<Sondaggio> findByFiltri(String titolo, String codiceAccesso,
            LocalDate dataCreazioneMin, LocalDate dataCreazioneMax) {

		String titoloParam = (titolo != null && !titolo.isBlank())
		? "%" + titolo.trim().toLowerCase() + "%" : null;
		
		String codiceAccessoParam = (codiceAccesso != null && !codiceAccesso.isBlank())
		? "%" + codiceAccesso.trim().toLowerCase() + "%" : null;
		
		return sr.findByfiltri(titoloParam, codiceAccessoParam, dataCreazioneMin, dataCreazioneMax);
		}
	
	@Transactional(isolation=Isolation.REPEATABLE_READ)
	public void cancellaSondaggio(Long id) {
		Sondaggio sondaggio = sr.findById(id).orElseThrow(()-> new SondaggioNonTrovatoException(id.toString()));
		sr.delete(sondaggio);
	}
}
