CREATE DATABASE IF NOT EXISTS `buscai`;
USE `buscai`;

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `login` varchar(100) NOT NULL,
  `senha` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `mercado`;
CREATE TABLE `mercado` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `localizacao` varchar(255) NOT NULL,
  `usuario_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `usuario_id` (`usuario_id`),
  CONSTRAINT `mercado_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `produto`;
CREATE TABLE `produto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `preco` decimal(10,2) NOT NULL,
  `categoria` varchar(100) NOT NULL,
  `disponibilidade` tinyint(1) NOT NULL DEFAULT '1',
  `mercado_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `mercado_id` (`mercado_id`),
  CONSTRAINT `produto_ibfk_1` FOREIGN KEY (`mercado_id`) REFERENCES `mercado` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

INSERT INTO usuario (login, senha) VALUES 
('admin', 'admin123');

SET SQL_SAFE_UPDATES = 0;

DELETE FROM buscai.mercado;
DELETE FROM buscai.produto;

INSERT INTO mercado (nome, localizacao) VALUES
('Mercado Central', 'Rua A, 123, Centro'),
('Supermercado Popular', 'Avenida B, 456, Bairro Novo'),
('Hipermercado Extra', 'Avenida C, 789, Cidade Velha'),
('Mercado Verde', 'Rua D, 101, Jardim das Flores'),
('Supermercado Família', 'Avenida E, 202, Vila Nova');

INSERT INTO produto (nome, preco, categoria, disponibilidade, mercado_id) VALUES
('Tomate', 4.50, 'Hortifrúti', 1, 22),
('Arroz', 19.99, 'Grãos', 1, 22),
('Feijão', 7.99, 'Grãos', 1, 22),
('Cenoura', 3.50, 'Hortifrúti', 1, 22),
('Maçã', 5.00, 'Frutas', 1, 22);

INSERT INTO produto (nome, preco, categoria, disponibilidade, mercado_id) VALUES
('Leite', 3.29, 'Laticínios', 1, 23),
('Pão', 2.99, 'Padaria', 1, 23),
('Manteiga', 5.49, 'Laticínios', 1, 23),
('Queijo', 10.00, 'Laticínios', 1, 23),
('Iogurte', 2.50, 'Laticínios', 1, 23);

INSERT INTO produto (nome, preco, categoria, disponibilidade, mercado_id) VALUES
('Cerveja', 6.00, 'Bebidas', 1, 24),
('Detergente', 3.50, 'Limpeza', 1, 24),
('Sabão em pó', 8.00, 'Limpeza', 1, 24),
('Soda', 4.00, 'Bebidas', 1, 24),
('Suco', 5.50, 'Bebidas', 1, 24);

INSERT INTO produto (nome, preco, categoria, disponibilidade, mercado_id) VALUES
('Alface', 2.50, 'Hortifrúti', 1, 25),
('Pepino', 1.99, 'Hortifrúti', 1, 25),
('Brócolis', 3.20, 'Hortifrúti', 1, 25),
('Batata', 2.70, 'Hortifrúti', 1, 25),
('Cebola', 2.00, 'Hortifrúti', 1, 25);

INSERT INTO produto (nome, preco, categoria, disponibilidade, mercado_id) VALUES
('Frango', 12.99, 'Carnes', 1, 26),
('Carne Bovina', 30.00, 'Carnes', 1, 26),
('Peixe', 20.00, 'Carnes', 1, 26),
('Linguiça', 15.00, 'Carnes', 1, 26),
('Ovos', 5.00, 'Laticínios', 1, 26);

INSERT INTO produto (nome, preco, categoria, disponibilidade, mercado_id) VALUES
('Batata-doce', 3.00, 'Hortifrúti', 0, 22);

INSERT INTO produto (nome, preco, categoria, disponibilidade, mercado_id) VALUES
('Tomate', 5.00, 'Hortifrúti', 1, 23);
