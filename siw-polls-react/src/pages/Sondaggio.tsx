import { useEffect, useState } from "react";
import { Link, useParams, useNavigate } from 'react-router-dom';
import { BACKEND_URL } from '../components/config';
import { useAuth } from '../components/AuthContext';
import { ricercaPerCodiceAccesso, getCommentiSondaggio, getStatistiche } from "../service/SondaggioService";
import { controllaPartecipazione, eliminaVotazione, getVotazioneUtente } from "../service/VotazioneService";
import type { Sondaggio, Commento, Statistica } from '../types';
import styles from './Sondaggio.module.css';

export default function Sondaggio() {
    const { cod } = useParams<{ cod: string }>();
    const navigate = useNavigate();
    const { utente } = useAuth();
    const [votoModificabile, setVotoModificabile] = useState<boolean>(false);
    const [sondaggio, setSondaggio] = useState<Sondaggio>();
    const [commenti, setCommenti] = useState<Commento[]>([]);
    const [statistiche, setStatistiche] = useState<Statistica[]>([]);
    const [haGiaVotato, setHaGiaVotato] = useState<boolean>(false);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const [scaduto,setScaduto]= useState<boolean>(false);

    useEffect(() => {
        const fetchDati = async () => {
            if (cod===undefined) {
                setError("codice del sondaggio mancante.");
                setLoading(false);
                return;
            }
            try {
                const sondaggioData = await ricercaPerCodiceAccesso(cod);
                setSondaggio(sondaggioData);
                if (utente) {
                const [giaVotato, commentiData] = await Promise.all([
                    controllaPartecipazione(cod),
                    getCommentiSondaggio(cod)
                ]);
                setScaduto(
                    sondaggioData?.dataScadenza 
                        ? new Date(sondaggioData.dataScadenza).getTime() < Date.now() 
                        : false
                );
                setHaGiaVotato(giaVotato);
                setCommenti(commentiData);

                if (giaVotato) {
                    const [statisticheData, votazioneUtente] = await Promise.all([
                        getStatistiche(cod),
                        getVotazioneUtente(cod)
                    ]);
                    setStatistiche(statisticheData);
                    setVotoModificabile(Boolean(votazioneUtente));
                }
}
            } catch (err: any) {
                console.error("Errore nel recupero del sondaggio:", err);
                if (err.response && err.response.status === 404) {
                    setError("Sondaggio non trovato.");
                } else if (err.response && err.response.status >= 500) {
                    navigate('/500');
                } else {
                    setError("Impossibile caricare il sondaggio al momento.");
                }
            } finally {
                setLoading(false);
            }
        };
        fetchDati();
    }, [cod, navigate, utente]);

    if (loading) return <h2 className={styles.centerMessage}>Caricamento in corso...</h2>;

    if (error) {
        return (
            <div className={styles.errorContainer}>
                <h2>Oops!</h2>
                <p>{error}</p>
                <Link to="/" className={styles.linkHome}>Torna alla Home</Link>
            </div>
        );
    }

    if (!sondaggio) return <h2 className={styles.centerMessage}>Nessun dato disponibile.</h2>;

    const dataFormattata = new Date(sondaggio.dataScadenza).toLocaleDateString('it-IT', {
        year: 'numeric', month: 'long', day: 'numeric'
    });

    // Helper: recupera il conteggio voti per una data opzione dalle statistiche caricate
    const getConteggioVoti = (domandaId: number, opzioneId: number): number => {
        const stat = statistiche.find(
            s => s.domandaId === domandaId && s.opzioneId === opzioneId
        );
        return stat ? stat.numeroVoti : 0;
    };

    const mostraStatistiche = utente && haGiaVotato && statistiche.length > 0;

    const eliminaVoto = async () => {
        try 
        {if (!cod) {
                setError("ID del sondaggio mancante.");
                setLoading(false);
                return;
            }
            await eliminaVotazione(cod);
            alert("Voto eliminato con successo!");
            navigate(`/`);
        } catch (error) {
            console.error("Errore durante l'eliminazione del voto:", error);
            alert("attenzione votazione non associata al tuo account o non hai ancora votato o hai votato in modo anonimo");
        }
    };

    return (
        <div className={styles.container}>
            <header className={styles.header}>
                <h1 className={styles.titolo}>{sondaggio.titolo}</h1>
                <p className={styles.descrizione}>{sondaggio.descrizione}</p>

                {sondaggio.immagine && (
                    <div className={styles.imageContainer}>
                        <img
                            src={`${BACKEND_URL}/immagini/${sondaggio.immagine}`}
                            alt={sondaggio.titolo}
                            className={styles.immagine}
                        />
                    </div>
                )}
                <p className={styles.scadenza}>
                    Scade il: {dataFormattata}
                </p>
            </header>

            <section className={styles.section}>
                <h2>Domande del Sondaggio ({sondaggio.domande.length})</h2>
                {sondaggio.domande.map((domanda) => (
                    <div key={domanda.id} className={styles.domandaCard}>
                        <h3>{domanda.testo}</h3>
                        <ul className={styles.opzioniList}>
                            {domanda.opzioni.map((opzione) => (
                                <li key={opzione.id} className={styles.opzioneItem}>
                                    {opzione.testo}
                                    {mostraStatistiche && (
                                        <span style={{ marginLeft: '10px', opacity: 0.75 }}>
                                            — {getConteggioVoti(domanda.id, opzione.id)} voti
                                        </span>
                                    )}
                                </li>
                            ))}
                        </ul>
                    </div>
                ))}
            </section>

            {utente && (
                <section className={styles.section}>
                    <h2>Commenti ({commenti.length})</h2>
                    {commenti.length > 0 ? (
                        commenti.map((commento) => (
                            <div key={commento.id} className={styles.commentoCard}>
                                <p className={styles.commentoMeta}>
                                    <span><strong>{commento.utente.nome} {commento.utente.cognome}</strong></span>
                                    <span> - {new Date(commento.data).toLocaleDateString('it-IT')}</span>
                                </p>
                                <p className={styles.commentoTesto}>{commento.testo}</p>
                            </div>
                        ))
                    ) : (
                        <p className={styles.noCommenti}>Ancora nessun commento. Sii il primo a commentare!</p>
                    )}
                </section>
            )}

            <div className={styles.actionBox}>
                {utente ? (
                    haGiaVotato && !scaduto ? (
                        votoModificabile ? (
                            <div>
                                <p className={styles.actionText}>Hai già votato questo sondaggio. Puoi modificarlo o eliminarlo se desideri.</p>
                                <div className={styles.buttonBox}>
                                    <Link
                                        to={`/sondaggio/${cod}/vota`}
                                        state={{ sondaggioGiaCaricato: sondaggio }}
                                        className={styles.btnPrimary}>
                                        Modifica Voto
                                    </Link>
                                    <button type="button" onClick={eliminaVoto} className={styles.btnDelete}>
                                        Elimina Voto
                                    </button>
                                </div>
                            </div>
                        ) : (
                            <p className={styles.actionText}>
                                Hai già partecipato a questo sondaggio in modalità anonima. Il voto anonimo non può essere modificato né eliminato.
                            </p>
                        )
                    ) : (
                        scaduto ? (
                            <p className={styles.actionText}>Il sondaggio è scaduto</p>
                        ) : (
                            <div>
                                <p className={styles.actionText}>Ciao <strong>{utente.nome}</strong>, puoi partecipare a questo sondaggio!</p>
                                <Link
                                    to={`/sondaggio/${cod}/vota`}
                                    state={{ sondaggioGiaCaricato: sondaggio }}
                                    className={styles.btnPrimary}>
                                    Vai al form di Votazione
                                </Link>
                            </div>
                        )
                    )
                ) : (
                    <div>
                        <p className={styles.actionText}>Devi essere registrato per poter votare e leggere i commenti.</p>
                        <a href={`${BACKEND_URL}/login?returnUrl=${window.location.pathname}`} className={styles.btnSecondary}>
                            Accedi per Votare, modificare o eliminare il tuo voto
                        </a>
                    </div>
                )}
            </div>
        </div>
    );
}