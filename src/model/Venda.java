package model;

import dao.DataBaseConection;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Venda> buscarVendas(DataBaseConection banco){
        List<Venda> vendas = new ArrayList<>();

        try (Session session = banco.getSession()) {
            String cypherQuery = "MATCH (v:Venda)-[:FEITA_POR]->(c:Cliente) RETURN v, c";
            Result result = session.run(cypherQuery);

            while (result.hasNext()) {
                var record = result.next();
                var vendaNode = record.get("v").asNode();
                var clienteNode = record.get("c").asNode();

                Venda venda = new Venda();
                venda.codVenda = vendaNode.get("codVenda").asLong();
                venda.valorVenda = vendaNode.get("valor").asDouble();
                venda.dataVenda = vendaNode.get("data").asString();
                venda.metodoPag = vendaNode.get("metodo_pag").asString();
                venda.codCliente = clienteNode.get("cpf").asString();

                vendas.add(venda);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vendas;
    }

    public static void adicionarItemVenda(DataBaseConection banco, String codCliente, int metodoPag, double precoTotal, List<String> livros){
        try (Session session = banco.getSession()) {
            session.writeTransaction(new TransactionWork<Void>() {
                @Override
                public Void execute(Transaction tx) {
                    // Criar a venda
                    String createVendaQuery = String.format(
                            "CREATE (v:Venda {valor: %.2f, data: date(), metodo_pag: '%s'}) RETURN id(v) AS codVenda",
                            precoTotal, Venda.metodosPagamento[metodoPag - 1]
                    );

                    Result vendaResult = tx.run(createVendaQuery);
                    int codVenda = vendaResult.single().get("codVenda").asInt();

                    // Relacionar a venda com o cliente
                    String relateVendaClienteQuery = String.format(
                            "MATCH (c:Cliente {cpf: '%s'}), (v:Venda {valor: %.2f}) CREATE (v)-[:FEITA_POR]->(c)",
                            codCliente, precoTotal
                    );
                    tx.run(relateVendaClienteQuery);

                    // Relacionar a venda com os livros
                    for (String livro : livros) {
                        String[] itens = livro.split("\\|");
                        String titulo = itens[0];
                        int qtd = Integer.parseInt(itens[1]);

                        String relateVendaLivroQuery = String.format(
                                "MATCH (l:Livro {titulo: '%s'}), (v:Venda {valor: %.2f}) CREATE (v)-[:vendido]->(l)",
                                titulo, precoTotal
                        );
                        tx.run(relateVendaLivroQuery);

                        // Atualizar a quantidade no estoque (não implementado)
                    }

                    return null;
                }
            });

            System.out.println("Venda registrada com sucesso!");
        } catch (Exception e) {
            System.out.println("Não foi possivel registrar a venda!");
            e.printStackTrace();
        }
    }
}
