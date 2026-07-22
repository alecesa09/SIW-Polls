package it.uniroma3.siw.dto;

import java.time.LocalDate;

public class SondaggioDTO {
	private Long id;
	
	private String titolo;
	
	private String immagine;
	
	private LocalDate dataScadenzaVoto;

	public SondaggioDTO(Long id, String titolo, String immagine, LocalDate dataScadenzaVoto) {
	    this.id = id;
	    this.titolo = titolo;
	    this.immagine = immagine;
	    this.dataScadenzaVoto = dataScadenzaVoto;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}

	public LocalDate getScadenzaVoto() {
		return dataScadenzaVoto;
	}

	public void setScadenzaVoto(LocalDate scadenzaVoto) {
		this.dataScadenzaVoto = scadenzaVoto;
	}
	
	
}
