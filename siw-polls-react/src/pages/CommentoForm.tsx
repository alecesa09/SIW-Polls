import { useState } from "react";
import { postCommento } from "../service/CommentoService";
import { useParams,useNavigate, Link } from "react-router-dom";

export default function SezioneCommento() {
    const navigate = useNavigate();
    const [testo, setTesto] = useState<string>("");
    const [isSubmitting, setIsSubmitting] = useState<boolean>(false);
    const {sondaggioId } = useParams<{ sondaggioId: string }>();
    const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        setTesto(e.target.value);
    };

    const inviaCommento = async () => {
        setIsSubmitting(true);
        try {
            if(!sondaggioId){
                throw(new Error("sondaggio non definito"))
            }
            else{
                await postCommento(sondaggioId,testo);
                alert("Commento registrato con successo!");
                navigate(`/sondaggio/${sondaggioId}`);
            }
            
        } catch (error) {
            console.error("Errore durante l'invio del commento:", error);
            alert("Si è verificato un errore durante l'invio del commento. Riprova.");
        } finally {
            setIsSubmitting(false);
        }
        setIsSubmitting(false);
    };

    return (
        <div className="comment-form-container">
            <h3>Inserisci un commento</h3>
            
            <textarea 
                value={testo} 
                onChange={handleChange}
                disabled={isSubmitting}
                placeholder="Scrivi qui il tuo commento..."
                rows={4}
                style={{ width: "100%", padding: "10px" }}
            />
            
            <button 
                onClick={inviaCommento}
                // Disabilita il bottone se sta caricando o se il testo è vuoto
                disabled={isSubmitting || testo.trim() === ""}
                style={{ marginTop: "10px" }}
            >
                {isSubmitting ? "Invio in corso..." : "Invia Commento"}
            </button>
            <Link to={`/sondaggio/${sondaggioId}`}>
                Non voglio lasciare un commento
            </Link>
        </div>
    );
}
