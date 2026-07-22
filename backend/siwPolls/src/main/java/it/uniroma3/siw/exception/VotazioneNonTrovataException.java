package it.uniroma3.siw.exception;

public class VotazioneNonTrovataException extends RuntimeException {

	public VotazioneNonTrovataException() {
		super("la votazione non esiste");
	}
	
}
