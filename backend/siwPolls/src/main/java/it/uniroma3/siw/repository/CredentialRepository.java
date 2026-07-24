package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.Credential;

public interface CredentialRepository extends JpaRepository<Credential, Long> {
	public Optional<Credential> findByUsername(String username);
	
	public boolean existsByUsername(String username);

	public void deleteByUtenteId(Long idUtente);
	
	
}
