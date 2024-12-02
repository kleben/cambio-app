CREATE TABLE moeda (
                       id SERIAL PRIMARY KEY,
                       nome VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE produto (
                         id SERIAL PRIMARY KEY,
                         nome VARCHAR(50) NOT NULL UNIQUE,
                         valor DECIMAL(10, 2) NOT NULL
);

CREATE TABLE reino (
                       id SERIAL PRIMARY KEY,
                       nome VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE taxa_cambio (
                             id SERIAL PRIMARY KEY,
                             moeda_id INT NOT NULL REFERENCES moeda(id),
                             taxa DECIMAL(10, 4) NOT NULL,
                             data_hora TIMESTAMP NOT NULL
);

CREATE TABLE transacao (
                           id SERIAL PRIMARY KEY,
                           produto_id INT NULL REFERENCES produto(id),
                           moeda_origem_id INT NOT NULL REFERENCES moeda(id),
                           moeda_destino_id INT NOT NULL REFERENCES moeda(id),
                           reino_id INT NOT NULL REFERENCES reino(id),
                           taxa_cambio DECIMAL(10, 4) NULL,
                           valor_transacao DECIMAL(10, 2) NOT NULL,
                           taxa_adicional DECIMAL(10, 4) NULL,
                           qtd_produtos DECIMAL(10, 4) NULL,
                           data_hora TIMESTAMP NOT NULL,
                           tipo_transacao VARCHAR(20) NOT NULL
);
