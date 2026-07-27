package it.uniroma3.siw.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

@RestControllerAdvice(basePackages = "it.uniroma3.siw.controller.rest")
public class RestExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    // --- Validazione automatica di @Valid sui DTO/Entity ---
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException ex) {
        String messaggio = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        logger.error("Errore di validazione: {}", messaggio);
        return ResponseEntity.badRequest().body(messaggio);
    }

    // --- 400 Bad Request: input del client non valido ---
    @ExceptionHandler({
        DataScadenzaNelPassatoException.class,
        IllegalVoteException.class,
        VotazioneIncompletaException.class,
        IllegalArgumentException.class,
        EmailUtenteDuplicataException.class,
        UsernameDuplicatoException.class,
        SondaggioScadutoException.class,
        VotoGiaEspressoException.class,
        ModificaVotoInesistenteException.class,
    })
    public ResponseEntity<String> handleBadRequest(RuntimeException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    // --- 404 Not Found: risorsa non esistente ---
    @ExceptionHandler({
        SondaggioNonTrovatoException.class,
        UtenteNotFoundException.class,
        VotazioneNonTrovataException.class
    })
    public ResponseEntity<String> handleNotFound(RuntimeException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(SalvataggioImmagineException.class)
    public ResponseEntity<String> handleSalvataggioImmagineException(SalvataggioImmagineException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error("Eccezione non gestita", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Si è verificato un errore imprevisto.");
    }
}
