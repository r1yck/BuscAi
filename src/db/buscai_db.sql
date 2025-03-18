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

-- Depois cria a tabela mercado, que faz referência à tabela usuario
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

-- Por fim, cria a tabela produto, que faz referência à tabela mercado
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
