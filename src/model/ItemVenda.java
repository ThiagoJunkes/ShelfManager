package model;

import dao.DataBaseConection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemVenda {
    private int codPedido;
    private int codLivro;
    private int qtdLivros;

    // Getters e Setters
    public int getCodPedido() {
        return codPedido;
    }

    public void setCodPedido(int codPedido) {
        this.codPedido = codPedido;
    }

    public int getCodLivro() {
        return codLivro;
    }

    public void setCodLivro(int codLivro) {
        this.codLivro = codLivro;
    }

    public int getQtdLivros() {
        return qtdLivros;
    }

    public void setQtdLivros(int qtdLivros) {
        this.qtdLivros = qtdLivros;
    }

    public static List<ItemVenda> buscarItensVenda(DataBaseConection banco){
        List<ItemVenda> itensVendas = new ArrayList<>();
        ResultSet resultSet = null;

        try{
            String sql = "SELECT * FROM itens_vendas";
            resultSet = banco.statement.executeQuery(sql);

            while (resultSet.next()) {
                ItemVenda itemVenda = new ItemVenda();

                itemVenda.codPedido = resultSet.getInt("cod_pedido");
                itemVenda.codLivro = resultSet.getInt("cod_livro");
                itemVenda.qtdLivros = resultSet.getInt("qtd_livros");

                itensVendas.add(itemVenda);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return itensVendas;
    }
}