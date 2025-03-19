package frontend.controller;

import backend.dao.ConexaoMySQL;
import backend.dao.MercadoDAO;
import backend.dao.ProdutoDAO;
import backend.models.Produto;
import backend.models.Mercado;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class CompararPrecoController implements Initializable {

    @FXML
    private Label nomeProdutoLabel; // Label para exibir o nome do produto
    @FXML
    private ListView<String> listaComparacaoView; // ListView para exibir os preços em diferentes mercados

    private Produto produtoSelecionado; // Produto que será comparado
    private ProdutoDAO produtoDAO; // DAO para produtos
    private MercadoDAO mercadoDAO; // DAO para mercados
    private Connection conexao; // Conexão com o banco de dados

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializa a lista de comparação
        listaComparacaoView.setItems(FXCollections.observableArrayList());
    }

    // Método para garantir a conexão
    private Connection garantirConexao() {
        try {
            if (this.conexao == null || this.conexao.isClosed()) {
                this.conexao = ConexaoMySQL.getConexao(); // Método para obter uma nova conexão
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.conexao;
    }

    // Método para definir o produto e a conexão
    public void setProduto(String nomeProduto, Connection conexao) throws SQLException {
        this.conexao = conexao; // Define a conexão
        this.produtoDAO = new ProdutoDAO(garantirConexao()); // Usa a conexão garantida
        this.mercadoDAO = new MercadoDAO(garantirConexao());
        
        // Obtém o produto a partir do nome
        List<Produto> produtos = produtoDAO.listarProdutosPorMercado(0); // Passa um ID de mercado que faz sentido ou um método para buscar pelo nome
        for (Produto p : produtos) {
            if (p.getNome().equals(nomeProduto)) {
                this.produtoSelecionado = p; // Define o produto selecionado
                break;
            }
        }

        // Atualiza a interface com as informações do produto
        nomeProdutoLabel.setText(produtoSelecionado != null ? produtoSelecionado.getNome() : "Produto não encontrado");
        listarPrecosNosMercados(); // Lista os preços em todos os mercados
    }

    private void listarPrecosNosMercados() throws SQLException {
        if (produtoSelecionado != null) {
            List<Mercado> mercados = mercadoDAO.listarMercados(); // Obtém a lista de mercados
            ObservableList<String> precos = FXCollections.observableArrayList();

            for (Mercado mercado : mercados) {
                // Obtém o produto associado ao mercado
                List<Produto> produtos = produtoDAO.listarProdutosPorMercado(mercado.getId());
                for (Produto p : produtos) {
                    if (p.getNome().equals(produtoSelecionado.getNome())) {
                        // Adiciona o preço do produto ao resultado da comparação
                        precos.add(mercado.getNome() + ": R$" + p.getPreco() + " - " + (p.isDisponibilidade() ? "Disponível" : "Indisponível"));
                    }
                }
            }

            // Atualiza a ListView com os preços encontrados
            listaComparacaoView.setItems(precos);
        }
    }

    @FXML
    public void voltar(ActionEvent event) throws IOException {
        // Carrega a tela anterior (ListaProdutos.fxml ou outra tela relevante)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontend/views/ListaProdutos.fxml"));
        Parent root = loader.load();

        // Muda a cena para a tela anterior
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Lista de Produtos");
        stage.show();
    }
}
