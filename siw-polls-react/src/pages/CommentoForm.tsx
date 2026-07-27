import { useState } from "react";
import { postCommento } from "../service/CommentoService";
import { useParams, useNavigate, Link } from "react-router-dom";
import styles from "./CommentoForm.module.css";
import { useAuth } from "../components/AuthContext";
import { BACKEND_URL } from "../components/config";
import gestisciErrore from "../components/gestoreErrori";

export default function SezioneCommento() {
    const navigate = useNavigate();
    const { utente } = useAuth();
    const [testo, setTesto] = useState<string>("");
    const [isSubmitting, setIsSubmitting] = useState<boolean>(false);
    const { cod } = useParams<{ cod: string }>();

    const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        setTesto(e.target.value);
    };

    const inviaCommento = async () => {
        if (!utente) {
                      window.location.href = `${BACKEND_URL}/login`;
                      return;
                    }
        setIsSubmitting(true);
        try {
            if (!cod) {
                throw new Error("sondaggio non definito");
            } else {
                await postCommento(cod, testo);
                alert("Commento registrato con successo!");
                navigate(`/sondaggio/${cod}`);
            }
        } catch (error: any) {
            gestisciErrore(error, navigate);
        } finally {
                setIsSubmitting(false);
            }
    };

    return (
        <div className={styles.commentFormContainer}>
            <h3>Inserisci un commento</h3>

            <textarea 
                className={styles.commentTextarea}
                value={testo} 
                onChange={handleChange}
                disabled={isSubmitting}
                placeholder="Scrivi qui il tuo commento..."
                rows={4}
            />

            <button 
                className={styles.commentSubmitBtn}
                onClick={inviaCommento}
                disabled={isSubmitting || testo.trim() === ""}
            >
                {isSubmitting ? "Invio in corso..." : "Invia Commento"}
            </button>
            <Link className={styles.commentSkipLink} to={`/sondaggio/${cod}`}>
                Non voglio lasciare un commento
            </Link>
        </div>
    );
}
