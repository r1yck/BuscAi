-- Criação do banco de dados
CREATE DATABASE IF NOT EXISTS buscai_db;

-- Seleciona o banco de dados
USE buscai_db;

-- Criação da tabela Mercado
CREATE TABLE mercado (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    localizacao VARCHAR(255) NOT NULL
);

-- Criação da tabela Produto com a relação com Mercado
CREATE TABLE produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    disponivel BOOLEAN NOT NULL,
    categoria VARCHAR(50) NOT NULL,   -- Categoria como uma string
    mercado_id INT,                   -- Relacionamento com Mercado
    FOREIGN KEY (mercado_id) REFERENCES mercado(id)  -- Chave estrangeira
);

