
INSERT INTO utente (id, nome, cognome, email) VALUES (1, 'alessio', 'cesaroni', 'alessio.cesaroni@fake.it');
INSERT INTO credential (id, username, psw, ruolo, utente_id) VALUES (1, 'cesa', '$2a$12$/MlAeF6XHN.QL/.FVlWx2OmwJMc.nqOHoZ6cHr5SMhAfPeHaQjq82', 'ADMIN', 1);


ALTER SEQUENCE utente_seq RESTART WITH 2;
ALTER SEQUENCE credential_seq RESTART WITH 2;