package backend.services;

import backend.models.Produto;
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

    // Filtra produtos por nome da marca (parcial ou exato, case-insensitive)
    public static List<Produto> filtrarPorMarca(List<Produto> produtos, String marca) {
        return produtos.stream()
                .filter(produto -> produto.getNome().equalsIgnoreCase(marca) && produto.isDisponivel())
                .collect(Collectors.toList());
    }
}
