package backend.services;

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
        // Lógica adicional para iniciar o aplicativo
    }

    // Método para fechar o aplicativo
    public void fechar() {
        System.out.println("Fechando " + nome);
        // Lógica adicional para fechar o aplicativo
    }

    // Adiciona um mercado ao aplicativo
    public void adicionarMercado(Mercado mercado) {
        mercados.add(mercado);
    }

    // Exibe os produtos de todos os mercados
    public void listarProdutosDisponiveis() {
        System.out.println("Produtos disponíveis em todos os mercados:");
        for (Mercado mercado : mercados) {
            System.out.println("Mercado: " + mercado.getNome());
            for (Produto produto : mercado.listarProdutos()) {
                if (produto.isDisponivel()) {
                    System.out.println("Produto: " + produto);
                }
            }
            System.out.println();
        }
    }

    // Filtra produtos por disponibilidade e exibe os resultados
    public void aplicarFiltroDisponibilidade() {
        System.out.println("Filtrando produtos disponíveis:");
        for (Mercado mercado : mercados) {
            List<Produto> produtosDisponiveis = FiltroDePreferencias.filtrarPorDisponibilidade(mercado.listarProdutos());
            System.out.println("Mercado: " + mercado.getNome() + " - Produtos disponíveis:");
            produtosDisponiveis.forEach(System.out::println);
            System.out.println();
        }
    }

    // Compara preços de um produto específico entre os mercados
    public void compararPrecos(String nomeProduto) {
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