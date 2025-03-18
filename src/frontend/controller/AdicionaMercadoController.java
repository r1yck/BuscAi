package frontend.controller;

import backend.dao.ConexaoMySQL;
import backend.dao.MercadoDAO;
import backend.models.Mercado;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class AdicionaMercadoController {
    @FXML
    private TextField nomeMercadoField;
    @FXML
    private TextField localizacaoMercadoField;

    private MercadoDAO mercadoDAO;
    private int mercadoId; // Variável para armazenar o ID do mercado

    public AdicionaMercadoController() {
        try {
            Connection conexao = ConexaoMySQL.getConexao();
            mercadoDAO = new MercadoDAO(conexao);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
public void adicionarMercado(ActionEvent event) throws IOException {
    String nome = nomeMercadoField.getText();
    String localizacao = localizacaoMercadoField.getText();

    if (nome.isEmpty() || localizacao.isEmpty()) {
        System.out.println("Por favor, preencha todos os campos.");
        return;
    }

    // Adiciona o mercado ao banco
    Mercado mercado = new Mercado(0, nome, localizacao);
    mercadoDAO.adicionarMercado(mercado);

    // Obtém o ID do mercado recém-cadastrado
    this.mercadoId = mercadoDAO.getUltimoMercadoId();
    System.out.println("Último Mercado ID: " + mercadoId);

    // Exibe o Alert informando que o mercado foi adicionado
    javafx.application.Platform.runLater(() -> {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Mercado Adicionado");
        alert.setHeaderText(null);
        alert.setContentText("O mercado foi adicionado com sucesso!");
        alert.showAndWait();  // Exibe o alert e espera o usuário fechar

        try {
            // Após o Alert ser fechado, navega para a tela de adicionar produto
            vaPraAdicionarProduto(event);
        } catch (IOException e) {
            e.printStackTrace(); // Tratar exceção caso aconteça
        }
    });
}


    @FXML
    public void vaPraAdicionarProduto(ActionEvent event) throws IOException {
        // Carrega o FXML corretamente com o FXMLLoader
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/views/AdicionarProduto.fxml"));
        Parent root = loader.load();  // Carrega o FXML

        AdicionarProdutoController adicionarProdutoController = loader.getController();
        
        // Passa o ID do mercado para o controlador da próxima tela
        adicionarProdutoController.setMercadoId(mercadoId);

        // Muda a tela para a de adicionar produto
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void voltar(ActionEvent event) throws IOException {
        // Carrega a tela anterior (MenuMercado.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/views/AdicionarMercado.fxml"));
        Parent root = loader.load();

        // Muda a cena para a tela anterior
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
