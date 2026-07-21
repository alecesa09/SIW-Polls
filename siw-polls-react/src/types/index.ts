export interface Utente {
  id: number;
  cognome: string;
  email: string;
  nome: string;
}

export interface Commento {
  id: number;
  data: string;
  testo: string;
  utente: Utente;
}

export interface Opzione {
  id: number;
  testo: string;
}

export interface Domanda {
  id: number;
  opzioni: Opzione[];
  testo: string;
}

export interface Sondaggio {
  id: number;
  titolo: string;
  descrizione: string;
  immagine: string;
  dataScadenza: string;
  domande: Domanda[];
  commenti ?: Commento[];
}

export interface SondaggioDTO{
    id: number,
    titolo: string,
    immagine: string,
    dataScadenza: string;
}

export interface AuthContextType {
  utente: Utente | null; 
  setUtente: (utente: Utente | null) => void;
}
export interface Voto{
  domandaId: number;
  opzioneId: number;
}

export interface Votazione {
  sondaggioId: number;
  visibilita: string;
  voti: Voto[];
}

export interface Statistica {
  domandaId: number;
  opzioneId: number;
  numeroVoti: number;
}

export interface testo{
  contenuto: string;
}
