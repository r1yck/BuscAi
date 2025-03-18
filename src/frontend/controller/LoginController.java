package frontend.controller;

import backend.dao.ConexaoMySQL;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField senhaField;

    // Método chamado no botão "Entrar"
    public void validarLogin(ActionEvent event) throws IOException {
        String login = loginField.getText();
        String senha = senhaField.getText();

        if (autenticarUsuario(login, senha)) {
            // Se autenticado, abre o MenuMercado
            switchToMenuMercado(event);
        } else {
            // Se falhar, exibe alerta
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro de Login");
            alerta.setHeaderText(null);
            alerta.setContentText("Usuário ou senha inválidos!");
            alerta.showAndWait();
        }
    }

    // Método que valida o login no banco de dados
    private boolean autenticarUsuario(String login, String senha) {
        String sql = "SELECT * FROM usuario WHERE login = ? AND senha = ?";
        try (Connection conexao = ConexaoMySQL.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.setString(2, senha);  // Caso esteja usando hash de senha, compare aqui o hash

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Usuário encontrado: " + rs.getString("login"));
                    return true; // Se encontrou o usuário, retorna true
                }
            }
        } catch (SQLException e) {
            // Log do erro
            e.printStackTrace();
            mostrarErro("Erro de conexão com o banco de dados: " + e.getMessage());
        }
        System.out.println("Usuário não encontrado");
        return false;
    }

    // Método para alternar para a tela de MenuMercado
    private void switchToMenuMercado(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("frontend/views/MenuMercado.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Método para voltar ao MenuView
    public void voltarMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("frontend/views/MenuView.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Exibe uma mensagem de erro no formato de Alert
    private void mostrarErro(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
