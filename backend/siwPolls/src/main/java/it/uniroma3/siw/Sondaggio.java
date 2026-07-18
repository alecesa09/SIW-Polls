package it.uniroma3.siw;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity
public class Sondaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String titolo;

    @NotBlank
    private String descrizione;

    private String immagine;

    @NotNull
    private LocalDate dataScadenza;

    private LocalDate dataCreazione;

    public enum Visibilita { PUBBLICO, PRIVATO }

    @Enumerated(EnumType.STRING)
    private Visibilita visibilita;

    @Column(unique = true)
    private String codiceAccesso;

    @OneToMany(mappedBy = "sondaggio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Domanda> domande;
    
    @OneToMany(mappedBy = "sondaggio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commento> commenti;

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}

	public LocalDate getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(LocalDate dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public LocalDate getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(LocalDate dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Visibilita getVisibilita() {
		return visibilita;
	}

	public void setVisibilita(Visibilita visibilita) {
		this.visibilita = visibilita;
	}

	public String getCodiceAccesso() {
		return codiceAccesso;
	}

	public void setCodiceAccesso(String codiceAccesso) {
		this.codiceAccesso = codiceAccesso;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Domanda> getDomande() {
		return domande;
	}

	public void setDomande(List<Domanda> domande) {
		this.domande = domande;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sondaggio other = (Sondaggio) obj;
		return Objects.equals(id, other.id);
	}

	public List<Commento> getCommenti() {
		return commenti;
	}

	public void setCommenti(List<Commento> commenti) {
		this.commenti = commenti;
	} 
    
    
}
