package it.uniroma3.siw.exception;

public class SondaggioNonTrovatoException extends RuntimeException {

	public SondaggioNonTrovatoException(Long id) {
		super("il sondaggio con id:"+ id);
	}
	
}
