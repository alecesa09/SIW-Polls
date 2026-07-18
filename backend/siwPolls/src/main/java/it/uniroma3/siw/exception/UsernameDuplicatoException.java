package it.uniroma3.siw.exception;

public class UsernameDuplicatoException extends RuntimeException {
    public UsernameDuplicatoException() {
        super("username gia registrato nel sistema");
    }
}
