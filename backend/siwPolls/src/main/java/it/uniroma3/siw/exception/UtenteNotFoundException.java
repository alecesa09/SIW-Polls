package it.uniroma3.siw.exception;

public class UtenteNotFoundException extends RuntimeException {

	public UtenteNotFoundException() {
		super("utente non trovato");
	}
	
}
