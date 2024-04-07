import dao.DataBaseConection;
import model.*;
import view.Menu;

import java.text.SimpleDateFormat;
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
            case "1": //Adicionar Cliente
                try {
                    System.out.println("Para adicionar um novo cliente informe as seguintes informações:");
                    Cliente novoCliente = new Cliente();
                    Endereco novoEndereco = new Endereco();

                    System.out.print("Nome: ");
                    novoCliente.setNome(scanner.nextLine());

                    System.out.print("Sobrenome: ");
                    novoCliente.setSobrenome(scanner.nextLine());

                    System.out.print("CPF: ");
                    long cpf = Long.parseLong(scanner.nextLine());
                    while (String.valueOf(cpf).length() != 11) {
                        System.out.println("CPF inválido! Deve conter 11 dígitos.");
                        System.out.print("CPF: ");
                        cpf = Long.parseLong(scanner.nextLine());
                    }
                    novoCliente.setCpf(Long.parseLong(scanner.nextLine()));

                    System.out.print("Email: ");
                    novoCliente.setEmailCliente(scanner.nextLine());

                    System.out.print("Telefone '(XX) 12345-6789': ");
                    String telefone = "";
                    while (telefone.isEmpty() || !telefone.matches("\\(\\d{2}\\)\\s\\d{5}-\\d{4}")) {
                        telefone = scanner.nextLine();
                        if (!telefone.matches("\\(\\d{2}\\)\\s\\d{5}-\\d{4}")) {
                            System.out.println("Formato de telefone inválido. Digite novamente no formato '(XX) 12345-6789': ");
                        }
                    }
                    novoCliente.setTelefoneCliente(telefone);

                    System.out.print("Rua: ");
                    novoEndereco.setRua(scanner.nextLine());

                    System.out.print("Cidade: ");
                    novoEndereco.setCidade(scanner.nextLine());

                    String sigla = "";
                    while(sigla.length() != 2){
                        System.out.print("Sigla do Estado: ");
                        sigla = scanner.nextLine();
                    }
                    novoEndereco.setEstado(sigla.toUpperCase());

                    System.out.print("CEP: ");
                    novoEndereco.setCep(Integer.parseInt(scanner.nextLine()));

                    System.out.print("Complemento: ");
                    novoEndereco.setComplemento(scanner.nextLine());

                    novoCliente.endereco = novoEndereco;

                    if (Cliente.adicionarCliente(novoCliente, banco)) {
                        System.out.println("Novo cliente adicionado com sucesso!");
                    } else {
                        System.out.println("Falha ao adicionar novo cliente.");
                    }
                } catch (Exception e){
                    System.out.println("Informação inválida, cancelando operação!");
                }
                break;
            case "2": //Editar Cliente
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
                        try{
                            int escolhaEditar = Integer.parseInt(scanner.nextLine());
                            boolean cadastrar = true;
                            switch (escolhaEditar) {
                                case 1:
                                    System.out.print("Digite o nome: ");
                                    String nome = scanner.nextLine();
                                    if (nome.length() <= 50 && !nome.equals("")){
                                        editar.setNome(nome);
                                    }else{
                                        System.out.println("Ultrapassou o limite de caracteres!");
                                    }
                                case 2:
                                    System.out.print("Digite o sobrenome: ");
                                    String sobrenome = scanner.nextLine();
                                    if (sobrenome.length() <= 250 && !sobrenome.equals("")){
                                        editar.setNome(sobrenome);
                                    }else{
                                        System.out.println("Ultrapassou o limite de caracteres!");
                                    }
                                case 3:
                                    System.out.print("Digite o novo CPF: ");
                                    long cpf = Long.parseLong(scanner.nextLine());
                                    if (String.valueOf(cpf).length() == 11) {
                                        editar.setCpf(cpf);
                                    } else {
                                        System.out.println("CPF inválido! Deve conter 11 dígitos.");
                                        cadastrar = false;
                                    }
                                    break;
                                case 4:
                                    System.out.print("Digite o novo email: ");
                                    String email = scanner.nextLine();
                                    if(email.contains("@") && email.length() < 100){
                                        editar.setEmailCliente(email);
                                    }else{
                                        System.out.println("E-mail inválido! Deve conter @ e ter menos de 100 dígitos.");
                                        cadastrar = false;
                                    }
                                    break;
                                case 5:
                                    System.out.print("Digite o novo telefone '(XX) XXXX-XXXX': ");
                                    String telefone = scanner.nextLine();
                                    if (telefone.matches("\\(\\d{2}\\)\\s\\d{4}-\\d{4}")) {
                                        editar.setTelefoneCliente(telefone);
                                    } else {
                                        System.out.println("Formato de telefone inválido! Deve ser no formato '(XX) XXXX-XXXX'.");
                                        cadastrar = false;
                                    }
                                    break;
                                case 6:
                                    System.out.print("Digite a rua: ");
                                    String rua = scanner.nextLine();
                                    if (rua.length() <= 50 && !rua.equals("")){
                                        editar.endereco.setRua(rua);
                                    }else{
                                        System.out.println("Ultrapassou o limite de caracteres!");
                                        cadastrar = false;
                                    }
                                    break;
                                case 7:
                                    System.out.print("Digite a nova cidade: ");
                                    String cidade = scanner.nextLine();
                                    if (cidade.length() <= 100 && !cidade.equals("")) {
                                        editar.endereco.setCidade(cidade);
                                    } else {
                                        System.out.println("Cidade ultrapassou o limite de caracteres ou está vazia!");
                                        cadastrar = false;
                                    }
                                    break;
                                case 8:
                                    System.out.print("Digite o novo estado: ");
                                    String estado = scanner.nextLine();
                                    if (estado.length() == 2 && estado.matches("[A-Za-z]{2}")) {
                                        editar.endereco.setEstado(estado.toUpperCase());
                                    } else {
                                        System.out.println("Estado inválido! Deve ter duas letras.");
                                        cadastrar = false;
                                    }
                                    break;
                                case 9:
                                    System.out.print("Digite o novo CEP: ");
                                    int cep = Integer.parseInt(scanner.nextLine());
                                    if (String.valueOf(cep).length() == 8) {
                                        editar.endereco.setCep(cep);
                                    } else {
                                        System.out.println("CEP inválido! Deve conter 8 dígitos.");
                                        cadastrar = false;
                                    }
                                    break;
                                case 10:
                                    System.out.print("Digite o novo complemento: ");
                                    String complemento = scanner.nextLine();
                                    if (complemento.length() <= 100) {
                                        editar.endereco.setComplemento(complemento);
                                    } else {
                                        System.out.println("Complemento ultrapassou o limite de caracteres!");
                                        cadastrar = false;
                                    }
                                    break;
                                default:
                                    System.out.println("Escolha Inválida!");
                                    cadastrar = false;
                            }
                            if(cadastrar){
                                if(escolhaEditar > 5 && escolhaEditar <= 10){
                                    Endereco.editarEndereco(editar.endereco, banco);
                                } else if (escolhaEditar > 0){
                                    Cliente.editarCliente(editar, banco);
                                }

                            }
                        } catch (Exception e) {
                            System.out.println("Opção Inválida!");
                        }
                    } else{
                        System.out.println("Cliente com código " + codigoCliente + " não encontrado.");
                    }
                }
                catch (Exception e){
                    System.out.println("Código Inválido!");
                }
                break;
            case "3": // Excluir Cliente
                System.out.print("Digite o código do cliente que deseja excluir: ");
                Cliente excluir = new Cliente();
                try{
                    int codigoCliente = Integer.parseInt(scanner.nextLine());
                    boolean clienteEncontrado = false;

                    for (Cliente cliente : clientes) {
                        if (cliente.getCodCliente() == codigoCliente) {
                            clienteEncontrado = true;
                            excluir = cliente;
                            break;  // Achou o cliente, não precisa mais continuar o loop
                        }
                    }

                    if (clienteEncontrado) {
                        excluir.printClienteSemFormatacao();
                        System.out.print("Tem certeza que deseja excluir o Cliente acima? (s/n)");
                        String resposta = scanner.nextLine();
                        if(resposta.toUpperCase().equals("SIM") || resposta.toUpperCase().equals("S")){
                            Cliente.excluirCliente(excluir, banco);
                        }
                    } else{
                        System.out.println("Cliente com código " + codigoCliente + " não encontrado.");
                    }
                }
                catch (Exception e){
                    System.out.println("Código Inválido!");
                }
                break;
            default:
                System.out.println("Carregando Menu Inicial...");
        }
    }
}