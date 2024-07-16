CREATE TABLE votes(
    id SERIAL,
    proposal_id SERIAL NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    approved BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL,
    modified_at TIMESTAMP NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT proposal_fk
        FOREIGN KEY (proposal_id)
            REFERENCES proposals(id)
)