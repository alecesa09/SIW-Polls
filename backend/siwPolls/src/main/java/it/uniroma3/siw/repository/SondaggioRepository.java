package it.uniroma3.siw.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.Sondaggio;
import it.uniroma3.siw.Utente;
import it.uniroma3.siw.dto.SondaggioDTO;
import it.uniroma3.siw.dto.StatisticheDTO;

public interface SondaggioRepository extends JpaRepository<Sondaggio, Long> {
	@Query("SELECT s FROM Sondaggio s WHERE s.visibilita = :visibilita AND s.dataScadenzaVoto >= :oggi ORDER BY s.dataCreazione DESC")
	List<SondaggioDTO> findTop6RecentiAttivi(@Param("visibilita") Sondaggio.Visibilita visibilita, @Param("oggi") LocalDate oggi, Pageable pageable);
	@Query("SELECT new it.uniroma3.siw.dto.StatisticheDTO(d.id, o.id, COUNT(v.id)) " +
		       "FROM Sondaggio s " +
		       "JOIN s.domande d " +
		       "JOIN d.opzioni o " +
		       "LEFT JOIN o.voti v " +
		       "WHERE s.id = :id " +
		       "GROUP BY d.id, o.id")
	List<StatisticheDTO> getStatistiche(@Param("id") Long id);
	
}
