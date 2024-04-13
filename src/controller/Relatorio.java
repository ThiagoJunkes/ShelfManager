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

    private static String path_destino;

    static {
        try {
            path_destino = "/home/yasmin/projeto-BAN2/relatorios";
        } catch (Exception e) {
            System.err.println("Erro ao definir o caminho de destino: " + e.getMessage());
            path_destino = "relatorios/";
        }
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

    // Relatório de estoque atual
    private static void relatorioEstoqueAtual(Connection conn) {
        String query = "SELECT l.titulo, e.qtd_estoque\n" +
                "FROM livros l\n" +
                "JOIN estoque e ON l.cod_livro = e.cod_livro;";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            FileWriter writer = new FileWriter(path_destino + "relatorio_estoque_atual.csv");

            writer.append("Título do Livro,Quantidade em Estoque\n");

            while (rs.next()) {
                String titulo = rs.getString("titulo");
                int qtdEstoque = rs.getInt("qtd_estoque");
                writer.append(titulo).append(",").append(String.valueOf(qtdEstoque)).append("\n");
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
        String query = "SELECT SUM(valor_venda) AS valor_total_vendas\n" +
                "FROM vendas\n" +
                "WHERE data_venda BETWEEN '2024-01-01' AND '2024-12-31';";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            FileWriter writer = new FileWriter(path_destino + "relatorio_valor_total_vendas.csv");

            writer.append("Valor Total de Vendas\n");

            while (rs.next()) {
                double valorTotalVendas = rs.getDouble("valor_total_vendas");
                writer.append(String.valueOf(valorTotalVendas)).append("\n");
            }

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
        Connection conn = null;
        try {
            DataBaseConection dbConnection = new DataBaseConection();
            conn = dbConnection.connection;

            Scanner scanner = new Scanner(System.in);
            int opcao;
            do {
                System.out.println("\n=== Menu de Relatórios ===");
                System.out.println("1. Relatório de Livros Mais Vendidos");
                System.out.println("2. Relatório de Estoque Atual");
                System.out.println("3. Relatório de Valor Total de Vendas");
                System.out.println("0. Sair");
                System.out.print("Escolha uma opção: ");

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
