package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.Commento;

public interface CommentoRepository extends JpaRepository<Commento,Long > {

	List<Commento> findBySondaggioId(Long id);

}
