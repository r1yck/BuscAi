package backend.models;

public class Produto {
    private int id;
    private String nome;
    private double preco;
    private boolean disponivel;
    private Categoria categoria;
    private Mercado mercado; // Atributo Mercado

    public Produto(int id, String nome, double preco, boolean disponivel, Categoria categoria, Mercado mercado) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.disponivel = disponivel;
        this.categoria = categoria;
        this.mercado = mercado; // Inicializando mercado
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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    // Novo método getMercado e setMercado
    public Mercado getMercado() {
        return mercado;
    }

    public void setMercado(Mercado mercado) {
        this.mercado = mercado;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                ", disponivel=" + disponivel +
                ", categoria=" + categoria.name() + // Exibe o nome legível da categoria
                ", mercado=" + (mercado != null ? mercado.getNome() : "Sem mercado") + // Exibe nome do mercado
                '}';
    }
}
