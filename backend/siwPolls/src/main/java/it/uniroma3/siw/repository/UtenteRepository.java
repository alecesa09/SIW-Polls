package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long>{
	public boolean existsByEmail(String email);
	@Query("SELECT c.utente FROM Credential c WHERE c.username = :username")
	Optional<Utente> findByCredentialUsername(@Param("username") String username);

	public boolean existsByIdAndPartecipazioniCodiceAccesso(Long idUtente, String cod);
	public boolean existsByIdAndPartecipazioniId(Long idUtente, Long idSondaggio);
	
	@Query("SELECT u FROM Utente u WHERE " +
		       "(:nome IS NULL OR LOWER(u.nome) LIKE :nome) AND " +
		       "(:cognome IS NULL OR LOWER(u.cognome) LIKE :cognome) AND " +
		       "(:id IS NULL OR u.id = :id)")
		public List<Utente> findByParametri(
		    @Param("nome") String nome,
		    @Param("cognome") String cognome,
		    @Param("id") Long id
		);
	 
	 
}
