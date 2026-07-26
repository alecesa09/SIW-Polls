package it.uniroma3.siw.prestazioni;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import it.uniroma3.siw.Domanda;
import it.uniroma3.siw.Opzione;
import it.uniroma3.siw.Sondaggio;
import it.uniroma3.siw.repository.SondaggioRepository;


import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;



@Component
public class PrestazioniTestRunner implements CommandLineRunner {
   
    @Autowired
    private SondaggioRepository sr;
      
    @Override
    @Transactional // strategie per il fetch dellaa classifica
    public void run(String... args) throws Exception {

        String codice = "9F3B2C11-6A4D-4E7F-8B21-1234567890AB";
        
        /* 
        // warm-up per il primo test
        sr.findSondaggioByCodiceAccesso("3AE83C52-5855-47C1-80A6-FB880DE6D31B").get().getDomande();

        // --- TEST 1: LAZY ---
        StopWatch stopWatchLazy = new StopWatch();
        stopWatchLazy.start();

        Sondaggio sondaggio = sr.findSondaggioByCodiceAccesso(codice).get();
        
        for (Domanda d : sondaggio.getDomande()) {
            Set<Opzione> opzioni = d.getOpzioni();
            opzioni.size(); // forza il lazy
        }

        stopWatchLazy.stop();
        System.out.println("Tempo LAZY: " + stopWatchLazy.getTotalTimeSeconds() + " secondi");
        
       
        // warm-up per il secondo test (query diversa, serve il suo warm-up separato)
        sr.findCompletoByCod("3AE83C52-5855-47C1-80A6-FB880DE6D31B");

        // --- TEST 2: JOIN FETCH ---
        StopWatch stopWatchJoinFetch = new StopWatch();
        stopWatchJoinFetch.start();

        Sondaggio sondaggioJoinFetch = sr.findCompletoByCod(codice).get();
        for (Domanda d : sondaggioJoinFetch.getDomande()) {
            Set<Opzione> opzioni = d.getOpzioni();
            opzioni.size();
        }

        stopWatchJoinFetch.stop();
        
        System.out.println("Tempo JOIN FETCH: " + stopWatchJoinFetch.getTotalTimeSeconds() + " secondi");

        System.out.println("--- FINE TEST PRESTAZIONI ---");
        */
    }
    
    //Tempo LAZY:       1.663120299 secondi
    //Tempo EAGER:      0.246633101 secondi
    //Tempo JOIN FETCH: 0.246935801 secondi
}
