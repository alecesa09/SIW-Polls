package it.uniroma3.siw.exception;

public class SondaggioScadutoException extends RuntimeException {

	public SondaggioScadutoException() {
		super("questo sondaggio è scaduto");
	}
	
}
