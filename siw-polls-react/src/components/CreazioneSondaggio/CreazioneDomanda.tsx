import styles from './CreazioneDomanda.module.css';

interface OpzioneForm { testo: string; }
interface DomandaForm { testo: string; opzioni: OpzioneForm[]; }

interface Props {
  index: number;
  domanda: DomandaForm;
  onUpdate: (index: number, nuovaDomanda: DomandaForm) => void;
  onRemove: () => void;
}

export default function CreazioneDomanda({ index, domanda, onUpdate, onRemove }: Props) {
  const aggiornaTesto = (testo: string) => {
    onUpdate(index, { ...domanda, testo });
  };

  const aggiungiOpzione = () => {
    const nuoveOpzioni = [...domanda.opzioni, { testo: '' }];
    onUpdate(index, { ...domanda, opzioni: nuoveOpzioni });
  };

  const rimuoviOpzione = (idx: number) => {
    const nuoveOpzioni = domanda.opzioni.filter((_, i) => i !== idx);
    onUpdate(index, { ...domanda, opzioni: nuoveOpzioni });
  };

  const aggiornaOpzione = (idx: number, testo: string) => {
    const nuoveOpzioni = [...domanda.opzioni];
    nuoveOpzioni[idx] = { testo };
    onUpdate(index, { ...domanda, opzioni: nuoveOpzioni });
  };

  return (
    <div className={styles.domandaCard}>
      {/* Intestazione della Domanda */}
      <div className={styles.domandaHeader}>
        <span className={styles.domandaTitle}>Domanda {index + 1}</span>
        <button type="button" className={styles.btnDanger} onClick={onRemove}>
          Rimuovi domanda
        </button>
      </div>

      {/* Input Testo Domanda */}
      <div className={styles.formGroup}>
        <label className={styles.label}>Testo della domanda:</label>
        <input
          type="text"
          className={styles.input}
          placeholder="Es. Qual è il tuo linguaggio di programmazione preferito?"
          value={domanda.testo}
          onChange={(e) => aggiornaTesto(e.target.value)}
        />
      </div>

      {/* Opzioni */}
      <div className={styles.formGroup}>
        <label className={styles.label}>Risposte possibili:</label>
        {domanda.opzioni.map((opzione, idx) => (
          <div key={idx} className={styles.opzioneRow}>
            <input
              type="text"
              className={styles.input}
              placeholder={`Opzione ${idx + 1}`}
              value={opzione.testo}
              onChange={(e) => aggiornaOpzione(idx, e.target.value)}
            />
            {/* Nascondiamo il tasto X se c'è una sola opzione (opzionale ma consigliato per UX) */}
            {domanda.opzioni.length > 1 && (
              <button 
                type="button" 
                className={styles.btnIconDanger} 
                onClick={() => rimuoviOpzione(idx)}
                title="Rimuovi questa risposta"
              >
                X
              </button>
            )}
          </div>
        ))}
      </div>

      {/* Bottone per aggiungere nuova opzione */}
      <button type="button" className={styles.btnSecondary} onClick={aggiungiOpzione}>
        + Nuova risposta
      </button>
    </div>
  );
}