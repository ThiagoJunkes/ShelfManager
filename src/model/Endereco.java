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

    public static boolean editarEndereco(Endereco endereco, DataBaseConection banco) {
        boolean sucesso = false;

        try (Session session = banco.getSession()) {
            String query = "MATCH (e:Endereco {cod_endereco: " + endereco.getCodEndereco() + "}) " +
                    "SET e.rua = '" + endereco.getRua() + "', " +
                    "    e.cidade = '" + endereco.getCidade() + "', " +
                    "    e.estado = '" + endereco.getEstado() + "', " +
                    "    e.cep = " + endereco.getCep() + ", " +
                    "    e.complemento = '" + endereco.getComplemento() + "' " +
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
            e.printStackTrace();
            System.out.println("Falha ao atualizar endereço.");
        }

        return sucesso;
    }

    public static void excluirEndereco(Endereco endereco, DataBaseConection banco) {
        try (Session session = banco.getSession()) {
            String query = "MATCH (e:Endereco {cod_endereco: " + endereco.getCodEndereco() + "}) " +
                    "DETACH DELETE e";

            session.writeTransaction(tx -> {
                tx.run(query);
                return null;
            });

            System.out.println("Endereço excluído com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Falha ao excluir endereço.");
        }
    }

    public static List<Endereco> buscarEnderecos(DataBaseConection banco) {
        List<Endereco> enderecos = new ArrayList<>();

        try (Session session = banco.getSession()) {
            String query = "MATCH (e:Endereco) RETURN e";

            List<Record> result = session.readTransaction(tx -> {
                Result resultSet = tx.run(query);
                return resultSet.list();
            });

            for (Record record : result) {
                Node enderecoNode = record.get("e").asNode();
                Endereco endereco = new Endereco();

                endereco.setCodEndereco(enderecoNode.get("cod_endereco").asInt());
                endereco.setRua(enderecoNode.get("rua").asString());
                endereco.setCidade(enderecoNode.get("cidade").asString());
                endereco.setEstado(enderecoNode.get("estado").asString());
                endereco.setCep(enderecoNode.get("cep").asInt());
                endereco.setComplemento(enderecoNode.get("complemento").asString());

                enderecos.add(endereco);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Falha ao buscar endereços.");
        }

        return enderecos;
    }


}

