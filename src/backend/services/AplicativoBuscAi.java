package backend.services;

import backend.models.Categoria;
import backend.models.ComparadorDePrecos;
import backend.models.Mercado;
import backend.models.Produto;
import java.util.ArrayList;
import java.util.List;

public class AplicativoBuscAi {
    private String nome;
    private String versao;
    private List<Mercado> mercados; // Lista de mercados cadastrados no aplicativo

    public AplicativoBuscAi(String nome, String versao) {
        this.nome = nome;
        this.versao = versao;
        this.mercados = new ArrayList<>();
    }

    // Método para iniciar o aplicativo
    public void iniciar() {
        System.out.println("Iniciando " + nome + " versão " + versao);
    }

    // Método para fechar o aplicativo
    public void fechar() {
        System.out.println("Fechando " + nome);
    }

    // Adiciona um mercado ao aplicativo
    public void adicionarMercado(Mercado mercado) {
        mercados.add(mercado);
    }

    // Exibe os produtos de todos os mercados que estão disponíveis
    public void listarProdutosDisponiveis() {
        if (mercados.isEmpty()) {
            System.out.println("Nenhum mercado cadastrado.");
            return;
        }
        System.out.println("Produtos disponíveis em todos os mercados:");
        for (Mercado mercado : mercados) {
            System.out.println("Mercado: " + mercado.getNome());
            mercado.listarProdutos().stream()
                .filter(Produto::isDisponivel)  // Filtra os produtos disponíveis
                .forEach(produto -> System.out.println("Produto: " + produto));
            System.out.println();
        }
    }

    // Filtra produtos por categoria e disponibilidade e exibe os resultados
    public void aplicarFiltroPorCategoria(Categoria categoria) {
        if (mercados.isEmpty()) {
            System.out.println("Nenhum mercado cadastrado.");
            return;
        }
        System.out.println("Filtrando produtos da categoria: " + categoria);
        for (Mercado mercado : mercados) {
            List<Produto> produtosFiltrados = mercado.listarProdutosPorCategoria(categoria);
            if (!produtosFiltrados.isEmpty()) {
                System.out.println("Mercado: " + mercado.getNome());
                produtosFiltrados.forEach(produto -> System.out.println("Produto: " + produto));
            }
            System.out.println();
        }
    }

    // Filtra produtos por disponibilidade e exibe os resultados
    public void aplicarFiltroDisponibilidade() {
        if (mercados.isEmpty()) {
            System.out.println("Nenhum mercado cadastrado.");
            return;
        }
        System.out.println("Filtrando produtos disponíveis:");
        for (Mercado mercado : mercados) {
            List<Produto> produtosDisponiveis = FiltroDePreferencias.filtrarPorDisponibilidade(mercado.listarProdutos());
            System.out.println("Mercado: " + mercado.getNome() + " - Produtos disponíveis:");
            produtosDisponiveis.forEach(produto -> System.out.println("Produto: " + produto));
            System.out.println();
        }
    }

    // Filtra produtos com preço abaixo de um valor máximo e exibe os resultados
    public void aplicarFiltroPrecoMaximo(double precoMaximo) {
        if (mercados.isEmpty()) {
            System.out.println("Nenhum mercado cadastrado.");
            return;
        }
        System.out.println("Filtrando produtos com preço máximo de R$" + precoMaximo + ":");
        for (Mercado mercado : mercados) {
            List<Produto> produtosFiltrados = FiltroDePreferencias.filtrarPorPrecoMaximo(mercado.listarProdutos(), precoMaximo);
            System.out.println("Mercado: " + mercado.getNome() + " - Produtos abaixo de R$" + precoMaximo + ":");
            produtosFiltrados.forEach(produto -> System.out.println("Produto: " + produto));
            System.out.println();
        }
    }

    // Compara preços de um produto específico entre os mercados
    public void compararPrecos(String nomeProduto) {
        if (mercados.isEmpty()) {
            System.out.println("Nenhum mercado cadastrado.");
            return;
        }
        Mercado melhorMercado = ComparadorDePrecos.encontrarMelhorPreco(nomeProduto, mercados);
        if (melhorMercado != null) {
            System.out.println("O menor preço para '" + nomeProduto + "' é no mercado " + melhorMercado.getNome());
        } else {
            System.out.println("Produto '" + nomeProduto + "' não encontrado nos mercados disponíveis.");
        }
    }

    @Override
    public String toString() {
        return "AplicativoBuscAi{" +
                "nome='" + nome + '\'' +
                ", versao='" + versao + '\'' +
                ", mercados=" + mercados +
                '}';
    }
}