-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: src/main/resources/db/changelog/db.changelog-master.xml
-- Ran at: 17.10.17 19:12
-- Against: H2@jdbc:h2:file:D:/refDB
-- Liquibase version: 3.5.3
-- *********************************************************************

-- Create Database Lock Table
CREATE TABLE DATABASECHANGELOGLOCK (ID INT NOT NULL, LOCKED BOOLEAN NOT NULL, LOCKGRANTED TIMESTAMP, LOCKEDBY VARCHAR(255), CONSTRAINT PK_DATABASECHANGELOGLOCK PRIMARY KEY (ID));

-- Initialize Database Lock Table
DELETE FROM DATABASECHANGELOGLOCK;

INSERT INTO DATABASECHANGELOGLOCK (ID, LOCKED) VALUES (1, FALSE);

-- Lock Database
UPDATE DATABASECHANGELOGLOCK SET LOCKED = TRUE, LOCKEDBY = 'DESKTOP-7VHU4U0 (192.168.56.1)', LOCKGRANTED = '2017-10-17 19:12:04.695' WHERE ID = 1 AND LOCKED = FALSE;

-- Create Database Change Log Table
CREATE TABLE DATABASECHANGELOG (ID VARCHAR(255) NOT NULL, AUTHOR VARCHAR(255) NOT NULL, FILENAME VARCHAR(255) NOT NULL, DATEEXECUTED TIMESTAMP NOT NULL, ORDEREXECUTED INT NOT NULL, EXECTYPE VARCHAR(10) NOT NULL, MD5SUM VARCHAR(35), DESCRIPTION VARCHAR(255), COMMENTS VARCHAR(255), TAG VARCHAR(255), LIQUIBASE VARCHAR(20), CONTEXTS VARCHAR(255), LABELS VARCHAR(255), DEPLOYMENT_ID VARCHAR(10));

-- Changeset src/main/resources/db/changelog/db.changelog-master.xml::1494353121972-1::Admin (generated)
CREATE SEQUENCE hibernate_sequence;

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1494353121972-1', 'Admin (generated)', 'src/main/resources/db/changelog/db.changelog-master.xml', NOW(), 1, '7:afe897bea20bc08d1681087c94d0967d', 'createSequence sequenceName=hibernate_sequence', '', 'EXECUTED', NULL, NULL, '3.5.3', '8260325476');

-- Changeset src/main/resources/db/changelog/db.changelog-master.xml::1494353121972-2::Admin (generated)
CREATE TABLE CART_PRODUCTS (CART_ID BIGINT NOT NULL, CART_ITEM_ID BIGINT NOT NULL);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1494353121972-2', 'Admin (generated)', 'src/main/resources/db/changelog/db.changelog-master.xml', NOW(), 2, '7:d4a8d26aa7d18caf267f5fd469f32b67', 'createTable tableName=CART_PRODUCTS', '', 'EXECUTED', NULL, NULL, '3.5.3', '8260325476');

-- Changeset src/main/resources/db/changelog/db.changelog-master.xml::1494353121972-3::Admin (generated)
CREATE TABLE Cart (id BIGINT AUTO_INCREMENT NOT NULL, dateTime BINARY(255), CONSTRAINT CartPK PRIMARY KEY (id));

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1494353121972-3', 'Admin (generated)', 'src/main/resources/db/changelog/db.changelog-master.xml', NOW(), 3, '7:7ad03d800f3771313cad7970228df300', 'createTable tableName=Cart', '', 'EXECUTED', NULL, NULL, '3.5.3', '8260325476');

-- Changeset src/main/resources/db/changelog/db.changelog-master.xml::1494353121972-4::Admin (generated)
CREATE TABLE CartItem (id BIGINT AUTO_INCREMENT NOT NULL, quantity BIGINT, PRODUCT_ID BIGINT, CONSTRAINT CartItemPK PRIMARY KEY (id));

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1494353121972-4', 'Admin (generated)', 'src/main/resources/db/changelog/db.changelog-master.xml', NOW(), 4, '7:3b51f43b863067acaa194ed41532042d', 'createTable tableName=CartItem', '', 'EXECUTED', NULL, NULL, '3.5.3', '8260325476');

-- Changeset src/main/resources/db/changelog/db.changelog-master.xml::1494353121972-5::Admin (generated)
CREATE TABLE Category (id BIGINT AUTO_INCREMENT NOT NULL, description VARCHAR(255), name VARCHAR(255), CONSTRAINT CategoryPK PRIMARY KEY (id));

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1494353121972-5', 'Admin (generated)', 'src/main/resources/db/changelog/db.changelog-master.xml', NOW(), 5, '7:3b7fc7e0ef18584331f07b0f60f25c35', 'createTable tableName=Category', '', 'EXECUTED', NULL, NULL, '3.5.3', '8260325476');

-- Changeset src/main/resources/db/changelog/db.changelog-master.xml::1494353121972-6::Admin (generated)
CREATE TABLE ORDERS (id BIGINT AUTO_INCREMENT NOT NULL, price DECIMAL(19, 2), CART_ID BIGINT, DETAIL_ID BIGINT, USER_ORDER BIGINT, CONSTRAINT ORDERSPK PRIMARY KEY (id));

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1494353121972-6', 'Admin (generated)', 'src/main/resources/db/changelog/db.changelog-master.xml', NOW(), 6, '7:845327508352f733560bb437627a7d2b', 'createTable tableName=ORDERS', '', 'EXECUTED', NULL, NULL, '3.5.3', '8260325476');

-- Changeset src/main/resources/db/changelog/db.changelog-master.xml::1494353121972-7::Admin (generated)
CREATE TABLE Product (id BIGINT AUTO_INCREMENT NOT NULL, active TINYINT(1) DEFAULT 1, description VARCHAR(255), imageUrl VARCHAR(255), name VARCHAR(255), price DECIMAL(19, 2), CATEGORY_ID BIGINT, CONSTRAINT ProductPK PRIMARY KEY (id));

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1494353121972-7', 'Admin (generated)', 'src/main/resources/db/changelog/db.changelog-master.xml', NOW(), 7, '7:e1b57993eff7d03b0b7919687da56bd6', 'createTable tableName=Product', '', 'EXECUTED', NULL, NULL, '3.5.3', '8260325476');

-- Changeset src/main/resources/db/changelog/db.changelog-master.xml::1494353121972-8::Admin (generated)
CREATE TABLE ShippingDetail (id BIGINT AUTO_INCREMENT NOT NULL, city VARCHAR(255), houseNum VARCHAR(255), phoneNumber VARCHAR(255), postCode VARCHAR(255), street VARCHAR(255), CONSTRAINT ShippingDetailPK PRIMARY KEY (id));

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1494353121972-8', 'Admin (generated)', 'src/main/resources/db/changelog/db.changelog-master.xml', NOW(), 8, '7:d159fb758d72231a69080d61e4a66494', 'createTable tableName=ShippingDetail', '', 'EXECUTED', NULL, NULL, '3.5.3', '8260325476');

-- Changeset src/main/resources/db/changelog/db.changelog-master.xml::1494353121972-9::Admin (generated)
CREATE TABLE USERS (id BIGINT AUTO_INCREMENT NOT NULL, active BOOLEAN, email VARCHAR(255), lastName VARCHAR(255), name VARCHAR(255), passwordHash VARCHAR(255), role INT, CONSTRAINT USERSPK PRIMARY KEY (id));

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1494353121972-9', 'Admin (generated)', 'src/main/resources/db/changelog/db.changelog-master.xml', NOW(), 9, '7:6bd1cea53e3550a30fe86e4f2d87af49', 'createTable tableName=USERS', '', 'EXECUTED', NULL, NULL, '3.5.3', '8260325476');

-- Changeset src/main/resources/db/changelog/db.changelog-master.xml::1494353121972-10::Admin (generated)
ALTER TABLE CART_PRODUCTS ADD PRIMARY KEY (CART_ID, CART_ITEM_ID);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1494353121972-10', 'Admin (generated)', 'src/main/resources/db/changelog/db.changelog-master.xml', NOW(), 10, '7:82f3664c882fb39ea9de47cb05b3b673', 'addPrimaryKey tableName=CART_PRODUCTS', '', 'EXECUTED', NULL, NULL, '3.5.3', '8260325476');

-- Changeset src/main/resources/db/changelog/db.changelog-master.xml::1494353121972-11::Admin (generated)
ALTER TABLE Category ADD CONSTRAINT UC_CATEGORYNAME_COL UNIQUE (name);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1494353121972-11', 'Admin (generated)', 'src/main/resources/db/changelog/db.changelog-master.xml', NOW(), 11, '7:37190df77002895831a8e128d73fd5c6', 'addUniqueConstraint constraintName=UC_CATEGORYNAME_COL, tableName=Category', '', 'EXECUTED', NULL, NULL, '3.5.3', '8260325476');

-- Changeset src/main/resources/db/changelog/db.changelog-master.xml::1494353121972-12::Admin (generated)
ALTER TABLE Product ADD CONSTRAINT UC_PRODUCTNAME_COL UNIQUE (name);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1494353121972-12', 'Admin (generated)', 'src/main/resources/db/changelog/db.changelog-master.xml', NOW(), 12, '7:9b66e64138fa41c5ffde30bf6f99451c', 'addUniqueConstraint constraintName=UC_PRODUCTNAME_COL, tableName=Product', '', 'EXECUTED', NULL, NULL, '3.5.3', '8260325476');

-- Changeset src/main/resources/db/changelog/db.changelog-master.xml::1494353121972-13::Admin (generated)
ALTER TABLE USERS ADD CONSTRAINT UC_USERSEMAIL_COL UNIQUE (email);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1494353121972-13', 'Admin (generated)', 'src/main/resources/db/changelog/db.changelog-master.xml', NOW(), 13, '7:00fe24d96270c01b0f4f76a2376f19f9', 'addUniqueConstraint constraintName=UC_USERSEMAIL_COL, tableName=USERS', '', 'EXECUTED', NULL, NULL, '3.5.3', '8260325476');

-- Changeset src/main/resources/db/changelog/db.changelog-master.xml::1494353121972-14::Admin (generated)
ALTER TABLE ORDERS ADD CONSTRAINT FK1rkqc6xhvrqvmfcalc4sq6b13 FOREIGN KEY (CART_ID) REFERENCES Cart (id);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1494353121972-14', 'Admin (generated)', 'src/main/resources/db/changelog/db.changelog-master.xml', NOW(), 14, '7:2c774120b12a6dd1575fa029d80d9624', 'addForeignKeyConstraint baseTableName=ORDERS, constraintName=FK1rkqc6xhvrqvmfcalc4sq6b13, referencedTableName=Cart', '', 'EXECUTED', NULL, NULL, '3.5.3', '8260325476');

-- Changeset src/main/resources/db/changelog/db.changelog-master.xml::1494353121972-15::Admin (generated)
ALTER TABLE CART_PRODUCTS ADD CONSTRAINT FK4ga3l4kxvxtdp66l4or0d77k8 FOREIGN KEY (CART_ITEM_ID) REFERENCES CartItem (id);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1494353121972-15', 'Admin (generated)', 'src/main/resources/db/changelog/db.changelog-master.xml', NOW(), 15, '7:2836ac2f6b56a27ff06f634c34a68306', 'addForeignKeyConstraint baseTableName=CART_PRODUCTS, constraintName=FK4ga3l4kxvxtdp66l4or0d77k8, referencedTableName=CartItem', '', 'EXECUTED', NULL, NULL, '3.5.3', '8260325476');

-- Changeset src/main/resources/db/changelog/db.changelog-master.xml::1494353121972-16::Admin (generated)
ALTER TABLE ORDERS ADD CONSTRAINT FK7j0677wwonm5foffikdlxbyaq FOREIGN KEY (USER_ORDER) REFERENCES USERS (id);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1494353121972-16', 'Admin (generated)', 'src/main/resources/db/changelog/db.changelog-master.xml', NOW(), 16, '7:aef43591cf92d72644aa129087b0616e', 'addForeignKeyConstraint baseTableName=ORDERS, constraintName=FK7j0677wwonm5foffikdlxbyaq, referencedTableName=USERS', '', 'EXECUTED', NULL, NULL, '3.5.3', '8260325476');

-- Changeset src/main/resources/db/changelog/db.changelog-master.xml::1494353121972-17::Admin (generated)
ALTER TABLE Product ADD CONSTRAINT FKgm7hqnhehl0jm23weqxbb1t0 FOREIGN KEY (CATEGORY_ID) REFERENCES Category (id);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1494353121972-17', 'Admin (generated)', 'src/main/resources/db/changelog/db.changelog-master.xml', NOW(), 17, '7:432406959acf88a720b0e0ff8e581b2c', 'addForeignKeyConstraint baseTableName=Product, constraintName=FKgm7hqnhehl0jm23weqxbb1t0, referencedTableName=Category', '', 'EXECUTED', NULL, NULL, '3.5.3', '8260325476');

-- Changeset src/main/resources/db/changelog/db.changelog-master.xml::1494353121972-18::Admin (generated)
ALTER TABLE CartItem ADD CONSTRAINT FKonvjut6573ev7cois2wgpa2lr FOREIGN KEY (PRODUCT_ID) REFERENCES Product (id);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1494353121972-18', 'Admin (generated)', 'src/main/resources/db/changelog/db.changelog-master.xml', NOW(), 18, '7:fed6e70abfee0aedc96457f134f0b084', 'addForeignKeyConstraint baseTableName=CartItem, constraintName=FKonvjut6573ev7cois2wgpa2lr, referencedTableName=Product', '', 'EXECUTED', NULL, NULL, '3.5.3', '8260325476');

-- Changeset src/main/resources/db/changelog/db.changelog-master.xml::1494353121972-19::Admin (generated)
ALTER TABLE ORDERS ADD CONSTRAINT FKrugs15dmrpe9mkthfne9k2aee FOREIGN KEY (DETAIL_ID) REFERENCES ShippingDetail (id);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1494353121972-19', 'Admin (generated)', 'src/main/resources/db/changelog/db.changelog-master.xml', NOW(), 19, '7:5a5e64fd5b2eaddf0f6b217e379bf155', 'addForeignKeyConstraint baseTableName=ORDERS, constraintName=FKrugs15dmrpe9mkthfne9k2aee, referencedTableName=ShippingDetail', '', 'EXECUTED', NULL, NULL, '3.5.3', '8260325476');

-- Changeset src/main/resources/db/changelog/db.changelog-master.xml::1494353121972-20::Admin (generated)
ALTER TABLE CART_PRODUCTS ADD CONSTRAINT FKwenfoxyuwmd5fjqohmustlpq FOREIGN KEY (CART_ID) REFERENCES Cart (id);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1494353121972-20', 'Admin (generated)', 'src/main/resources/db/changelog/db.changelog-master.xml', NOW(), 20, '7:5de65335a6611f4686a3f479b706a59f', 'addForeignKeyConstraint baseTableName=CART_PRODUCTS, constraintName=FKwenfoxyuwmd5fjqohmustlpq, referencedTableName=Cart', '', 'EXECUTED', NULL, NULL, '3.5.3', '8260325476');

-- Release Database Lock
UPDATE DATABASECHANGELOGLOCK SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

