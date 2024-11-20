package backend.models;

import java.util.List;

public class ComparadorDePrecos {

    // Encontra o mercado com o menor preço para um produto específico
    public static Mercado encontrarMelhorPreco(String produtoNome, List<Mercado> mercados) {
        Mercado mercadoComMenorPreco = null;
        double menorPreco = Double.MAX_VALUE;

        for (Mercado mercado : mercados) {
            for (Produto produto : mercado.listarProdutos()) {
                if (produto.getNome().equalsIgnoreCase(produtoNome) && produto.isDisponivel()) {
                    if (produto.getPreco() < menorPreco) {
                        menorPreco = produto.getPreco();
                        mercadoComMenorPreco = mercado;
                    }
                }
            }
        }
        return mercadoComMenorPreco;
    }

    // Compara e exibe preços de todos os produtos disponíveis por mercado
    public static String compararTodosPrecos(List<Mercado> mercados) {
        StringBuilder comparacao = new StringBuilder("Comparação de Preços:\n");

        for (Mercado mercado : mercados) {
            comparacao.append("Mercado: ").append(mercado.getNome()).append("\n");
            for (Produto produto : mercado.listarProdutos()) {
                if (produto.isDisponivel()) {
                    comparacao.append("Produto: ").append(produto.getNome())
                            .append(" - Preço: ").append(produto.getPreco())
                            .append(" - Categoria: ").append(produto.getCategoria())
                            .append("\n");
                }
            }
            comparacao.append("\n");
        }
        return comparacao.toString();
    }

    // Nova funcionalidade: Filtrar e encontrar o menor preço de produtos por categoria
    public static Mercado encontrarMelhorPrecoPorCategoria(Categoria categoria, List<Mercado> mercados) {
        Mercado mercadoComMenorPreco = null;
        double menorPreco = Double.MAX_VALUE;

        for (Mercado mercado : mercados) {
            for (Produto produto : mercado.listarProdutos()) {
                if (produto.getCategoria() == categoria && produto.isDisponivel()) {
                    if (produto.getPreco() < menorPreco) {
                        menorPreco = produto.getPreco();
                        mercadoComMenorPreco = mercado;
                    }
                }
            }
        }
        return mercadoComMenorPreco;
    }
}
