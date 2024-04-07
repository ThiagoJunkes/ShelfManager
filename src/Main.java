import dao.DataBaseConection;
import model.*;
import view.Menu;

import java.util.List;
import java.util.Scanner;

public class Main {

    DataBaseConection banco;
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
                    opcaoClientes(banco);
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

    private static void opcaoClientes(DataBaseConection banco){
        Scanner scanner = new Scanner(System.in);

        List<Cliente> clientes = Cliente.buscarClientes(banco);
        Menu.clientes(clientes);
        System.out.println(" 0 - Menu Inicial | 1 - Adicionar Cliente | 2 - Editar Cliente | 3 - Excluir Cliente ");
        System.out.print("Escolha uma opção: ");
        String escolhaInt = scanner.nextLine();
        switch (escolhaInt) {
            case "1":
                break;
            case "2":
                System.out.print("Digite o código do cliente que deseja editar: ");
                Cliente editar = new Cliente();
                try{
                    int codigoCliente = Integer.parseInt(scanner.nextLine());
                    boolean clienteEncontrado = false;

                    for (Cliente cliente : clientes) {
                        if (cliente.getCodCliente() == codigoCliente) {
                            clienteEncontrado = true;
                            editar = cliente;
                            break;  // Achou o cliente, não precisa mais continuar o loop
                        }
                    }

                    if (clienteEncontrado) {
                        editar.printClienteSemFormatacao();
                        System.out.print("Digite o número da categoria que deseja editar: ");
                        String escolhaEditar = scanner.nextLine();
                        switch (escolhaEditar) {
                            case "1":
                                System.out.println("Digite o novo nome: ");
                                String nome = scanner.nextLine();
                                if (nome.length() <= 50 && !nome.equals("")){
                                    editar.setNome(nome);
                                }else{
                                    System.out.println("Nome ultrapassa o limite de caracteres!");
                                }
                            case "2":
                            case "3":
                            case "4":
                            case "5":
                            case "6":
                            default:
                                System.out.println("Escolha Inválida!");
                        }
                    } else{
                        System.out.println("Cliente com código " + codigoCliente + " não encontrado.");
                    }
                }
                catch (Exception e){
                    System.out.println("Código Inválido!");
                }
                break;
            case "3":

                break;
            default:
                System.out.println("Carregando Menu Inicial...");
        }
    }
}