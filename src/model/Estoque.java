package model;

import dao.DataBaseConection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public static List<Estoque> buscarEstoque(DataBaseConection banco){
        List<Estoque> estoque = new ArrayList<>();
        ResultSet resultSet = null;

        try{
            String sql = "SELECT e.*, l.* FROM estoque e JOIN livros l ON e.cod_livro = l.cod_livro ORDER BY e.qtd_estoque";
            resultSet = banco.statement.executeQuery(sql);

            while (resultSet.next()) {
                Estoque est = new Estoque();
                Livro livro = new Livro();

                est.codLivro = resultSet.getInt("cod_livro");
                est.qtdEstoque = resultSet.getInt("qtd_estoque");
                livro.setCodLivro(resultSet.getInt("cod_livro"));
                livro.setTitulo(resultSet.getString("titulo"));
                livro.setGenero(resultSet.getString("genero"));
                livro.setAutor(resultSet.getString("autor"));
                livro.setIsbn(resultSet.getLong("isbn"));
                livro.setAnoPublicacao(resultSet.getDate("ano_publicacao"));
                livro.setPreco(resultSet.getDouble("preco"));
                livro.setCodEditora(resultSet.getInt("cod_Editora"));

                est.livro = livro;
                estoque.add(est);
            }

        }catch (SQLException e) {
            System.out.println("Erro ao buscar Estoque: " + e.getMessage());
        }

        return estoque;
    }

    public static void editarEstoque(Estoque est, DataBaseConection banco) {
        try {
            String sql = "UPDATE estoque " +
                    "SET qtd_estoque = ? " +
                    "WHERE cod_livro = ?";
            banco.preparedStatement = banco.connection.prepareStatement(sql);

            banco.preparedStatement.setInt(1, est.getQtdEstoque());
            banco.preparedStatement.setInt(2, est.getCodLivro());

            int linhasAfetadas = banco.preparedStatement.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Estoque atualizado com sucesso!");
            } else {
                System.out.println("Nenhum estoque de livro atualizado.");
            }

        } catch (SQLException e) {
            System.out.println("Nenhum estoque de livro atualizado.");
        }
    }
}