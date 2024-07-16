CREATE TABLE proposals(
    id SERIAL,
    title VARCHAR(32) NOT NULL,
    description TEXT,
    approved, BOOLEAN, NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    modified_at TIMESTAMP NOT NULL,
    PRIMARY KEY(id)
)