import { AppBar, Toolbar, Button } from '@mui/material';
import { Link } from 'react-router-dom';

import logoAziendale from '../../assets/Logo.jpg';
import { BACKEND_URL } from '../config'; 
import styles from './Nav.module.css';

interface NavbarProps {
  isLoggedIn: boolean;
  // 1. Dichiara che il componente accetta questa funzione
  onLogout: () => void; 
}

// 2. Estrai onLogout dalle props
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
            
            {/* 3. SOSTITUISCI href CON onClick */}
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