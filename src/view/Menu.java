package view;

import model.Cliente;

import java.text.SimpleDateFormat;
import java.util.List;

public class Menu {

    public static void inicial(){
        System.out.println("*******************************************");
        System.out.println("*               ShelfManager              *");
        System.out.println("*******************************************");
        System.out.println("*  Sistema de Gerenciamento de Livraria   *");
        System.out.println("*******************************************");
        System.out.println("*            Escolha uma opção:           *");
        System.out.println("*                                         *");
        System.out.println("*            1. Clientes                  *");
        System.out.println("*            2. Editoras                  *");
        System.out.println("*            3. Livros                    *");
        System.out.println("*            4. Vendas                    *");
        System.out.println("*            5. Estoque                   *");
        System.out.println("*            6. Relatórios                *");
        System.out.println("*                                         *");
        System.out.println("*            0. Sair                      *");
        System.out.println("*******************************************");
    }

    public static void clientes(List<Cliente> clientes){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("Clientes:");
        System.out.println("|Cod Cliente | Nome            | Sobrenome       | CPF            | Email                     | Telefone        |Data Cadastro | Cod Endereco |");
        System.out.println("|------------|-----------------|-----------------|----------------|---------------------------|-----------------|--------------|--------------|");

        for (Cliente cliente : clientes) {
            // Limitando o tamanho dos campos
            String email = cliente.getEmailCliente().length() > 25 ? cliente.getEmailCliente().substring(0, 25) : cliente.getEmailCliente();
            String nome = cliente.getNome().length() > 15 ? cliente.getNome().substring(0, 15) : cliente.getNome();
            String sobrenome = cliente.getSobrenome().length() > 15 ? cliente.getSobrenome().substring(0, 15) : cliente.getSobrenome();
            String telefone = cliente.getTelefoneCliente().length() > 15 ? cliente.getTelefoneCliente().substring(0, 15) : cliente.getTelefoneCliente();

            System.out.printf("| %-10d | %-15s | %-15s | %-14d | %-25s | %-15s | %-12s | %-12d |\n",
                    cliente.getCodCliente(),
                    nome,
                    sobrenome,
                    cliente.getCpf(),
                    email,
                    telefone,
                    dateFormat.format(cliente.getDataCadastro()),
                    cliente.getCodEndereco());
        }
        System.out.println("|------------|-----------------|-----------------|----------------|---------------------------|-----------------|--------------|--------------|");

    }

    public static void editoras(){
        System.out.println("Editoras");
    }

    public static void estoque(){
        System.out.println("Estoque");
    }

    public static void livros(){
        System.out.println("Livros");
    }

    public static void vendas(){
        System.out.println("Vendas");
    }

    public static void relatorios(){
        System.out.println("Cliente");
    }
}
