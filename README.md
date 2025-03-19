# BuscAi

<p align="center">
    <img src="buscAi_logo_-_x.png" alt="Logo do BuscAi" width="300"/>
</p>

**BuscAi** é uma plataforma digital para busca e comparação de preços de produtos em diferentes supermercados, projetada para ajudar os usuários a encontrar as melhores ofertas de acordo com suas preferências e localização. A aplicação facilita o acesso a informações de preços e disponibilidade de produtos, promovendo uma experiência prática e intuitiva.

## Funcionalidades

1. **Cadastro de Mercados e Produtos**
   - Mercados podem cadastrar produtos com detalhes de preço, categoria e disponibilidade.
   - Usuários podem consultar produtos específicos e comparar seus preços em diferentes mercados.

2. **Comparação de Preços**
   - Sistema de comparação de preços, permitindo ao usuário visualizar as melhores ofertas do produto escolhido.

3. **Filtro de Preferências**
   - Filtragem de resultados por preço, categoria ou disponibilidade.

## Estrutura do Projeto

O projeto está organizado nas seguintes camadas:

- **DAO (Data Access Object)**: Manipulação e gerenciamento de dados para persistência no banco de dados.
   - **ConexaoMySQL**: Implementação específica para o banco de dados MySQL.
   - **MercadoDAO e ProdutoDAO**: Realizam operações CRUD para as entidades `Mercado` e `Produto`.
- **Model**: Representação das entidades de negócio (como `Mercado` e `Produto`), com atributos e métodos.
- **Controllers**: Lógica de negócios e funcionalidades principais.
   - **CompararPrecoController**: Classe para comparar preços de produtos em mercados.
   - **ListaProdutosController**: Métodos para filtrar produtos conforme disponibilidade, preço e marca.
   - **Main**: Classe principal que inicia o sistema.

## Tecnologias Utilizadas

- **Java**: Desenvolvimento do backend.
- **MySQL**: Banco de dados relacional para armazenamento de informações de mercados e produtos.
- **Apache NetBeans**: IDE para o desenvolvimento e gerenciamento do projeto.
- **Git/GitHub**: Controle de versão e colaboração.

## Instalação e Configuração

### 1. Clone o Repositório

```bash
git clone https://github.com/seu-usuario/BuscAi.git
cd BuscAi
```
