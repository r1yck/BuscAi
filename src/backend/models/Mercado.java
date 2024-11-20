package backend.models;

import java.util.ArrayList;
import java.util.List;

public class Mercado {

    private int id;
    private String nome;
    private String localizacao;
    private List<Produto> produtos; // Lista de produtos disponíveis no mercado

    public Mercado(int id, String nome, String localizacao) {
        this.id = id;
        this.nome = nome;
        this.localizacao = localizacao;
        this.produtos = new ArrayList<>();
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    // Adicionar um produto ao mercado
    public void adicionarProduto(Produto produto) {
        if (!produtos.contains(produto)) {
            produtos.add(produto);
        }
    }

    // Remover um produto do mercado
    public void removerProduto(Produto produto) {
        produtos.remove(produto);
    }

    // Listar todos os produtos disponíveis no mercado
    public List<Produto> listarProdutos() {
        return new ArrayList<>(produtos);
    }

    // Método para listar produtos por categoria
    public List<Produto> listarProdutosPorCategoria(Categoria categoria) {
        List<Produto> produtosPorCategoria = new ArrayList<>();
        for (Produto produto : produtos) {
            if (produto.getCategoria() == categoria) {
                produtosPorCategoria.add(produto);
            }
        }
        return produtosPorCategoria;
    }

    @Override
    public String toString() {
        return "Mercado{"
                + "id=" + id
                + ", nome='" + nome + '\''
                + ", localizacao='" + localizacao + '\''
                + ", produtos=" + produtos
                + '}';
    }
}
