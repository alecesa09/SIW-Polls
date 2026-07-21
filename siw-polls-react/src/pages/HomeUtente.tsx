import { useEffect, useState } from "react";
import { useAuth } from "../components/AuthContext";
import { BACKEND_URL } from '../components/config';
import styles from './HomeUtente.module.css';
import { useNavigate, Link } from "react-router-dom";
import type { SondaggioDTO } from "../types"; 
import { ricercaSondaggiUtente, ricercaSondaggiVotatiUtente } from "../service/SondaggioService";

export default function UtenteHome() {
    const { utente } = useAuth(); 
    const navigate = useNavigate();
    
    const [sondaggiCreati, setSondaggiCreati] = useState<SondaggioDTO[]>([]);
    const [sondaggiVotati, setSondaggiVotati] = useState<SondaggioDTO[]>([]);

    useEffect(() => {
        if (!utente) {
            window.location.href = `${BACKEND_URL}/login`; 
            return; 
        }

        const caricaDati = async () => {
            try {
              const creati = await ricercaSondaggiUtente(); 
              const votati = await ricercaSondaggiVotatiUtente();
              
              setSondaggiCreati(creati);
              setSondaggiVotati(votati); // Aggiunto il salvataggio nello stato [verificato]
              
            } catch (error) {
                console.error("Errore durante il recupero delle informazioni utente:", error);
            }
        };

        caricaDati();

    }, [utente]);

    return(
        <div className={styles.containerPrincipale}>
            
            {/* Contenitore 1: Sondaggi Creati */}
            <div className={styles.sezione}>
                <h2>I tuoi sondaggi</h2>
                {sondaggiCreati.length === 0 ? (
                    <p>Non hai ancora creato alcun sondaggio.</p>
                ) : (
                    <ul className={styles.listaSondaggi}>
                        {sondaggiCreati.map((sondaggio) => (
                            <li key={sondaggio.id} className={styles.cardSondaggio}>
                                {/* Link verso la pagina di dettaglio o gestione del sondaggio [verificato] */}
                                <Link to={`/sondaggio/${sondaggio.id}`}>
                                    {sondaggio.titolo} 
                                </Link>
                            </li>
                        ))}
                    </ul>
                )}
            </div>

            <hr className={styles.separatore} />

            {/* Contenitore 2: Voti Modificabili */}
            <div className={styles.sezione}>
                <h2>I tuoi voti ancora modificabili</h2>
                {sondaggiVotati.length === 0 ? (
                    <p>Non hai espresso voti modificabili.</p>
                ) : (
                    <ul className={styles.listaSondaggi}>
                        {sondaggiVotati.map((sondaggio) => (
                            <li key={sondaggio.id} className={styles.cardSondaggio}>
                                {/* Link verso il form riutilizzabile per la modifica [verificato] */}
                                <Link to={`/sondaggio/${sondaggio.id}/modifica`}>
                                    Modifica la tua votazione per: {sondaggio.titolo}
                                </Link>
                            </li>
                        ))}
                    </ul>
                )}
            </div>
            
        </div>
    );
}
