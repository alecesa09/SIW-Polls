package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long>{
	public boolean existsByEmail(String email);
	
	 Optional<Utente> findByCredentialUsername(String username);

	 public boolean existsByIdAndPartecipazioniId(Long id, Long id2);
}
