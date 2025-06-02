CREATE TABLE cliente (
    id SERIAL PRIMARY KEY,
    nome VARCHAR,
    senha VARCHAR,
    email VARCHAR,
    telefone VARCHAR,
    rua VARCHAR,
    bairro VARCHAR,
    cidade VARCHAR,
    estado VARCHAR
);

CREATE TABLE prestador (
    id SERIAL PRIMARY KEY,
    nome VARCHAR,
    senha VARCHAR,
    email VARCHAR,
    telefone VARCHAR,
    rua VARCHAR,
    bairro VARCHAR,
    cidade VARCHAR,
    estado VARCHAR
);

CREATE TABLE pedido (
    id SERIAL PRIMARY KEY,
    data DATE,
    fk_servicos_nome VARCHAR,
    fk_prestador_id INTEGER,
    fk_cliente_id INTEGER,
    status VARCHAR
);

CREATE TABLE servico (
    nome VARCHAR PRIMARY KEY,
    categoria VARCHAR,
    descricao VARCHAR
);

CREATE TABLE avaliacaosobreprestador (
    id SERIAL PRIMARY KEY,
    fk_cliente_id INTEGER,
    comentario VARCHAR,
    nota INTEGER,
    fk_prestador_id INTEGER
);

CREATE TABLE avaliacaosobrecliente (
    id SERIAL PRIMARY KEY,
    fk_prestador_id INTEGER,
    comentario VARCHAR,
    nota INTEGER,
    fk_cliente_id INTEGER
);

CREATE TABLE oferece (
    id SERIAL PRIMARY KEY,
    fk_prestador_id INTEGER,
    fk_servicos_nome VARCHAR
);

CREATE TABLE favoritado (
    id SERIAL PRIMARY KEY,
    fk_prestador_id INTEGER,
    fk_cliente_id INTEGER
);

-- Constraints
ALTER TABLE pedido ADD CONSTRAINT fk_pedido_servico
    FOREIGN KEY (fk_servicos_nome)
    REFERENCES servico (nome)
    ON DELETE CASCADE;

ALTER TABLE pedido ADD CONSTRAINT fk_pedido_prestador
    FOREIGN KEY (fk_prestador_id)
    REFERENCES prestador (id)
    ON DELETE CASCADE;

ALTER TABLE pedido ADD CONSTRAINT fk_pedido_cliente
    FOREIGN KEY (fk_cliente_id)
    REFERENCES cliente (id)
    ON DELETE CASCADE;

ALTER TABLE avaliacaosobreprestador ADD CONSTRAINT fk_avaliacaoprestador_prestador
    FOREIGN KEY (fk_prestador_id)
    REFERENCES prestador (id)
    ON DELETE SET NULL;

ALTER TABLE avaliacaosobreprestador ADD CONSTRAINT fk_avaliacaoprestador_cliente
    FOREIGN KEY (fk_cliente_id)
    REFERENCES cliente (id)
    ON DELETE SET NULL;

ALTER TABLE avaliacaosobrecliente ADD CONSTRAINT fk_avaliacaocliente_prestador
    FOREIGN KEY (fk_prestador_id)
    REFERENCES prestador (id)
    ON DELETE SET NULL;

ALTER TABLE avaliacaosobrecliente ADD CONSTRAINT fk_avaliacaocliente_cliente
    FOREIGN KEY (fk_cliente_id)
    REFERENCES cliente (id)
    ON DELETE SET NULL;

ALTER TABLE oferece ADD CONSTRAINT fk_oferece_servico
    FOREIGN KEY (fk_servicos_nome)
    REFERENCES servico (nome)
    ON DELETE RESTRICT;

ALTER TABLE oferece ADD CONSTRAINT fk_oferece_prestador
    FOREIGN KEY (fk_prestador_id)
    REFERENCES prestador (id)
    ON DELETE SET NULL;

ALTER TABLE favoritado ADD CONSTRAINT fk_favoritado_cliente
    FOREIGN KEY (fk_cliente_id)
    REFERENCES cliente (id)
    ON DELETE SET NULL;

ALTER TABLE favoritado ADD CONSTRAINT fk_favoritado_prestador
    FOREIGN KEY (fk_prestador_id)
    REFERENCES prestador (id)
    ON DELETE SET NULL;
