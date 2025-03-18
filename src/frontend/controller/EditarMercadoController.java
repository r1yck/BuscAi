package frontend.controller;

import backend.dao.ConexaoMySQL;
import backend.dao.MercadoDAO;
import backend.models.Mercado;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class EditarMercadoController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    // Campos da tela de edição
    public TextField nomeField;
    public TextField localizacaoField;

    private Mercado mercado;  // O mercado que será editado

    // Método para receber o mercado selecionado e preencher os campos
    public void setMercado(Mercado mercado) {
        this.mercado = mercado;
        nomeField.setText(mercado.getNome());
        localizacaoField.setText(mercado.getLocalizacao());
    }

    public void vaPraCadastrarMercado(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("frontend/views/AdicionaMercado.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Método para atualizar o mercado no banco de dados
    // Método para atualizar o mercado no banco de dados
    public void salvarEdicoes(ActionEvent event) throws IOException, SQLException {
        // Atualize o mercado com os novos valores dos campos
        mercado.setNome(nomeField.getText());
        mercado.setLocalizacao(localizacaoField.getText());

        try {
            // Obtenha a conexão e passe para o DAO
            Connection conexao = ConexaoMySQL.getConexao();
            MercadoDAO mercadoDAO = new MercadoDAO(conexao);

            // Alteração: passando o objeto mercado em vez dos três parâmetros separados
            mercadoDAO.atualizarMercado(mercado);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Agora, ao salvar, redireciona para a tela de edição de produtos do mercado
        vaParaEditarProdutos(event);
    }

    public void vaParaEditarProdutos(ActionEvent event) throws IOException {
        // Carregar a tela de edição de produtos
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("frontend/views/EditarProdutos.fxml"));
        Parent root = loader.load();

        // Passar o objeto mercado para a tela de edição de produtos
        EditarProdutoController controller = loader.getController();
        controller.setMercado(mercado);

        // Mudar para a nova cena
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
