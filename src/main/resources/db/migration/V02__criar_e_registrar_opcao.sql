CREATE TABLE opcao (
	opcao_codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL UNIQUE,
    colaborador_codigo BIGINT(20) NOT NULL REFERENCES colaborador(colaborador_codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

