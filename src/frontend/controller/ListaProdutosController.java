package frontend.controller;

import backend.dao.ProdutoDAO;
import backend.models.Mercado;
import backend.models.Produto;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.application.Platform;
import java.io.IOException;

/**
 * FXML Controller class para exibir os produtos do mercado selecionado.
 */
public class ListaProdutosController implements Initializable {

    @FXML
    private Label nomeMercadoLabel;

    @FXML
    private ListView<String> listaProdutosView;  // ListView para exibir os produtos

    private Mercado mercadoSelecionado;
    private ProdutoDAO produtoDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialização pode ser feita aqui, se necessário
    }

    public void setMercadoSelecionado(Mercado mercado, Connection conexao) {
        if (mercado != null) {
            this.mercadoSelecionado = mercado;
            this.produtoDAO = new ProdutoDAO(conexao); // Inicializa o ProdutoDAO com a conexão
            atualizarInterface();
        }
    }

    private void atualizarInterface() {
        if (mercadoSelecionado != null) {
            nomeMercadoLabel.setText(mercadoSelecionado.getNome());
            
            // Obter os produtos do mercado selecionado e exibi-los na ListView
            List<Produto> produtos = produtoDAO.listarProdutosPorMercado(mercadoSelecionado.getId());
            if (produtos != null) {
                ObservableList<String> produtosNomes = FXCollections.observableArrayList();
                for (Produto p : produtos) {
                    produtosNomes.add(p.getNome() + " - R$" + p.getPreco());
                }
                listaProdutosView.setItems(produtosNomes);
            }
        }
    }

    @FXML
    public void voltar(ActionEvent event) throws IOException {
        // Carrega a tela anterior (ListaMercados.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontend/views/ListaMercadosView.fxml"));
        Parent root = loader.load();

        // Muda a cena para a tela anterior
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
