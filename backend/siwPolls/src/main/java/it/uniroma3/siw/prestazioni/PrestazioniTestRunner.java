package it.uniroma3.siw.prestazioni;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import it.uniroma3.siw.Domanda;
import it.uniroma3.siw.Opzione;
import it.uniroma3.siw.Sondaggio;
import it.uniroma3.siw.repository.DomandaRepository;
import it.uniroma3.siw.repository.SondaggioRepository;


import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;



@Component
public class PrestazioniTestRunner implements CommandLineRunner {
   
    @Autowired
    private SondaggioRepository sr;
    @Autowired
    private DomandaRepository dr;
    
    
    
    @Override
    @Transactional // strategie per il fetch dellaa classifica
    public void run(String... args) throws Exception {
    	
    	
    	//caso d`uso  visualizzare il sondaggio
    	sr.findSondaggioByCodiceAccesso("TECH2026").get().getDomande();//warm up
        StopWatch stopWatchBase = new StopWatch();
        stopWatchBase.start();
        Sondaggio sondaggio = sr.findSondaggioByCodiceAccesso("TECH2026").get();
        //List<Domanda> domande = sondaggio.getDomande(); //eager lazy
        List<Domanda> domande = dr.findDomandeConOpzioniBySondaggioId(sondaggio.getId());//join fetch
        ;
        for (Domanda d : domande) {
        	List<Opzione> opzioni =d.getOpzioni();
        	opzioni.size(); //per forzare il lazy
        }
        stopWatchBase.stop();
        System.out.println("Tempo impiegato: " + stopWatchBase.getTotalTimeSeconds() + " secondi");
        //1.5048566 secondi lazy n+1 query
        //1.414207 secondi eager n+1 query
        //0.1887739 secondi join fetch  3 query
        
        
        System.out.println("--- FINE TEST PRESTAZIONI ---");
        
    }
}
