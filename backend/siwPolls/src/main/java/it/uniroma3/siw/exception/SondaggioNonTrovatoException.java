package it.uniroma3.siw.exception;

public class SondaggioNonTrovatoException extends RuntimeException {

	public SondaggioNonTrovatoException(String s) {
		super("il sondaggio con codice Accesso:"+ s + " non esiste" );
	}
}
