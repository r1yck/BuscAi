package backend.services;

import backend.models.Categoria;
import backend.models.Mercado;
import backend.models.Produto;
import backend.services.FiltroDePreferencias;
import backend.models.ComparadorDePrecos;
import backend.dao.MercadoDAO;
import backend.dao.ProdutoDAO;



import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Criando o aplicativo
        AplicativoBuscAi aplicativo = new AplicativoBuscAi("BuscAi", "1.0");
        aplicativo.iniciar();

        // Criando mercados
        Mercado mercado1 = new Mercado(1, "Mercado Central", "Rua A");
        Mercado mercado2 = new Mercado(2, "Supermercado XYZ", "Rua B");

        // Criando produtos e associando ao mercado
        Produto produto1 = new Produto(1, "Arroz", 20.0, true, Categoria.ALIMENTOS_NAO_PERECIVEIS, mercado1);
        Produto produto2 = new Produto(2, "Feijão", 15.0, true, Categoria.ALIMENTOS_NAO_PERECIVEIS, mercado1);
        Produto produto3 = new Produto(3, "Sabão", 5.0, false, Categoria.HIGIENE_PESSOAL, mercado2);

        // Adicionando produtos aos mercados
        mercado1.adicionarProduto(produto1);
        mercado1.adicionarProduto(produto2);
        mercado2.adicionarProduto(produto3);

        // Adicionando mercados ao aplicativo
        aplicativo.adicionarMercado(mercado1);
        aplicativo.adicionarMercado(mercado2);

        // Criação da interface gráfica
        while (true) {
            String[] options = {"Cliente", "Comerciante", "Sair"};
            int choice = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "BuscAi",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if (choice == 0) { // Cliente
                String[] clienteOptions = {"Listar Produtos", "Filtrar por Categoria", "Filtrar por Preço Máximo", "Comparar Preços", "Voltar"};
                int clienteChoice = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Cliente", 
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, clienteOptions, clienteOptions[0]);

                if (clienteChoice == 0) {
                    aplicativo.listarProdutosDisponiveis();
                } else if (clienteChoice == 1) {
                    String categoriaInput = JOptionPane.showInputDialog("Digite a categoria (HIGIENE_PESSOAL, ALIMENTOS_NAO_PERECIVEIS, ...):");
                    try {
                        Categoria categoria = Categoria.valueOf(categoriaInput);
                        aplicativo.aplicarFiltroPorCategoria(categoria);
                    } catch (IllegalArgumentException e) {
                        JOptionPane.showMessageDialog(null, "Categoria inválida!");
                    }
                } else if (clienteChoice == 2) {
                    double precoMaximo = Double.parseDouble(JOptionPane.showInputDialog("Digite o preço máximo:"));
                    aplicativo.aplicarFiltroPrecoMaximo(precoMaximo);
                } else if (clienteChoice == 3) {
                    String nomeProduto = JOptionPane.showInputDialog("Digite o nome do produto para comparar os preços:");
                    aplicativo.compararPrecos(nomeProduto);
                } else {
                    continue;
                }
            } else if (choice == 1) { // Comerciante
                String[] comercianteOptions = {"Adicionar Mercado", "Listar Mercados", "Editar Mercado", "Deletar Mercado", "Cadastrar Produto", "Editar Produto", "Deletar Produto", "Voltar"};
                int comercianteChoice = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Comerciante", 
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, comercianteOptions, comercianteOptions[0]);

                if (comercianteChoice == 0) {
                    String nomeMercado = JOptionPane.showInputDialog("Digite o nome do mercado:");
                    String localizacaoMercado = JOptionPane.showInputDialog("Digite a localização do mercado:");
                    Mercado novoMercado = new Mercado(mercado1.getId() + 1, nomeMercado, localizacaoMercado);
                    aplicativo.adicionarMercado(novoMercado);
                    JOptionPane.showMessageDialog(null, "Mercado adicionado com sucesso!");
                } else if (comercianteChoice == 1) { // Listar mercados
                    List<Mercado> mercados = aplicativo.listarMercados();
                    StringBuilder sb = new StringBuilder("Mercados disponíveis:\n");
                    for (Mercado m : mercados) {
                        sb.append(m.getNome()).append(" - ").append(m.getLocalizacao()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, sb.toString());
                } else if (comercianteChoice == 3) { // Deletar Mercado
                    String nomeMercadoDeletar = JOptionPane.showInputDialog("Digite o nome do mercado a ser deletado:");
                    Mercado mercadoDeletado = aplicativo.buscarMercadoPorNome(nomeMercadoDeletar);
                    if (mercadoDeletado != null) {
                        aplicativo.deletarMercado(mercadoDeletado);
                        JOptionPane.showMessageDialog(null, "Mercado deletado com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Mercado não encontrado.");
                    }
                } else if (comercianteChoice == 4) { // Cadastrar produto
                    String nomeProduto = JOptionPane.showInputDialog("Digite o nome do produto:");
                    double precoProduto = Double.parseDouble(JOptionPane.showInputDialog("Digite o preço do produto:"));
                    String categoriaProduto = JOptionPane.showInputDialog("Digite a categoria do produto (HIGIENE_PESSOAL, ALIMENTOS_NAO_PERECIVEIS, ...):");
                    Categoria categoria = Categoria.valueOf(categoriaProduto);

                    // Escolher mercado
                    List<Mercado> mercados = aplicativo.listarMercados();
                    String[] nomesMercados = new String[mercados.size()];
                    for (int i = 0; i < mercados.size(); i++) {
                        nomesMercados[i] = mercados.get(i).getNome();
                    }
                    String mercadoEscolhido = (String) JOptionPane.showInputDialog(null, "Escolha o mercado para o produto:",
                            "Escolher Mercado", JOptionPane.QUESTION_MESSAGE, null, nomesMercados, nomesMercados[0]);

                    Mercado mercadoSelecionado = mercados.stream().filter(m -> m.getNome().equals(mercadoEscolhido)).findFirst().orElse(null);

                    Produto novoProduto = new Produto(mercado1.getId() + 1, nomeProduto, precoProduto, true, categoria, mercadoSelecionado);
                    mercadoSelecionado.adicionarProduto(novoProduto);
                    JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
                }
                // Outras opções para editar e deletar produtos podem ser implementadas da mesma maneira
            } else {
                break; // Sair
            }
        }
        aplicativo.fechar();
    }
}
