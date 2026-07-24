import styles from "./Footer.module.css"
import logoAziendale from '../../assets/admin.png';
import { BACKEND_URL } from "../config";
import { useAuth } from "../AuthContext";
export default function Footer(){
    const utente =useAuth();
    return(
        <div className={styles.footerContainer}>
            {utente ? (
                    <a href={`${BACKEND_URL}/admin`}>
                    <img src={logoAziendale} alt="Logo Aziendale" className={styles.adminImmage} />
                    </a>
                ):(
                    <img src={logoAziendale} alt="Logo Aziendale" className={styles.adminImmage} />
                )
            } 
        </div>
    )
}