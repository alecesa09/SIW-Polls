
INSERT INTO utente (id, nome, cognome, email) VALUES (1, 'Mario', 'Rossi', 'mario.rossi@example.com');
INSERT INTO utente (id, nome, cognome, email) VALUES (2, 'Luigi', 'Verdi', 'luigi.verdi@example.com');
INSERT INTO utente (id, nome, cognome, email) VALUES (3, 'Giulia', 'Bianchi', 'giulia.bianchi@example.com');
INSERT INTO utente (id, nome, cognome, email) VALUES (4, 'alessio', 'cesaroni', 'alessio.cesaroni@fake.it');

---La stringa sottostante corrisponde alla password in chiaro: 'password'
INSERT INTO credential (id, username, psw, ruolo, utente_id) VALUES (1, 'admin', '$2a$12$/MlAeF6XHN.QL/.FVlWx2OmwJMc.nqOHoZ6cHr5SMhAfPeHaQjq82', 'ADMIN', 1);
INSERT INTO credential (id, username, psw, ruolo, utente_id) VALUES (2, 'user', '$2a$12$/MlAeF6XHN.QL/.FVlWx2OmwJMc.nqOHoZ6cHr5SMhAfPeHaQjq82', 'DEFAULT', 2);
INSERT INTO credential (id, username, psw, ruolo, utente_id) VALUES (3, 'giulia', '$2a$12$/MlAeF6XHN.QL/.FVlWx2OmwJMc.nqOHoZ6cHr5SMhAfPeHaQjq82', 'DEFAULT', 3);
INSERT INTO credential (id, username, psw, ruolo, utente_id) VALUES (4, 'cesa', '$2a$12$/MlAeF6XHN.QL/.FVlWx2OmwJMc.nqOHoZ6cHr5SMhAfPeHaQjq82', 'ADMIN', 4);


INSERT INTO sondaggio (id, titolo, descrizione, immagine, data_creazione, data_scadenza_voto, visibilita, codice_accesso, utente_id) VALUES (1, 'Tecnologie Backend', 'Quali tecnologie preferisci per lo sviluppo web?', 'springBoot.png', '2026-07-19', '2026-12-31', 'PUBBLICO', '3AE83C52-5855-47C1-80A6-FB880DE6D31B', 4);
INSERT INTO sondaggio (id, titolo, descrizione, immagine, data_creazione, data_scadenza_voto, visibilita, codice_accesso, utente_id) VALUES (2, 'Framework Frontend', 'Il panorama Frontend e in continua evoluzione. Quale libreria preferisci usare nei tuoi progetti?', 'react.png', '2026-07-15', '2026-11-30', 'PUBBLICO', 'E8C2A62B-1DA5-4510-B1F2-5BBBD2E03C51', 4);
INSERT INTO sondaggio (id, titolo, descrizione, immagine, data_creazione, data_scadenza_voto, visibilita, codice_accesso, utente_id) VALUES (3, 'Abitudini di Viaggio', 'Raccolta dati sulle preferenze di viaggio della community.', 'viaggio.jpg', '2026-07-10', '2026-10-31', 'PUBBLICO', '74AAE173-42BC-466E-985A-17A3D5510471', 4);
INSERT INTO sondaggio (id, titolo, descrizione, immagine, data_creazione, data_scadenza_voto, visibilita, codice_accesso, utente_id) VALUES (4, 'Abitudini Sportive', 'Raccontaci come ti mantieni in forma e quali sport segui.', 'sport.jpg', '2026-07-18', '2027-3-15', 'PUBBLICO', '41D0FA5D-24C9-4531-98FA-415326122542', 4);
INSERT INTO sondaggio (id, titolo, descrizione, immagine, data_creazione, data_scadenza_voto, visibilita, codice_accesso, utente_id) VALUES (5, 'Eventi Tech Italia', 'Sondaggio riservato per l organizzazione della trasferta aziendale.', 'TechEvent.jpg', '2026-07-19', '2026-09-01', 'PRIVATO', '1BCD4D58-B724-4800-B313-8058A1514822', 4);
INSERT INTO sondaggio (id, titolo, descrizione, immagine, data_creazione, data_scadenza_voto, visibilita, codice_accesso, utente_id) VALUES (6, 'Editor di Testo e IDE', 'Qual e lo strumento definitivo per scrivere codice in comodita?', 'IDE.png', '2026-07-01', '2026-08-30', 'PUBBLICO', 'A06E6279-FC65-446F-9199-43705A0346F0', 4);


INSERT INTO domanda (id, testo, sondaggio_id) VALUES (1, 'Quale framework utilizzi maggiormente in Java?', 1);
INSERT INTO domanda (id, testo, sondaggio_id) VALUES (2, 'Quale template engine preferisci per le viste server-side?', 1);
INSERT INTO domanda (id, testo, sondaggio_id) VALUES (3, 'Quale libreria/framework UI usi di piu?', 2);
INSERT INTO domanda (id, testo, sondaggio_id) VALUES (4, 'Qual e la tua meta di viaggio preferita?', 3);
INSERT INTO domanda (id, testo, sondaggio_id) VALUES (39, 'Come preferisci prenotare i tuoi viaggi?', 3);
INSERT INTO domanda (id, testo, sondaggio_id) VALUES (40, 'Viaggi da solo o in compagnia?', 3);
INSERT INTO domanda (id, testo, sondaggio_id) VALUES (5, 'Quale sport pratichi piu spesso?', 4);
INSERT INTO domanda (id, testo, sondaggio_id) VALUES (41, 'Con che frequenza ti alleni?', 4);
INSERT INTO domanda (id, testo, sondaggio_id) VALUES (42, 'Preferisci allenarti in palestra o all aperto?', 4);
INSERT INTO domanda (id, testo, sondaggio_id) VALUES (6, 'A quale evento parteciperai quest anno?', 5);
INSERT INTO domanda (id, testo, sondaggio_id) VALUES (7, 'Qual e il tuo IDE preferito per lo sviluppo backend?', 6);


INSERT INTO opzione (id, testo, domanda_id) VALUES (1, 'Spring Boot', 1);
INSERT INTO opzione (id, testo, domanda_id) VALUES (2, 'Quarkus', 1);
INSERT INTO opzione (id, testo, domanda_id) VALUES (3, 'Thymeleaf', 2);
INSERT INTO opzione (id, testo, domanda_id) VALUES (4, 'JSP', 2);
INSERT INTO opzione (id, testo, domanda_id) VALUES (5, 'React', 3);
INSERT INTO opzione (id, testo, domanda_id) VALUES (6, 'Angular', 3);
INSERT INTO opzione (id, testo, domanda_id) VALUES (7, 'Vue.js', 3);
INSERT INTO opzione (id, testo, domanda_id) VALUES (8, 'Mare', 4);
INSERT INTO opzione (id, testo, domanda_id) VALUES (9, 'Montagna', 4);
INSERT INTO opzione (id, testo, domanda_id) VALUES (39, 'Citta d arte', 4);
INSERT INTO opzione (id, testo, domanda_id) VALUES (40, 'Agenzia di viaggio', 39);
INSERT INTO opzione (id, testo, domanda_id) VALUES (41, 'Siti online (fai da te)', 39);
INSERT INTO opzione (id, testo, domanda_id) VALUES (42, 'Solo', 40);
INSERT INTO opzione (id, testo, domanda_id) VALUES (43, 'In coppia', 40);
INSERT INTO opzione (id, testo, domanda_id) VALUES (44, 'In gruppo', 40);
INSERT INTO opzione (id, testo, domanda_id) VALUES (10, 'Calcio', 5);
INSERT INTO opzione (id, testo, domanda_id) VALUES (11, 'Corsa', 5);
INSERT INTO opzione (id, testo, domanda_id) VALUES (12, 'Palestra/Fitness', 5);
INSERT INTO opzione (id, testo, domanda_id) VALUES (45, 'Tutti i giorni', 41);
INSERT INTO opzione (id, testo, domanda_id) VALUES (46, '2-3 volte a settimana', 41);
INSERT INTO opzione (id, testo, domanda_id) VALUES (47, 'Raramente', 41);
INSERT INTO opzione (id, testo, domanda_id) VALUES (48, 'Palestra', 42);
INSERT INTO opzione (id, testo, domanda_id) VALUES (49, 'All aperto', 42);
INSERT INTO opzione (id, testo, domanda_id) VALUES (13, 'Codemotion', 6);
INSERT INTO opzione (id, testo, domanda_id) VALUES (14, 'Devoxx', 6);
INSERT INTO opzione (id, testo, domanda_id) VALUES (15, 'Nessuno', 6);
INSERT INTO opzione (id, testo, domanda_id) VALUES (16, 'IntelliJ IDEA', 7);
INSERT INTO opzione (id, testo, domanda_id) VALUES (17, 'Eclipse', 7);
INSERT INTO opzione (id, testo, domanda_id) VALUES (18, 'VS Code', 7);


INSERT INTO commento (id, testo, data, sondaggio_id, utente_id) VALUES (1, 'Sondaggio molto pertinente!', '2026-07-19', 1, 2);
INSERT INTO commento (id, testo, data, sondaggio_id, utente_id) VALUES (2, 'Ottimo sondaggio, la battaglia sui framework frontend non ha mai fine.', '2026-07-16', 2, 3);
INSERT INTO commento (id, testo, data, sondaggio_id, utente_id) VALUES (3, 'Io preferisco sempre organizzare tutto da solo, niente agenzie.', '2026-07-12', 3, 1);


INSERT INTO votazione (id, sondaggio_id, utente_id, visibilita, data_voto) VALUES (1, 1, 2, 'NORMALE', '2026-07-19 14:31:00');)
INSERT INTO votazione (id, sondaggio_id, utente_id, visibilita, data_voto) VALUES (2, 2, 3, 'NORMALE', '2026-07-16 10:05:00');
INSERT INTO votazione (id, sondaggio_id, utente_id, visibilita, data_voto) VALUES (3, 3, 1, 'NORMALE', '2026-07-12 09:15:00');
INSERT INTO votazione (id, sondaggio_id, utente_id, visibilita, data_voto) VALUES (4, 6, 2, 'NORMALE', '2026-07-02 18:20:00');


INSERT INTO voto (id, votazione_id, domanda_id, opzione_id) VALUES (1, 1, 1, 1); 

INSERT INTO voto (id, votazione_id, domanda_id, opzione_id) VALUES (2, 1, 2, 3); 

INSERT INTO voto (id, votazione_id, domanda_id, opzione_id) VALUES (3, 2, 3, 5);  

INSERT INTO voto (id, votazione_id, domanda_id, opzione_id) VALUES (4, 3, 4, 8); 

INSERT INTO voto (id, votazione_id, domanda_id, opzione_id) VALUES (5, 4, 7, 17); 


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
SELECT setval('votazione_seq', (SELECT MAX(id) FROM votazione));
SELECT setval('voto_seq', (SELECT MAX(id) FROM voto));

-- ============================================================
-- NUOVO SONDAGGIO test
-- ============================================================


INSERT INTO sondaggio (id, titolo, descrizione, immagine, data_creazione, data_scadenza_voto, visibilita, codice_accesso, utente_id) VALUES (7, 'Preferenze Tech Generali', 'Un sondaggio con tante domande veloci a risposta binaria.', 'tech.png', '2026-07-24', '2026-12-31', 'PUBBLICO', '9F3B2C11-6A4D-4E7F-8B21-1234567890AB', 4);


INSERT INTO domanda (id, testo, sondaggio_id) VALUES (8, 'Tabs o Spaces?', 7);
INSERT INTO domanda (id, testo, sondaggio_id) VALUES (9, 'Dark mode o Light mode?', 7);
INSERT INTO domanda (id, testo, sondaggio_id) VALUES (10, 'REST o GraphQL?', 7);
INSERT INTO domanda (id, testo, sondaggio_id) VALUES (11, 'SQL o NoSQL?', 7);
INSERT INTO domanda (id, testo, sondaggio_id) VALUES (12, 'Monolite o Microservizi?', 7);
INSERT INTO domanda (id, testo, sondaggio_id) VALUES (13, 'Vim o VS Code?', 7);
INSERT INTO domanda (id, testo, sondaggio_id) VALUES (14, 'TDD si o no?', 7);
INSERT INTO domanda (id, testo, sondaggio_id) VALUES (15, 'Monorepo o Polyrepo?', 7);
INSERT INTO domanda (id, testo, sondaggio_id) VALUES (16, 'Cloud pubblico o self-hosted?', 7);
INSERT INTO domanda (id, testo, sondaggio_id) VALUES (17, 'Frontend o Backend?', 7);


INSERT INTO opzione (id, testo, domanda_id) VALUES (19, 'Tabs', 8);
INSERT INTO opzione (id, testo, domanda_id) VALUES (20, 'Spaces', 8);

INSERT INTO opzione (id, testo, domanda_id) VALUES (21, 'Dark mode', 9);
INSERT INTO opzione (id, testo, domanda_id) VALUES (22, 'Light mode', 9);

INSERT INTO opzione (id, testo, domanda_id) VALUES (23, 'REST', 10);
INSERT INTO opzione (id, testo, domanda_id) VALUES (24, 'GraphQL', 10);

INSERT INTO opzione (id, testo, domanda_id) VALUES (25, 'SQL', 11);
INSERT INTO opzione (id, testo, domanda_id) VALUES (26, 'NoSQL', 11);

INSERT INTO opzione (id, testo, domanda_id) VALUES (27, 'Monolite', 12);
INSERT INTO opzione (id, testo, domanda_id) VALUES (28, 'Microservizi', 12);

INSERT INTO opzione (id, testo, domanda_id) VALUES (29, 'Vim', 13);
INSERT INTO opzione (id, testo, domanda_id) VALUES (30, 'VS Code', 13);

INSERT INTO opzione (id, testo, domanda_id) VALUES (31, 'Si', 14);
INSERT INTO opzione (id, testo, domanda_id) VALUES (32, 'No', 14);

INSERT INTO opzione (id, testo, domanda_id) VALUES (33, 'Monorepo', 15);
INSERT INTO opzione (id, testo, domanda_id) VALUES (34, 'Polyrepo', 15);

INSERT INTO opzione (id, testo, domanda_id) VALUES (35, 'Cloud pubblico', 16);
INSERT INTO opzione (id, testo, domanda_id) VALUES (36, 'Self-hosted', 16);

INSERT INTO opzione (id, testo, domanda_id) VALUES (37, 'Frontend', 17);
INSERT INTO opzione (id, testo, domanda_id) VALUES (38, 'Backend', 17);


SELECT setval('sondaggio_seq', (SELECT MAX(id) FROM sondaggio));
SELECT setval('domanda_seq', (SELECT MAX(id) FROM domanda));
SELECT setval('opzione_seq', (SELECT MAX(id) FROM opzione));
