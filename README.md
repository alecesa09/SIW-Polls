siw-Polls

Progetto SIW di gestione sondaggi.

Il progetto si basa sulla gestione di sondaggi creati dagli utenti. Ogni sondaggio può essere votato solo dagli utenti registrati, ma può essere visualizzato da chiunque.
Gli utenti non registrati non possono vedere i commenti né le statistiche.

Ogni sondaggio ha un codice di accesso, necessario quando il creatore lo ha impostato come privato. Ogni utente può votare in forma anonima o pubblica:
- **Voto pubblico**: l'entità Utente è collegata alla votazione espressa, e il voto può essere modificato o eliminato in seguito.
- **Voto anonimo**: viene registrata solo la partecipazione dell'utente al sondaggio, senza collegare l'identità al voto specifico; di conseguenza, il voto anonimo non può più essere modificato né eliminato.

quasi tutte le viste sono state fatte in react tranne i login la registrazione  e l'intefaccia admin;

L'intero sistema è live ed è stato sottoposto a deployment strutturando un'architettura distribuita. Il sito è accessibile e testabile al seguente indirizzo: https://siw-polls.web.app

Nello specifico, i vari livelli dell'applicazione sono stati separati sfruttando piattaforme cloud differenti:
Presentation Tier (Frontend): L'interfaccia in React è stata buildata e ospitata su Firebase Hosting.
Logic Tier (Backend): L'applicazione Spring Boot è deployata come servizio web sulla piattaforma Railway.
Data Tier (Database): I dati sono gestiti tramite un database PostgreSQL ospitato in cloud su Neon.

## Casi d'uso
- Creazione sondaggio (utente)
- Eliminazione sondaggio (admin)
- Creazione / modifica / eliminazione votazione (utente)
- Creazione commento (utente)
- Ricerca sondaggio per titolo
- Ricerca sondaggio per codice di accesso
- Visualizzazione home
- Visualizzazione home utente (utente)
- Visualizzazione sondaggio
- Eliminazione utente (admin)
- Registrazione e accesso

Analisi sperimentale sulle prestazioni

L'analisi è stata effettuata sul caso d'uso "visualizzazione di un sondaggio": dato il codice di accesso di un sondaggio, è necessario caricare N domande (N = 30 creando un caso fittizio per fare vedere bene le differenze di prestazione)
e, per ciascuna di esse, le opzioni corrispondenti.

Il test è stato eseguito contro un database remoto su Neon, per rendere più evidente l'impatto della latenza di rete su ogni singola query — impatto che sarebbe stato trascurabile con un database in locale.

In tutti e tre gli scenari viene eseguita inizialmente una query per caricare il sondaggio poi vengono richieste con un .getDomande() le domanda e poi in un for si richiedono le opzioni con un getOpzioni() 

Risultati
| strategia  | tempo    | numero query |
| LAZY       | 1.6631 s |      32      |
| EAGER      | 0.2466 s |       2      |
| JOIN FETCH | 0.2469 s |       1      |

Il numero di query cresce linearmente con il numero di domande sia per la strategia LAZY : genera 32 query (1 per il sondaggio, 1 per le domande, 30 per le opzioni — una per ciascuna domanda).

La strategia con `EAGER`, invece, riduce il numero di query a 2 indipendentemente dal numero di domande, portando il tempo di esecuzione da 1.6s a 0.24s con un miglioramento importante delle prestazioni.
(Va notato cheb il comportamento di eager è non sempre garantito con il fetch hibernate per evitare join esplosivi potrebbe decidere di fare una query per ogni domanda ripresentando cosi il problema N+1 )

La strategia con `JOIN_FETCH`, invece, riduce il numero di query a 1 indipendentemente dal numero di domande, portando il tempo di esecuzione da 1.6s a 0.24s con un miglioramento importante delle prestazioni. 

Questo esperimento dimostra concretamente come la scelta della strategia di fetch sia determinante per le prestazioni dell'applicazione.
