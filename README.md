# BuscAi

**BuscAi** é uma plataforma digital para busca e comparação de preços de produtos em diferentes supermercados, projetada para ajudar os usuários a encontrar as melhores ofertas de acordo com suas preferências e localização. A aplicação facilita o acesso a informações de preços e disponibilidade de produtos, promovendo uma experiência prática e intuitiva.

## Funcionalidades

1. **Cadastro de Mercados e Produtos**
   - Mercados podem cadastrar produtos com detalhes de preço, categoria e disponibilidade.
   - Usuários podem consultar produtos específicos e comparar seus preços em diferentes mercados.

2. **Comparação de Preços**
   - Sistema de comparação de preços, permitindo ao usuário visualizar as melhores ofertas de acordo com critérios definidos.

3. **Filtro de Preferências**
   - Filtragem de resultados por preço, avaliações, marcas ou disponibilidade.

## Estrutura do Projeto

O projeto está organizado nas seguintes camadas:

- **DAO (Data Access Object)**: Manipulação e gerenciamento de dados para persistência no banco de dados.
- **Model**: Representação das entidades de negócio (como `Mercado` e `Produto`) e seus atributos e métodos.
- **Services**: Lógica de negócios e funcionalidades principais (como comparações e filtros de preferências).

## Tecnologias Utilizadas

- **Java**: Desenvolvimento back-end.
- **MySQL**: Banco de dados relacional para armazenamento de informações de mercados e produtos.
- **Apache NetBeans**: IDE para o desenvolvimento e gerenciamento do projeto.
- **Git/GitHub**: Controle de versão e colaboração.

## Instalação e Configuração

1. **Clone o Repositório**:
   ```bash
   git clone https://github.com/seu-usuario/BuscAi.git
   cd BuscAi
