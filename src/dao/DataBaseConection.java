package dao;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

public class DataBaseConection {
    private final String uri = "neo4j://localhost:7687";
    private final String username = "neo4j";
    private final String password = "12345678";

    private Driver driver;
    private Session session;

    public DataBaseConection() {
        try {
            // Estabelecer conexão
            driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password));
            session = driver.session();
            System.out.println("Conexão com o Neo4j estabelecida com sucesso!");
        } catch (Exception e) {
            System.out.println("Verifique sua conexão com o Neo4j. O sistema não irá funcionar corretamente!");
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        // Fechar recursos
        try {
            if (session != null) {
                session.close();
            }
            if (driver != null) {
                driver.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Session getSession() {
        closeConnection();
        driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password));
        session = driver.session();
        return session;
    }

}
