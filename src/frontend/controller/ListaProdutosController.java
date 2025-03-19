package frontend.controller;

import backend.dao.ConexaoMySQL;
import backend.dao.ProdutoDAO;
import backend.models.Mercado;
import backend.models.Produto;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ListaProdutosController implements Initializable {

    @FXML
    private Label nomeMercadoLabel;

    @FXML
    private ListView<String> listaProdutosView;  // ListView para exibir os produtos

    @FXML
    private CheckBox disponibilidadeCheckBox; // CheckBox para disponibilidade
    @FXML
    private ChoiceBox<String> categoriaChoiceBox; // ChoiceBox para categoria
    @FXML
    private TextField faixaPrecoField; // TextField para faixa de preço

    private Mercado mercadoSelecionado;
    private ProdutoDAO produtoDAO;
    private Connection conexao; // Armazena a conexão para ser reutilizada

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Preenche a ChoiceBox com as categorias
        categoriaChoiceBox.getItems().addAll(
                "Todos", "Hortifrúti", "Grãos", "Frutas",
                "Laticínios", "Padaria", "Bebidas",
                "Limpeza", "Carnes"
        );
        categoriaChoiceBox.setValue("Todos"); // Valor padrão

        // Adiciona listeners para aplicar filtros automaticamente
        disponibilidadeCheckBox.setOnAction(e -> listarProdutos());
        categoriaChoiceBox.setOnAction(e -> listarProdutos());
        faixaPrecoField.textProperty().addListener((observable, oldValue, newValue) -> listarProdutos());
    }

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

    public void setMercadoSelecionado(Mercado mercado, Connection conexao) {
        if (mercado != null && conexao != null) {
            this.mercadoSelecionado = mercado;
            this.conexao = conexao;
            this.produtoDAO = new ProdutoDAO(garantirConexao()); // Usa uma conexão garantida
            try {
                atualizarInterface();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void atualizarInterface() throws SQLException {
        if (mercadoSelecionado != null) {
            nomeMercadoLabel.setText(mercadoSelecionado.getNome());
            listarProdutos(); // Lista os produtos inicialmente
        }
    }

    private void listarProdutos() {
        if (mercadoSelecionado != null) {
            try {
                this.conexao = garantirConexao(); // Garante que a conexão está ativa
                this.produtoDAO = new ProdutoDAO(this.conexao); // Usa a conexão válida

                List<Produto> produtos = produtoDAO.listarProdutosPorMercado(mercadoSelecionado.getId());
                ObservableList<String> produtosNomes = FXCollections.observableArrayList();

                for (Produto p : produtos) {
                    boolean disponibilidade = disponibilidadeCheckBox.isSelected() ? p.isDisponibilidade() : true;
                    boolean categoriaFiltro = categoriaChoiceBox.getValue().equals("Todos") || p.getCategoria().equals(categoriaChoiceBox.getValue());
                    boolean faixaPrecoFiltro = true;

                    String precoTexto = faixaPrecoField.getText();
                    if (!precoTexto.isEmpty()) {
                        try {
                            float faixaPreco = Float.parseFloat(precoTexto);
                            faixaPrecoFiltro = p.getPreco() <= faixaPreco;
                        } catch (NumberFormatException e) {
                            System.out.println("Valor inválido para faixa de preço. Ignorando filtro.");
                        }
                    }

                    if (disponibilidade && categoriaFiltro && faixaPrecoFiltro) {
                        produtosNomes.add(p.getNome() + " - R$" + p.getPreco() + " - " + p.getCategoria() + " - " + (p.isDisponibilidade() ? "Disponível" : "Indisponível"));
                    }
                }

                listaProdutosView.setItems(produtosNomes);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void compararPrecos(ActionEvent event) throws IOException, SQLException {
        // Lógica para ir para a tela de comparação de preços do produto selecionado
        String produtoSelecionado = listaProdutosView.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
            // Extrai o nome do produto selecionado
            String nomeProduto = produtoSelecionado.split(" - ")[0]; // Extrai o nome do produto

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontend/views/CompararPreco.fxml"));
            Parent root = loader.load();

            // Aqui você pode passar o nome do produto para a nova tela de comparação
            CompararPrecoController compararPrecosController = loader.getController();
            compararPrecosController.setProduto(nomeProduto, this.conexao); // Passa a conexão correta

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Comparar Preços");
            stage.show();
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
