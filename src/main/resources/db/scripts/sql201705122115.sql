-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: src/main/resources/db/changelog/db.changelog-master.xml
-- Ran at: 12.05.17 23:15
-- Against: H2@jdbc:h2:file:D:/refDB
-- Liquibase version: 3.5.3
-- *********************************************************************

-- Lock Database
UPDATE DATABASECHANGELOGLOCK
SET LOCKED = TRUE, LOCKEDBY = 'Admin-Komputer (192.168.254.100)', LOCKGRANTED = '2017-05-12 23:15:31.132'
WHERE ID = 1 AND LOCKED = FALSE;

-- Changeset src/main/resources/db/changelog/db.changelog-master.xml::1494601261315-2::Admin (generated)
DROP TABLE TESTOWA;

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID)
VALUES ('1494601261315-2', 'Admin (generated)', 'src/main/resources/db/changelog/db.changelog-master.xml', NOW(), 24,
                           '7:fcc346c8d60139d25bcc657d04ba0916', 'dropTable tableName=TESTOWA', '', 'EXECUTED', NULL,
                           NULL, '3.5.3', '4623731809');

-- Release Database Lock
UPDATE DATABASECHANGELOGLOCK
SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL
WHERE ID = 1;

