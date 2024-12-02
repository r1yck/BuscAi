package backend.services;

import backend.models.Categoria;
import backend.models.Mercado;
import backend.models.Produto;

public class Main {
    public static void main(String[] args) {
        // Criando o aplicativo
        AplicativoBuscAi aplicativo = new AplicativoBuscAi("BuscAi", "1.0");

        // Iniciando o aplicativo
        aplicativo.iniciar();

        // Criando mercados
        Mercado mercado1 = new Mercado(1, "Mercado Central", "Rua A");
        Mercado mercado2 = new Mercado(2, "Supermercado XYZ", "Rua B");

        // Criando produtos e associando ao mercado
        Produto produto1 = new Produto(1, "Arroz", 20.0, true, Categoria.ALIMENTOS_NAO_PERECIVEIS, mercado1); // Mercado associado
        Produto produto2 = new Produto(2, "Feijão", 15.0, true, Categoria.ALIMENTOS_NAO_PERECIVEIS, mercado1); // Mercado associado
        Produto produto3 = new Produto(3, "Sabão", 5.0, false, Categoria.HIGIENE_PESSOAL, mercado2); // Mercado associado

        // Adicionando produtos aos mercados
        mercado1.adicionarProduto(produto1);
        mercado1.adicionarProduto(produto2);
        mercado2.adicionarProduto(produto3);

        // Adicionando mercados ao aplicativo
        aplicativo.adicionarMercado(mercado1);
        aplicativo.adicionarMercado(mercado2);

        // Listando produtos disponíveis em todos os mercados
        aplicativo.listarProdutosDisponiveis();

        // Aplicando filtro de disponibilidade
        aplicativo.aplicarFiltroDisponibilidade();

        // Aplicando filtro de preço máximo
        aplicativo.aplicarFiltroPrecoMaximo(18.0);

        // Comparando preços de um produto específico
        aplicativo.compararPrecos("Arroz");

        // Fechando o aplicativo
        aplicativo.fechar();
    }
}