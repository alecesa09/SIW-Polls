import { useState } from "react";
import CreazioneDomanda from "../components/CreazioneSondaggio/CreazioneDomanda";
import { creaSondaggio } from "../service/SondaggioService";
import { useNavigate } from "react-router-dom";

interface OpzioneForm { testo: string; }
interface DomandaForm { testo: string; opzioni: OpzioneForm[]; }

export default function CreazioneSondaggio() {
    const navigate =useNavigate();
  const [sondaggio, setSondaggio] = useState<{
    titolo: string;
    descrizione: string;
    dataScadenzaVoto: string;
    visibilita: 'PUBBLICO' | 'PRIVATO';
    domande: DomandaForm[];
  }>({
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
    await creaSondaggio(sondaggio);
        navigate("/utenteHome")
  } catch (err) {
    console.error(err);
        alert("qualcosa è andato storto nell invio del sondaggio")
  }
};

  return (
    <form onSubmit={handleSubmit}>
        <label>Titolo:</label>
        <input
        type="text"
        value={sondaggio.titolo}
        onChange={(e) => setSondaggio(prev => ({ ...prev, titolo: e.target.value }))}
        />
        <label>Descrizione:</label>
        <input
        type="text"
        value={sondaggio.descrizione}
        onChange={(e) => setSondaggio(prev => ({ ...prev, descrizione: e.target.value }))}
        />
        <label>Data Dcadenza Votazione:</label>
        <input
        type="date"
        value={sondaggio.dataScadenzaVoto}
        onChange={(e) => setSondaggio(prev => ({ ...prev, dataScadenzaVoto: e.target.value }))}
        />
        <br></br>

        <label>rende il sondaggio accessibile solo a chi ha codice di accesso?</label>
        <input
            type="checkbox"
            checked={sondaggio.visibilita === 'PRIVATO'}
            onChange={(e) => setSondaggio(prev => ({
                ...prev,
                visibilita: e.target.checked ? 'PRIVATO' : 'PUBBLICO'
            }))}
        />
       
        <br></br>
        
        <button type="button" onClick={aggiungiDomanda}>Nuova Domanda</button>

        {sondaggio.domande.map((domanda, index) => (
        <div key={index} style={{ margin: '10px 0' }}>
            <CreazioneDomanda
            index={index}
            domanda={domanda}
            onUpdate={aggiornaDomanda}
            onRemove={() => rimuoviDomanda(index)}
            />
        </div>
      ))}

        <button type="submit">Crea Sondaggio</button>
    </form>
  );
}