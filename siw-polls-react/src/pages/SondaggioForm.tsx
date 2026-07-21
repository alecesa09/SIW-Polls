import { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import type { Domanda, Opzione, Sondaggio, Votazione, Voto } from '../types';
import { postVoti } from '../service/SondaggioService';
import styles from './Sondaggio.module.css'; // Assicurati di avere questo file

function SondaggioForm() {
    const location = useLocation();
    const navigate = useNavigate();
    const sondaggio = location.state?.sondaggioGiaCaricato as Sondaggio;
    const [visibilita, setVisibilita] = useState<string>("PUBBLICA");
    const [risposte, setRisposte] = useState<{ [key: number]: number }>({});
    const [isSubmitting, setIsSubmitting] = useState<boolean>(false);

    if (!sondaggio) {
        return <h2 className={styles.centerMessage}>Nessun sondaggio caricato. Torna alla home.</h2>;
    }
    const handleChange = (domandaId: number, opzioneId: number) => {
        setRisposte(prev => ({
            ...prev,
            [domandaId]: opzioneId
        }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault(); 
        
        if (Object.keys(risposte).length < sondaggio.domande.length) {
            alert("Per favore, rispondi a tutte le domande prima di inviare.");
            return;
        }

        const payload: Votazione = {
            sondaggioId: sondaggio.id,
            visibilita: visibilita,
            voti: Object.keys(risposte).map(key => ({
                domandaId: Number(key),
                opzioneId: risposte[Number(key)]
            }))
        };

        setIsSubmitting(true); // Disabilita il bottone

        try {
            await postVoti(payload);
            
            alert("Voto registrato con successo!");
            navigate(`/sondaggio/${sondaggio.id}/commentoForm`);
            
        } catch (error) {
            console.error("Errore durante l'invio del voto:", error);
            alert("Si è verificato un errore durante l'invio del voto. Riprova.");
        } finally {
            setIsSubmitting(false);
        }
    };

    return(
        <div className={styles.container}>
            <h1 className={styles.titolo}>Vota: {sondaggio.titolo}</h1>
            
            <form onSubmit={handleSubmit} className={styles.section}>
                {sondaggio.domande.map((domanda: Domanda) => (
                    <div key={domanda.id} className={styles.domandaCard}>
                        <h3>{domanda.testo}</h3>
                        
                        {/* 2. Cicliamo tutte le OPZIONI per questa domanda */}
                        <div className={styles.opzioniList}>
                            {domanda.opzioni.map((opzione: Opzione) => (
                                <div key={opzione.id} className={styles.opzioneItem}>
                                    <label style={{ display: 'flex', alignItems: 'center', gap: '10px', cursor: 'pointer' }}>
                                        <input 
                                            type="radio" 
                                            name={`domanda_${domanda.id}`} 
                                            value={opzione.id}
                                            checked={risposte[domanda.id] === opzione.id}
                                            onChange={() => handleChange(domanda.id, opzione.id)}
                                            disabled={isSubmitting}
                                        />
                                        {opzione.testo}
                                    </label>
                                </div>
                            ))}
                        </div>
                    </div>
                ))}
                <div style={{ marginBottom: '20px', padding: '15px', backgroundColor: 'rgba(255,255,255,0.1)', borderRadius: '8px' }}>
                    <label style={{ fontWeight: 'bold', display: 'block', marginBottom: '10px' }}>
                        Come vuoi esprimere il tuo voto?
                    </label>
                    <select 
                        value={visibilita} 
                        onChange={(e) => setVisibilita(e.target.value)}
                        style={{ padding: '8px', borderRadius: '4px', width: '100%' }}
                    >
                        <option value="NORMALE">Voto Pubblico (sarai associato al voto ma il voto sara rintracciabile e modificabile)</option>
                        <option value="ANONIMA">Voto Anonimo (non sarai associato al voto ma il voto sara irrintracciabile e non modificabile)</option>
                    </select>
                </div>
                <div className={styles.actionBox}>
                    <button 
                        type="submit" 
                        className={styles.btnPrimary} 
                        disabled={isSubmitting}
                    >
                        {isSubmitting ? "Invio in corso..." : "Invia Voto"}
                    </button>
                </div>
            </form>
        </div>
    );
}

export default SondaggioForm;