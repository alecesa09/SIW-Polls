package it.uniroma3.siw;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.uniroma3.siw.Sondaggio.Visibilita;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Votazione {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sondaggio_id")
    private Sondaggio sondaggio;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;
    
    public enum registrazione { NORMALE, ANONIMA }
    
    @Enumerated(EnumType.STRING)
    private registrazione visibilita;

    private LocalDateTime dataVoto;

    @OneToMany(mappedBy = "votazione", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Voto> voti = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Sondaggio getSondaggio() {
		return sondaggio;
	}

	public void setSondaggio(Sondaggio sondaggio) {
		this.sondaggio = sondaggio;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public LocalDateTime getDataVoto() {
		return dataVoto;
	}

	public void setDataVoto(LocalDateTime dataVoto) {
		this.dataVoto = dataVoto;
	}

	public List<Voto> getVoti() {
		return voti;
	}

	public void setVoti(List<Voto> voti) {
		this.voti = voti;
	}

	public Votazione(Sondaggio sondaggio, Utente utente,Votazione.registrazione visibilita, LocalDateTime dataVoto) {
		this.sondaggio = sondaggio;
		this.utente = utente;
		this.dataVoto = dataVoto;
		this.visibilita=visibilita;
	}

	public registrazione getVisibilita() {
		return visibilita;
	}

	public void setVisibilita(registrazione visibilita) {
		this.visibilita = visibilita;
	}
    
    
}
