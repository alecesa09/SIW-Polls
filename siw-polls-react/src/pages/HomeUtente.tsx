import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

import { useAuth } from "../components/AuthContext";
import { BACKEND_URL } from "../components/config";
import { ricercaSondaggiUtente } from "../service/SondaggioService";
import { ricercaSondaggiVotatiUtente } from "../service/VotazioneService";
import type { SondaggioDTO } from "../types";
import styles from "./HomeUtente.module.css";

const ELEMENTI_PER_PAGINA = 5;

export default function UtenteHome() {
  const { utente } = useAuth();

  const [sondaggiCreati, setSondaggiCreati] = useState<SondaggioDTO[]>([]);
  const [sondaggiVotati, setSondaggiVotati] = useState<SondaggioDTO[]>([]);

  const [paginaCreati, setPaginaCreati] = useState(1);
  const [paginaVotati, setPaginaVotati] = useState(1);

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
        setSondaggiVotati(votati);
      } catch (error) {
        console.error(
          "Errore durante il recupero delle informazioni utente:",
          error
        );
        alert(error);
      }
    };

    caricaDati();
  }, [utente]);
  
  const numeroPagineSondaggiCreati = Math.ceil(
    sondaggiCreati.length / ELEMENTI_PER_PAGINA
  );
  const numeroPagineSondaggiVotati = Math.ceil(
    sondaggiVotati.length / ELEMENTI_PER_PAGINA
  );

  const sondaggiCreatiCorrenti = sondaggiCreati.slice(
    (paginaCreati - 1) * ELEMENTI_PER_PAGINA,
    paginaCreati * ELEMENTI_PER_PAGINA
  );

  const sondaggiVotatiCorrenti = sondaggiVotati.slice(
    (paginaVotati - 1) * ELEMENTI_PER_PAGINA,
    paginaVotati * ELEMENTI_PER_PAGINA
  );

  return (
    <main className={styles.homeContainer}>
      <section className={styles.heroSection}>
        <h1>La tua area personale</h1>
        <p>
          Gestisci i sondaggi che hai creato e le votazioni ancora modificabili.
        </p>
      </section>

      <div className={styles.sectionsGrid}>
        {/* Sondaggi creati */}
        <section className={styles.sectionContainer}>
          <h2 className={styles.sectionTitle}>I tuoi sondaggi</h2>

          {sondaggiCreati.length === 0 ? (
            <p className={styles.emptyMessage}>
              Non hai ancora creato alcun sondaggio.
            </p>
          ) : (
            <>
              <ul className={styles.pollList}>
                {sondaggiCreatiCorrenti.map((sondaggio) => (
                  <li
                    key={sondaggio.codiceAccesso}
                    className={styles.pollItem}
                  >
                    <Link
                      to={`/sondaggio/${sondaggio.codiceAccesso}`}
                      className={styles.pollLink}
                    >
                      <div className={styles.cardTitle}>
                        <div>{sondaggio.titolo}</div>
                        <div className={styles.codiceBadge}>
                          codice: {sondaggio.codiceAccesso}
                        </div>
                      </div>
                    </Link>
                  </li>
                ))}
              </ul>

              {numeroPagineSondaggiCreati > 1 && (
                <div
                  className={styles.pagination}
                  aria-label="Paginazione sondaggi creati"
                >
                  <button
                    type="button"
                    className={styles.paginationButton}
                    onClick={() =>
                      setPaginaCreati((pagina) => Math.max(pagina - 1, 1))
                    }
                    disabled={paginaCreati === 1}
                  >
                    Precedente
                  </button>

                  <span className={styles.paginationInfo}>
                    Pagina {paginaCreati} di {numeroPagineSondaggiCreati}
                  </span>

                  <button
                    type="button"
                    className={styles.paginationButton}
                    onClick={() =>
                      setPaginaCreati((pagina) =>
                        Math.min(pagina + 1, numeroPagineSondaggiCreati)
                      )
                    }
                    disabled={
                      paginaCreati === numeroPagineSondaggiCreati
                    }
                  >
                    Successiva
                  </button>
                </div>
              )}
            </>
          )}
        </section>

        {/* Voti modificabili */}
        <section className={styles.sectionContainer}>
          <h2 className={styles.sectionTitle}>
            I tuoi voti ancora modificabili
          </h2>

          {sondaggiVotati.length === 0 ? (
            <p className={styles.emptyMessage}>
              Non hai espresso voti modificabili.
            </p>
          ) : (
            <>
              <ul className={styles.pollList}>
                {sondaggiVotatiCorrenti.map((sondaggio) => (
                  <li
                    key={sondaggio.codiceAccesso}
                    className={styles.pollItem}
                  >
                    <Link
                      to={`/sondaggio/${sondaggio.codiceAccesso}`}
                      className={styles.pollLink}
                    >
                      Modifica la tua votazione per: {sondaggio.titolo}
                    </Link>
                  </li>
                ))}
              </ul>

              {numeroPagineSondaggiVotati > 1 && (
                <div
                  className={styles.pagination}
                  aria-label="Paginazione voti modificabili"
                >
                  <button
                    type="button"
                    className={styles.paginationButton}
                    onClick={() =>
                      setPaginaVotati((pagina) => Math.max(pagina - 1, 1))
                    }
                    disabled={paginaVotati === 1}
                  >
                    Precedente
                  </button>

                  <span className={styles.paginationInfo}>
                    Pagina {paginaVotati} di {numeroPagineSondaggiVotati}
                  </span>

                  <button
                    type="button"
                    className={styles.paginationButton}
                    onClick={() =>
                      setPaginaVotati((pagina) =>
                        Math.min(pagina + 1, numeroPagineSondaggiVotati)
                      )
                    }
                    disabled={
                      paginaVotati === numeroPagineSondaggiVotati
                    }
                  >
                    Successiva
                  </button>
                </div>
              )}
            </>
          )}
        </section>
      </div>
    </main>
  );
}