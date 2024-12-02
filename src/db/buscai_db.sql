-- Criação do banco de dados
CREATE DATABASE IF NOT EXISTS buscai_db;

-- Seleciona o banco de dados
USE buscai_db;

-- Criação da tabela mercado
CREATE TABLE IF NOT EXISTS mercado (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    localizacao VARCHAR(100)
);

-- Criação da tabela produto
CREATE TABLE IF NOT EXISTS produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco DECIMAL(10, 2),
    disponivel BOOLEAN,
    categoria VARCHAR(50),
    mercado_id INT,
    FOREIGN KEY (mercado_id) REFERENCES mercado(id)
);
