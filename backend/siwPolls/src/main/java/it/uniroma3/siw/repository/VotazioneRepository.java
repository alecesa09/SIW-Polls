package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.uniroma3.siw.Sondaggio;
import it.uniroma3.siw.Votazione;

public interface VotazioneRepository extends JpaRepository<Votazione, Long> {
	
	Optional<Votazione> findBySondaggioCodiceAccessoAndUtenteId(String cod, Long utenteId);

	Optional<Votazione> findBySondaggioIdAndUtenteId(Long idSondaggio, Long id);

}
