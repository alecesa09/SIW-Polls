import { createContext, useContext } from 'react';
import type { Utente } from '../types';

// 1. Modifica l'interfaccia per usare l'oggetto Utente o null, invece di boolean
interface AuthContextType {
  utente: Utente | null;
  setUtente: (utente: Utente | null) => void;
}

// 2. Inizializza il context con valori che rispettino l'interfaccia definita sopra
export const AuthContext = createContext<AuthContextType>({
  utente: null, // Il valore di partenza è null (utente non autenticato)
  setUtente: () => {},
});

export const useAuth = () => useContext(AuthContext);
