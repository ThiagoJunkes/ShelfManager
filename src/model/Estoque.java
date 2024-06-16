package model;

import dao.DataBaseConection;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Estoque {
    private int codLivro;
    private int qtdEstoque;

    public Livro livro;

    // Getters e Setters
    public int getCodLivro() {
        return this.codLivro;
    }

    public void setCodLivro(int codLivro) {
        this.codLivro = codLivro;
    }

    public int getQtdEstoque() {
        return this.qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public void printEstoqueSemFormatacao(){
        System.out.println("Código Livro: " + codLivro);
        System.out.println("Título:       " + livro.getTitulo());
        System.out.println("ISBN:         " + livro.getIsbn());
        System.out.println("Quantidade:   " + qtdEstoque);
    }

    public static List<Estoque> buscarEstoque(DataBaseConection banco) {
        List<Estoque> estoque = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try (Session session = banco.getSession()) {
            String query = "MATCH (e:Estoque)-[:INCLUI]->(livro:Livro) " +
                    "RETURN e.cod_livro AS codLivro, e.qtd_estoque AS qtdEstoque, " +
                    "       livro.cod_livro AS codLivroLivro, livro.titulo AS titulo, " +
                    "       livro.genero AS genero, livro.autor AS autor, " +
                    "       livro.isbn AS isbn, livro.ano_publicacao AS anoPublicacao, " +
                    "       livro.preco AS preco, livro.cod_editora AS codEditora";

            List<Record> result = session.readTransaction(tx -> {
                Result resultSet = tx.run(query);
                return resultSet.list();
            });

            for (Record record : result) {
                Value estNode = record.get("e");
                Value livroNode = record.get("livro");

                Estoque est = new Estoque();
                est.setCodLivro(estNode.get("codLivro").asInt());
                est.setQtdEstoque(estNode.get("qtdEstoque").asInt());

                Livro livro = new Livro();
                livro.setCodLivro(livroNode.get("codLivroLivro").asInt());
                livro.setTitulo(livroNode.get("titulo").asString());
                livro.setGenero(livroNode.get("genero").asString());
                livro.setAutor(livroNode.get("autor").asString());
                livro.setIsbn(livroNode.get("isbn").asLong());
                livro.setAnoPublicacao(sdf.parse(livroNode.get("anoPublicacao").asString()));
                livro.setPreco(livroNode.get("preco").asDouble());
                livro.setCodEditora(livroNode.get("codEditora").asInt());

                est.livro = livro;
                estoque.add(est);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Falha ao buscar Estoque.");
        }

        return estoque;
    }

    public static void editarEstoque(Estoque est, DataBaseConection banco) {
        try (Session session = banco.getSession()) {
            String query = "MATCH (e:Estoque {cod_livro: " + est.getCodLivro() + "}) " +
                    "SET e.qtd_estoque = " + est.getQtdEstoque() + " " +
                    "RETURN e";

            List<Record> result = session.writeTransaction(tx -> {
                Result resultSet = tx.run(query);
                return resultSet.list();
            });

            if (!result.isEmpty()) {
                System.out.println("Estoque atualizado com sucesso!");
            } else {
                System.out.println("Nenhum estoque de livro atualizado.");
            }

        } catch (Exception e) {
            System.out.println("Falha ao atualizar Estoque.");
        }
    }
}