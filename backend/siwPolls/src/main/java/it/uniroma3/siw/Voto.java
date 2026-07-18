package it.uniroma3.siw;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Voto {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private LocalDateTime dataVoto;

    //@ManyToOne
    //private Utente utente;

    //@ManyToOne
    //private Opzione opzione;
}
