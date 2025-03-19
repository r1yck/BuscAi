package frontend.controller;

import backend.dao.ConexaoMySQL;
import backend.dao.ProdutoDAO;
import backend.models.Produto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.application.Platform;

public class AdicionarProdutoController {
    @FXML
    private TextField nomeProdutoField;
    @FXML
    private TextField precoProdutoField;
    @FXML
    private CheckBox disponibilidadeProdutoCheckBox;
    @FXML
    private ChoiceBox<String> categoriaProdutoChoiceBox;

    private ProdutoDAO produtoDAO;
    private int mercadoId; // Mercado ID já passado da tela anterior

    public void setMercadoId(int id) {
        this.mercadoId = id; // Recebe o ID do mercado da tela anterior
    }

    @FXML
    public void initialize() {
        // Preenche a ChoiceBox com as categorias
        categoriaProdutoChoiceBox.setItems(FXCollections.observableArrayList(
            "Hortifrúti", "Açougue", "Frios e Laticínios", "Padaria", "Mercearia", 
            "Bebidas", "Congelados", "Matinais", "Alimentos para animais", 
            "Higiene pessoal", "Limpeza", "Utilidades domésticas", "Papelaria"
        ));
    }

    @FXML
    public void adicionarProduto(ActionEvent event) {
        String nomeProduto = nomeProdutoField.getText();
        String precoTexto = precoProdutoField.getText();
        String categoria = categoriaProdutoChoiceBox.getValue();
        boolean disponibilidade = disponibilidadeProdutoCheckBox.isSelected();

        if (nomeProduto.isEmpty() || precoTexto.isEmpty() || categoria == null) {
            System.out.println("Preencha todos os campos corretamente.");
            return;
        }

        try {
            float preco = Float.parseFloat(precoTexto);
            // Agora criamos o produto passando todos os parâmetros necessários
            Produto produto = new Produto(0, nomeProduto, preco, categoria, disponibilidade, mercadoId);
            Connection conexao = ConexaoMySQL.getConexao();
            produtoDAO = new ProdutoDAO(conexao);
            produtoDAO.adicionarProduto(produto);

            // Exibe o Alert informando que o produto foi adicionado
            Platform.runLater(() -> {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Produto Adicionado");
                alert.setHeaderText(null);
                alert.setContentText("O produto foi adicionado com sucesso!");
                alert.showAndWait();  // Exibe o alert e espera o usuário fechar

                // Limpar os campos
                nomeProdutoField.clear();
                precoProdutoField.clear();
                disponibilidadeProdutoCheckBox.setSelected(false);
                categoriaProdutoChoiceBox.getSelectionModel().clearSelection();
            });

        } catch (NumberFormatException e) {
            System.out.println("Erro: O preço deve ser um número válido.");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar produto: " + e.getMessage());
        }
    }

    @FXML
    public void finalizarCadastro(ActionEvent event) {
        // Lógica para cancelar o cadastro
        System.out.println("Cadastro finalizado.");

        // Exibe um alerta antes de finalizar o cadastro
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Finalizar Cadastro");
            alert.setHeaderText(null);
            alert.setContentText("Deseja finalizar o cadastro?");

            alert.showAndWait().ifPresent(response -> {
                if (response == javafx.scene.control.ButtonType.OK) {
                    // Fechar a tela atual
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();
                }
            });
        });
    }

    @FXML
    public void voltar(ActionEvent event) throws IOException {
        // Carrega a tela anterior (AdicionarMercado.fxml ou MenuMercado.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/views/AdicionarMercado.fxml")); // Ajuste o caminho, se necessário
        Parent root = loader.load();

        // Muda a cena para a tela anterior
        Platform.runLater(() -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });
    }
}
