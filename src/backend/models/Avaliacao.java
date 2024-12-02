package backend.models;

public class Avaliacao {
    private int id;
    private int nota; // Nota da avaliação (por exemplo, de 1 a 5)
    private String comentario; // Comentário adicional sobre o produto/mercado
    private String autor; // Nome ou identificador do autor da avaliação

    // Construtor
    public Avaliacao(int id, int nota, String comentario, String autor) {
        this.id = id;
        setNota(nota); // Usando o setter para validar a nota
        setComentario(comentario); // Definindo o comentário
        setAutor(autor); // Definindo o autor
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        if (nota >= 1 && nota <= 5) {
            this.nota = nota;
        } else {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 5.");
        }
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        // Se o comentário for nulo ou vazio, define um valor padrão
        if (comentario == null || comentario.trim().isEmpty()) {
            this.comentario = "Sem comentários adicionais.";
        } else {
            this.comentario = comentario;
        }
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        if (autor == null || autor.trim().isEmpty()) {
            throw new IllegalArgumentException("O autor não pode ser vazio.");
        }
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Avaliacao{" +
                "id=" + id +
                ", nota=" + nota +
                ", comentario='" + comentario + '\'' +
                ", autor='" + autor + '\'' +
                '}';
    }
}
