import org.neo4j.driver.Session;
import org.neo4j.driver.Result;

import dao.DataBaseConection;

public class TesteConexao {
    public static void main(String[] args) {
        DataBaseConection dbConnection = new DataBaseConection();
        Session session = dbConnection.getSession();

        try {
            // Executar uma consulta simples
            Result result = session.run("MATCH (n) RETURN count(n) AS nodeCount");

            // Processar o resultado
            if (result.hasNext()) {
                int nodeCount = result.next().get("nodeCount").asInt();
                System.out.println("Número de nós no Neo4j: " + nodeCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConnection.closeConnection();
        }
    }
}
