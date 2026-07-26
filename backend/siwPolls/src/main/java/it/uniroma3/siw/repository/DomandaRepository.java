package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.Credential;
import it.uniroma3.siw.Domanda;

public interface DomandaRepository extends JpaRepository<Domanda, Long> {
	//per evitare problema n+1
		@Query("SELECT DISTINCT d FROM Domanda d LEFT JOIN FETCH d.opzioni WHERE d.sondaggio.id = :sondaggioId")
		List<Domanda> findDomandeConOpzioniBySondaggioId(@Param("sondaggioId") Long sondaggioId);
}
