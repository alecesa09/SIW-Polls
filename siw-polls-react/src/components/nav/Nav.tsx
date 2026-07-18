import { AppBar, Toolbar, Button } from '@mui/material';
import { Link } from 'react-router-dom';
import logoAziendale from '../../assets/Logo.jpg';
import { BACKEND_URL } from '../config'; 
import styles from './Nav.module.css';

// [Speculazione] Ipotizzo che tu debba passare una funzione dal componente App 
// per aggiornare lo stato utenteLoggato a false
interface NavbarProps {
  isLoggedIn: boolean;
  onLogout: () => void; // Nuova prop
}

export default function Navbar({ isLoggedIn, onLogout }: NavbarProps) {
  return (
    <AppBar position="static" color="primary">
      <Toolbar>
        <Link to="/" className={styles.logoContainer}>
          <img src={logoAziendale} alt="Logo" className={styles.logoImage} />
        </Link>

        <div className={styles.spacer}></div>

        {isLoggedIn ? (
          <>
            <Button color="inherit" sx={{ fontWeight: 'bold' }}>
              Crea Sondaggio
            </Button>
            {/* Sostituito href con onClick */}
            <Button color="inherit" onClick={onLogout}>
              Logout
            </Button>
          </>
        ) : (
          <>
            <Button color="inherit" href={`${BACKEND_URL}/login`}>
              Accedi
            </Button>
            <Button color="inherit" variant="outlined" sx={{ marginLeft: 1, borderColor: 'white' }} href={`${BACKEND_URL}/register`}>
              Registrati
            </Button>
          </>
        )}
      </Toolbar>
    </AppBar>
  );
}