package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.Commento;

public interface CommentoRepository extends JpaRepository<Commento,Long > {
	
	@Query("SELECT c FROM Commento c WHERE c.sondaggio.codiceAccesso = :cod")
	List<Commento> findByCodiceAccesso(@Param("cod") String cod);

}
