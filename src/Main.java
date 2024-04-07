import dao.DataBaseConection;
import model.*;
import view.Menu;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DataBaseConection banco = new DataBaseConection();

        String escolha;
        do{
            Menu.inicial();
            System.out.println("Escolha uma opcao: ");
            escolha = scanner.nextLine();

            switch (escolha){
                case "1":
                    System.out.println("Opção Clientes selecionada.");
                    List<Cliente> clientes = Cliente.buscarClientes(banco);
                    Menu.clientes(clientes);
                    System.out.println("Ações:");
                    System.out.println("0 - Menu Inicial | 1 - Editar Cliente");
                    break;
                case "2":
                    System.out.println("Opção Editoras selecionada.");
                    Menu.editoras();
                    break;
                case "3":
                    System.out.println("Opção Livros selecionada.");
                    Menu.livros();
                    break;
                case "4":
                    System.out.println("Opção Vendas selecionada.");
                    Menu.vendas();
                    break;
                case "5":
                    System.out.println("Opção Estoque selecionada.");
                    Menu.estoque();
                    break;
                case "6":
                    System.out.println("Opção Relatórios selecionada.");
                    Menu.relatorios();
                    break;
                case "0":
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

            if (!escolha.equals("0")) {
                System.out.println("Pressione Enter para continuar...");
                scanner.nextLine();
            }

        }while(!escolha.equals("0"));
    }
}