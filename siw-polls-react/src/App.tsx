import { useState, useEffect } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { AuthContext } from './components/AuthContext';
import type { Utente } from './types';
import Navbar from './components/nav/Nav.tsx';
import Home from './pages/Home.tsx';
import Sondaggio from './pages/Sondaggio.tsx';
import { AuthService } from './service/AuthService';

function App() {

  const [utente, setUtente] = useState<Utente | null>(null);
  const [caricamento, setCaricamento] = useState(true); 

  useEffect(() => {
    const controllaSessione = async () => {
      try {
        const userData = await AuthService.getLog();
        setUtente(userData);
      } catch (error) {
        console.error("Errore recupero dati:", error);
        setUtente(null); // In caso di errore, ci assicuriamo che lo stato sia null
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

  // 3. Calcoliamo il booleano per i componenti che richiedono solo true/false
  const isLoggedIn = utente !== null;

  return (
    // 4. Avvolgi tutto nel Provider, passando lo stato e la funzione per aggiornarlo
    <AuthContext.Provider value={{ utente, setUtente }}>
      <BrowserRouter>
        {/* Passiamo isLoggedIn calcolato alla Navbar */}
        <Navbar isLoggedIn={isLoggedIn} />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/sondaggio/:id" element={<Sondaggio />} />
        </Routes>
      </BrowserRouter>
    </AuthContext.Provider>
  );
}

export default App;
