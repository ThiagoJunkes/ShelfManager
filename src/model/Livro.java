package model;

import dao.DataBaseConection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public void printLivroSemFormatacao(){
        System.out.println("Livro: " + codLivro);
    }

    public static List<Livro> buscarLivros(DataBaseConection banco){
        List<Livro> livros = new ArrayList<>();
        ResultSet resultSet = null;

        try{
            String sql = "SELECT l.*, e.* FROM livros l JOIN editoras e ON e.cod_editora = l.cod_editora";
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
        String sql = "INSERT INTO livros (titulo, genero, autor, isbn, ano_publicacao, preco, cod_editora) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = banco.connection.prepareStatement(sql);
            statement.setString(1, livro.getTitulo());
            statement.setString(2, livro.getGenero());
            statement.setString(3, livro.getAutor());
            statement.setLong(4, livro.getIsbn());
            statement.setDate(5, livro.getAnoPublicacao());
            statement.setDouble(6, livro.getPreco());
            statement.setInt(7, livro.getCodEditora());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Novo livro adicionado com sucesso!");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return false;
    }

    public static boolean excluirLivro(Livro livro, DataBaseConection banco) {
        String sql = "DELETE FROM livros WHERE cod_livro=?";
        try {
            PreparedStatement statement = banco.connection.prepareStatement(sql);
            statement.setInt(1, livro.getCodLivro());

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Livro excluído com sucesso!");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}

