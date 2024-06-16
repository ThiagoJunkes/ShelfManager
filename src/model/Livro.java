package model;

import dao.DataBaseConection;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Livro {
    private int codLivro;
    private String titulo;
    private String genero;
    private String autor;
    private long isbn;
    private Date anoPublicacao;
    private double preco;
    private int codEditora;

    private int qtdEstoque;

    public Editora editora;

    // Getters e Setters
    public int getCodLivro() {
        return codLivro;
    }

    public void setCodLivro(int codLivro) {
        this.codLivro = codLivro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public Date getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Date anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getCodEditora() {
        return codEditora;
    }

    public void setCodEditora(int codEditora) {
        this.codEditora = codEditora;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public Livro() {
    }

    public Livro(String titulo, String genero, String autor, long isbn, Date anoPublicacao, double preco, int quantidadeEstoque, Editora editora) {
        this.titulo = titulo;
        this.genero = genero;
        this.autor = autor;
        this.isbn = isbn;
        this.anoPublicacao = anoPublicacao;
        this.preco = preco;
        this.qtdEstoque = quantidadeEstoque;
        this.editora = editora;
    }

    // Getters e Setters omitidos para brevidade

    public void printLivroFormatado() {
        String tituloFormatado = titulo.length() > 20 ? titulo.substring(0, 20) : titulo;
        String editoraFormatada = editora.getNomeEditora().length() > 20 ? editora.getNomeEditora().substring(0, 20) : editora.getNomeEditora();

        System.out.printf("%-20s | %-20s | %-20s | %-20s\n",
                tituloFormatado, genero, editoraFormatada, isbn);
    }

    public void printLivroSemFormatacao() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = sdf.format(anoPublicacao);

        System.out.println("Livro: " + titulo);
        System.out.println("0 - Para voltar ao Menu e alterar a Editora no Menu Editoras");
        System.out.println("1 - Título:            " + titulo);
        System.out.println("2 - Gênero:            " + genero);
        System.out.println("3 - Autor:             " + autor);
        System.out.println("4 - ISBN:              " + isbn);
        System.out.println("5 - Ano de Publicação: " + dataFormatada);
        System.out.println("6 - Preço:             " + preco);
        System.out.println("7 - Editora:           " + editora.getCodEditora() + " | " + editora.getNomeEditora());
    }

    public static List<Livro> buscarLivros(DataBaseConection banco) {
        List<Livro> livros = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try (Session session = banco.getSession()) {
            String query = "MATCH (livro:Livro)-[:EDITORA]->(editora:Editora) RETURN livro, editora";
            try (Transaction tx = session.beginTransaction()){
                 Result result = tx.run(query);

                while (result.hasNext()) {
                    Record record = result.next();
                    Node livroNode = record.get("livro").asNode();
                    Node editoraNode = record.get("editora").asNode();

                    Livro livro = new Livro();
                    livro.setTitulo(livroNode.get("titulo").asString());
                    livro.setGenero(livroNode.get("genero").asString());
                    livro.setAutor(livroNode.get("autor").asString());
                    livro.setIsbn(livroNode.get("isbn").asLong());
                    livro.setAnoPublicacao(sdf.parse(livroNode.get("ano_publicacao").asString()));
                    livro.setPreco(livroNode.get("preco").asDouble());
                    livro.setQtdEstoque(livroNode.get("quantidade_estoque").asInt());

                    Editora editora = new Editora();
                    editora.setNomeEditora(editoraNode.get("nome").asString());
                    editora.setNomeContato(editoraNode.get("nome_contato").asString());
                    editora.setEmailEditora(editoraNode.get("email").asString());
                    editora.setTelefoneEditora(editoraNode.get("telefone").asString());

                    livro.editora = editora;
                    livros.add(livro);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar Livros: " + e.getMessage());
        }

        return livros;
    }

    public static boolean adicionarLivro(Livro livro, DataBaseConection banco) {
        try (Session session = banco.getSession()) {
            String query = "CREATE (livro:Livro {titulo: '" + livro.getTitulo() + "', " +
                    "genero: '" + livro.getGenero() + "', " +
                    "autor: '" + livro.getAutor() + "', " +
                    "isbn: " + livro.getIsbn() + ", " +
                    "ano_publicacao: '" + livro.getAnoPublicacao().toString() + "', " +
                    "preco: " + livro.getPreco() + ", " +
                    "quantidade_estoque: " + livro.getQtdEstoque() + "})" +
                    "-[:EDITORA]->(editora:Editora {nome: '" + livro.editora.getNomeEditora() + "', " +
                    "nome_contato: '" + livro.editora.getNomeContato() + "', " +
                    "email: '" + livro.editora.getEmailEditora() + "', " +
                    "telefone: '" + livro.editora.getTelefoneEditora() + "'})";

            session.writeTransaction(tx -> {
                tx.run(query);
                return null;
            });

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean editarLivro(Livro livroAtualizado, DataBaseConection banco) {
        try (Session session = banco.getSession()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Formato para a data

            String query = "MATCH (livro:Livro {isbn: " + livroAtualizado.getIsbn() + "})-[:EDITORA]->(editora:Editora) " +
                    "SET livro.titulo = '" + livroAtualizado.getTitulo() + "', " +
                    "    livro.genero = '" + livroAtualizado.getGenero() + "', " +
                    "    livro.autor = '" + livroAtualizado.getAutor() + "', " +
                    "    livro.ano_publicacao = date('" + sdf.format(livroAtualizado.getAnoPublicacao()) + "'), " +
                    "    livro.preco = " + livroAtualizado.getPreco() + ", " +
                    "    livro.quantidade_estoque = " + livroAtualizado.getQtdEstoque() + ", " +
                    "    editora.nome = '" + livroAtualizado.editora.getNomeEditora() + "', " +
                    "    editora.nome_contato = '" + livroAtualizado.editora.getNomeContato() + "', " +
                    "    editora.email = '" + livroAtualizado.editora.getEmailEditora() + "', " +
                    "    editora.telefone = '" + livroAtualizado.editora.getTelefoneEditora() + "' " +
                    "RETURN livro";

            List<Record> result = session.writeTransaction(tx -> {
                Result resultSet = tx.run(query);
                return resultSet.list();
            });

            if (!result.isEmpty()) {
                System.out.println("Livro atualizado com sucesso!");
                return true;
            } else {
                System.out.println("Nenhum livro atualizado.");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Falha ao atualizar livro.");
            return false;
        }
    }


    public static boolean excluirLivro(Livro livroExcluir, DataBaseConection banco) {
        try (Session session = banco.getSession()) {
            String query = "MATCH (livro:Livro {isbn: " + livroExcluir.getIsbn() + "}) " +
                    "OPTIONAL MATCH (livro)-[r:EDITORA]->() " +
                    "DELETE livro, r";

            session.writeTransaction(tx -> {
                tx.run(query);
                return null;
            });

            return true;
        } catch (Exception e) {
            return false;
        }
    }



}
