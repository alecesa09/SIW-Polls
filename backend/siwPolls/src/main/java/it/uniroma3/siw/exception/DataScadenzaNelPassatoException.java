package it.uniroma3.siw.exception;

public class DataScadenzaNelPassatoException extends RuntimeException {

	public DataScadenzaNelPassatoException(String data) {
		super("la data di scadenza non puo essere nel passato");
	}
	
}
