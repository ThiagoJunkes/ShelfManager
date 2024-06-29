package controller;

import dao.DataBaseConection;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static org.neo4j.driver.Values.parameters;

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
    private static void relatorioLivrosMaisVendidos(DataBaseConection banco) {
        String query = "MATCH (v:Venda)-[r:vendido]->(l:Livro) " +
                "RETURN l.titulo AS titulo, SUM(r.qtd) AS total_vendido " +
                "ORDER BY total_vendido DESC";

        try (Session session = banco.getSession()) {
            Result result = session.run(query);
            FileWriter writer = new FileWriter(path_destino + "relatorio_livros_mais_vendidos.csv");

            writer.append("Título do Livro,Total Vendido\n");

            while (result.hasNext()) {
                var record = result.next();
                String titulo = record.get("titulo").asString();
                int totalVendido = record.get("total_vendido").asInt();
                writer.append(titulo).append(",").append(String.valueOf(totalVendido)).append("\n");
            }

            writer.flush();
            writer.close();

            System.out.println("Relatório de Livros Mais Vendidos gerado com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao gerar o relatório de livros mais vendidos: " + e.getMessage());
        }
    }

    private static void relatorioEstoqueAtual(DataBaseConection banco) {
        String query = "MATCH (l:Livro)-[:publicado_por]->(e:Editora) " +
                "RETURN l.titulo AS titulo, l.quantidade_estoque AS qtdEstoque, e.nome AS nomeEditora";

        try (Session session = banco.getSession()) {
            Result result = session.run(query);
            FileWriter writer = new FileWriter(path_destino + "relatorio_estoque_atual.csv");

            writer.append("Título do Livro,Quantidade em Estoque,Editora\n");

            while (result.hasNext()) {
                var record = result.next();
                String titulo = record.get("titulo").asString();
                int qtdEstoque = record.get("qtdEstoque").asInt();
                String nomeEditora = record.get("nomeEditora").asString();
                writer.append(titulo).append(",").append(String.valueOf(qtdEstoque)).append(",").append(nomeEditora).append("\n");
            }

            writer.flush();
            writer.close();

            System.out.println("Relatório de Estoque Atual gerado com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao gerar o relatório de estoque atual: " + e.getMessage());
        }
    }

    // Relatório de valor total de vendas
    private static void relatorioValorTotalVendas(DataBaseConection banco) {
        String query = "MATCH (v:Venda)-[r:vendido]->(l:Livro) " +
                "RETURN v.data AS dataVenda, l.isbn AS codLivro, l.titulo AS tituloLivro, r.qtd AS qtdLivros, v.valor AS valorVenda " +
                "ORDER BY v.data";

        try (Session session = banco.getSession()) {
            Result result = session.run(query);
            FileWriter writer = new FileWriter(path_destino + "relatorio_valor_total_vendas.csv");

            writer.append("Data da Venda,ISBN do Livro,Título do Livro,Quantidade Vendida,Valor da Venda\n");

            double valorTotalVendas = 0.0;

            while (result.hasNext()) {
                var record = result.next();
                String dataVenda = record.get("dataVenda").asString();
                long isbnLivro = record.get("codLivro").asLong();
                String tituloLivro = record.get("tituloLivro").asString();
                int qtdLivros = record.get("qtdLivros").asInt();
                double valorVenda = record.get("valorVenda").asDouble();
                valorTotalVendas += valorVenda;
                writer.append(dataVenda).append(",").append(String.valueOf(isbnLivro)).append(",")
                        .append(tituloLivro).append(",").append(String.valueOf(qtdLivros)).append(",")
                        .append(String.valueOf(valorVenda)).append("\n");
            }

            writer.append("Valor Total de Vendas:,").append(String.valueOf(valorTotalVendas)).append("\n");

            writer.flush();
            writer.close();

            System.out.println("Relatório de Valor Total de Vendas gerado com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao gerar o relatório de valor total de vendas: " + e.getMessage());
        }
    }


    public static void main(String[] args) {

        if(path_destino.isEmpty()) escolherPastaDestino();

        try {
            DataBaseConection banco = new DataBaseConection();

            Scanner scanner = new Scanner(System.in);
            int opcao;
            do {
                exibirMenu();
                opcao = scanner.nextInt();

                switch (opcao) {
                    case 1:
                        relatorioLivrosMaisVendidos(banco);
                        break;
                    case 2:
                        relatorioEstoqueAtual(banco);
                        break;
                    case 3:
                        relatorioValorTotalVendas(banco);
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

        } catch (Exception e) {
            System.err.println("Erro ao fechar a conexão: " + e.getMessage());
        }

    }
}
