package frontend.controller;

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
import java.sql.DriverManager;
import java.util.List;
import java.util.ResourceBundle;

public class ListaMercadosController implements Initializable {

    @FXML
    private ListView<Mercado> listaMercados;

    private Stage stage;
    private Scene scene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarMercados();
    }

    private void carregarMercados() {
        try (Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/buscai", "root", "@Henri0202")) {
            MercadoDAO mercadoDAO = new MercadoDAO(conexao);
            List<Mercado> mercados = mercadoDAO.listarMercados();
            ObservableList<Mercado> mercadoObservableList = FXCollections.observableArrayList(mercados);
            listaMercados.setItems(mercadoObservableList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirProdutosMercado() {
        Mercado mercadoSelecionado = listaMercados.getSelectionModel().getSelectedItem();
        if (mercadoSelecionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/views/ListaProdutos.fxml"));
                Parent root = loader.load();
                
                // Passa o mercado selecionado para a tela de produtos
                ListaProdutosController controller = loader.getController();
                controller.setMercadoSelecionado(mercadoSelecionado);

                stage = (Stage) listaMercados.getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void voltaMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("frontend/views/MenuView.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
