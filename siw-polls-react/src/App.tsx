import { useState, useEffect } from 'react';
import Navbar from './components/nav/Nav.tsx';
import { AuthService } from './service/AuthService.ts';

function App() {
  const [utenteLoggato, setUtente] = useState(false);
  const [caricamento, setCaricamento] = useState(true); 

  useEffect(() => {
    const controllaSessione = async () => {
      try {
        // 2. Chiama il metodo dall'oggetto AuthService
        const log = await AuthService.getLog();
        setUtente(log);
      } catch (error) {
        console.error("Errore recupero dati:", error);
      } finally {
        setCaricamento(false);
      }
    };

    controllaSessione();
  }, []); 

  // 3. CREA LA FUNZIONE DI LOGOUT
  const handleLogout = async () => {
    try {
      await AuthService.logout(); // Fa la chiamata POST al backend
      setUtente(false);           // Aggiorna la grafica istantaneamente
    } catch (error) {
      console.error("Errore durante il logout", error);
    }
  };

  if (caricamento) {
    return (
      <div style={{ textAlign: 'center', marginTop: '50px' }}>
        <h2>Caricamento in corso...</h2>
      </div>
    );
  }

  return (
    <div>
      {/* 4. Passa la funzione alla Navbar tramite la prop onLogout */}
      <Navbar isLoggedIn={utenteLoggato} onLogout={handleLogout} />
    </div>
  );
}

export default App;