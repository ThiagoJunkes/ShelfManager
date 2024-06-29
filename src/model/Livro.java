package model;

import dao.DataBaseConection;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public void setLivro(Livro novoLivro) {
        this.codLivro = novoLivro.getCodLivro();
        this.titulo = novoLivro.getTitulo();
        this.genero = novoLivro.getGenero();
        this.autor = novoLivro.getAutor();
        this.isbn = novoLivro.getIsbn();
        this.anoPublicacao = novoLivro.getAnoPublicacao();
        this.preco = novoLivro.getPreco();
        this.codEditora = novoLivro.getCodEditora();
        this.qtdEstoque = novoLivro.getQtdEstoque();
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

        System.out.printf("%-5d | %-20s | %-20s | %-20s | %-20s\n",
                codLivro, tituloFormatado, genero, editoraFormatada, isbn);
    }

    public void printLivroFormatadoVenda(){
        String tituloFormatado = titulo.length() > 20 ? titulo.substring(0, 20) : titulo;
        String novoISBN = String.valueOf(isbn);
        novoISBN = novoISBN.length() > 17 ? novoISBN.substring(0, 20) : novoISBN;

        System.out.printf("%-5d | %-20s | %-17s | %d\n",
                codLivro, tituloFormatado, isbn, qtdEstoque);
    }

    public void printEstoqueSemFormatacaoEstoque(){
        System.out.println("Código Livro: " + codLivro);
        System.out.println("Título:       " + getTitulo());
        System.out.println("ISBN:         " + getIsbn());
        System.out.println("Quantidade:   " + qtdEstoque);
    }

    public void printLivroSemFormatacao() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = sdf.format(anoPublicacao);

        System.out.println("Livro: " + titulo);
        System.out.println("0 - Para voltar ao Menu");
        System.out.println("1 - Título:            " + titulo);
        System.out.println("2 - Gênero:            " + genero);
        System.out.println("3 - Autor:             " + autor);
        System.out.println("4 - Ano de Publicação: " + dataFormatada);
        System.out.println("5 - Preço:             " + preco);
        System.out.println("6 - Editora:           " + editora.getCodEditora() + " | " + editora.getNomeEditora());
    }

    public static List<Livro> buscarLivros(DataBaseConection banco) {
        List<Livro> livros = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        int cod_livro = 1, cod_editora = 1;

        try (Session session = banco.getSession()) {
            String query = "MATCH (livro:Livro)-[:publicado_por]->(editora:Editora) RETURN livro, editora";
            try (Transaction tx = session.beginTransaction()){
                 Result result = tx.run(query);

                while (result.hasNext()) {
                    Record record = result.next();
                    Node livroNode = record.get("livro").asNode();
                    Node editoraNode = record.get("editora").asNode();

                    Livro livro = new Livro();
                    livro.setCodLivro(cod_livro);
                    livro.setTitulo(livroNode.get("titulo").asString());
                    livro.setGenero(livroNode.get("genero").asString());
                    livro.setAutor(livroNode.get("autor").asString());
                    livro.setIsbn(livroNode.get("isbn").asLong());
                    livro.setAnoPublicacao(sdf.parse(livroNode.get("ano_publicacao").asString()));
                    livro.setPreco(livroNode.get("preco").asDouble());
                    livro.setQtdEstoque(livroNode.get("quantidade_estoque").asInt());

                    Editora editora = new Editora();
                    editora.setCodEditora(cod_editora);
                    editora.setNomeEditora(editoraNode.get("nome").asString());
                    editora.setNomeContato(editoraNode.get("nome_contato").asString());
                    editora.setEmailEditora(editoraNode.get("email").asString());
                    editora.setTelefoneEditora(editoraNode.get("telefone").asString());

                    livro.editora = editora;
                    livros.add(livro);
                    cod_livro++;
                    cod_editora++;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar Livros: " + e.getMessage());
        }

        return livros;
    }

    public static boolean adicionarLivro(Livro livro, DataBaseConection banco) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dataPublicacaoFormatada = formatter.format(livro.getAnoPublicacao());

        try (Session session = banco.getSession()) {
            // Verificar se a editora já existe
            String verificaEditoraQuery = "MATCH (editora:Editora {nome: '" + livro.editora.getNomeEditora() + "', " +
                    "nome_contato: '" + livro.editora.getNomeContato() + "', " +
                    "email: '" + livro.editora.getEmailEditora() + "', " +
                    "telefone: '" + livro.editora.getTelefoneEditora() + "'}) RETURN editora";

            Record editoraExistente = session.readTransaction(tx -> {
                Result result = tx.run(verificaEditoraQuery);
                return result.single();
            });

            if (editoraExistente == null) {
                // Se a editora não existir, retornar falso
                return false;
            } else {
                // Query para verificar se já existe um livro com o mesmo isbn
                String checkQuery = "MATCH (e:Livro {isbn: " + livro.getIsbn() + "}) RETURN e";
                boolean livroExiste = session.readTransaction(tx -> {
                    Result result = tx.run(checkQuery);
                    return result.hasNext();
                });

                if (livroExiste) {
                    System.out.println("Não foi possível adicionar o livro pois já existe um com o mesmo ISBN!");
                    return false;
                }
                // Se a editora existe, criar o livro e associar a editora
                String criaLivroQuery = "MATCH (editora:Editora {nome: '" + livro.editora.getNomeEditora() + "', " +
                        "nome_contato: '" + livro.editora.getNomeContato() + "', " +
                        "email: '" + livro.editora.getEmailEditora() + "', " +
                        "telefone: '" + livro.editora.getTelefoneEditora() + "'}) " +
                        "CREATE (livro:Livro {titulo: '" + livro.getTitulo() + "', " +
                        "genero: '" + livro.getGenero() + "', " +
                        "autor: '" + livro.getAutor() + "', " +
                        "isbn: " + livro.getIsbn() + ", " +
                        "ano_publicacao: '" + dataPublicacaoFormatada + "', " +
                        "preco: " + livro.getPreco() + ", " +
                        "quantidade_estoque: " + livro.getQtdEstoque() + "}) " +
                        "CREATE (livro)-[:publicado_por]->(editora)";

                session.writeTransaction(tx -> {
                    tx.run(criaLivroQuery);
                    return null;
                });

                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }



    public static boolean editarLivro(Livro livroAtualizado, DataBaseConection banco) {
        try (Session session = banco.getSession()) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Formato para a data

            // Consulta para excluir o vínculo atual entre o livro e a editora
            String excluirVinculoQuery = "MATCH (livro:Livro {isbn: " + livroAtualizado.getIsbn() + "})-[r:publicado_por]->(editora:Editora) " +
                    "DELETE r";

            // Consulta para criar novo vínculo entre o livro e a nova editora
            String criarNovoVinculoQuery = "MATCH (livro:Livro {isbn: " + livroAtualizado.getIsbn() + "}), " +
                    "(novaEditora:Editora {nome: '" + livroAtualizado.editora.getNomeEditora() + "', " +
                    "nome_contato: '" + livroAtualizado.editora.getNomeContato() + "', " +
                    "email: '" + livroAtualizado.editora.getEmailEditora() + "', " +
                    "telefone: '" + livroAtualizado.editora.getTelefoneEditora() + "'}) " +
                    "CREATE (livro)-[:publicado_por]->(novaEditora)";

            // Consulta para atualizar os campos do livro
            String atualizarCamposLivroQuery = "MATCH (livro:Livro {isbn: " + livroAtualizado.getIsbn() + "}) " +
                    "SET livro.titulo = '" + livroAtualizado.getTitulo() + "', " +
                    "    livro.genero = '" + livroAtualizado.getGenero() + "', " +
                    "    livro.autor = '" + livroAtualizado.getAutor() + "', " +
                    "    livro.ano_publicacao = '" + sdf.format(livroAtualizado.getAnoPublicacao()) + "', " +
                    "    livro.preco = " + livroAtualizado.getPreco() + " " +
                    "RETURN livro";

            List<Record> result = session.writeTransaction(tx -> {
                tx.run(excluirVinculoQuery);
                tx.run(criarNovoVinculoQuery);
                Result resultSet = tx.run(atualizarCamposLivroQuery);
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
            String query1 = "MATCH (livro:Livro {isbn: " + livroExcluir.getIsbn() + "}) " +
                    "MATCH (livro)-[r:publicado_por]->() " +
                    "DELETE r";

            String query2 = "MATCH (livro:Livro {isbn: " + livroExcluir.getIsbn() + "}) " +
                    "DELETE livro";


            System.out.println(query2);
            System.out.println(query1);
            session.writeTransaction(tx -> {
                tx.run(query1);
                tx.run(query2);
                return null;
            });

            return true;
        } catch (Exception e) {
            System.out.println("Não foi possivel excluir este livro!");
            System.out.println("Verifique se ele não possui alguma venda");
            return false;
        }
    }

    public static boolean editarEstoque(Livro livroAtualizado, DataBaseConection banco) {
        try (Session session = banco.getSession()) {

            // Consulta para atualizar os estoque do livro
            String atualizarCamposEstoqueQuery = "MATCH (livro:Livro {isbn: " + livroAtualizado.getIsbn() + "}) " +
                    "   SET livro.quantidade_estoque = " + livroAtualizado.getQtdEstoque() + " " +
                    "RETURN livro";

            List<Record> result = session.writeTransaction(tx -> {
                Result resultSet = tx.run(atualizarCamposEstoqueQuery);
                return resultSet.list();
            });

            if (!result.isEmpty()) {
                System.out.println("Estoque atualizado com sucesso!");
                return true;
            } else {
                System.out.println("Estoque não foi atualizado.");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Falha ao atualizar estoque.");
            return false;
        }
    }

}
