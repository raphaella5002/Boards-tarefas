--liquibase formatted sql
--changeset raphaella:202503221447
--comment: boards table create

CREATE TABLE BOARDS(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR (100) NOT NULL
) ENGINE=InnoDB

--rollback DROP TABLE BOARDS