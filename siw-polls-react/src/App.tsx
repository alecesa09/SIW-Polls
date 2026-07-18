import { useState, useEffect } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';

import Navbar from './components/nav/Nav.tsx';
import Home from './pages/Home.tsx';
import { AuthService } from './service/AuthService';

function App() {
  const [utenteLoggato, setUtente] = useState(false);
  const [caricamento, setCaricamento] = useState(true); 

  const handleLogout = async () => {
    try {
      await AuthService.logout();
      setUtente(false); // La Navbar si aggiorna istantaneamente
    } catch (error) {
      console.error("Errore durante il logout", error);
    }
  };

  useEffect(() => {
    const controllaSessione = async () => {
      try {
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
  
  if (caricamento) {
    return (
      <div style={{ textAlign: 'center', marginTop: '50px' }}>
        <h2>Caricamento in corso...</h2>
      </div>
    );
  }

  return (
    <BrowserRouter>
      <Navbar isLoggedIn={utenteLoggato} onLogout={handleLogout} />
      <Routes>
        <Route path="/" element={<Home />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
