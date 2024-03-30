package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConection {
    private String host = "localhost"; // endereço IP do seu servidor PostgreSQL
    private String port = "5432"; // porta padrão do PostgreSQL
    private String databaseName = "ShelfManager"; // nome do banco de dados
    private String username = "postgres"; // nome de usuário do PostgreSQL
    private String password = "admin"; // senha do PostgreSQL

    private String connectionString = "jdbc:postgresql://" + host + ":" + port + "/" + databaseName + "?user=" + username + "&password=" + password;

    public Connection connection = null;
    public Statement statement = null;
    public DataBaseConection(){
        try{

        // Estabelecer conexão
        connection = DriverManager.getConnection(connectionString);
        System.out.println("Conexão estabelecida com sucesso!");

        // Criar uma declaração
        statement = connection.createStatement();
        }catch (SQLException e) {
            e.printStackTrace();
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
