import dao.DataBaseConection;
import model.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Teste");
        DataBaseConection banco = new DataBaseConection();

        List<Cliente> clientes = Cliente.buscarClientes(banco);

        System.out.println("CLientes:");
        for (Cliente cliente: clientes) {
            System.out.println(cliente.getCodCliente() + " | " + cliente.getNome() + " | " + cliente.getDataCadastro());
        }

        List<Editora> editoras = Editora.buscarEditoras(banco);

        System.out.println("Editoras:");
        for (Editora editora: editoras) {
            System.out.println(editora.getCodEditora() + " | " + editora.getNomeEditora());
        }

        List<Endereco> enderecos = Endereco.buscarEnderecos(banco);

        System.out.println("Enderecos:");
        for (Endereco endereco: enderecos) {
            System.out.println(endereco.getCodEndereco() + " | " + endereco.getCep());
        }

        List<Livro> livros = Livro.buscarLivros(banco);

        System.out.println("Enderecos:");
        for (Livro livro: livros) {
            System.out.println(livro.getCodLivro() + " | " + livro.getTitulo());
        }

        List<Venda> vendas = Venda.buscarVendas(banco);

        System.out.println("Vendas:");
        for (Venda venda: vendas) {
            System.out.println(venda.getCodVenda() + " | " + venda.getValorVenda());
        }

        List<Estoque> estoque = Estoque.buscarEstoque(banco);

        System.out.println("Estoque:");
        for (Estoque est: estoque) {
            System.out.println(est.getCodLivro() + " | " + est.getQtdEstoque());
        }

        List<ItemVenda> itensVendas = ItemVenda.buscarItensVenda(banco);

        System.out.println("itens das Vendas:");
        for (ItemVenda itemVenda: itensVendas) {
            System.out.println(itemVenda.getCodPedido() + " | " + itemVenda.getCodLivro() + " | " + itemVenda.getQtdLivros());
        }
    }
}