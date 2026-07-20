package it.uniroma3.siw;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class,
		  property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Utente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotBlank
	private String nome;
	@NotBlank
	private String cognome;
	@NotBlank @Column(nullable = false, unique = true) 
	private String email;
	
	@JsonIgnore
	@OneToMany(mappedBy="utente")
	List<Sondaggio> sondaggi;
	
	@JsonIgnore
	@OneToMany(mappedBy = "utente", cascade = CascadeType.ALL)
    private List<Voto> voti;
	
	@JsonIgnore
	@OneToMany
    private List<Sondaggio> partecipazioni;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		Utente other = (Utente) obj;
		return Objects.equals(id, other.id);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Voto> getVoti() {
		return voti;
	}

	public void setVoti(List<Voto> voti) {
		this.voti = voti;
	}

	public List<Sondaggio> getSondaggi() {
		return sondaggi;
	}

	public void setSondaggi(List<Sondaggio> sondaggi) {
		this.sondaggi = sondaggi;
	}

	public List<Sondaggio> getPartecipazioni() {
		return partecipazioni;
	}

	public void setPartecipazioni(List<Sondaggio> partecipazioni) {
		this.partecipazioni = partecipazioni;
	}
}
