import { useState, useEffect } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { AuthContext } from './components/AuthContext';
import type { Utente } from './types';
import Navbar from './components/nav/Nav.tsx';
import Home from './pages/Home.tsx';
import Sondaggio from './pages/Sondaggio.tsx';
import { AuthService } from './service/AuthService';
import NotFound from './pages/NotFound.tsx';
import ServerError from './pages/ServerError.tsx';
import SondaggioForm from './pages/SondaggioForm.tsx';
import CommentoForm from './pages/CommentoForm.tsx';
import HomeUtente from './pages/HomeUtente.tsx';
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
        setUtente(null);
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
    <AuthContext.Provider value={{ utente, setUtente }}>
      <BrowserRouter>
        <Navbar/>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/sondaggio/:id" element={<Sondaggio />} />
          <Route path="/sondaggio/:id/vota" element={<SondaggioForm />} />
          <Route path="/sondaggio/:sondaggioId/commentoForm" element={<CommentoForm />} />
          <Route path="/utenteHome" element={<HomeUtente />} />




          <Route path="/500" element={<ServerError />} />
          {/* Rotta 404 Catch-All: DEVE essere l'ultima Route */}
          <Route path="*" element={<NotFound />} />
        </Routes>
      </BrowserRouter>
    </AuthContext.Provider>
  );
}

export default App;
