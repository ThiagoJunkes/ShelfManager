package model;

import dao.DataBaseConection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public void printLivroSemFormatacao(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = sdf.format(anoPublicacao);

        System.out.println("Livro: " + codLivro);
        System.out.println("0 - Para voltar ao Menu e alterar a Editora no Menu Editoras");
        System.out.println("1 - Título:            " + titulo);
        System.out.println("2 - Gênero:            " + genero);
        System.out.println("3 - Autor:             " + autor);
        System.out.println("4 - ISBN:              " + isbn);
        System.out.println("5 - Ano de Publicação: " + dataFormatada);
        System.out.println("6 - Preço:             " + preco);
        System.out.println("7 - Editora:           " + editora.getCodEditora() + " | " + editora.getNomeEditora());
    }

    public static List<Livro> buscarLivros(DataBaseConection banco){
        List<Livro> livros = new ArrayList<>();
        ResultSet resultSet = null;

        try{
            String sql = "SELECT l.*, e.* FROM livros l JOIN editoras e ON e.cod_editora = l.cod_editora ORDER BY l.cod_livro";
            resultSet = banco.statement.executeQuery(sql);

            while (resultSet.next()) {
                Livro livro = new Livro();
                Editora editora = new Editora();

                livro.codLivro = resultSet.getInt("cod_livro");
                livro.titulo = resultSet.getString("titulo");
                livro.genero = resultSet.getString("genero");
                livro.autor = resultSet.getString("autor");
                livro.isbn = resultSet.getLong("isbn");
                livro.anoPublicacao = resultSet.getDate("ano_publicacao");
                livro.preco = resultSet.getDouble("preco");
                livro.codEditora = resultSet.getInt("cod_Editora");
                editora.setCodEditora(resultSet.getInt("cod_editora"));
                editora.setNomeEditora(resultSet.getString("nome_editora"));
                editora.setNomeContato(resultSet.getString("nome_contato"));
                editora.setEmailEditora(resultSet.getString("email_editora"));
                editora.setTelefoneEditora(resultSet.getString("telefone_editora"));

                livro.editora = editora;
                livros.add(livro);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return  livros;
    }

    public static boolean adicionarLivro(Livro livro, DataBaseConection banco) {
        String sqlLivro = "INSERT INTO livros (titulo, genero, autor, isbn, ano_publicacao, preco, cod_editora) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlEstoque = "INSERT INTO estoque (cod_livro, qtd_estoque) VALUES (?, ?)";

        try {
            // Inserindo o livro na tabela livros
            PreparedStatement statementLivro = banco.connection.prepareStatement(sqlLivro, PreparedStatement.RETURN_GENERATED_KEYS);
            statementLivro.setString(1, livro.getTitulo());
            statementLivro.setString(2, livro.getGenero());
            statementLivro.setString(3, livro.getAutor());
            statementLivro.setLong(4, livro.getIsbn());
            statementLivro.setDate(5, livro.getAnoPublicacao());
            statementLivro.setDouble(6, livro.getPreco());
            statementLivro.setInt(7, livro.getCodEditora());

            int rowsInsertedLivro = statementLivro.executeUpdate();

            // Obtendo o código do livro inserido
            ResultSet generatedKeys = statementLivro.getGeneratedKeys();
            int codLivroInserido = -1;
            if (generatedKeys.next()) {
                codLivroInserido = generatedKeys.getInt(1);
            }

            if (rowsInsertedLivro > 0 && codLivroInserido != -1) {
                // Inserindo a quantidade em estoque na tabela estoque
                PreparedStatement statementEstoque = banco.connection.prepareStatement(sqlEstoque);
                statementEstoque.setInt(1, codLivroInserido);
                statementEstoque.setInt(2, livro.getQtdEstoque());

                int rowsInsertedEstoque = statementEstoque.executeUpdate();

                if (rowsInsertedEstoque > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public static boolean editarLivro(Livro livro, DataBaseConection banco) {
        String sql = "UPDATE livros SET titulo=?, genero=?, autor=?, isbn=?, ano_publicacao=?, preco=?, cod_editora=? WHERE cod_livro=?";
        try {
            PreparedStatement statement = banco.connection.prepareStatement(sql);
            statement.setString(1, livro.getTitulo());
            statement.setString(2, livro.getGenero());
            statement.setString(3, livro.getAutor());
            statement.setLong(4, livro.getIsbn());
            statement.setDate(5, livro.getAnoPublicacao());
            statement.setDouble(6, livro.getPreco());
            statement.setInt(7, livro.getCodEditora());
            statement.setInt(8, livro.getCodLivro());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Livro atualizado com sucesso!");
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public static boolean excluirLivro(Livro livro, DataBaseConection banco) {
        String sqlVendas = "SELECT COUNT(*) AS total_vendas FROM itens_vendas WHERE cod_livro=?";
        String sqlExcluirEstoque = "DELETE FROM estoque WHERE cod_livro=?";
        String sqlExcluirLivro = "DELETE FROM livros WHERE cod_livro=?";

        try {
            // Verifica se há vendas relacionadas a este livro
            PreparedStatement statementVendas = banco.connection.prepareStatement(sqlVendas);
            statementVendas.setInt(1, livro.getCodLivro());
            ResultSet rsVendas = statementVendas.executeQuery();

            int totalVendas = 0;
            if (rsVendas.next()) {
                totalVendas = rsVendas.getInt("total_vendas");
            }

            if (totalVendas > 0) {
                System.out.println("Não é possível excluir um livro pos ele já possui uma venda!");
                return false;
            }

            // Se não há vendas, exclui o livro do estoque
            PreparedStatement statementExcluirEstoque = banco.connection.prepareStatement(sqlExcluirEstoque);
            statementExcluirEstoque.setInt(1, livro.getCodLivro());
            statementExcluirEstoque.executeUpdate();

            // Exclui o livro da tabela livros
            PreparedStatement statementExcluirLivro = banco.connection.prepareStatement(sqlExcluirLivro);
            statementExcluirLivro.setInt(1, livro.getCodLivro());

            int rowsDeleted = statementExcluirLivro.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Livro excluído com sucesso!");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }


}

