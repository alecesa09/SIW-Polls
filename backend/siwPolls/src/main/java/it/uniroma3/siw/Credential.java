package it.uniroma3.siw;

import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;

@Entity 
public class Credential {
	public static final String DEFAULT_ROLE = "DEFAULT";
	public static final String ADMIN_ROLE = "ADMIN";
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id;
	@Column(nullable = false, unique = true)
	@NotBlank
	private String username;
	
	@Column(nullable = false, unique = false) 
	@NotBlank
	private String psw; 
	
	private String ruolo; 
	
	@OneToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY) 
	private Utente utente;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public static String getDefaultRole() {
		return DEFAULT_ROLE;
	}
	public static String getAdminRole() {
		return ADMIN_ROLE;
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
		Credential other = (Credential) obj;
		return Objects.equals(id, other.id);
	}
	public String getPsw() {
		return psw;
	}
	public void setPsw(String psw) {
		this.psw = psw;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	} 
	
	
	
}
