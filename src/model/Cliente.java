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

    public Endereco endereco;

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

        if (endereco != null) {
            System.out.println("Endereço:");
            System.out.println("6 - Rua:           " + endereco.getRua());
            System.out.println("7 - Cidade:        " + endereco.getCidade());
            System.out.println("8 - Estado:        " + endereco.getEstado());
            System.out.println("9 - CEP:           " + endereco.getCep());
            System.out.println("10- Complemento:   " + endereco.getComplemento());
        }
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
            String sql = "SELECT c.*, e.* FROM clientes c JOIN enderecos e on c.cod_endereco = e.cod_endereco ORDER BY cod_cliente";
            resultSet = banco.statement.executeQuery(sql);

            while (resultSet.next()) {
                Cliente cliente = new Cliente();
                Endereco endereco = new Endereco();

                cliente.codCliente = resultSet.getInt("cod_cliente");
                cliente.nome = resultSet.getString("nome");
                cliente.sobrenome = resultSet.getString("sobrenome");
                cliente.cpf = resultSet.getLong("cpf");
                cliente.emailCliente = resultSet.getString("email_cliente");
                cliente.telefoneCliente = resultSet.getString("telefone_cliente");
                cliente.dataCadastro = resultSet.getDate("data_cadastro");
                cliente.codEndereco = resultSet.getInt("cod_endereco");
                endereco.setCodEndereco(resultSet.getInt("cod_endereco"));
                endereco.setRua(resultSet.getString("rua"));
                endereco.setCidade(resultSet.getString("cidade"));
                endereco.setEstado(resultSet.getString("estado"));
                endereco.setCep(resultSet.getInt("cep"));
                endereco.setComplemento(resultSet.getString("complemento"));

                cliente.endereco = endereco;
                clientes.add(cliente);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return  clientes;
    }

    public static boolean adicionarCliente(Cliente cliente, DataBaseConection banco) {
        boolean sucesso = false;

        try {
            // Insere o endereço primeiro
            String sqlEndereco = "INSERT INTO enderecos (rua, cidade, estado, cep, complemento) VALUES (?, ?, ?, ?, ?) RETURNING cod_endereco";
            banco.preparedStatement = banco.connection.prepareStatement(sqlEndereco);
            banco.preparedStatement.setString(1, cliente.endereco.getRua());
            banco.preparedStatement.setString(2, cliente.endereco.getCidade());
            banco.preparedStatement.setString(3, cliente.endereco.getEstado());
            banco.preparedStatement.setInt(4, cliente.endereco.getCep());
            banco.preparedStatement.setString(5, cliente.endereco.getComplemento());

            // Executa a inserção e obtém o ID do endereço inserido
            ResultSet resultSetEndereco = banco.preparedStatement.executeQuery();
            int codEndereco = -1; // Valor inicial inválido
            if (resultSetEndereco.next()) {
                codEndereco = resultSetEndereco.getInt(1); // Pega o primeiro resultado da primeira coluna
            }

            if (codEndereco != -1) {
                // Se o endereço foi inserido com sucesso, insere o cliente
                String sqlCliente = "INSERT INTO clientes (nome, sobrenome, cpf, email_cliente, telefone_cliente, cod_endereco) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
                banco.preparedStatement = banco.connection.prepareStatement(sqlCliente);
                banco.preparedStatement.setString(1, cliente.getNome());
                banco.preparedStatement.setString(2, cliente.getSobrenome());
                banco.preparedStatement.setLong(3, cliente.getCpf());
                banco.preparedStatement.setString(4, cliente.getEmailCliente());
                banco.preparedStatement.setString(5, cliente.getTelefoneCliente());
                banco.preparedStatement.setInt(6, codEndereco);

                int linhasAfetadasCliente = banco.preparedStatement.executeUpdate();
                if (linhasAfetadasCliente > 0) {
                    sucesso = true;
                    System.out.println("Cliente adicionado com sucesso!");
                } else {
                    System.out.println("Falha ao adicionar cliente.");
                }
            } else {
                System.out.println("Falha ao obter o ID do endereço.");
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

    public static void excluirCliente(Cliente cliente, DataBaseConection banco) {

        try {
            String sqlVerificarCompras = "SELECT cod_venda FROM vendas WHERE cod_cliente = ?";
            banco.preparedStatement = banco.connection.prepareStatement(sqlVerificarCompras);
            banco.preparedStatement.setInt(1, cliente.getCodCliente());
            ResultSet rs = banco.preparedStatement.executeQuery();

            if (rs.next()) {
                System.out.println("Não é possível excluir o cliente pois ele já realizou uma compra.");
            } else {
                String sqlCliente = "DELETE FROM clientes WHERE cod_cliente = ?";
                banco.preparedStatement = banco.connection.prepareStatement(sqlCliente);
                banco.preparedStatement.setInt(1, cliente.getCodCliente());

                int linhasAfetadasCliente = banco.preparedStatement.executeUpdate();
                if (linhasAfetadasCliente > 0) {
                    System.out.println("Cliente excluído com sucesso!");
                    Endereco.excluirEndereco(cliente.endereco, banco);
                } else {
                    System.out.println("Falha ao excluir cliente.");
                }
            }

            rs.close();
        } catch (SQLException e) {
            System.out.println("Falha ao excluir cliente.");
        } finally {
            try {
                if (banco.preparedStatement != null) {
                    banco.preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

