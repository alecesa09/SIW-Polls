package it.uniroma3.siw.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.Sondaggio;
import it.uniroma3.siw.dto.SondaggioDTO;
import it.uniroma3.siw.repository.SondaggioRepository;

@Service
public class SondaggioService {
	private final SondaggioRepository sr;
	public SondaggioService(SondaggioRepository sr) {
		this.sr=sr;
	}
	
	public List<SondaggioDTO> getSondaggiRecenti(){
		Pageable pageable = PageRequest.of(0, 6);
		return sr.findTop6RecentiAttivi(Sondaggio.Visibilita.PUBBLICO,LocalDate.now(),pageable);
	}

	public Optional<Sondaggio> getSondaggioById(Long id) {
		return sr.findById(id);
	}
}
