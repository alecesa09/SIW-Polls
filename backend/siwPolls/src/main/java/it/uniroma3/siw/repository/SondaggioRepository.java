package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.uniroma3.siw.Sondaggio;
import it.uniroma3.siw.Utente;
import it.uniroma3.siw.dto.SondaggioDTO;

public interface SondaggioRepository extends JpaRepository<Sondaggio, Long> {
	
	List<SondaggioDTO> findTop6ByVisibilitaOrderByDataCreazioneDesc(Sondaggio.Visibilita visibilita);
}
