/* 
 * V1__createSequenceTable.sql
 * Erzeugt alle Datenbankobjekte, um in MySQL Sequenzen nachzustellen.
 */
/*
 * Tabelle mit den Sequence-Werten aller autogenerierten Primärschlüssel.
 */
CREATE TABLE T_SEQUENCE (
	SEQUENCE_NAME CHAR(32) PRIMARY KEY NOT NULL,
    NEXT_VAL BIGINT NOT NULL
);

CREATE UNIQUE INDEX X_SEQUENCE
	ON T_SEQUENCE
   (SEQUENCE_NAME ASC);
