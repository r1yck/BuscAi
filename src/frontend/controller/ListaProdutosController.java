/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package frontend.controller;

import backend.models.Mercado;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author mafxr
 */
public class ListaProdutosController implements Initializable {

    @FXML
    private Label nomeMercadoLabel;

    private Mercado mercadoSelecionado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialização da interface, se necessário
    }

    public void setMercadoSelecionado(Mercado mercado) {
        this.mercadoSelecionado = mercado;
        atualizarInterface();
    }

    private void atualizarInterface() {
        if (mercadoSelecionado != null) {
            nomeMercadoLabel.setText(mercadoSelecionado.getNome());
        }
    }
}
