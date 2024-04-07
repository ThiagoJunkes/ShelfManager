package dao;

import java.sql.*;

public class DataBaseConection {
    private String host = "localhost"; // endereço IP do seu servidor PostgreSQL
    private String port = "5432"; // porta padrão do PostgreSQL
    private String databaseName = "ShelfManager"; // nome do banco de dados
    private String username = "postgres"; // nome de usuário do PostgreSQL
    private String password = "admin"; // senha do PostgreSQL

    private String connectionString = "jdbc:postgresql://" + host + ":" + port + "/" + databaseName + "?user=" + username + "&password=" + password;

    public Connection connection = null;
    public Statement statement = null;
    public PreparedStatement preparedStatement;
    public DataBaseConection(){
        try{

        // Estabelecer conexão
        connection = DriverManager.getConnection(connectionString);
        System.out.println("Conexão com o Banco de Dados estabelecida com sucesso!");

        // Criar uma declaração
        statement = connection.createStatement();
        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("O sistema não irá funcionar corretamente! Verifique sua conexão com o Banco de Dados.");
        }
    }

    public void closeConnection(){
        // Fechar recursos
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
