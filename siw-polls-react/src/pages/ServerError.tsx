import { Link } from 'react-router-dom';

export default function ServerError() {
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
            <h1 style={{ fontSize: '4rem', margin: '0' }}>500</h1>
            <h2>Errore Interno del Server</h2>
            <p style={{ maxWidth: '500px', marginBottom: '30px' }}>
                Ops! Qualcosa è andato storto sui nostri server. Stiamo lavorando per risolvere il problema il prima possibile.
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
                Ritorna alla Home
            </Link>
        </div>
    );
}