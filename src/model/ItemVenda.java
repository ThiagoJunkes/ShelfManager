package model;

import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.Values;
import org.neo4j.driver.exceptions.ClientException;
import org.neo4j.driver.types.Node;

import dao.DataBaseConection;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemVenda {
    private int codPedido;
    private int codLivro;
    private int qtdLivros;

    public List<Livro> livros;
    public Venda venda;
    public Cliente cliente;

    public ItemVenda() {
        livros = new ArrayList<>();
    }

    public int getCodPedido() {
        return codPedido;
    }

    public void setCodPedido(int codPedido) {
        this.codPedido = codPedido;
    }

    public int getCodLivro() {
        return codLivro;
    }

    public void setCodLivro(int codLivro) {
        this.codLivro = codLivro;
    }

    public int getQtdLivros() {
        return qtdLivros;
    }

    public void setQtdLivros(int qtdLivros) {
        this.qtdLivros = qtdLivros;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public void printItemVendaFormatado() {
        System.out.println("-------------------------------------------------");
        System.out.println("Código Livro: " + codLivro);
        //System.out.println("  Livro: " + livro.getTitulo() + " ISBN: " + livro.getIsbn());
        System.out.println("  Quantidade: " + qtdLivros);
        System.out.println("  Metodo de Pagamento: " + venda.getMetodoPag());
        System.out.println("  Cliente: " + cliente.getNome() + " " + cliente.getSobrenome() + " CPF: " + cliente.getCpf());
    }

    public void printItemVendaSemFormatacao() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = sdf.format(venda.getDataVenda());

        System.out.println("Venda: " + codPedido);
        System.out.println("1 - Código Livro:        " + codLivro);
        //System.out.println("Livro: " + livro.getTitulo() + " ISBN: " + livro.getIsbn());
        System.out.println("2 - Quantidade:          " + qtdLivros);
        System.out.println("3 - Metodo de Pagamento: " + venda.getMetodoPag());
        System.out.println("4 - Data da Venda:       " + dataFormatada);
        System.out.println("5 - Código Cliente:      " + cliente.getCodCliente());
        System.out.println("Cliente: " + cliente.getNome() + " " + cliente.getSobrenome() + " CPF: " + cliente.getCpf());
    }

    public static List<ItemVenda> buscarItensVenda(DataBaseConection banco) {
        List<ItemVenda> itensVendas = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try (Session session = banco.getSession()) {
            // Query para buscar todas as vendas com seus livros e clientes
            String query = "MATCH (v:Venda)-[r:vendido]->(l:Livro), (v)-[:FEITA_POR]->(c:Cliente) " +
                    "RETURN v, collect({livro: l, qtd: r.qtd}) AS livros, c ORDER BY v.codigo";
            try(Transaction tx = session.beginTransaction()){
                Result result = tx.run(query);

                while (result.hasNext()) {
                    Record record = result.next();

                    Venda venda = new Venda();
                    venda.setCodVenda(record.get("v").asNode().get("codigo").asInt());
                    venda.setValorVenda(record.get("v").asNode().get("valor").asDouble());
                    venda.setMetodoPag(record.get("v").asNode().get("metodo_pag").asString());
                    venda.setDataVenda((record.get("v").asNode().get("data").asString()));
                    // Atribua outros campos de Venda conforme necessário

                    List<Livro> livros = new ArrayList<>();
                    for (Object livroObject  : record.get("livros").asList()) {
                        Map<String, Object> livroMap = (Map<String, Object>) livroObject;
                        Node livroNode = (Node) livroMap.get("livro");
                        long quantidade = (long) livroMap.get("qtd");

                        Livro livro = new Livro();;
                        livro.setTitulo(livroNode.get("titulo").asString());
                        livro.setGenero(livroNode.get("genero").asString());
                        livro.setAutor(livroNode.get("autor").asString());
                        livro.setIsbn(livroNode.get("isbn").asLong());
                        livro.setPreco(livroNode.get("preco").asDouble());
                        livro.setAnoPublicacao(sdf.parse(livroNode.get("ano_publicacao").asString()));
                        livro.setQtdEstoque((int) quantidade);

                        livros.add(livro);
                    }

                    Cliente cliente = new Cliente();
                    cliente.setNome(record.get("c").asNode().get("nome").asString());
                    cliente.setSobrenome(record.get("c").asNode().get("sobrenome").asString());
                    cliente.setCpf(Long.parseLong(record.get("c").asNode().get("cpf").asString()));
                    cliente.setEmailCliente(record.get("c").asNode().get("email").asString());
                    cliente.setTelefoneCliente(record.get("c").asNode().get("telefone").asString());
                    cliente.setDataCadastro(sdf.parse(record.get("c").asNode().get("data_cadastro").asString()));
                    // Atribua outros campos de Cliente conforme necessário

                    ItemVenda itemVenda = new ItemVenda();
                    itemVenda.setCodPedido((int) venda.getCodVenda());
                    itemVenda.setVenda(venda);
                    itemVenda.livros = livros;
                    itemVenda.cliente = cliente;
                    itemVenda.setQtdLivros(livros.size()); // Ajuste conforme sua lógica

                    itensVendas.add(itemVenda);
                }
            }
        } catch (Exception e) {
            System.out.println("Falha ao buscar Vendas!");
        }

        return itensVendas;
    }
    public static void editarItemVenda(DataBaseConection banco, ItemVenda itemVenda, int remover) {
        try {
            Session session = banco.getSession();

            // Atualizar dados do item de venda
            String query = "MATCH (v:Venda)-[r:CONTEM]->(l:Livro) " +
                    "WHERE ID(v) = $codPedido AND ID(l) = $codLivro " +
                    "SET r.qtdLivros = $qtdLivros";
            session.run(query, Values.parameters(
                    "codPedido", itemVenda.getVenda().getCodVenda(),
                    //"codLivro", itemVenda.getLivro().getCodLivro(),
                    "qtdLivros", itemVenda.getQtdLivros()
            ));

            session.close();
            System.out.println("Item de venda atualizado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void excluirItemVenda(DataBaseConection banco, ItemVenda itemVenda) {
        try {
            Session session = banco.getSession();

            // Excluir relacionamento de venda com livro
            String query = "MATCH (v:Venda)-[r:CONTEM]->(l:Livro) " +
                    "WHERE ID(v) = $codPedido AND ID(l) = $codLivro " +
                    "DELETE r";
            session.run(query, Values.parameters(
                    "codPedido", itemVenda.getVenda().getCodVenda() //,
                    //"codLivro", itemVenda.getLivro().getCodLivro()
            ));

            session.close();
            System.out.println("Item de venda excluído com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
