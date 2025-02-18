package backend.models;

public class Produto {

    private int id;
    private String nome;
    private float preco;
    private String categoria;
    private boolean disponibilidade;
    private int mercadoId; 

    public Produto(int id, String nome, float preco, String categoria, boolean disponibilidade, int mercadoId) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
        this.disponibilidade = disponibilidade;
        this.mercadoId = mercadoId;
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

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    // Getter e setter para o mercadoId
    public int getMercadoId() {
        return mercadoId;
    }

    public void setMercadoId(int mercadoId) {
        this.mercadoId = mercadoId;
    }

    @Override
    public String toString() {
        return "Produto{id=" + id + ", nome='" + nome + "', preco=" + preco + ", categoria='" + categoria + "', disponibilidade=" + disponibilidade + ", mercadoId=" + mercadoId + "}";
    }
}
