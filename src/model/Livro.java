package model;

import dao.DataBaseConection;

import java.sql.Date;
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

    public static List<Livro> buscarLivros(DataBaseConection banco){
        List<Livro> livros = new ArrayList<>();
        ResultSet resultSet = null;

        try{
            String sql = "SELECT * FROM livros";
            resultSet = banco.statement.executeQuery(sql);

            while (resultSet.next()) {
                Livro livro = new Livro();

                livro.codLivro = resultSet.getInt("cod_livro");
                livro.titulo = resultSet.getString("titulo");
                livro.genero = resultSet.getString("genero");
                livro.autor = resultSet.getString("autor");
                livro.isbn = resultSet.getLong("isbn");
                livro.anoPublicacao = resultSet.getDate("ano_publicacao");
                livro.preco = resultSet.getDouble("preco");
                livro.codEditora = resultSet.getInt("cod_Editora");

                livros.add(livro);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return  livros;
    }
}

