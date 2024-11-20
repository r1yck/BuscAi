CREATE DATABASE IF NOT EXISTS buscai_db;
USE buscai_db;

-- Tabela de mercados com mais detalhes
CREATE TABLE IF NOT EXISTS mercado (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    localizacao VARCHAR(255) NOT NULL,
    telefone VARCHAR(20),  
    email VARCHAR(255)     
);

-- Tabela de produtos com mais informações
CREATE TABLE IF NOT EXISTS produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    disponivel BOOLEAN DEFAULT TRUE,
    mercado_id INT,
    categoria VARCHAR(255), 
    marca VARCHAR(255),     
    FOREIGN KEY (mercado_id) REFERENCES mercado(id) ON DELETE CASCADE
);

-- Criando índices para otimizar consultas
CREATE INDEX idx_produto_nome ON produto(nome);
CREATE INDEX idx_produto_mercado_id ON produto(mercado_id);
CREATE INDEX idx_mercado_nome ON mercado(nome);
