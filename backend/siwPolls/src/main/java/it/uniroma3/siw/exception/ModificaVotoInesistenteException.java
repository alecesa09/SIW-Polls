package it.uniroma3.siw.exception;

public class ModificaVotoInesistenteException extends RuntimeException {

	public ModificaVotoInesistenteException() {
		super("non puoi modificare una votazione che non esiste ( attenzione se si è messo voto anonimo non è piu possibile modificare o visualizzare la votazione");
	}
	
}
