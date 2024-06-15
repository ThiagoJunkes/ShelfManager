import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Record;

import dao.DataBaseConection;

public class TesteConexao {
    public static void main(String[] args) {
        DataBaseConection dbConnection = new DataBaseConection();
        Session session = dbConnection.getSession();

        try {
            // Executar uma consulta para obter os nomes dos livros, seus ISBNs e o nome da editora
            Result result = session.run(
                    "MATCH (livro:Livro)-[:publicado_por]->(editora:Editora) " +
                            "RETURN livro.titulo AS titulo, livro.isbn AS isbn, editora.nome AS editora");

            // Processar o resultado
            System.out.println("Livros cadastrados:");
            while (result.hasNext()) {
                Record record = result.next();
                String titulo = record.get("titulo").asString();
                long isbn = record.get("isbn").asLong();
                String editora = record.get("editora").asString();

                System.out.println("Livro: " + titulo + ", ISBN: " + isbn + ", Editora: " + editora);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConnection.closeConnection();
        }
    }
}
