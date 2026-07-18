package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long>{
	public boolean existsByEmail(String email);
}
