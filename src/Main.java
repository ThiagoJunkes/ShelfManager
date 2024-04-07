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
            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextLine();

            switch (escolha){
                case "1":
                    System.out.println("Opção Clientes selecionada.");
                    List<Cliente> clientes = Cliente.buscarClientes(banco);
                    Menu.clientes(clientes);
                    System.out.println(" 0 - Menu Inicial | 1 - Editar Cliente | 2 - Excluir Cliente ");
                    System.out.print("Escolha uma opção: ");
                    String escolhaInt = scanner.nextLine();
                    switch (escolhaInt) {
                        case "1":
                            System.out.print("Digite o código do cliente que deseja editar: ");
                            try{
                                int codigoCliente = Integer.parseInt(scanner.nextLine());
                                boolean clienteEncontrado = false;

                                for (Cliente cliente : clientes) {
                                    if (cliente.getCodCliente() == codigoCliente) {
                                        clienteEncontrado = true;
                                        cliente.printClienteSemFormatacao();
                                        break;  // Achou o cliente, não precisa mais continuar o loop
                                    }
                                }

                                if (clienteEncontrado) {
                                    System.out.println("Digite o número da categoria que deseja editar: ");
                                    scanner.nextLine();
                                } else{
                                    System.out.println("Cliente com código " + codigoCliente + " não encontrado.");
                                }
                            }
                            catch (Exception e){
                                System.out.println("Codigo Invalido!");
                            }
                            break;
                        case "2":

                            break;
                        default:
                            System.out.println("Carregando Menu Inicial...");
                    }
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