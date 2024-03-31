package model;

import dao.DataBaseConection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Endereco {
    private int codEndereco;
    private String rua;
    private String cidade;
    private String estado;
    private int cep;
    private String complemento;

    // Getters e Setters
    public int getCodEndereco() {
        return codEndereco;
    }

    public void setCodEndereco(int codEndereco) {
        this.codEndereco = codEndereco;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public static List<Endereco> buscarEnderecos(DataBaseConection banco){
        List<Endereco> enderecos = new ArrayList<>();
        ResultSet resultSet = null;

        try{
            String sql = "SELECT * FROM enderecos";
            resultSet = banco.statement.executeQuery(sql);

            while (resultSet.next()) {
                Endereco endereco = new Endereco();

                endereco.codEndereco = resultSet.getInt("cod_endereco");
                endereco.rua = resultSet.getString("rua");
                endereco.cidade = resultSet.getString("cidade");
                endereco.estado = resultSet.getString("estado");
                endereco.cep = resultSet.getInt("cep");
                endereco.complemento = resultSet.getString("complemento");

                enderecos.add(endereco);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return  enderecos;
    }
}

