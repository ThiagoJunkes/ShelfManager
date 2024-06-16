package model;

import dao.DataBaseConection;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Editora {
    private int codEditora;
    private String nomeEditora;
    private String nomeContato;
    private String emailEditora;
    private String telefoneEditora;

    // Getters e Setters
    public int getCodEditora() {
        return codEditora;
    }

    public void setCodEditora(int codEditora) {
        this.codEditora = codEditora;
    }

    public String getNomeEditora() {
        return nomeEditora;
    }

    public void setNomeEditora(String nomeEditora) {
        this.nomeEditora = nomeEditora;
    }

    public String getNomeContato() {
        return nomeContato;
    }

    public void setNomeContato(String nomeContato) {
        this.nomeContato = nomeContato;
    }

    public String getEmailEditora() {
        return emailEditora;
    }

    public void setEmailEditora(String emailEditora) {
        this.emailEditora = emailEditora;
    }

    public String getTelefoneEditora() {
        return telefoneEditora;
    }

    public void setTelefoneEditora(String telefoneEditora) {
        this.telefoneEditora = telefoneEditora;
    }

    public void printEditoraSemFormatacao(){
        System.out.println("Editora: " + codEditora);
        System.out.println("1. Nome da Editora:     " + nomeEditora);
        System.out.println("2. Nome do Contato:     " + nomeContato);
        System.out.println("3. Email da Editora:    " + emailEditora);
        System.out.println("4. Telefone da Editora: " + telefoneEditora);
    }
    public static List<Editora> buscarEditoras(DataBaseConection banco) {
        List<Editora> editoras = new ArrayList<>();

        try (Session session = banco.getSession()) {
            String query = "MATCH (editora:Editora) RETURN editora.codEditora AS codEditora, " +
                    "editora.nomeEditora AS nomeEditora, " +
                    "editora.nomeContato AS nomeContato, " +
                    "editora.email AS emailEditora, " +
                    "editora.telefone AS telefoneEditora";

            List<Record> result = session.readTransaction(tx -> {
                Result resultSet = tx.run(query);
                return resultSet.list();
            });

            for (Record record : result) {
                Editora editora = new Editora();
                editora.codEditora = record.get("codEditora").asInt();
                editora.nomeEditora = record.get("nomeEditora").asString();
                editora.nomeContato = record.get("nomeContato").asString();
                editora.emailEditora = record.get("emailEditora").asString();
                editora.telefoneEditora = record.get("telefoneEditora").asString();
                editoras.add(editora);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return editoras;
    }

    public static boolean editarEditora(Editora editora, DataBaseConection banco) {
        try (Session session = banco.getSession()) {
            String query = "MATCH (editora:Editora {email: '" + editora.getEmailEditora() + "'}) " +
                    "SET editora.nome_editora = '" + editora.getNomeEditora() + "', " +
                    "    editora.nome_contato = '" + editora.getNomeContato() + "', " +
                    "    editora.telefone = '" + editora.getTelefoneEditora() + "' " +
                    "RETURN editora";

            List<Record> result = session.writeTransaction(tx -> {
                Result resultSet = tx.run(query);
                return resultSet.list();
            });

            if (!result.isEmpty()) {
                System.out.println("Editora atualizada com sucesso!");
                return true;
            } else {
                System.out.println("Nenhuma editora atualizada.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Falha ao atualizar editora.");
            return false;
        }
    }

    public static boolean adicionarEditora(Editora editora, DataBaseConection banco) {
        try (Session session = banco.getSession()) {
            String query = "CREATE (editora:Editora {nome: '" + editora.getNomeEditora() + "', " +
                    "nome_contato: '" + editora.getNomeContato() + "', " +
                    "email: '" + editora.getEmailEditora() + "', " +
                    "telefone: '" + editora.getTelefoneEditora() + "'})";

            session.writeTransaction(tx -> {
                tx.run(query);
                return null;
            });

            System.out.println("Editora adicionada com sucesso.");
            return true;
        } catch (Exception e) {
            System.out.println("Falha ao adicionar editora.");
            return false;
        }
    }

    public static boolean excluirEditora(Editora editora, DataBaseConection banco) {
        try (Session session = banco.getSession()) {
            // Verificar se existem livros associados à editora
            String queryVerificarLivros = "MATCH (livro:Livro)-[:EDITORA]->(editora:Editora {nome: '" + editora.getNomeEditora() + "'}) RETURN livro";

            List<Record> livrosAssociados = session.readTransaction(tx -> {
                Result result = tx.run(queryVerificarLivros);
                return result.list();
            });

            if (!livrosAssociados.isEmpty()) {
                System.out.println("Não é possível excluir a editora pois existem livros associados a ela.");
                return false;
            }

            // Se não há livros associados, então pode excluir a editora
            String queryExcluirEditora = "MATCH (editora:Editora {nome: '" + editora.getNomeEditora() + "'}) " +
                    "DETACH DELETE editora";

            session.writeTransaction(tx -> {
                tx.run(queryExcluirEditora);
                return null;
            });

            System.out.println("Editora excluída com sucesso!");
            return true;
        } catch (Exception e) {
            System.out.println("Falha ao excluir editora.");
            return false;
        }
    }

}

