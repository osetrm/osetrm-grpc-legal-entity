CREATE TABLE legal_entity
(
    legal_entity_id                BIGSERIAL PRIMARY KEY,
    global_legal_entity_identifier TEXT NOT NULL,
    legal_name                     TEXT NOT NULL
);
ALTER SEQUENCE legal_entity_legal_entity_id_seq RESTART 1000000000000000000;