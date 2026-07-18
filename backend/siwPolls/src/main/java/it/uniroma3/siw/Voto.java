package it.uniroma3.siw;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Voto {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private LocalDateTime dataVoto;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="utente_id")
    private Utente utente;

    @ManyToOne(fetch = FetchType.LAZY)
    private Opzione opzione;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataVoto() {
		return dataVoto;
	}

	public void setDataVoto(LocalDateTime dataVoto) {
		this.dataVoto = dataVoto;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public Opzione getOpzione() {
		return opzione;
	}

	public void setOpzione(Opzione opzione) {
		this.opzione = opzione;
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
		Voto other = (Voto) obj;
		return Objects.equals(id, other.id);
	}
    
}
