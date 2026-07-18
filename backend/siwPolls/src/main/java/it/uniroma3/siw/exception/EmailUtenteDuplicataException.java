package it.uniroma3.siw.exception;

public class EmailUtenteDuplicataException extends RuntimeException {
	public EmailUtenteDuplicataException() {
		super("la mail inserita è gia stata registrata");
	}

}
