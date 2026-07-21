import { Link, useNavigate } from 'react-router-dom';
import logoAziendale from '../../assets/Logo.jpg';
import { BACKEND_URL } from '../config';
import styles from './Nav.module.css';
import { useAuth } from '../AuthContext';
import { useEffect, useRef, useState, type FormEvent } from 'react';
import { ricerca } from '../../service/SondaggioService';
import type { SondaggioDTO } from '../../types';
import { Logout } from "../../service/AuthService"; 
export default function Navbar() {
  const { utente, setUtente } = useAuth(); 
  const navigate = useNavigate();

  const [payload, setPayload] = useState('');
  const [risultati, setRisultati] = useState<SondaggioDTO[]>([]);
  const [mostraRisultati, setMostraRisultati] = useState<boolean>(false);
  const searchBoxRef = useRef<HTMLDivElement>(null);

  // Ricerca con debounce: aspetta 300ms di inattività prima di chiamare il backend
  useEffect(() => {
    const testo = payload.trim();

    if (testo.length === 0) {
      setRisultati([]);
      setMostraRisultati(false);
      return;
    }

    const timeoutId = setTimeout(async () => {
      try {
        const data = await ricerca(testo);
        setRisultati(data);
        setMostraRisultati(true);
      } catch (error) {
        console.error("Errore durante la ricerca", error);
        setRisultati([]);
      }
    }, 300);

    // Cancella il timer precedente se l'utente digita di nuovo prima dei 300ms
    return () => clearTimeout(timeoutId);
  }, [payload]);

  // Chiude il dropdown se si clicca fuori dal box di ricerca
  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (searchBoxRef.current && !searchBoxRef.current.contains(event.target as Node)) {
        setMostraRisultati(false);
      }
    };
    document.addEventListener('mousedown', handleClickOutside);
    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, []);

  const handleRisultatoClick = (id: number) => {
    setMostraRisultati(false);
    setPayload('');
    navigate(`/sondaggio/${id}`);
  };

  const handleLogout = async (e: FormEvent<HTMLFormElement>) => {
          e.preventDefault(); // BLOCCA il ricaricamento della pagina!
          
          try {
              await Logout.Logout(); 
              setUtente(null);
              navigate("/")
              alert("Logout effettuato con successo!");
          } catch (error) {
              console.error("Errore durante il logout:", error);
          }
      };

  return (
    <header className={styles.header}>
      <nav className={styles.navbar} aria-label="Navigazione principale">

        <Link to="/" className={styles.logoContainer} aria-label="Vai alla Home">
          <img src={logoAziendale} alt="Logo Aziendale" className={styles.logoImage} />
        </Link>

        <div className={styles.searchContainer} ref={searchBoxRef}>
          <input
            type="text"
            placeholder="Cerca un sondaggio..."
            value={payload}
            onChange={(e) => setPayload(e.target.value)}
            onFocus={() => { if (risultati.length > 0) setMostraRisultati(true); }}
          />

          {mostraRisultati && (
            <ul className={styles.searchResults}>
              {risultati.length > 0 ? (
                risultati.map((sondaggio) => (
                  <li
                    key={sondaggio.id}
                    className={styles.searchResultItem}
                    onClick={() => handleRisultatoClick(sondaggio.id)}
                  >
                    {sondaggio.immagine && (
                      <img
                        src={`${BACKEND_URL}/immagini/${sondaggio.immagine}`}
                        alt={sondaggio.titolo}
                        className={styles.searchResultImage}
                      />
                    )}
                    <span>{sondaggio.titolo}</span>
                  </li>
                ))
              ) : (
                <li className={styles.searchNoResults}>Nessun risultato trovato</li>
              )}
            </ul>
          )}
        </div>

        <ul className={styles.navMenu}>
          {utente ? (
            <>
              <li className={styles.navItem}>
                <form onSubmit={handleLogout} className={styles.codiceAccessoForm}>
                  <button type="submit" className={`${styles.btn} ${styles.btnSecondary}`}>
                    Logout
                  </button>
                </form>
              </li>
              <li className={styles.navItem}>
                <Link to={`/utenteHome`} className={`${styles.btn} ${styles.btnOutline}`}>
                  ciao {utente.nome}
                </Link>
              </li>
            </>
          ) : (
            <>
              <li className={styles.navItem}>
                <a href={`${BACKEND_URL}/login`} className={styles.navLink}>
                  Accedi
                </a>
              </li>
              <li className={styles.navItem}>
                <a href={`${BACKEND_URL}/register`} className={`${styles.btn} ${styles.btnPrimary}`}>
                  Registrati
                </a>
              </li>
            </>
          )}
        </ul>

      </nav>
    </header>
  );
}