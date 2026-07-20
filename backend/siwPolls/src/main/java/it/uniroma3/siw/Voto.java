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
    
    @ManyToOne
    @JoinColumn(name = "votazione_id")
    private Votazione votazione;

    @ManyToOne
    @JoinColumn(name = "domanda_id")
    private Domanda domanda;

    @ManyToOne(fetch = FetchType.LAZY)
    private Opzione opzione;

	public Voto(Domanda domanda, Opzione opzione, Votazione v) {
		this.domanda=domanda;
		this.opzione=opzione;
		this.votazione=v;
		v.getVoti().add(this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
