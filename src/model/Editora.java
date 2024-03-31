package model;

import dao.DataBaseConection;

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
}

