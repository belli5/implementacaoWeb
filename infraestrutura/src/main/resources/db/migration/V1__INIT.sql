CREATE TABLE cliente (
                         id INT PRIMARY KEY,
                         nome VARCHAR(100),
                         email VARCHAR(100),
                         telefone VARCHAR(20),
                         rua VARCHAR(100),
                         bairro VARCHAR(100),
                         cidade VARCHAR(100),
                         estado VARCHAR(100)
);

CREATE TABLE prestador (
                           id INT PRIMARY KEY,
                           nome VARCHAR(100),
                           email VARCHAR(100),
                           telefone VARCHAR(20),
                           rua VARCHAR(100),
                           bairro VARCHAR(100),
                           cidade VARCHAR(100),
                           estado VARCHAR(100)
);

CREATE TABLE prestacao_servico (
                                   id INT PRIMARY KEY,
                                   descricao TEXT,
                                   valor FLOAT,
                                   bairro VARCHAR(100),
                                   categoria VARCHAR(100),
                                   prestador_id INT REFERENCES prestador(id)
);

CREATE TABLE avaliacao (
                           id INT PRIMARY KEY,
                           prestador_id INT REFERENCES prestador(id),
                           cliente_id INT REFERENCES cliente(id),
                           nota FLOAT,
                           comentario TEXT,
                           tipo_avaliacao VARCHAR(50)
);

CREATE TABLE pedido (
                        id INT PRIMARY KEY,
                        prestador_id INT REFERENCES prestador(id),
                        cliente_id INT REFERENCES cliente(id),
                        data TIMESTAMP,
                        status VARCHAR(50),
                        servico_id INT REFERENCES prestacao_servico(id)
);

CREATE TABLE cliente_prestador_favorito (
                                            cliente_id INT REFERENCES cliente(id),
                                            prestador_id INT REFERENCES prestador(id),
                                            PRIMARY KEY (cliente_id, prestador_id)
);

CREATE TABLE historico_servico_cliente (
                                           cliente_id INT REFERENCES cliente(id),
                                           servico_id INT REFERENCES prestacao_servico(id),
                                           PRIMARY KEY (cliente_id, servico_id)
);
