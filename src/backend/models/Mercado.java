package backend.models;

import java.util.ArrayList;
import java.util.List;

public class Mercado {

    private int id;
    private String nome;
    private String localizacao;
    private List<Produto> produtos; // Lista de produtos do mercado

    public Mercado(int id, String nome, String localizacao) {
        this.id = id;
        this.nome = nome;
        this.localizacao = localizacao;
        this.produtos = new ArrayList<>(); // Inicializa a lista para evitar NullPointerException
    }

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

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void adicionarProduto(Produto produto) {
        this.produtos.add(produto);
    }

    public void removerProduto(Produto produto) {
        this.produtos.remove(produto);
    }

    @Override
    public String toString() {
        return nome + " - " + localizacao;
    }
}
