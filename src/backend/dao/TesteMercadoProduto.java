package backend.dao;

import backend.models.Mercado;
import backend.models.Produto;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TesteMercadoProduto {
    public static void main(String[] args) {
        try (Connection conexao = ConexaoMySQL.getConexao()) { 
            MercadoDAO mercadoDAO = new MercadoDAO(conexao);
            ProdutoDAO produtoDAO = new ProdutoDAO(conexao);

            // Teste - Adicionar Mercado
            Mercado mercado = new Mercado(0, "Supermercado Teste", "Rua Exemplo, 123");
            mercadoDAO.adicionarMercado(mercado);
            System.out.println("Mercado adicionado: " + mercado);

            // Garantir que o ID do mercado foi atualizado corretamente
            int mercadoId = mercado.getId(); 

            if (mercadoId == 0) {
                System.out.println("Erro ao obter o ID do mercado. O produto não pode ser adicionado.");
                return; // Evitar tentar adicionar o produto sem um ID válido
            }

            // Teste - Listar Mercados
            List<Mercado> mercados = mercadoDAO.listarMercados();
            System.out.println("Lista de mercados: " + mercados);

            // Teste - Atualizar Mercado
            mercadoDAO.atualizarMercado(mercadoId, "Supermercado Atualizado", "Avenida Nova, 456");
            System.out.println("Mercado atualizado: " + mercadoDAO.listarMercados());

            // Teste - Adicionar Produto (Agora com mercado_id)
            Produto produto = new Produto(0, "Arroz", 5.99f, "Alimentos", true, mercadoId);
            produtoDAO.adicionarProduto(produto);
            System.out.println("Produto adicionado: " + produto);

            // Garantir que o ID do produto foi atualizado corretamente
            int produtoId = produto.getId(); // O ID gerado pelo banco após a inserção

            if (produtoId == 0) {
                System.out.println("Erro ao obter o ID do produto. O produto não pode ser atualizado ou removido.");
                return; // Evitar tentar atualizar ou remover o produto sem um ID válido
            }

            // Teste - Listar Produtos
            List<Produto> produtos = produtoDAO.listarProdutos();
            System.out.println("Lista de produtos: " + produtos);

            // Teste - Atualizar Produto
            produtoDAO.atualizarProduto(produtoId, "Feijão", 6.99f, "Alimentos", true, mercadoId);
            System.out.println("Produto atualizado: " + produtoDAO.listarProdutos());

            // Teste - Remover Produto
            produtoDAO.removerProduto(produtoId);
            System.out.println("Produtos após remoção: " + produtoDAO.listarProdutos());

            // Teste - Remover Mercado
            mercadoDAO.removerMercado(mercadoId);
            System.out.println("Mercados após remoção: " + mercadoDAO.listarMercados());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
