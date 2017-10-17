-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: src/main/resources/db/changelog/db.changelog-master.xml
-- Ran at: 17.10.17 19:15
-- Against: H2@jdbc:h2:file:D:/refDB
-- Liquibase version: 3.5.3
-- *********************************************************************

-- Lock Database
UPDATE DATABASECHANGELOGLOCK SET LOCKED = TRUE, LOCKEDBY = 'DESKTOP-7VHU4U0 (192.168.56.1)', LOCKGRANTED = '2017-10-17 19:15:33.620' WHERE ID = 1 AND LOCKED = FALSE;

-- Changeset src/main/resources/db/changelog/db.changelog-master.xml::1508260513065-1::Admin (generated)
ALTER TABLE CART DROP COLUMN TEST;

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1508260513065-1', 'Admin (generated)', 'src/main/resources/db/changelog/db.changelog-master.xml', NOW(), 22, '7:9bc5b6082571c50de46eaeee4e965272', 'dropColumn columnName=TEST, tableName=CART', '', 'EXECUTED', NULL, NULL, '3.5.3', '8260534240');

-- Release Database Lock
UPDATE DATABASECHANGELOGLOCK SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

