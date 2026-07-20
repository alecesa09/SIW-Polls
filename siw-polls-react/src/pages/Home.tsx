import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import apiClient from '../service/api'; // [Speculazione] Assumo che l'istanza Axios si trovi qui
import styles from './Home.module.css';
import { BACKEND_URL } from '../components/config';
import type { SondaggioDTO } from '../types';
import { useAuth } from '../components/AuthContext';

export default function Home() {
  const [sondaggi, setSondaggi] = useState<SondaggioDTO[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const { utente } = useAuth();//per il momnto non lo uso decidi se levarlo una volta finito

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

  if (loading) {
    return <div className={styles.loadingMessage}>Caricamento sondaggi in corso...</div>;
  }

  if (error) {
    return <div className={styles.errorMessage}>{error}</div>;
  }

  return (
    <main className={styles.homeContainer}>
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
              {/* Gestione dell'immagine con un placeholder di fallback */}
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
    </main>
  );
}