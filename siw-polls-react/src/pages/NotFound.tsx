import { Link } from 'react-router-dom';

export default function NotFound() {
    return (
        <div style={{
            backgroundColor: '#001f3f', // Sfondo blu scuro
            color: '#ffffff',
            height: '100vh',
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'center',
            alignItems: 'center',
            textAlign: 'center',
            padding: '20px'
        }}>
            <h1 style={{ fontSize: '4rem', margin: '0' }}>404</h1>
            <h2>Pagina Non Trovata</h2>
            <p style={{ marginBottom: '30px' }}>
                La risorsa che stai cercando non esiste o è stata spostata.
            </p>
            <Link 
                to="/" 
                style={{
                    backgroundColor: '#ffffff',
                    color: '#001f3f',
                    padding: '10px 20px',
                    textDecoration: 'none',
                    borderRadius: '5px',
                    fontWeight: 'bold'
                }}
            >
                Torna alla Home
            </Link>
        </div>
    );
}