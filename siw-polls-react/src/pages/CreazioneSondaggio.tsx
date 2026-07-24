// src/pages/CreazioneSondaggio.tsx (o simile)

import { useState } from "react";
import { useNavigate } from "react-router-dom";
import CreazioneDomanda from "../components/CreazioneSondaggio/CreazioneDomanda";
// Importa le interfacce e la funzione dal tuo service
import type { SondaggioForm, DomandaForm } from "../types/index.ts";
import { creaSondaggio } from "../service/SondaggioService";
import styles from './CreazioneSondaggio.module.css';

export default function CreazioneSondaggio() {
  const navigate = useNavigate();
  const [file, setFile] = useState<File | null>(null);
  const [sondaggio, setSondaggio] = useState<SondaggioForm>({
    titolo: '',
    descrizione: '',
    domande: [],
    dataScadenzaVoto: '',
    visibilita: 'PUBBLICO',
  });

  const aggiungiDomanda = () => {
    setSondaggio(prev => ({
      ...prev,
      domande: [...prev.domande, { testo: '', opzioni: [{ testo: '' }] }]
    }));
  };

  const rimuoviDomanda = (index: number) => {
    setSondaggio(prev => ({
      ...prev,
      domande: prev.domande.filter((_, i) => i !== index)
    }));
  };

  const aggiornaDomanda = (index: number, nuovaDomanda: DomandaForm) => {
    setSondaggio(prev => {
      const nuoveDomande = [...prev.domande];
      nuoveDomande[index] = nuovaDomanda;
      return { ...prev, domande: nuoveDomande };
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!sondaggio.titolo.trim() || sondaggio.domande.length === 0) return;
    
    try {
      // L'idea è evitare di fare una seconda chiamata, inviando 
      // questionario (e logiche come il voto anonimo) insieme all'immagine
      await creaSondaggio(sondaggio, file);
      navigate("/utenteHome");
    } catch (err) {
      console.error(err);
      alert("Qualcosa è andato storto nell'invio del sondaggio");
    }
  };

  return (
  <form onSubmit={handleSubmit} className={styles.formContainer}>
    <h1 className={styles.formTitle}>Crea un nuovo sondaggio</h1>

    <div className={styles.formGroup}>
      <label className={styles.label}>Titolo:</label>
      <input
        type="text"
        className={styles.input}
        value={sondaggio.titolo}
        placeholder="Inserisci il titolo del sondaggio"
        onChange={(e) => setSondaggio(prev => ({ ...prev, titolo: e.target.value }))}
      />
    </div>

    <div className={styles.formGroup}>
      <label className={styles.label}>Descrizione:</label>
      <input
        type="text"
        className={styles.input}
        value={sondaggio.descrizione}
        placeholder="Breve descrizione (opzionale)"
        onChange={(e) => setSondaggio(prev => ({ ...prev, descrizione: e.target.value }))}
      />
    </div>

    <div className={styles.formGroup}>
      <label className={styles.label}>Data Scadenza Votazione:</label>
      <input
        type="date"
        className={styles.input}
        value={sondaggio.dataScadenzaVoto}
        onChange={(e) => setSondaggio(prev => ({ ...prev, dataScadenzaVoto: e.target.value }))}
      />
    </div>

    <div className={styles.formGroup}>
      <label className={styles.label}>Immagine di copertina:</label>
      <input 
        type="file" 
        className={styles.input}
        onChange={(e) => setFile(e.target.files?.[0] || null)} 
      />
    </div>

    <div className={styles.checkboxGroup}>
      <input
        type="checkbox"
        id="visibilitaPrivata"
        className={styles.checkboxInput}
        checked={sondaggio.visibilita === 'PRIVATO'}
        onChange={(e) => setSondaggio(prev => ({
          ...prev,
          visibilita: e.target.checked ? 'PRIVATO' : 'PUBBLICO'
        }))}
      />
      <label htmlFor="visibilitaPrivata" className={styles.label}>
        Rendi il sondaggio privato (accesso solo tramite codice)
      </label>
    </div>
    
    <div>
      <button type="button" className={styles.btnSecondary} onClick={aggiungiDomanda}>
        + Nuova Domanda
      </button>
    </div>

    {sondaggio.domande.map((domanda, index) => (
      <div key={index} className={styles.domandaCard}>
        {/* Passa gli stili o ricrea queste classi all'interno di CreazioneDomanda */}
        <CreazioneDomanda
          index={index}
          domanda={domanda}
          onUpdate={aggiornaDomanda}
          onRemove={() => rimuoviDomanda(index)}
        />
      </div>
    ))}

    <button type="submit" className={styles.btnPrimary}>Crea Sondaggio</button>
  </form>
);
}