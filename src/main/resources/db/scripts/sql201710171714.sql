-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: src/main/resources/db/changelog/db.changelog-master.xml
-- Ran at: 17.10.17 19:14
-- Against: H2@jdbc:h2:file:D:/refDB
-- Liquibase version: 3.5.3
-- *********************************************************************

-- Lock Database
UPDATE DATABASECHANGELOGLOCK SET LOCKED = TRUE, LOCKEDBY = 'DESKTOP-7VHU4U0 (192.168.56.1)', LOCKGRANTED = '2017-10-17 19:14:20.241' WHERE ID = 1 AND LOCKED = FALSE;

-- Changeset src/main/resources/db/changelog/db.changelog-master.xml::1508260419192-1::Admin (generated)
ALTER TABLE Cart ADD test BIGINT;

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1508260419192-1', 'Admin (generated)', 'src/main/resources/db/changelog/db.changelog-master.xml', NOW(), 21, '7:2ea9283d8ae3d05b7435a48d573cfbdb', 'addColumn tableName=Cart', '', 'EXECUTED', NULL, NULL, '3.5.3', '8260460964');

-- Release Database Lock
UPDATE DATABASECHANGELOGLOCK SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

