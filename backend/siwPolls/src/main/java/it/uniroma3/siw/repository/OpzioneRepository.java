package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.Opzione;

public interface OpzioneRepository extends JpaRepository<Opzione, Long> {
    
    boolean existsByIdAndDomandaIdAndDomandaSondaggioId(
        Long opzioneId, Long domandaId, Long sondaggioId);
}
