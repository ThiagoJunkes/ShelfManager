package model;

import dao.DataBaseConection;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private int codCliente;
    private String nome;
    private String sobrenome;
    private long cpf;
    private String emailCliente;
    private String telefoneCliente;
    private Date dataCadastro;
    private int codEndereco;

    // Getters e Setters
    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public long getCpf() {
        return cpf;
    }

    public void setCpf(long cpf) {
        this.cpf = cpf;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getTelefoneCliente() {
        return telefoneCliente;
    }

    public void setTelefoneCliente(String telefoneCliente) {
        this.telefoneCliente = telefoneCliente;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public int getCodEndereco() {
        return codEndereco;
    }

    public void setCodEndereco(int codEndereco) {
        this.codEndereco = codEndereco;
    }

    public static List<Cliente> buscarClientes(DataBaseConection banco){
        List<Cliente> clientes = new ArrayList<>();
        ResultSet resultSet = null;

        try{
            String sql = "SELECT * FROM clientes";
            resultSet = banco.statement.executeQuery(sql);

            while (resultSet.next()) {
                Cliente cliente = new Cliente();

                cliente.codCliente = resultSet.getInt("cod_cliente");
                cliente.nome = resultSet.getString("nome");
                cliente.sobrenome = resultSet.getString("sobrenome");
                cliente.cpf = resultSet.getLong("cpf");
                cliente.emailCliente = resultSet.getString("email_cliente");
                cliente.telefoneCliente = resultSet.getString("telefone_cliente");
                cliente.dataCadastro = resultSet.getDate("data_cadastro");
                cliente.codEndereco = resultSet.getInt("cod_endereco");

                clientes.add(cliente);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }


        return  clientes;
    }
}

