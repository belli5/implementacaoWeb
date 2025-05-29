CREATE TABLE Cliente (
    id INTEGER PRIMARY KEY,
    nome VARCHAR,
    email VARCHAR,
    telefone VARCHAR,
    rua VARCHAR,
    cidade VARCHAR,
    bairro VARCHAR,
    estado VARCHAR
);

CREATE TABLE Prestador (
    id INTEGER PRIMARY KEY,
    nome VARCHAR,
    email VARCHAR,
    telefone VARCHAR,
    rua VARCHAR,
    bairro VARCHAR,
    cidade VARCHAR,
    estado VARCHAR
);

CREATE TABLE Pedido (
    id INTEGER PRIMARY KEY,
    data DATE,
    status VARCHAR,
    fk_Servicos_nome VARCHAR,
    fk_Prestador_id INTEGER,
    fk_Cliente_id INTEGER
);

CREATE TABLE Servicos (
    nome VARCHAR PRIMARY KEY,
    categoria VARCHAR,
    descricao VARCHAR
);

CREATE TABLE cliente_avalia (
    fk_Prestador_id INTEGER,
    fk_Cliente_id INTEGER,
    id INTEGER PRIMARY KEY,
    nota INTEGER,
    comentario VARCHAR
);

CREATE TABLE prestador_avalia (
    fk_Prestador_id INTEGER,
    fk_Cliente_id INTEGER,
    id INTEGER PRIMARY KEY,
    nota INTEGER,
    comentario VARCHAR
);

CREATE TABLE oferece (
    fk_Servicos_nome VARCHAR,
    fk_Prestador_id INTEGER
);

CREATE TABLE favoritados (
    fk_Cliente_id INTEGER,
    fk_Prestador_id INTEGER
);

ALTER TABLE Pedido ADD CONSTRAINT FK_Pedido_2
    FOREIGN KEY (fk_Servicos_nome)
    REFERENCES Servicos (nome)
    ON DELETE CASCADE;

ALTER TABLE Pedido ADD CONSTRAINT FK_Pedido_3
    FOREIGN KEY (fk_Prestador_id)
    REFERENCES Prestador (id)
    ON DELETE CASCADE;

ALTER TABLE Pedido ADD CONSTRAINT FK_Pedido_4
    FOREIGN KEY (fk_Cliente_id)
    REFERENCES Cliente (id)
    ON DELETE CASCADE;

ALTER TABLE cliente_avalia ADD CONSTRAINT FK_cliente_avalia_2
    FOREIGN KEY (fk_Prestador_id)
    REFERENCES Prestador (id)
    ON DELETE SET NULL;

ALTER TABLE cliente_avalia ADD CONSTRAINT FK_cliente_avalia_3
    FOREIGN KEY (fk_Cliente_id)
    REFERENCES Cliente (id)
    ON DELETE SET NULL;

ALTER TABLE prestador_avalia ADD CONSTRAINT FK_prestador_avalia_2
    FOREIGN KEY (fk_Prestador_id)
    REFERENCES Prestador (id)
    ON DELETE SET NULL;

ALTER TABLE prestador_avalia ADD CONSTRAINT FK_prestador_avalia_3
    FOREIGN KEY (fk_Cliente_id)
    REFERENCES Cliente (id)
    ON DELETE SET NULL;

ALTER TABLE oferece ADD CONSTRAINT FK_oferece_1
    FOREIGN KEY (fk_Servicos_nome)
    REFERENCES Servicos (nome)
    ON DELETE RESTRICT;

ALTER TABLE oferece ADD CONSTRAINT FK_oferece_2
    FOREIGN KEY (fk_Prestador_id)
    REFERENCES Prestador (id)
    ON DELETE SET NULL;

ALTER TABLE favoritados ADD CONSTRAINT FK_favoritados_1
    FOREIGN KEY (fk_Cliente_id)
    REFERENCES Cliente (id)
    ON DELETE SET NULL;

ALTER TABLE favoritados ADD CONSTRAINT FK_favoritados_2
    FOREIGN KEY (fk_Prestador_id)
    REFERENCES Prestador (id)
    ON DELETE SET NULL;