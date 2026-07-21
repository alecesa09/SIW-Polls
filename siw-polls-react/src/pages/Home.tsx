import { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import apiClient from '../service/api';
import { ricercaPerCodiceAcesso } from '../service/SondaggioService';
import styles from './Home.module.css';
import { BACKEND_URL } from '../components/config';
import type { SondaggioDTO } from '../types';
import { useAuth } from '../components/AuthContext';

export default function Home() {
  const [sondaggi, setSondaggi] = useState<SondaggioDTO[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();
  const [codiceAccesso, setCodiceAccesso] = useState<string>('');
  const [erroreCodice, setErroreCodice] = useState<string | null>(null);
  const [ricercaInCorso, setRicercaInCorso] = useState<boolean>(false);

  useEffect(() => {
    const fetchSondaggiRecenti = async () => {
      try {
        const response = await apiClient.get('/sondaggi/recenti');
        setSondaggi(response.data);
      } catch (err) {
        console.error("Errore nel recupero dei sondaggi:", err);
        setError("Impossibile caricare i sondaggi al momento.");
      } finally {
        setLoading(false);
      }
    };

    fetchSondaggiRecenti();
  }, []);

  const handleSubmitCodice = async (e: React.FormEvent) => {
    e.preventDefault();

    const codice = codiceAccesso.trim();
    if (!codice) {
      setErroreCodice("Inserisci un codice di accesso.");
      return;
    }

    setErroreCodice(null);
    setRicercaInCorso(true);

    try {
      const sondaggio = await ricercaPerCodiceAcesso(codice);
      navigate(`/sondaggio/${sondaggio.id}`);
    } catch (err: any) {
      console.error("Errore nella ricerca per codice:", err);
      if (err.response && err.response.status === 404) {
        setErroreCodice("Nessun sondaggio trovato con questo codice.");
      } else {
        setErroreCodice("Si è verificato un errore. Riprova.");
      }
    } finally {
      setRicercaInCorso(false);
    }
  };

  if (loading) {
    return <div className={styles.loadingMessage}>Caricamento sondaggi in corso...</div>;
  }

  if (error) {
    return <div className={styles.errorMessage}>{error}</div>;
  }

  return (
    <main className={styles.homeContainer}>
      <div className={styles.pageLayout}>

        {/* Colonna principale: sondaggi recenti */}
        <div className={styles.mainColumn}>
          <section className={styles.heroSection}>
            <h1 className="title-main">Sondaggi Recenti</h1>
            <p>Partecipa alle ultime votazioni della community.</p>
          </section>

          <div className={styles.gridContainer}>
            {sondaggi.length === 0 ? (
              <p>Nessun sondaggio disponibile al momento.</p>
            ) : (
              sondaggi.map((sondaggio) => (
                <article key={sondaggio.id} className={`card-custom ${styles.card}`}>
                  <div className={styles.imageContainer}>
                    {sondaggio.immagine ? (
                      <img
                        src={`${BACKEND_URL}/immagini/${sondaggio.immagine}`}
                        alt={sondaggio.titolo}
                        className={styles.cardImage}
                      />
                    ) : (
                      <div className={styles.placeholderImage}>Nessuna Immagine</div>
                    )}
                  </div>

                  <div className={styles.cardContent}>
                    <h2 className={styles.cardTitle}>{sondaggio.titolo}</h2>
                    <p className={styles.cardMeta}>
                      <strong>Scadenza:</strong> {new Date(sondaggio.dataScadenza).toLocaleDateString('it-IT')}
                    </p>

                    <Link to={`/sondaggio/${sondaggio.id}`} className={styles.btnPartecipa}>
                      Partecipa
                    </Link>
                  </div>
                </article>
              ))
            )}
          </div>
        </div>

        {/* Colonna laterale destra: accesso tramite codice */}
        <aside className={styles.sidebarColumn}>
          <div className={`card-custom ${styles.codiceAccessoBox}`}>
            <h2 className={styles.codiceAccessoTitle}>Hai un codice di accesso?</h2>
            <p className={styles.codiceAccessoText}>
              Inserisci il codice per accedere direttamente a un sondaggio privato.
            </p>

            <form onSubmit={handleSubmitCodice} className={styles.codiceAccessoForm}>
              <input
                type="text"
                placeholder="Es. TECH2026"
                value={codiceAccesso}
                onChange={(e) => setCodiceAccesso(e.target.value)}
                disabled={ricercaInCorso}
                className={styles.codiceAccessoInput}
              />
              <button
                type="submit"
                className={styles.btnPartecipa}
                disabled={ricercaInCorso}
              >
                {ricercaInCorso ? "Ricerca in corso..." : "Vai al Sondaggio"}
              </button>
            </form>

            {erroreCodice && (
              <p className={styles.codiceAccessoErrore}>{erroreCodice}</p>
            )}
          </div>
        </aside>

      </div>
    </main>
  );
}