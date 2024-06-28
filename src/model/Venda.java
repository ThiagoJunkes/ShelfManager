package model;

import dao.DataBaseConection;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Venda {
    private long codVenda;
    private double valorVenda;
    private String dataVenda;
    private String metodoPag;
    private String codCliente;

    public static String[] metodosPagamento = {
            "Cartão de Crédito",
            "Cartão de Débito",
            "Boleto Bancário",
            "Dinheiro",
            "Pix"
    };

    // Getters e Setters
    public long getCodVenda() {
        return codVenda;
    }

    public void setCodVenda(long codVenda) {
        this.codVenda = codVenda;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getMetodoPag() {
        return metodoPag;
    }

    public void setMetodoPag(String metodoPag) {
        this.metodoPag = metodoPag;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public static void adicionarItemVenda(DataBaseConection banco, ItemVenda vendaRealizada) {
        try (Session session = banco.getSession()) {
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = currentDate.format(formatter);
            session.writeTransaction(new TransactionWork<Void>() {
                @Override
                public Void execute(Transaction tx) {
                    String buscaCodigoQuery = "MATCH (v:Venda) RETURN MAX(v.codigo) AS maxCodVenda";
                    Result buscaResult = tx.run(buscaCodigoQuery);
                    int novoCod = buscaResult.single().get("maxCodVenda").asInt() + 1;

                    // Criar a venda
                    String createVendaQuery = String.format(
                            Locale.US,
                            "CREATE (v:Venda {codigo: %d, valor: %.2f, data: '%s', metodo_pag: '%s'}) RETURN v.codigo AS codVenda",
                            novoCod, vendaRealizada.venda.getValorVenda(), formattedDate, vendaRealizada.venda.getMetodoPag()
                    );

                    Result vendaResult = tx.run(createVendaQuery);
                    int codVenda = vendaResult.single().get("codVenda").asInt();

                    // Relacionar a venda com o cliente
                    String relateVendaClienteQuery = String.format(
                            "MATCH (c:Cliente {cpf: '%s'}), (v:Venda {codigo: %d}) CREATE (v)-[:FEITA_POR]->(c)",
                            vendaRealizada.cliente.getCpf(), codVenda
                    );
                    tx.run(relateVendaClienteQuery);

                    // Relacionar a venda com os livros
                    for (Livro livro : vendaRealizada.livros) {
                        String relateVendaLivroQuery = String.format(
                                "MATCH (l:Livro {isbn: '%s'}), (v:Venda {codigo: %d}) CREATE (v)-[:vendido]->(l)",
                                livro.getIsbn(), codVenda
                        );
                        tx.run(relateVendaLivroQuery);

                        // Atualizar a quantidade no estoque
                        String updateEstoqueQuery = String.format(
                                "MATCH (l:Livro {isbn: '%s'}) SET l.qtdEstoque = l.qtdEstoque - 1",
                                livro.getIsbn()
                        );
                        tx.run(updateEstoqueQuery);
                    }

                    return null;
                }
            });

            System.out.println("Venda registrada com sucesso!");
        } catch (Exception e) {
            System.out.println("Não foi possível registrar a venda!");
            e.printStackTrace();
        }
    }
}
