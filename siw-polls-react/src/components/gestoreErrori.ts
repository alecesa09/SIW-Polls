import type { NavigateFunction } from 'react-router-dom';

export default function gestisciErrore(error: any, navigate: NavigateFunction) {
    const messaggio = error?.response?.data || "Si è verificato un errore. Riprova.";
    const status = error?.response?.status;

    console.error(messaggio, error);

    if (status === 404) {
        navigate('/404', { state: { messaggio } });
    } else if (status >= 500) {
        navigate('/500', { state: { messaggio } });
    } else {
        alert(messaggio);
    }
}