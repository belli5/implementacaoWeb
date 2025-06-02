CREATE TABLE Cliente (
                         id INTEGER PRIMARY KEY,
                         nome VARCHAR,
                         senha VARCHAR,
                         email VARCHAR,
                         telefone VARCHAR,
                         rua VARCHAR,
                         bairro VARCHAR,
                         cidade VARCHAR,
                         estado VARCHAR
);

CREATE TABLE Prestador (
                           id INTEGER PRIMARY KEY,
                           nome VARCHAR,
                           senha VARCHAR,
                           email VARCHAR,
                           telefone VARCHAR,
                           rua VARCHAR,
                           bairro VARCHAR,
                           cidade VARCHAR,
                           estado VARCHAR
);

CREATE TABLE Pedido (
    id SERIAL PRIMARY KEY,
    data DATE,
    fk_Servicos_nome VARCHAR,
    fk_Prestador_id INTEGER,
    fk_Cliente_id INTEGER,
    status VARCHAR
);


CREATE TABLE Servico (
                         nome VARCHAR PRIMARY KEY,
                         categoria VARCHAR,
                         descricao VARCHAR
);

CREATE TABLE AvaliacaoSobrePrestador (
                                         id INTEGER PRIMARY KEY,
                                         fk_Cliente_id INTEGER,
                                         comentario VARCHAR,
                                         nota INTEGER,
                                         fk_Prestador_id INTEGER
);

CREATE TABLE AvaliacaoSobreCliente (
                                       id INTEGER PRIMARY KEY,
                                       fk_Prestador_id INTEGER,
                                       comentario VARCHAR,
                                       nota INTEGER,
                                       fk_Cliente_id INTEGER
);

CREATE TABLE Oferece (
                         id INTEGER PRIMARY KEY,
                         fk_Prestador_id INTEGER,
                         fk_Servicos_nome VARCHAR
);

CREATE TABLE Favoritado (
                            id INTEGER PRIMARY KEY,
                            fk_Prestador_id INTEGER,
                            fk_Cliente_id INTEGER
);

ALTER TABLE Pedido ADD CONSTRAINT FK_Pedido_2
    FOREIGN KEY (fk_Servicos_nome)
        REFERENCES Servico (nome)
        ON DELETE CASCADE;

ALTER TABLE Pedido ADD CONSTRAINT FK_Pedido_3
    FOREIGN KEY (fk_Prestador_id)
        REFERENCES Prestador (id)
        ON DELETE CASCADE;

ALTER TABLE Pedido ADD CONSTRAINT FK_Pedido_4
    FOREIGN KEY (fk_Cliente_id)
        REFERENCES Cliente (id)
        ON DELETE CASCADE;

ALTER TABLE AvaliacaoSobrePrestador ADD CONSTRAINT FK_AvaliacaoSobrePrestador_2
    FOREIGN KEY (fk_Prestador_id)
        REFERENCES Prestador (id)
        ON DELETE SET NULL;

ALTER TABLE AvaliacaoSobrePrestador ADD CONSTRAINT FK_AvaliacaoSobrePrestador_3
    FOREIGN KEY (fk_Cliente_id)
        REFERENCES Cliente (id)
        ON DELETE SET NULL;

ALTER TABLE AvaliacaoSobreCliente ADD CONSTRAINT FK_AvaliacaoSobreCliente_2
    FOREIGN KEY (fk_Prestador_id)
        REFERENCES Prestador (id)
        ON DELETE SET NULL;

ALTER TABLE AvaliacaoSobreCliente ADD CONSTRAINT FK_AvaliacaoSobreCliente_3
    FOREIGN KEY (fk_Cliente_id)
        REFERENCES Cliente (id)
        ON DELETE SET NULL;

ALTER TABLE Oferece ADD CONSTRAINT FK_Oferece_2
    FOREIGN KEY (fk_Servicos_nome)
        REFERENCES Servico (nome)
        ON DELETE RESTRICT;

ALTER TABLE Oferece ADD CONSTRAINT FK_Oferece_3
    FOREIGN KEY (fk_Prestador_id)
        REFERENCES Prestador (id)
        ON DELETE SET NULL;

ALTER TABLE Favoritado ADD CONSTRAINT FK_Favoritado_2
    FOREIGN KEY (fk_Cliente_id)
        REFERENCES Cliente (id)
        ON DELETE SET NULL;

ALTER TABLE Favoritado ADD CONSTRAINT FK_Favoritado_3
    FOREIGN KEY (fk_Prestador_id)
        REFERENCES Prestador (id)
        ON DELETE SET NULL;