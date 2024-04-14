package model;

import dao.DataBaseConection;

import java.sql.Date;
import java.sql.PreparedStatement;
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

    public static void adicionarItemVenda(DataBaseConection banco, int codCliente, int metodoPag, float precoTotal, List<String> livros){

        String sqlVendas = "INSERT INTO vendas (valor_venda, metodo_pag, cod_cliente)  VALUES (?, ?, ?)";
        String sqlItensVendas = "INSERT INTO itens_vendas (cod_pedido, cod_livro, qtd_livros) VALUES ";
        boolean first = true;
        for (String vendas: livros) {
            String[] itens = vendas.split("\\|");
            if(first) sqlItensVendas += "(?, "+ itens[0] + ", " + itens[1] + ")";
            else      sqlItensVendas += ", (?, "+ itens[0] + ", " + itens[1] + ")";
            first = false;
        }
        System.out.println(sqlItensVendas);
        try {

            PreparedStatement statementVendas = banco.connection.prepareStatement(sqlVendas, PreparedStatement.RETURN_GENERATED_KEYS);
            statementVendas.setFloat(1, precoTotal);
            statementVendas.setString(2, Venda.metodosPagamento[metodoPag-1]);
            statementVendas.setInt(3, codCliente);

            int rowsInsertedVenda = statementVendas.executeUpdate();

            // Obtendo o código da venda inserida
            ResultSet generatedKeys = statementVendas.getGeneratedKeys();
            int codVendaInserido = -1;
            if (generatedKeys.next()) {
                codVendaInserido = generatedKeys.getInt(1);
            }

            if (rowsInsertedVenda > 0 && codVendaInserido != -1) {
                // Inserindo a quantidade em estoque na tabela estoque
                PreparedStatement statementEstoque = banco.connection.prepareStatement(sqlItensVendas);
                for (int i = 1; i <= livros.size(); i++){
                    statementEstoque.setInt(i,codVendaInserido);
                }


                int rowsInsertedItensVendas= statementEstoque.executeUpdate();

                if (rowsInsertedItensVendas > 0) {
                    System.out.println("Venda registrada com sucesso!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Não foi possivel registrar a venda!");
        }
    }
}


