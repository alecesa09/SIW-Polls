package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.Voto;
import it.uniroma3.siw.dto.VotoDTO;

public interface VotoRepository extends JpaRepository<Voto, Long> {
	@Query("SELECT new it.uniroma3.siw.dto.VotoDTO(v.domanda.id, v.opzione.id) "
		     + "FROM Voto v "
		     + "WHERE v.votazione.utente.id = :idUtente AND v.votazione.sondaggio.id = :idSondaggio")
		List<VotoDTO> getVotiSondaggio(@Param("idSondaggio") Long idSondaggio, @Param("idUtente") Long idUtente);
			
	
}
