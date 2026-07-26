siw-Polls

Progetto SIW di gestione sondaggi.

Il progetto si basa sulla gestione di sondaggi creati dagli utenti. Ogni sondaggio può essere votato solo dagli utenti registrati, ma può essere visualizzato da chiunque.
Gli utenti non registrati non possono vedere i commenti né le statistiche.

Ogni sondaggio ha un codice di accesso, necessario quando il creatore lo ha impostato come privato. Ogni utente può votare in forma anonima o pubblica:
- **Voto pubblico**: l'entità Utente è collegata alla votazione espressa, e il voto può essere modificato o eliminato in seguito.
- **Voto anonimo**: viene registrata solo la partecipazione dell'utente al sondaggio, senza collegare l'identità al voto specifico; di conseguenza, il voto anonimo non può più essere modificato né eliminato.

quasi tutte le viste sono state fattte in react tranne i login la registrazione  e l'intefaccia admin;

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

L'analisi è stata effettuata sul caso d'uso "visualizzazione di un sondaggio": dato il codice di accesso di un sondaggio, è necessario caricare N domande (N = 30) e, per ciascuna di esse, le opzioni corrispondenti.

Il test è stato eseguito contro un database remoto su Neon, per rendere più evidente l'impatto della latenza di rete su ogni singola query — impatto che sarebbe stato trascurabile con un database in locale.

In tutti e tre gli scenari viene eseguita inizialmente una query per caricare il sondaggio. Da qui, le strategie differiscono:

- LAZY / EAGER: una query per caricare tutte le domande, seguita da una query separata per le opzioni di **ciascuna** domanda (problema N+1).
- JOIN FETCH: un'unica query aggiuntiva che carica contemporaneamente domande e opzioni tramite JOIN.

Risultati
| strategia  | tempo    | numero query |
| LAZY       | 1.5049 s |      32      |
| EAGER      | 1.4142 s |      32      |
| JOIN FETCH | 0.1888 s |       2      |

Il numero di query cresce linearmente con il numero di domande sia per la strategia LAZY che per quella EAGER: entrambe generano **32 query** (1 per il sondaggio, 1 per le domande, 30 per le opzioni — una per ciascuna domanda).
Questo evidenzia un aspetto spesso frainteso di Hibernate: impostare una relazione come `EAGER` **non elimina il problema N+1**, ma cambia solo **il momento** in cui le query vengono eseguite (immediatamente al caricamento del sondaggio, invece che al primo accesso alla collezione).

La strategia con `JOIN FETCH`, invece, riduce il numero di query a 2 indipendentemente dal numero di domande, portando il tempo di esecuzione da 1.5s a 0.19s con un miglioramento importante delle prestazioni. 

Questo esperimento dimostra concretamente come la scelta della strategia di fetch sia determinante per le prestazioni dell'applicazione.
