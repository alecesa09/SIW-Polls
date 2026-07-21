package it.uniroma3.siw;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Opzione {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String testo;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="domanda_id")
	private Domanda domanda;
	
	@OneToMany(mappedBy = "opzione")
	@JsonIgnore
	private List<Voto> voti;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public Domanda getDomanda() {
		return domanda;
	}
	public void setDomanda(Domanda domanda) {
		this.domanda = domanda;
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
		Opzione other = (Opzione) obj;
		return Objects.equals(id, other.id);
	}
	
	public List<Voto> getVoti() {
	    return voti;
	}

	public void setVoti(List<Voto> voti) {
	    this.voti = voti;
	}
}
