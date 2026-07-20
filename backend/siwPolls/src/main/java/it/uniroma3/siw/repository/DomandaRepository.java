package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.Credential;
import it.uniroma3.siw.Domanda;

public interface DomandaRepository extends JpaRepository<Domanda, Long> {

}
