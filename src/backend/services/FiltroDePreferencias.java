package backend.services;

import backend.models.Produto;
import backend.models.Categoria;
import java.util.List;
import java.util.stream.Collectors;

public class FiltroDePreferencias {

    // Filtra produtos disponíveis
    public static List<Produto> filtrarPorDisponibilidade(List<Produto> produtos) {
        return produtos.stream()
                .filter(Produto::isDisponivel)
                .collect(Collectors.toList());
    }

    // Filtra produtos com preço abaixo de um valor máximo
    public static List<Produto> filtrarPorPrecoMaximo(List<Produto> produtos, double precoMaximo) {
        return produtos.stream()
                .filter(produto -> produto.getPreco() <= precoMaximo && produto.isDisponivel())
                .collect(Collectors.toList());
    }

    // Filtra produtos por nome da marca (case-insensitive e busca parcial)
    public static List<Produto> filtrarPorMarca(List<Produto> produtos, String marca) {
        return produtos.stream()
                .filter(produto -> produto.getNome().toLowerCase().contains(marca.toLowerCase()) && produto.isDisponivel())
                .collect(Collectors.toList());
    }

    // Filtra produtos por categoria
    public static List<Produto> filtrarPorCategoria(List<Produto> produtos, Categoria categoria) {
        return produtos.stream()
                .filter(produto -> produto.getCategoria() == categoria && produto.isDisponivel())
                .collect(Collectors.toList());
    }

    // Combina filtros: categoria e preço máximo
    public static List<Produto> filtrarPorCategoriaEPrecoMaximo(List<Produto> produtos, Categoria categoria, double precoMaximo) {
        return produtos.stream()
                .filter(produto -> produto.getCategoria() == categoria && produto.getPreco() <= precoMaximo && produto.isDisponivel())
                .collect(Collectors.toList());
    }
}
