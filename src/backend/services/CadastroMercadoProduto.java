package backend.services;

import backend.dao.MercadoDAO;
import backend.dao.ProdutoDAO;
import backend.models.Mercado;
import backend.models.Produto;
import backend.models.Categoria;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CadastroMercadoProduto {

    private final MercadoDAO mercadoDAO;
    private final ProdutoDAO produtoDAO;

    public CadastroMercadoProduto(MercadoDAO mercadoDAO, ProdutoDAO produtoDAO) {
        this.mercadoDAO = mercadoDAO;
        this.produtoDAO = produtoDAO;
    }

    // Método para cadastrar um novo mercado
    public void cadastrarMercado() {
        Scanner scanner = new Scanner(System.in);

        // Verificação de login
        System.out.print("Nome de usuário: ");
        String usuario = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        if (LoginService.autenticar(usuario, senha)) {
            System.out.println("Autenticação bem-sucedida!");

            // Dados do mercado
            System.out.print("Nome do mercado: ");
            String nome = scanner.nextLine();
            System.out.print("Localização do mercado: ");
            String localizacao = scanner.nextLine();

            // Criando e salvando o mercado
            try {
                Mercado mercado = new Mercado(0, nome, localizacao);
                mercadoDAO.salvar(mercado);
                System.out.println("Mercado cadastrado com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao cadastrar mercado: " + e.getMessage());
            }
        } else {
            System.out.println("Autenticação falhou. Usuário ou senha incorretos.");
        }
    }

    // Método para cadastrar um novo produto
    public void cadastrarProduto() {
        Scanner scanner = new Scanner(System.in);

        // Verificação de login
        System.out.print("Nome de usuário: ");
        String usuario = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        if (LoginService.autenticar(usuario, senha)) {
            System.out.println("Autenticação bem-sucedida!");

            // Dados do produto
            System.out.print("Nome do produto: ");
            String nome = scanner.nextLine();
            System.out.print("Preço do produto: ");
            double preco = scanner.nextDouble();
            System.out.print("Disponível (true/false): ");
            boolean disponivel = scanner.nextBoolean();
            scanner.nextLine(); // Consumir a quebra de linha
            System.out.print("Categoria do produto (HIGIENE_PESSOAL, ALIMENTOS_NAO_PERECIVEIS, etc.): ");
            String categoriaStr = scanner.nextLine();
            Categoria categoria = Categoria.valueOf(categoriaStr);

            // Selecionando o mercado (caso tenha vários mercados, você pode adicionar uma lógica para listar)
            System.out.println("Escolha um mercado para o produto: ");
            try {
                List<Mercado> mercados = mercadoDAO.buscarTodos();
                for (int i = 0; i < mercados.size(); i++) {
                    System.out.println((i + 1) + ": " + mercados.get(i).getNome());
                }
                int opcao = scanner.nextInt();
                scanner.nextLine(); // Consumir a quebra de linha
                Mercado mercadoEscolhido = mercados.get(opcao - 1); // Seleciona o mercado com base na escolha

                // Criando e salvando o produto
                try {
                    Produto produto = new Produto(0, nome, preco, disponivel, categoria, mercadoEscolhido);
                    produtoDAO.salvar(produto);
                    System.out.println("Produto cadastrado com sucesso!");
                } catch (SQLException e) {
                    System.out.println("Erro ao cadastrar produto: " + e.getMessage());
                }
            } catch (SQLException e) {
                System.out.println("Erro ao buscar mercados: " + e.getMessage());
            }
        } else {
            System.out.println("Autenticação falhou. Usuário ou senha incorretos.");
        }
    }

    // Método para editar produto
    public void editarProduto() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nome de usuário: ");
        String usuario = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        if (LoginService.autenticar(usuario, senha)) {
            System.out.println("Autenticação bem-sucedida!");

            try {
                List<Produto> produtos = produtoDAO.buscarTodos();
                for (int i = 0; i < produtos.size(); i++) {
                    System.out.println((i + 1) + ": " + produtos.get(i).getNome());
                }

                System.out.print("Escolha o produto a ser editado: ");
                int opcao = scanner.nextInt();
                scanner.nextLine();

                Produto produto = produtos.get(opcao - 1);

                // Atualizando informações
                System.out.print("Novo nome do produto: ");
                produto.setNome(scanner.nextLine());
                System.out.print("Novo preço: ");
                produto.setPreco(scanner.nextDouble());
                System.out.print("Disponível (true/false): ");
                produto.setDisponivel(scanner.nextBoolean());
                scanner.nextLine(); // Consumir quebra de linha

                produtoDAO.atualizar(produto);
                System.out.println("Produto atualizado com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao editar produto: " + e.getMessage());
            }
        } else {
            System.out.println("Autenticação falhou.");
        }
    }

    // Método para excluir produto
    public void excluirProduto() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nome de usuário: ");
        String usuario = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        if (LoginService.autenticar(usuario, senha)) {
            System.out.println("Autenticação bem-sucedida!");

            try {
                List<Produto> produtos = produtoDAO.buscarTodos();
                for (int i = 0; i < produtos.size(); i++) {
                    System.out.println((i + 1) + ": " + produtos.get(i).getNome());
                }

                System.out.print("Escolha o produto a ser excluído: ");
                int opcao = scanner.nextInt();
                scanner.nextLine();

                Produto produto = produtos.get(opcao - 1);

                produtoDAO.deletar(produto.getId());
                System.out.println("Produto excluído com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao excluir produto: " + e.getMessage());
            }
        } else {
            System.out.println("Autenticação falhou.");
        }
    }

}
