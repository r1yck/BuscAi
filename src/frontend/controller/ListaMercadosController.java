package frontend.controller;

import backend.dao.ConexaoMySQL;
import backend.dao.MercadoDAO;
import backend.models.Mercado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ListaMercadosController implements Initializable {

    @FXML
    private ListView<Mercado> listaMercados;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarMercados();
    }

    private void carregarMercados() {
        try (Connection conexao = ConexaoMySQL.getConexao()) {
            MercadoDAO mercadoDAO = new MercadoDAO(conexao);
            List<Mercado> mercados = mercadoDAO.listarMercados();
            ObservableList<Mercado> mercadoObservableList = FXCollections.observableArrayList(mercados);
            listaMercados.setItems(mercadoObservableList);
        } catch (SQLException e) {
            System.err.println("Erro ao carregar mercados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirProdutosMercado() {
        Mercado mercadoSelecionado = listaMercados.getSelectionModel().getSelectedItem();
        if (mercadoSelecionado != null) {
            try {
                // Carrega a nova cena para produtos do mercado
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontend/views/ListaProdutos.fxml"));
                Parent root = loader.load();

                // Obtém o controller e passa o mercado selecionado
                ListaProdutosController controller = loader.getController();
                try (Connection conexao = ConexaoMySQL.getConexao()) {
                    controller.setMercadoSelecionado(mercadoSelecionado, conexao);
                } catch (SQLException e) {
                    System.err.println("Erro ao obter conexão: " + e.getMessage());
                    e.printStackTrace();
                }

                // Atualiza a cena da janela existente
                Stage stage = (Stage) listaMercados.getScene().getWindow(); // Obtém a janela atual
                stage.setScene(new Scene(root));
                stage.setTitle("Produtos do Mercado"); // Atualiza o título da janela
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void voltaMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/frontend/views/MenuView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
