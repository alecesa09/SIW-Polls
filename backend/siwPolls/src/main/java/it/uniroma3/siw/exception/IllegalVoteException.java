package it.uniroma3.siw.exception;

public class IllegalVoteException extends RuntimeException {
	public IllegalVoteException () {
		super("i le risposte inserite non corrispondo al sondaggio o alle domande");
	}
}
