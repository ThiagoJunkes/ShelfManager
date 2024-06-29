package model;

import dao.DataBaseConection;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Endereco {
    private long codEndereco;
    private String rua;
    private String cidade;
    private String estado;
    private int cep;
    private String complemento;

    // Getters e Setters
    public long getCodEndereco() {
        return codEndereco;
    }

    public void setCodEndereco(long codEndereco) {
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

    public static boolean editarEndereco(Cliente cliente, DataBaseConection banco) {
        boolean sucesso = false;

        try (Session session = banco.getSession()) {
            String query = "MATCH (e:Endereco {cpf_morador: '" + cliente.getCpf() + "'}) " +
                    "SET e.rua = '" + cliente.endereco.getRua() + "', " +
                    "    e.cidade = '" + cliente.endereco.getCidade() + "', " +
                    "    e.estado = '" + cliente.endereco.getEstado() + "', " +
                    "    e.cep = " + cliente.endereco.getCep() + ", " +
                    "    e.complemento = '" + cliente.endereco.getComplemento() + "' " +
                    "RETURN e";

            List<Record> result = session.writeTransaction(tx -> {
                Result resultSet = tx.run(query);
                return resultSet.list();
            });

            if (!result.isEmpty()) {
                System.out.println("Endereço atualizado com sucesso!");
                sucesso = true;
            } else {
                System.out.println("Nenhum endereço atualizado.");
            }
        } catch (Exception e) {
            System.out.println("Falha ao atualizar endereço.");
        }

        return sucesso;
    }

}

