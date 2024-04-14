package controller;

import dao.DataBaseConection;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Relatorio {

    private static String path_destino = "";

    private static void escolherPastaDestino() {
        Scanner scanner = new Scanner(System.in);

        do{
            System.out.print("Digite o caminho da pasta de destino para os relatórios: ");
            path_destino = scanner.nextLine();
            if(!path_destino.endsWith("\\")) path_destino += "\\";
        } while (!verificarPastaDestino());

        System.out.println("Pasta de destino definida para: " + path_destino);
    }

    private static boolean verificarPastaDestino() {
        File directory = new File(path_destino);
        if(directory.exists()) return true;

        exibirMensagemErro();
        return false;
    }

    private static void exibirMensagemErro() {
        System.out.println("A pasta de destino não existe. Por favor, defina um novo caminho.");
    }

    private static void exibirMenu() {
        System.out.println("\n====== Menu de Relatórios ======");
        System.out.println("  1. Relatório de Livros Mais Vendidos");
        System.out.println("  2. Relatório de Estoque Atual");
        System.out.println("  3. Relatório de Valor Total de Vendas");
        System.out.println("  4. Mudar Pasta de Destino");
        System.out.println("  0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    // Relatório de livros mais vendidos
    private static void relatorioLivrosMaisVendidos(Connection conn) {
        String query = "SELECT l.titulo, SUM(iv.qtd_livros) AS total_vendido\n" +
                "FROM livros l\n" +
                "JOIN itens_vendas iv ON l.cod_livro = iv.cod_livro\n" +
                "GROUP BY l.titulo\n" +
                "ORDER BY total_vendido DESC;";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            FileWriter writer = new FileWriter(path_destino + "relatorio_livros_mais_vendidos.csv");

            writer.append("Título do Livro,Total Vendido\n");

            while (rs.next()) {
                String titulo = rs.getString("titulo");
                int totalVendido = rs.getInt("total_vendido");
                writer.append(titulo).append(",").append(String.valueOf(totalVendido)).append("\n");
            }

            rs.close();
            stmt.close();
            writer.flush();
            writer.close();

            System.out.println("Relatório de Livros Mais Vendidos gerado com sucesso!");
            System.out.println("\n");
        } catch (SQLException | IOException e) {
            System.err.println("Erro ao gerar o relatório de livros mais vendidos: " + e.getMessage());
        }
    }

    private static void relatorioEstoqueAtual(Connection conn) {
        String query = "SELECT l.titulo, e.qtd_estoque, d.nome_editora\n" +
                "FROM livros l\n" +
                "JOIN estoque e ON l.cod_livro = e.cod_livro\n" +
                "LEFT JOIN editoras d ON l.cod_editora = d.cod_editora;";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            FileWriter writer = new FileWriter(path_destino + "relatorio_estoque_atual.csv");

            writer.append("Título do Livro,Quantidade em Estoque,Editora\n");

            while (rs.next()) {
                String titulo = rs.getString("titulo");
                int qtdEstoque = rs.getInt("qtd_estoque");
                String nomeEditora = rs.getString("nome_editora");
                writer.append(titulo).append(",").append(String.valueOf(qtdEstoque)).append(",").append(nomeEditora).append("\n");
            }

            rs.close();
            stmt.close();
            writer.flush();
            writer.close();

            System.out.println("Relatório de Estoque Atual gerado com sucesso!");
            System.out.println("\n");
        } catch (SQLException | IOException e) {
            System.err.println("Erro ao gerar o relatório de estoque atual: " + e.getMessage());
        }
    }

    // Relatório de valor total de vendas
    private static void relatorioValorTotalVendas(Connection conn) {
        String query = "SELECT v.data_venda, iv.cod_livro, l.titulo, iv.qtd_livros, v.valor_venda\n" +
                "FROM vendas v\n" +
                "JOIN itens_vendas iv ON v.cod_venda = iv.cod_pedido\n" +
                "JOIN livros l ON iv.cod_livro = l.cod_livro\n" +
                "WHERE v.data_venda BETWEEN '2024-01-01' AND '2024-12-31'\n" +
                "ORDER BY v.data_venda;";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            FileWriter writer = new FileWriter(path_destino + "relatorio_valor_total_vendas.csv");

            writer.append("Data da Venda,Código do Livro,Título do Livro,Quantidade Vendida,Valor da Venda\n");

            double valorTotalVendas = 0.0;

            while (rs.next()) {
                String dataVenda = rs.getString("data_venda");
                int codLivro = rs.getInt("cod_livro");
                String tituloLivro = rs.getString("titulo");
                int qtdLivros = rs.getInt("qtd_livros");
                double valorVenda = rs.getDouble("valor_venda");
                double totalVenda = qtdLivros * valorVenda;
                valorTotalVendas += totalVenda;
                writer.append(dataVenda).append(",").append(String.valueOf(codLivro)).append(",")
                        .append(tituloLivro).append(",").append(String.valueOf(qtdLivros)).append(",")
                        .append(String.valueOf(valorVenda)).append(",").append(String.valueOf(totalVenda)).append("\n");
            }

            writer.append("Valor Total de Vendas:,").append(String.valueOf(valorTotalVendas)).append("\n");

            rs.close();
            stmt.close();
            writer.flush();
            writer.close();

            System.out.println("Relatório de Valor Total de Vendas gerado com sucesso!");
            System.out.println("\n");
        } catch (SQLException | IOException e) {
            System.err.println("Erro ao gerar o relatório de valor total de vendas: " + e.getMessage());
        }
    }


    public static void main(String[] args) {

        if(path_destino.isEmpty()) escolherPastaDestino();

        Connection conn = null;
        try {
            DataBaseConection dbConnection = new DataBaseConection();
            conn = dbConnection.connection;

            Scanner scanner = new Scanner(System.in);
            int opcao;
            do {
                exibirMenu();
                opcao = scanner.nextInt();

                switch (opcao) {
                    case 1:
                        relatorioLivrosMaisVendidos(conn);
                        break;
                    case 2:
                        relatorioEstoqueAtual(conn);
                        break;
                    case 3:
                        relatorioValorTotalVendas(conn);
                        break;
                    case 4:
                        escolherPastaDestino();
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                        break;
                }
            } while (opcao != 0);

        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}
