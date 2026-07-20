import { Link } from 'react-router-dom';
import logoAziendale from '../../assets/Logo.jpg';
import { BACKEND_URL } from '../config'; // Assicurati che il path sia corretto
import styles from './Nav.module.css';
import { useAuth } from '../AuthContext';

export default function Navbar() {
  const { utente } = useAuth();
  return (
    <header className={styles.header}>
      <nav className={styles.navbar} aria-label="Navigazione principale">
        
        <Link to="/" className={styles.logoContainer} aria-label="Vai alla Home">
          <img src={logoAziendale} alt="Logo Aziendale" className={styles.logoImage} />
        </Link>
        <ul className={styles.navMenu}>
          {utente ? (
            <>
              <li className={styles.navItem}>
                <button className={`${styles.btn} ${styles.btnSecondary}`}>
                  Crea Sondaggio
                </button>
              </li>
              <li className={styles.navItem}>
                <a href={`${BACKEND_URL}/utente`} className={`${styles.btn} ${styles.btnOutline}`}>
                  ciao {utente.nome}
                </a>
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