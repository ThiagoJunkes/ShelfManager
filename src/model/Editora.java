package model;

import dao.DataBaseConection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public static List<Editora> buscarEditoras(DataBaseConection banco){
        List<Editora> editoras = new ArrayList<>();
        ResultSet resultSet = null;

        try{
            String sql = "SELECT * FROM editoras";
            resultSet = banco.statement.executeQuery(sql);

            while (resultSet.next()) {
                Editora editora = new Editora();

                editora.codEditora = resultSet.getInt("cod_editora");
                editora.nomeEditora = resultSet.getString("nome_editora");
                editora.nomeContato = resultSet.getString("nome_contato");
                editora.emailEditora = resultSet.getString("email_editora");
                editora.telefoneEditora = resultSet.getString("telefone_editora");

                editoras.add(editora);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }


        return  editoras;
    }

    public static void editarEditora(Editora editora, DataBaseConection banco) {
        try {
            String sql = "UPDATE editoras SET nome_editora = ?, nome_contato = ?, email_editora = ?, telefone_editora = ? WHERE cod_editora = ?";
            banco.preparedStatement = banco.connection.prepareStatement(sql);

            banco.preparedStatement.setString(1, editora.getNomeEditora());
            banco.preparedStatement.setString(2, editora.getNomeContato());
            banco.preparedStatement.setString(3, editora.getEmailEditora());
            banco.preparedStatement.setString(4, editora.getTelefoneEditora());
            banco.preparedStatement.setInt(5, editora.getCodEditora());

            int linhasAfetadas = banco.preparedStatement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Editora atualizada com sucesso!");
            } else {
                System.out.println("Falha ao atualizar editora.");
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
    }
    public static boolean adicionarEditora(Editora editora, DataBaseConection banco) {
        boolean sucesso = false;
        try {
            String sql = "INSERT INTO editoras (nome_editora, nome_contato, email_editora, telefone_editora) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = banco.connection.prepareStatement(sql);
            preparedStatement.setString(1, editora.getNomeEditora());
            preparedStatement.setString(2, editora.getNomeContato());
            preparedStatement.setString(3, editora.getEmailEditora());
            preparedStatement.setString(4, editora.getTelefoneEditora());

            int linhasAfetadas = preparedStatement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Editora adicionada com sucesso.");
                sucesso = true;
            } else {
                System.out.println("Falha ao adicionar editora.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar editora: " + e.getMessage());
            System.out.println("Falha ao adicionar editora.");
        }
        return sucesso;
    }
    public static void excluirEditora(Editora editora, DataBaseConection banco) {
        try {
            // Verificar se existem livros associados à editora
            String sqlLivros = "SELECT * FROM livros WHERE cod_editora = ?";
            banco.preparedStatement = banco.connection.prepareStatement(sqlLivros);
            banco.preparedStatement.setInt(1, editora.getCodEditora());
            ResultSet resultSet = banco.preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Não é possível excluir a editora pois existem livros associados a ela.");
                return;
            }

            // Se não há livros, então pode excluir a editora
            String sql = "DELETE FROM editoras WHERE cod_editora = ?";
            banco.preparedStatement = banco.connection.prepareStatement(sql);
            banco.preparedStatement.setInt(1, editora.getCodEditora());

            int linhasAfetadas = banco.preparedStatement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Editora excluída com sucesso!");
            } else {
                System.out.println("Falha ao excluir editora.");
            }
        } catch (SQLException e) {
            System.out.println("Falha ao excluir editora.");
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

