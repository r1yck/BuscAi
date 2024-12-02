package backend.services;

import backend.models.Produto;
import backend.models.Categoria;
import java.util.List;
import java.util.stream.Collectors;

public class FiltroDePreferencias {

    // Filtra produtos com preço abaixo de um valor máximo
    public static List<Produto> filtrarPorPrecoMaximo(List<Produto> produtos, double precoMaximo) {
        return produtos.stream()
                .filter(produto -> produto.getPreco() <= precoMaximo && produto.isDisponivel()) // Verificação de disponibilidade
                .collect(Collectors.toList());
    }

    // Combina filtros: preço máximo e categoria
    public static List<Produto> filtrarPorPrecoMaximoECategoria(List<Produto> produtos, double precoMaximo, Categoria categoria) {
        return produtos.stream()
                .filter(produto -> produto.getPreco() <= precoMaximo &&
                                   produto.getCategoria().equals(categoria) &&  // Usando equals() para comparar categorias
                                   produto.isDisponivel()) // Verificação de disponibilidade
                .collect(Collectors.toList());
    }

    // Filtra produtos por disponibilidade
    public static List<Produto> filtrarPorDisponibilidade(List<Produto> produtos) {
        return produtos.stream()
                .filter(Produto::isDisponivel) // Filtra apenas os produtos disponíveis
                .collect(Collectors.toList());
    }
}
