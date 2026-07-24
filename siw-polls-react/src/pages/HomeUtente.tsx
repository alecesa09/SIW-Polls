import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

import { useAuth } from "../components/AuthContext";
import { BACKEND_URL } from "../components/config";
import {ricercaSondaggiUtente} from "../service/SondaggioService";
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
      }
    };

    caricaDati();
  }, [utente]);

  /*
   * Calcolo del numero totale di pagine.
   * Math.ceil arrotonda verso l'alto.
   *
   * Esempio:
   * 11 elementi / 5 = 2,2
   * Math.ceil(2,2) = 3 pagine
   */
  const totalePagineCreati = Math.ceil(
    sondaggiCreati.length / ELEMENTI_PER_PAGINA
  );

  const totalePagineVotati = Math.ceil(
    sondaggiVotati.length / ELEMENTI_PER_PAGINA
  );

  /*
   * Calcolo degli indici necessari per estrarre
   * solamente gli elementi della pagina corrente.
   */
  const indiceInizioCreati =
    (paginaCreati - 1) * ELEMENTI_PER_PAGINA;

  const indiceFineCreati =
    indiceInizioCreati + ELEMENTI_PER_PAGINA;

  const indiceInizioVotati =
    (paginaVotati - 1) * ELEMENTI_PER_PAGINA;

  const indiceFineVotati =
    indiceInizioVotati + ELEMENTI_PER_PAGINA;

  /*
   * slice non modifica l'array originale.
   * Restituisce solamente gli elementi della pagina corrente.
   */
  const sondaggiCreatiPagina = sondaggiCreati.slice(
    indiceInizioCreati,
    indiceFineCreati
  );

  const sondaggiVotatiPagina = sondaggiVotati.slice(
    indiceInizioVotati,
    indiceFineVotati
  );

  return (
    <main className={styles.homeContainer}>
      <section className={styles.heroSection}>
        <h1>La tua area personale</h1>

        <p>
          Gestisci i sondaggi che hai creato e le votazioni ancora
          modificabili.
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
                {sondaggiCreatiPagina.map((sondaggio) => (
                  <li
                    key={sondaggio.codiceAccesso}
                    className={styles.pollItem}
                  >
                    <Link
                      to={`/sondaggio/${sondaggio.codiceAccesso}`}
                      className={styles.pollLink}
                    >
                      <p className={styles.cardTitle}>
                          <span>{sondaggio.titolo}</span>
                          <span className={styles.codiceBadge}>codice: {sondaggio.codiceAccesso}</span>
                      </p>
                    </Link>
                  </li>
                ))}
              </ul>

              {totalePagineCreati > 1 && (
                <div
                  className={styles.pagination}
                  aria-label="Paginazione sondaggi creati"
                >
                  <button
                    type="button"
                    className={styles.paginationButton}
                    onClick={() =>
                      setPaginaCreati((pagina) => pagina - 1)
                    }
                    disabled={paginaCreati === 1}
                  >
                    Precedente
                  </button>

                  <span className={styles.paginationInfo}>
                    Pagina {paginaCreati} di {totalePagineCreati}
                  </span>

                  <button
                    type="button"
                    className={styles.paginationButton}
                    onClick={() =>
                      setPaginaCreati((pagina) => pagina + 1)
                    }
                    disabled={
                      paginaCreati === totalePagineCreati
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
                {sondaggiVotatiPagina.map((sondaggio) => (
                  <li
                    key={sondaggio.codiceAccesso}
                    className={styles.pollItem}
                  >
                    <Link
                      to={`/sondaggio/${sondaggio.codiceAccesso}`}
                      className={styles.pollLink}
                    >
                      Modifica la tua votazione per:{" "}
                      {sondaggio.titolo}
                    </Link>
                  </li>
                ))}
              </ul>

              {totalePagineVotati > 1 && (
                <div
                  className={styles.pagination}
                  aria-label="Paginazione voti modificabili"
                >
                  <button
                    type="button"
                    className={styles.paginationButton}
                    onClick={() =>
                      setPaginaVotati((pagina) => pagina - 1)
                    }
                    disabled={paginaVotati === 1}
                  >
                    Precedente
                  </button>

                  <span className={styles.paginationInfo}>
                    Pagina {paginaVotati} di {totalePagineVotati}
                  </span>

                  <button
                    type="button"
                    className={styles.paginationButton}
                    onClick={() =>
                      setPaginaVotati((pagina) => pagina + 1)
                    }
                    disabled={
                      paginaVotati === totalePagineVotati
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