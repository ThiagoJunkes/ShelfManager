package model;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;
import org.neo4j.driver.exceptions.ClientException;

import dao.DataBaseConection;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ItemVenda {
    private int codPedido;
    private int codLivro;
    private int qtdLivros;

    public Livro livro;
    public Venda venda;
    public Cliente cliente;

    public ItemVenda() {
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

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void printItemVendaFormatado() {
        System.out.println("-------------------------------------------------");
        System.out.println("Código Livro: " + codLivro);
        System.out.println("  Livro: " + livro.getTitulo() + " ISBN: " + livro.getIsbn());
        System.out.println("  Quantidade: " + qtdLivros);
        System.out.println("  Metodo de Pagamento: " + venda.getMetodoPag());
        System.out.println("  Cliente: " + cliente.getNome() + " " + cliente.getSobrenome() + " CPF: " + cliente.getCpf());
    }

    public void printItemVendaSemFormatacao() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = sdf.format(venda.getDataVenda());

        System.out.println("Venda: " + codPedido);
        System.out.println("1 - Código Livro:        " + codLivro);
        System.out.println("Livro: " + livro.getTitulo() + " ISBN: " + livro.getIsbn());
        System.out.println("2 - Quantidade:          " + qtdLivros);
        System.out.println("3 - Metodo de Pagamento: " + venda.getMetodoPag());
        System.out.println("4 - Data da Venda:       " + dataFormatada);
        System.out.println("5 - Código Cliente:      " + cliente.getCodCliente());
        System.out.println("Cliente: " + cliente.getNome() + " " + cliente.getSobrenome() + " CPF: " + cliente.getCpf());
    }

    public static List<ItemVenda> buscarItensVenda(DataBaseConection banco) {
        List<ItemVenda> itensVendas = new ArrayList<>();

        try {
            Session session = banco.getSession();

            // Query para buscar todos os itens de venda
            String query = "MATCH (v:Venda)-[:CONTEM]->(l:Livro)<-[:FEZ]-(c:Cliente) RETURN v, l, c";
            var result = session.run(query);

            while (result.hasNext()) {
                var record = result.next();

                ItemVenda itemVenda = new ItemVenda();
                Venda venda = new Venda();
                Livro livro = new Livro();
                Cliente cliente = new Cliente();

                // Preenchendo dados da venda
                venda.setCodVenda(record.get("v").asNode().get("cod_venda").asInt());
                venda.setMetodoPag(record.get("v").asNode().get("metodo_pag").asString());
                // Atribua outros campos de Venda conforme necessário

                // Preenchendo dados do livro
                livro.setCodLivro(record.get("l").asNode().get("cod_livro").asInt());
                livro.setTitulo(record.get("l").asNode().get("titulo").asString());
                livro.setGenero(record.get("l").asNode().get("genero").asString());
                livro.setAutor(record.get("l").asNode().get("autor").asString());
                livro.setIsbn(record.get("l").asNode().get("isbn").asLong());
                livro.setAnoPublicacao(Date.valueOf(record.get("l").asNode().get("ano_publicacao").asLocalDate()));
                livro.setPreco(record.get("l").asNode().get("preco").asDouble());
                // Atribua outros campos de Livro conforme necessário

                // Preenchendo dados do cliente
                cliente.setCodCliente(record.get("c").asNode().get("cod_cliente").asInt());
                cliente.setNome(record.get("c").asNode().get("nome").asString());
                cliente.setSobrenome(record.get("c").asNode().get("sobrenome").asString());
                cliente.setCpf(record.get("c").asNode().get("cpf").asLong());
                cliente.setEmailCliente(record.get("c").asNode().get("email_cliente").asString());
                cliente.setTelefoneCliente(record.get("c").asNode().get("telefone_cliente").asString());
                cliente.setDataCadastro(Date.valueOf(record.get("c").asNode().get("data_cadastro").asLocalDate()));
                // Atribua outros campos de Cliente conforme necessário

                itemVenda.setCodPedido((int) venda.getCodVenda());
                itemVenda.setCodLivro(livro.getCodLivro());
                itemVenda.setQtdLivros(1); // Exemplo, ajuste conforme sua lógica
                itemVenda.setVenda(venda);
                itemVenda.setLivro(livro);
                itemVenda.setCliente(cliente);

                itensVendas.add(itemVenda);
            }

            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return itensVendas;
    }

    public static void editarItemVenda(DataBaseConection banco, ItemVenda itemVenda) {
        try {
            Session session = banco.getSession();

            // Atualizar dados do item de venda
            String query = "MATCH (v:Venda)-[r:CONTEM]->(l:Livro) " +
                    "WHERE ID(v) = $codPedido AND ID(l) = $codLivro " +
                    "SET r.qtdLivros = $qtdLivros";
            session.run(query, Values.parameters(
                    "codPedido", itemVenda.getVenda().getCodVenda(),
                    "codLivro", itemVenda.getLivro().getCodLivro(),
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
                    "codPedido", itemVenda.getVenda().getCodVenda(),
                    "codLivro", itemVenda.getLivro().getCodLivro()
            ));

            session.close();
            System.out.println("Item de venda excluído com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
