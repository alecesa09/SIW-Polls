package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long>{
	public boolean existsByEmail(String email);
	@Query("SELECT c.utente FROM Credential c WHERE c.username = :username")
	Optional<Utente> findByCredentialUsername(@Param("username") String username);

	 public boolean existsByIdAndPartecipazioniId(Long id, Long id2);
	 
}
