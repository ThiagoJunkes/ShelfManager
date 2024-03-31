package model;

import dao.DataBaseConection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Estoque {
    private int codLivro;
    private int qtdEstoque;

    // Getters e Setters
    public int getCodLivro() {
        return codLivro;
    }

    public void setCodLivro(int codLivro) {
        this.codLivro = codLivro;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public static List<Estoque> buscarEstoque(DataBaseConection banco){
        List<Estoque> estoque = new ArrayList<>();
        ResultSet resultSet = null;

        try{
            String sql = "SELECT * FROM estoque";
            resultSet = banco.statement.executeQuery(sql);

            while (resultSet.next()) {
                Estoque est = new Estoque();

                est.codLivro = resultSet.getInt("cod_livro");
                est.qtdEstoque = resultSet.getInt("qtd_estoque");

                estoque.add(est);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return estoque;
    }
}