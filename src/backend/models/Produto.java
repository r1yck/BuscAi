package backend.models;

public class Produto {
    private int id;
    private String nome;
    private double preco;
    private boolean disponivel;
    private Categoria categoria; 

    public Produto(int id, String nome, double preco, boolean disponivel, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.disponivel = disponivel;
        this.categoria = categoria;
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

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                ", disponivel=" + disponivel +
                ", categoria=" + categoria +
                '}';
    }
}
