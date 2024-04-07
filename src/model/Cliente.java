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

    public void printClienteSemFormatacao() {
        System.out.println("Cliente: " + codCliente + " | Data Cadastro: " + dataCadastro);
        System.out.println("1 - Nome:          " + nome);
        System.out.println("2 - Sobrenome:     " + sobrenome);
        System.out.println("3 - CPF:           " + cpf);
        System.out.println("4 - Email:         " + emailCliente);
        System.out.println("5 - Telefone:      " + telefoneCliente);
        System.out.println("6 - Cod Endereco:  " + codEndereco);
    }

    public static boolean editarCliente(Cliente cliente, DataBaseConection banco) {
        boolean sucesso = false;

        try {
            String sql = "UPDATE clientes " +
                    "SET nome = ?, sobrenome = ?, cpf = ?, email_cliente = ?, telefone_cliente = ?, cod_endereco = ? " +
                    "WHERE cod_cliente = ?";
            banco.preparedStatement = banco.connection.prepareStatement(sql);

            banco.preparedStatement.setString(1, cliente.getNome());
            banco.preparedStatement.setString(2, cliente.getSobrenome());
            banco.preparedStatement.setLong(3, cliente.getCpf());
            banco.preparedStatement.setString(4, cliente.getEmailCliente());
            banco.preparedStatement.setString(5, cliente.getTelefoneCliente());
            banco.preparedStatement.setInt(6, cliente.getCodEndereco());
            banco.preparedStatement.setInt(7, cliente.getCodCliente());

            int linhasAfetadas = banco.preparedStatement.executeUpdate();
            if (linhasAfetadas > 0) {
                sucesso = true;
                System.out.println("Cliente atualizado com sucesso!");
            } else {
                System.out.println("Nenhum cliente atualizado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (banco.preparedStatement != null) {
                    banco.preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return sucesso;
    }


    public static List<Cliente> buscarClientes(DataBaseConection banco){
        List<Cliente> clientes = new ArrayList<>();
        ResultSet resultSet = null;

        try{
            String sql = "SELECT * FROM clientes ORDER BY cod_cliente";
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

