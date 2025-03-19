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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class CompararPrecoController implements Initializable {

    @FXML
    private Label nomeProdutoLabel;
    @FXML
    private ListView<String> listaComparacaoView;

    private Produto produtoSelecionado;
    private ProdutoDAO produtoDAO;
    private MercadoDAO mercadoDAO;
    private Connection conexao;
    private Mercado mercadoSelecionado; // Armazena o mercado selecionado

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listaComparacaoView.setItems(FXCollections.observableArrayList());
    }

    private Connection garantirConexao() {
        try {
            if (this.conexao == null || this.conexao.isClosed()) {
                this.conexao = ConexaoMySQL.getConexao();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.conexao;
    }

    public void setProduto(String nomeProduto, Connection conexao, Mercado mercado) throws SQLException {
        this.conexao = conexao;
        this.produtoDAO = new ProdutoDAO(garantirConexao());
        this.mercadoDAO = new MercadoDAO(garantirConexao());
        this.produtoSelecionado = produtoDAO.buscarProdutoPorNome(nomeProduto);
        this.mercadoSelecionado = mercado; // Define o mercado selecionado

        nomeProdutoLabel.setText(produtoSelecionado != null ? produtoSelecionado.getNome() : "Produto não encontrado");
        listarPrecosNosMercados();
    }

    private void listarPrecosNosMercados() throws SQLException {
        if (produtoSelecionado != null) {
            List<Mercado> mercados = mercadoDAO.listarMercados();
            List<String> precos = new ArrayList<>();

            for (Mercado mercado : mercados) {
                List<Produto> produtos = produtoDAO.listarProdutosPorMercado(mercado.getId());

                for (Produto p : produtos) {
                    if (p.getNome().equals(produtoSelecionado.getNome())) {
                        String precoFormatado = String.format(Locale.US, "%.2f", p.getPreco());
                        String precoInfo = String.format("%s: R$%s - %s",
                                mercado.getNome(), precoFormatado, (p.isDisponibilidade() ? "Disponível" : "Indisponível"));
                        precos.add(precoInfo);
                    }
                }
            }

            precos.sort(Comparator.comparingDouble(s -> {
                try {
                    String[] partes = s.split("R\\$");
                    if (partes.length > 1) {  // Verifica se há uma parte após "R$"
                        return Double.parseDouble(partes[1].split(" - ")[0].replace(",", "."));
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                return Double.MAX_VALUE; // Fallback para evitar erro
            }));

            listaComparacaoView.setItems(FXCollections.observableArrayList(precos));
        }
    }

    @FXML
    private void voltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontend/views/ListaProdutos.fxml"));
            Parent root = loader.load();

            // Aqui você deve obter a referência do controlador de ListaProdutos
            ListaProdutosController listaProdutosController = loader.getController();
            
            // Passar o mercado selecionado
            if (mercadoSelecionado != null) {
                listaProdutosController.setMercadoSelecionado(mercadoSelecionado, this.conexao);
            }

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
