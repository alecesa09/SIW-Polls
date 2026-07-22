import { useState } from "react";

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
    <div style={{ border: '1px solid #ccc', padding: '10px', marginBottom: '10px' }}>
      <label>Testo domanda:</label>
      <input
        type="text"
        value={domanda.testo}
        onChange={(e) => aggiornaTesto(e.target.value)}
      />
      <button type="button" onClick={aggiungiOpzione}>Nuova risposta</button>
      <button type="button" onClick={onRemove}>Rimuovi domanda</button>

      {domanda.opzioni.map((opzione, idx) => (
        <div key={idx} style={{ margin: '5px 0' }}>
          <label>Testo risposta:</label>
          <input
            type="text"
            value={opzione.testo}
            onChange={(e) => aggiornaOpzione(idx, e.target.value)}
          />
          <button type="button" onClick={() => rimuoviOpzione(idx)}>X</button>
        </div>
      ))}
    </div>
  );
}