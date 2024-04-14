package model;

import dao.DataBaseConection;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Venda {
    private int codVenda;
    private double valorVenda;
    private Date dataVenda;
    private String metodoPag;
    private int codCliente;

    public static String[] metodosPagamento = {
        "Cartão de Crédito",
        "Cartão de Débito",
        "Boleto Bancário",
        "Dinheiro",
        "Pix"
    };

    // Getters e Setters
    public int getCodVenda() {
        return codVenda;
    }

    public void setCodVenda(int codVenda) {
        this.codVenda = codVenda;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getMetodoPag() {
        return metodoPag;
    }

    public void setMetodoPag(String metodoPag) {
        this.metodoPag = metodoPag;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente){
        this.codCliente = codCliente;
    }

    public static List<Venda> buscarVendas(DataBaseConection banco){
        List<Venda> vendas = new ArrayList<>();
        ResultSet resultSet = null;

        try{
            String sql = "SELECT * FROM vendas";
            resultSet = banco.statement.executeQuery(sql);

            while (resultSet.next()) {
                Venda venda = new Venda();

                venda.codVenda = resultSet.getInt("cod_venda");
                venda.valorVenda = resultSet.getDouble("valor_venda");
                venda.dataVenda = resultSet.getDate("data_venda");
                venda.metodoPag = resultSet.getString("metodo_pag");
                venda.codCliente = resultSet.getInt("cod_cliente");

                vendas.add(venda);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return  vendas;
    }

    public static void adicionarItemVenda(DataBaseConection banco, int codCliente, int metodoPag, List<String> livros){
        for (String livroVenda : livros) {
            System.out.println( codCliente + " | " + metodoPag + " | " + livroVenda);
        }
    }
}


