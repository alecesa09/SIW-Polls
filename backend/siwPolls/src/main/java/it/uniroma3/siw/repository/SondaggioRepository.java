package it.uniroma3.siw.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
	
	@Query("SELECT s FROM Sondaggio s WHERE s.visibilita = 'PUBBLICO' AND LOWER(s.titolo) LIKE LOWER(CONCAT('%', :str, '%'))")
	List<SondaggioDTO> search(@Param("str") String str, Pageable pageable);

	SondaggioDTO findByCodiceAccesso(String str);
	@Query("SELECT s FROM Sondaggio s WHERE s.visibilita = PUBBLICO AND s.id = :id")
	Optional<Sondaggio> findByIdPubblici(@Param("id") Long id);
	List<SondaggioDTO> findByUtente(Utente utente);
	@Query("SELECT new it.uniroma3.siw.dto.SondaggioDTO(v.sondaggio.id, v.sondaggio.titolo, v.sondaggio.immagine, v.sondaggio.dataScadenzaVoto) FROM Votazione v WHERE v.utente.id = :id AND v.sondaggio.dataScadenzaVoto > CURRENT_DATE")
	List<SondaggioDTO> findSondaggiVotatiPerUtente(@Param("id") Long id);
	
}
