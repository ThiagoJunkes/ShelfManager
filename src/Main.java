import dao.DataBaseConection;
import model.Cliente;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Teste");
        DataBaseConection banco = new DataBaseConection();

        List<Cliente> clientes = Cliente.buscarClientes(banco);

        for (Cliente cliente: clientes) {
            System.out.println(cliente.getCodCliente() + " | " + cliente.getNome() + " | " + cliente.getDataCadastro());
        }
    }
}