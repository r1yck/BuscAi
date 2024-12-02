package backend.models;

import java.util.List;

public class ComparadorDePrecos {

    // Encontra o mercado com o menor preço para um produto específico
    public static Mercado encontrarMelhorPreco(String produtoNome, List<Mercado> mercados) {
        return encontrarMelhorPrecoPorProduto(produtoNome, null, mercados);
    }

    // Encontra o mercado com o menor preço por categoria
    public static Mercado encontrarMelhorPrecoPorCategoria(Categoria categoria, List<Mercado> mercados) {
        return encontrarMelhorPrecoPorProduto(null, categoria, mercados);
    }

    // Método privado que centraliza a lógica de encontrar o melhor preço por produto ou categoria
    private static Mercado encontrarMelhorPrecoPorProduto(String produtoNome, Categoria categoria, List<Mercado> mercados) {
        Mercado mercadoComMenorPreco = null;
        double menorPreco = Double.MAX_VALUE;

        for (Mercado mercado : mercados) {
            for (Produto produto : mercado.listarProdutos()) {
                boolean produtoValido = (produtoNome == null || produto.getNome().equalsIgnoreCase(produtoNome)) &&
                                        (categoria == null || produto.getCategoria().equals(categoria)) && // Usando equals para comparar categorias
                                        produto.isDisponivel(); // Verificação de disponibilidade

                if (produtoValido && produto.getPreco() < menorPreco) {
                    menorPreco = produto.getPreco();
                    mercadoComMenorPreco = mercado;
                }
            }
        }
        return mercadoComMenorPreco;
    }
}
