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

    public static boolean editarEndereco(Endereco endereco, DataBaseConection banco) {
        boolean sucesso = false;

        try {
            String sql = "UPDATE enderecos " +
                    "SET rua = ?, cidade = ?, estado = ?, cep = ?, complemento = ? " +
                    "WHERE cod_endereco = ?";
            banco.preparedStatement = banco.connection.prepareStatement(sql);

            banco.preparedStatement.setString(1, endereco.getRua());
            banco.preparedStatement.setString(2, endereco.getCidade());
            banco.preparedStatement.setString(3, endereco.getEstado());
            banco.preparedStatement.setInt(4, endereco.getCep());
            banco.preparedStatement.setString(5, endereco.getComplemento());
            banco.preparedStatement.setInt(6, endereco.getCodEndereco());


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

    public static void excluirEndereco(Endereco endereco, DataBaseConection banco) {
        try {
            String sqlEndereco = "DELETE FROM enderecos WHERE cod_endereco = ?";
            banco.preparedStatement = banco.connection.prepareStatement(sqlEndereco);
            banco.preparedStatement.setInt(1, endereco.getCodEndereco());

            int linhasAfetadasEndereco = banco.preparedStatement.executeUpdate();

        } catch (SQLException e) {
        }
    }

}

