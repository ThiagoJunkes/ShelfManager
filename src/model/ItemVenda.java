package model;

import dao.DataBaseConection;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    // Getters e Setters
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

    public void printItemVendaFormatado(){
        System.out.println("-------------------------------------------------");
        System.out.println("Código Livro: " + codLivro);
        System.out.println("  Livro: " + livro.getTitulo() + " ISBN: " + livro.getIsbn());
        System.out.println("  Quantidade: " + qtdLivros);
        System.out.println("  Metodo de Pagamento: " + venda.getMetodoPag());
        System.out.println("  Cliente: " + cliente.getNome() + " " + cliente.getSobrenome() + " CPF: " + cliente.getCpf());
    }

    public void printItemVendaSemFormatacao(){
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

    public static List<ItemVenda> buscarItensVenda(DataBaseConection banco){
        List<ItemVenda> itensVendas = new ArrayList<>();
        ResultSet resultSet = null;

        try{
            String sql = "SELECT * FROM itens_vendas iv " +
                         "JOIN vendas v ON iv.cod_pedido = v.cod_venda " +
                         "JOIN livros l ON iv.cod_livro = l.cod_livro JOIN clientes c ON v.cod_cliente = c.cod_cliente " +
                         "ORDER BY iv.cod_pedido";
            resultSet = banco.statement.executeQuery(sql);

            while (resultSet.next()) {
                ItemVenda itemVenda = new ItemVenda();
                Venda venda = new Venda();
                Livro livro = new Livro();
                Cliente cliente = new Cliente();

                itemVenda.codPedido = resultSet.getInt("cod_pedido");
                itemVenda.codLivro = resultSet.getInt("cod_livro");
                itemVenda.qtdLivros = resultSet.getInt("qtd_livros");

                livro.setCodLivro(resultSet.getInt("cod_livro"));
                livro.setTitulo(resultSet.getString("titulo"));
                livro.setGenero(resultSet.getString("genero"));
                livro.setAutor(resultSet.getString("autor"));
                livro.setIsbn(resultSet.getLong("isbn"));
                livro.setAnoPublicacao(resultSet.getDate("ano_publicacao"));
                livro.setPreco(resultSet.getDouble("preco"));
                livro.setCodEditora(resultSet.getInt("cod_editora"));

                venda.setCodVenda(resultSet.getInt("cod_venda"));
                venda.setValorVenda(resultSet.getDouble("valor_venda"));
                venda.setDataVenda(resultSet.getDate("data_venda"));
                venda.setMetodoPag(resultSet.getString("metodo_pag"));
                venda.setCodCliente(resultSet.getInt("cod_cliente"));

                cliente.setCodCliente(resultSet.getInt("cod_cliente"));
                cliente.setNome(resultSet.getString("nome"));
                cliente.setSobrenome(resultSet.getString("sobrenome"));
                cliente.setCpf(resultSet.getLong("cpf"));
                cliente.setEmailCliente(resultSet.getString("email_cliente"));
                cliente.setTelefoneCliente(resultSet.getString("telefone_cliente"));
                cliente.setDataCadastro(resultSet.getDate("data_cadastro"));
                cliente.setCodEndereco(resultSet.getInt("cod_endereco"));

                itemVenda.venda = venda;
                itemVenda.livro = livro;
                itemVenda.cliente = cliente;
                itensVendas.add(itemVenda);
            }

        }catch (SQLException e) {
            System.out.println("Erro ao buscar vendas!");
        }

        return itensVendas;
    }

    public static void editarItemVenda(DataBaseConection banco, ItemVenda venda, int codLivroOriginal){
        try{
            String sqlVendas = "UPDATE vendas SET metodo_pag = ?, cod_cliente = ?, data_venda = ? "+
                               "WHERE cod_venda = ?";

            String sqlItemVenda = "UPDATE itens_vendas SET cod_livro = ?, qtd_livros = ? " +
                                  "WHERE cod_livro = ? AND cod_pedido = ?";

            banco.preparedStatement = banco.connection.prepareStatement(sqlVendas);

            banco.preparedStatement.setString(1, venda.venda.getMetodoPag());
            banco.preparedStatement.setInt(2, venda.cliente.getCodCliente());
            banco.preparedStatement.setDate(3, venda.venda.getDataVenda());
            banco.preparedStatement.setInt(4, venda.getCodPedido());

            banco.preparedStatement.executeUpdate();


            banco.preparedStatement = banco.connection.prepareStatement(sqlItemVenda);

            banco.preparedStatement.setInt(1, venda.livro.getCodLivro());
            banco.preparedStatement.setInt(2, venda.getQtdLivros());
            banco.preparedStatement.setInt(3, codLivroOriginal);
            banco.preparedStatement.setInt(4, venda.getCodPedido());

            banco.preparedStatement.executeUpdate();
            System.out.println("Venda atualizada com sucesso!");
        }
        catch (Exception e){
            System.out.println("Não foi possivel atualizar Venda!");
        }
    }
}