CREATE TABLE pools(
    id SERIAL,
    proposal_id SERIAL NOT NULL,
    starts_at TIMESTAMP NOT NULL,
    ends_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    open BOOLEAN NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT proposal_fk
        FOREIGN KEY (proposal_id)
            REFERENCES proposals(id)
)