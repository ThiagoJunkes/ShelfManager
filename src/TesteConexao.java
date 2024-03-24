import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TesteConexao {
    public static void main(String[] args) {
        String host = "localhost"; // ou o endereço IP do seu servidor PostgreSQL
        String port = "5432"; // porta padrão do PostgreSQL
        String databaseName = "ShelfManager"; // nome do banco de dados que você criou
        String username = "postgres"; // seu nome de usuário do PostgreSQL
        String password = "admin"; // sua senha do PostgreSQL

        String connectionString = "jdbc:postgresql://" + host + ":" + port + "/" + databaseName +
                "?user=" + username + "&password=" + password;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Estabelecer conexão
            connection = DriverManager.getConnection(connectionString);
            System.out.println("Conexão estabelecida com sucesso!");

            // Criar uma declaração
            statement = connection.createStatement();

            // Executar uma consulta simples
            String sql = "SELECT version()";
            resultSet = statement.executeQuery(sql);

            // Processar o resultado
            while (resultSet.next()) {
                String version = resultSet.getString(1);
                System.out.println("Versão do PostgreSQL: " + version);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fechar recursos
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
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
}
