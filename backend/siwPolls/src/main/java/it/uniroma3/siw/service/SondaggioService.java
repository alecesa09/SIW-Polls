package it.uniroma3.siw.service;

import java.util.List;

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
		return sr.findTop6ByVisibilitaOrderByDataCreazioneDesc(Sondaggio.Visibilita.PUBBLICO);
	}
}
