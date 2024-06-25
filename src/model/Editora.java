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
        System.out.println("Editora: " + codEditora + " | " + nomeEditora);
        System.out.println("1. Nome do Contato:     " + nomeContato);
        System.out.println("2. Email da Editora:    " + emailEditora);
        System.out.println("3. Telefone da Editora: " + telefoneEditora);
    }
    public static List<Editora> buscarEditoras(DataBaseConection banco) {
        List<Editora> editoras = new ArrayList<>();
        int cod_editora = 1;

        try (Session session = banco.getSession()) {
            try (Transaction tx = session.beginTransaction()) {
                String query = "MATCH (editora:Editora) RETURN editora";
                Result result = tx.run(query);

                while (result.hasNext()) {
                    Record record = result.next();
                    Node editoraNode = record.get("editora").asNode();

                    Editora editora = new Editora();
                    editora.codEditora = cod_editora;
                    editora.nomeEditora = editoraNode.get("nome").asString();
                    editora.nomeContato = editoraNode.get("nome_contato").asString();
                    editora.emailEditora = editoraNode.get("email").asString();
                    editora.telefoneEditora = editoraNode.get("telefone").asString();
                    editoras.add(editora);
                    cod_editora ++;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar Editoras: " + e.getMessage());
        }

        return editoras;
    }

    public static boolean editarEditora(Editora editora, DataBaseConection banco) {
        try (Session session = banco.getSession()) {
            String query = "MATCH (editora:Editora {nome: '" + editora.getNomeEditora() + "'}) " +
                    "SET editora.nome_contato = '" + editora.getNomeContato() + "', " +
                    "    editora.telefone = '" + editora.getTelefoneEditora() + "', " +
                    "    editora.email = '" + editora.getEmailEditora() + "' " +
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

            // Query para verificar se já existe uma editora com o mesmo nome
            String checkQuery = "MATCH (e:Editora {nome: '" + editora.getNomeEditora() + "'}) RETURN e";
            boolean editoraExiste = session.readTransaction(tx -> {
                Result result = tx.run(checkQuery);
                return result.hasNext();
            });

            if (editoraExiste) {
                System.out.println("Não foi possível adicionar a editora pois já existe uma com esse nome!");
                return false;
            }

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
            String queryVerificarLivros = "MATCH (livro:Livro)-[:publicado_por]->(editora:Editora {nome: '" + editora.getNomeEditora() + "'}) RETURN livro";

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

