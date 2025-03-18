package frontend.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuController {

    private Stage stage;
    private Scene scene;

    // Método para abrir o login como modal
    public void abrirLogin(ActionEvent event) throws IOException {
        // Corrigido o caminho para o FXML de login
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontend/views/Login.fxml"));
        Parent loginRoot = loader.load();

        // Criar um novo Stage para o modal de login
        Stage loginStage = new Stage();
        loginStage.setScene(new Scene(loginRoot));
        
        // Tornar o login um modal, sobrepondo a tela principal
        loginStage.initModality(Modality.APPLICATION_MODAL); // Faz com que o login seja um modal
        loginStage.initOwner(((Node) event.getSource()).getScene().getWindow()); // Relaciona com a tela de MenuView

        // Mostrar o modal de login
        loginStage.showAndWait(); // 'showAndWait' faz o login ser bloqueante, esperando o fechamento
    }

    // Método para abrir a tela de Lista de Mercados
    public void abrirListaMercados(ActionEvent event) throws IOException {
        // Esse método será usado para abrir a tela de Listar Mercados
        carregarCena(event, "frontend/views/ListaMercadosView.fxml");
    }

    // Método auxiliar para carregar cenas normalmente
    private void carregarCena(ActionEvent event, String caminhoFXML) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(caminhoFXML));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
