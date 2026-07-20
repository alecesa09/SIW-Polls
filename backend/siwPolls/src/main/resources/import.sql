-- 1. Inserimento Utenti [verificato]
INSERT INTO utente (id, nome, cognome, email) VALUES (1, 'Mario', 'Rossi', 'mario.rossi@example.com');
INSERT INTO utente (id, nome, cognome, email) VALUES (2, 'Luigi', 'Verdi', 'luigi.verdi@example.com');
INSERT INTO utente (id, nome, cognome, email) VALUES (3, 'Giulia', 'Bianchi', 'giulia.bianchi@example.com');
INSERT INTO utente (id, nome, cognome, email) VALUES (4, 'alessio', 'cesaroni', 'alessio.cesaroni@fake.it');

-- 2. Inserimento Credenziali (OneToOne con Utente) [verificato]
-- [Speculazione] Si assume che il backend utilizzi BCrypt per l'hashing. La stringa sottostante corrisponde alla password in chiaro: 'password'
INSERT INTO credential (id, username, psw, ruolo, utente_id) VALUES (1, 'admin', '$2a$12$cFQDGMZXEPVMNfrmLUG92.zyQyWSD.4XmqoM9d66z8GwwUguhB5ku', 'ADMIN', 1);
INSERT INTO credential (id, username, psw, ruolo, utente_id) VALUES (2, 'user', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 'DEFAULT', 2);
INSERT INTO credential (id, username, psw, ruolo, utente_id) VALUES (3, 'giulia', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 'DEFAULT', 3);
INSERT INTO credential (id, username, psw, ruolo, utente_id) VALUES (4, 'cesa', '$2a$12$/MlAeF6XHN.QL/.FVlWx2OmwJMc.nqOHoZ6cHr5SMhAfPeHaQjq82', 'ADMIN', 4);

-- 3. Inserimento Sondaggi (ManyToOne con Utente) [verificato]
-- Sondaggio 1 (Esistente)
INSERT INTO sondaggio (id, titolo, descrizione, immagine, data_creazione, data_scadenza_voto, visibilita, codice_accesso, utente_id) VALUES (1, 'Tecnologie Backend', 'Quali tecnologie preferisci per lo sviluppo web?', 'springBoot.png', '2026-07-19', '2026-12-31', 'PUBBLICO', 'TECH2026', 1);
-- Sondaggio 2
INSERT INTO sondaggio (id, titolo, descrizione, immagine, data_creazione, data_scadenza_voto, visibilita, codice_accesso, utente_id) VALUES (2, 'Framework Frontend', 'Il panorama Frontend e in continua evoluzione. Quale libreria preferisci usare nei tuoi progetti?', 'react.png', '2026-07-15', '2026-11-30', 'PUBBLICO', 'FRONT26', 1);
-- Sondaggio 3
INSERT INTO sondaggio (id, titolo, descrizione, immagine, data_creazione, data_scadenza_voto, visibilita, codice_accesso, utente_id) VALUES (3, 'Database Relazionali vs NoSQL', 'Raccolta dati sulle preferenze dei DBMS per i nuovi microservizi.', 'dbms.jpg', '2026-07-10', '2026-10-31', 'PUBBLICO', 'DB2026', 2);
-- Sondaggio 4
INSERT INTO sondaggio (id, titolo, descrizione, immagine, data_creazione, data_scadenza_voto, visibilita, codice_accesso, utente_id) VALUES (4, 'Cloud Providers', 'Dove preferisci fare il deploy delle tue applicazioni enterprise?', 'cloudProvider.png', '2026-07-18', '2026-12-15', 'PUBBLICO', 'CLOUD26', 1);
-- Sondaggio 5
INSERT INTO sondaggio (id, titolo, descrizione, immagine, data_creazione, data_scadenza_voto, visibilita, codice_accesso, utente_id) VALUES (5, 'Eventi Tech Italia', 'Sondaggio riservato per l organizzazione della trasferta aziendale.', 'TechEvent.jpg', '2026-07-19', '2026-09-01', 'PUBBLICO', 'EVITA26', 2);
-- Sondaggio 6
INSERT INTO sondaggio (id, titolo, descrizione, immagine, data_creazione, data_scadenza_voto, visibilita, codice_accesso, utente_id) VALUES (6, 'Editor di Testo e IDE', 'Qual e lo strumento definitivo per scrivere codice in comodita?', 'IDE.png', '2026-07-01', '2026-08-30', 'PUBBLICO', 'IDE2026', 1);

-- 4. Inserimento Domande (ManyToOne con Sondaggio) [verificato]
INSERT INTO domanda (id, testo, sondaggio_id) VALUES (1, 'Quale framework utilizzi maggiormente in Java?', 1);
INSERT INTO domanda (id, testo, sondaggio_id) VALUES (2, 'Quale template engine preferisci per le viste server-side?', 1);
INSERT INTO domanda (id, testo, sondaggio_id) VALUES (3, 'Quale libreria/framework UI usi di piu?', 2);
INSERT INTO domanda (id, testo, sondaggio_id) VALUES (4, 'Scegli la tua tipologia di DBMS preferita', 3);
INSERT INTO domanda (id, testo, sondaggio_id) VALUES (5, 'Provider Cloud Principale', 4);
INSERT INTO domanda (id, testo, sondaggio_id) VALUES (6, 'A quale evento parteciperai quest anno?', 5);
INSERT INTO domanda (id, testo, sondaggio_id) VALUES (7, 'Qual e il tuo IDE preferito per lo sviluppo backend?', 6);

-- 5. Inserimento Opzioni (ManyToOne con Domanda) [verificato]
-- Opzioni Sondaggio 1
INSERT INTO opzione (id, testo, domanda_id) VALUES (1, 'Spring Boot', 1);
INSERT INTO opzione (id, testo, domanda_id) VALUES (2, 'Quarkus', 1);
INSERT INTO opzione (id, testo, domanda_id) VALUES (3, 'Thymeleaf', 2);
INSERT INTO opzione (id, testo, domanda_id) VALUES (4, 'JSP', 2);
-- Opzioni Sondaggio 2
INSERT INTO opzione (id, testo, domanda_id) VALUES (5, 'React', 3);
INSERT INTO opzione (id, testo, domanda_id) VALUES (6, 'Angular', 3);
INSERT INTO opzione (id, testo, domanda_id) VALUES (7, 'Vue.js', 3);
-- Opzioni Sondaggio 3
INSERT INTO opzione (id, testo, domanda_id) VALUES (8, 'Relazionale (es. PostgreSQL, MySQL)', 4);
INSERT INTO opzione (id, testo, domanda_id) VALUES (9, 'NoSQL (es. MongoDB)', 4);
-- Opzioni Sondaggio 4
INSERT INTO opzione (id, testo, domanda_id) VALUES (10, 'Amazon Web Services (AWS)', 5);
INSERT INTO opzione (id, testo, domanda_id) VALUES (11, 'Google Cloud Platform', 5);
INSERT INTO opzione (id, testo, domanda_id) VALUES (12, 'Microsoft Azure', 5);
-- Opzioni Sondaggio 5
INSERT INTO opzione (id, testo, domanda_id) VALUES (13, 'Codemotion', 6);
INSERT INTO opzione (id, testo, domanda_id) VALUES (14, 'Devoxx', 6);
INSERT INTO opzione (id, testo, domanda_id) VALUES (15, 'Nessuno', 6);
-- Opzioni Sondaggio 6
INSERT INTO opzione (id, testo, domanda_id) VALUES (16, 'IntelliJ IDEA', 7);
INSERT INTO opzione (id, testo, domanda_id) VALUES (17, 'Eclipse', 7);
INSERT INTO opzione (id, testo, domanda_id) VALUES (18, 'VS Code', 7);

-- 6. Inserimento Commenti (ManyToOne con Sondaggio e Utente) [verificato]
INSERT INTO commento (id, testo, data, sondaggio_id, utente_id) VALUES (1, 'Sondaggio molto pertinente!', '2026-07-19', 1, 2);
INSERT INTO commento (id, testo, data, sondaggio_id, utente_id) VALUES (2, 'Ottimo sondaggio, la battaglia sui framework frontend non ha mai fine.', '2026-07-16', 2, 3);
INSERT INTO commento (id, testo, data, sondaggio_id, utente_id) VALUES (3, 'Io uso Postgres per tutto.', '2026-07-12', 3, 1);

-- 7. Inserimento Voti (ManyToOne con Utente e Opzione) [verificato]
INSERT INTO voto (id, data_voto, utente_id, opzione_id) VALUES (1, '2026-07-19 14:30:00', 2, 1);
INSERT INTO voto (id, data_voto, utente_id, opzione_id) VALUES (2, '2026-07-19 14:31:00', 2, 3);
INSERT INTO voto (id, data_voto, utente_id, opzione_id) VALUES (3, '2026-07-16 10:05:00', 3, 5);
INSERT INTO voto (id, data_voto, utente_id, opzione_id) VALUES (4, '2026-07-12 09:15:00', 1, 8);
INSERT INTO voto (id, data_voto, utente_id, opzione_id) VALUES (5, '2026-07-02 18:20:00', 2, 17);

-- 8. Inserimento Partecipazioni Utente [verificato]
INSERT INTO utente_partecipazioni (utente_id, partecipazioni_id) VALUES (2, 1);
INSERT INTO utente_partecipazioni (utente_id, partecipazioni_id) VALUES (3, 2);
INSERT INTO utente_partecipazioni (utente_id, partecipazioni_id) VALUES (1, 3);
INSERT INTO utente_partecipazioni (utente_id, partecipazioni_id) VALUES (2, 6);

SELECT setval('utente_seq', (SELECT MAX(id) FROM utente));
SELECT setval('credential_seq', (SELECT MAX(id) FROM credential));
SELECT setval('sondaggio_seq', (SELECT MAX(id) FROM sondaggio));
SELECT setval('domanda_seq', (SELECT MAX(id) FROM domanda));
SELECT setval('opzione_seq', (SELECT MAX(id) FROM opzione));
SELECT setval('commento_seq', (SELECT MAX(id) FROM commento));
SELECT setval('voto_seq', (SELECT MAX(id) FROM voto));