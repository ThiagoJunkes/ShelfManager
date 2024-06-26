import dao.DataBaseConection;
import model.*;
import view.Menu;

import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import controller.Relatorio;

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
                    opcaoEditoras(banco);
                    break;
                case "3":
                    System.out.println("Opção Livros selecionada.");
                    opcaoLivros(banco);
                    break;
                case "4":
                    System.out.println("Opção Vendas selecionada.");
                    opcaoVendas(banco);
                    break;
                case "5":
                    System.out.println("Opção Estoque selecionada.");
                    opcaoEstoque(banco);
                    break;
                case "6":
                    System.out.println("Opção Relatórios selecionada.");
                    Relatorio.main(new String[0]);
                    break;
                case "0":
                    System.out.println("Saindo...");
                    banco.closeConnection();
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

                    System.out.print("CPF (somente digito): ");
                    String cpf = scanner.nextLine().trim();
                    while (!cpf.matches("^\\d{11}$")) {
                        System.out.println("CPF inválido! Deve conter 11 dígitos.");
                        System.out.print("CPF (somente digito): ");
                        scanner.nextLine().trim();
                    }
                    novoCliente.setCpf(Long.parseLong(cpf));

                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    while(!email.contains("@") || email.length() > 100){
                        System.out.println("E-mail inválido! Deve conter @ e ter menos de 100 dígitos.");
                        System.out.print("Email: ");
                        email = scanner.nextLine();
                    }
                    novoCliente.setEmailCliente(email);

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
                                    break;
                                case 2:
                                    System.out.print("Digite o sobrenome: ");
                                    String sobrenome = scanner.nextLine();
                                    if (sobrenome.length() <= 250 && !sobrenome.equals("")){
                                        editar.setNome(sobrenome);
                                    }else{
                                        System.out.println("Ultrapassou o limite de caracteres!");
                                    }
                                    break;
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
                                    if (telefone.matches("\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}")) {
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
                        System.out.print("Tem certeza que deseja excluir o Cliente acima? (s/n) ");
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

    private static void opcaoEditoras(DataBaseConection banco){
        Scanner scanner = new Scanner(System.in);

        List<Editora> editoras = Editora.buscarEditoras(banco);
        Menu.editoras(editoras);
        System.out.println(" 0 - Menu Inicial | 1 - Adicionar Editora | 2 - Editar Editora | 3 - Excluir Editora ");
        System.out.print("Escolha uma opção: ");
        String escolhaEditora = scanner.nextLine();
        switch (escolhaEditora) {
            case "1": // Adicionar Editora
                Editora novaEditora = new Editora();

                System.out.print("Nome da editora: ");
                novaEditora.setNomeEditora(scanner.nextLine());

                System.out.print("Nome do contato: ");
                novaEditora.setNomeContato(scanner.nextLine());

                System.out.print("Email da editora: ");
                String email = scanner.nextLine();
                while (!email.contains("@") || email.length() > 100) {
                    System.out.println("E-mail inválido! Deve conter @ e ter menos de 100 dígitos.");
                    System.out.print("Email da editora: ");
                    email = scanner.nextLine();
                }
                novaEditora.setEmailEditora(email);

                System.out.print("Telefone da editora '(XX) XXXX-XXXX': ");
                String telefone = scanner.nextLine();
                while (telefone.isEmpty() || !telefone.matches("\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}")) {
                    System.out.println("Formato de telefone inválido. Digite novamente no formato '(XX) XXXX-XXXX': ");
                    System.out.print("Telefone da editora '(XX) XXXX-XXXX': ");
                    telefone = scanner.nextLine();
                }
                novaEditora.setTelefoneEditora(telefone);

                if (Editora.adicionarEditora(novaEditora, banco)) {
                    System.out.println("Nova editora adicionada com sucesso!");
                } else {
                    System.out.println("Falha ao adicionar nova editora.");
                }
                break;
            case "2": // Editar Editora
                System.out.print("Digite o código da editora que deseja editar: ");
                int codigoEditar = Integer.parseInt(scanner.nextLine());
                Editora editoraEditar = null;
                for (Editora editora : editoras) {
                    if (editora.getCodEditora() == codigoEditar) {
                        editoraEditar = editora;
                        break;
                    }
                }

                if (editoraEditar != null) {
                    editoraEditar.printEditoraSemFormatacao();

                    System.out.print("Escolha o número da informação que deseja editar: ");
                    try{
                        int escolha = Integer.parseInt(scanner.nextLine());
                        boolean editar = true;
                        switch (escolha) {
                            case 1:
                                System.out.print("Novo Nome da Editora: ");
                                String novoNome = scanner.nextLine();
                                if (!novoNome.isEmpty()) {
                                    editoraEditar.setNomeEditora(novoNome);
                                } else {
                                    System.out.println("Nome da Editora não pode ser vazio. Operação cancelada.");
                                    editar = false;
                                }
                                break;
                            case 2:
                                System.out.print("Novo Nome do Contato: ");
                                String novoContato = scanner.nextLine();
                                if (!novoContato.isEmpty()) {
                                    editoraEditar.setNomeContato(novoContato);
                                } else {
                                    System.out.println("Nome do Contato não pode ser vazio. Operação cancelada.");
                                    editar = false;
                                }
                                break;
                            case 3:
                                System.out.print("Novo Email da Editora: ");
                                String novoEmail = scanner.nextLine();
                                if (!novoEmail.isEmpty() && novoEmail.contains("@") && novoEmail.length() < 100) {
                                    editoraEditar.setEmailEditora(novoEmail);
                                } else {
                                    System.out.println("Email da Editora inválido. Deve conter @ e ter menos de 100 caracteres.");
                                    editar = false;
                                }
                                break;
                            case 4:
                                System.out.print("Novo Telefone da Editora '(XX) XXXX-XXXX': ");
                                String novoTelefone = scanner.nextLine();
                                if (!novoTelefone.isEmpty() && novoTelefone.matches("\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}")) {
                                    editoraEditar.setTelefoneEditora(novoTelefone);
                                } else {
                                    System.out.println("Formato de telefone inválido. Deve ser no formato '(XX) XXXX-XXXX'.");
                                    editar = false;
                                }
                                break;
                            default:
                                System.out.println("Opção inválida. Voltando ao menu anterior...");
                        }

                        if(editar && escolha > 0 && escolha < 5){
                            Editora.editarEditora(editoraEditar, banco);
                        }
                    }catch (Exception e){
                        System.out.println("Opção Inválida!");
                    }
                } else {
                    System.out.println("Editora não encontrada.");
                }
                break;
            case "3": // Excluir Editora
                System.out.print("Digite o código da editora que deseja excluir: ");
                try{
                    int codigoExcluir = Integer.parseInt(scanner.nextLine());
                    Editora editoraExcluir = null;
                    for (Editora editora : editoras) {
                        if (editora.getCodEditora() == codigoExcluir) {
                            editoraExcluir = editora;
                            break;
                        }
                    }

                    if (editoraExcluir != null) {
                        editoraExcluir.printEditoraSemFormatacao();
                        System.out.print("Tem certeza que deseja excluir a Editora acima? (s/n) ");
                        String resposta = scanner.nextLine();
                        if(resposta.toUpperCase().equals("SIM") || resposta.toUpperCase().equals("S")){
                            Editora.excluirEditora(editoraExcluir, banco);
                        }
                    } else {
                        System.out.println("Editora não encontrada.");
                    }
                } catch (Exception e){
                    System.out.println("Código Inválido!");
                }
                break;
            default:
                System.out.println("Carregando Menu Inicial...");
        }
    }

    private static void opcaoLivros(DataBaseConection banco){
        Scanner scanner = new Scanner(System.in);

        List<Livro> livros = Livro.buscarLivros(banco);
        Menu.livros(livros);
        System.out.println(" 0 - Menu Inicial | 1 - Adicionar Livro | 2 - Editar Livro | 3 - Excluir Livro ");
        System.out.print("Escolha uma opção: ");
        String escolhaLivro = scanner.nextLine();
        switch (escolhaLivro) {
            case "1": // Adicionar Livro
                Livro novoLivro = new Livro();
                try {
                    System.out.println("Para adicionar um novo livro, informe as seguintes informações:");

                    System.out.print("Título: ");
                    novoLivro.setTitulo(scanner.nextLine());

                    System.out.print("Gênero: ");
                    novoLivro.setGenero(scanner.nextLine());

                    System.out.print("Autor: ");
                    novoLivro.setAutor(scanner.nextLine());

                    System.out.print("ISBN: ");
                    long isbn = Long.parseLong(scanner.nextLine());
                    novoLivro.setIsbn(isbn);

                    String data = "";
                    do{
                        System.out.print("Ano de Publicação (DD/MM/AAAA): ");
                        data = scanner.next();
                        if (!data.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
                            System.out.println("Formato de data inválido! Use DD/MM/AAAA.");
                        }
                    }while(!data.matches("^\\d{2}/\\d{2}/\\d{4}$"));

                    SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy");
                    java.util.Date parsedDate = sdfInput.parse(data);
                    Date sqlDate = new Date(parsedDate.getTime());
                    novoLivro.setAnoPublicacao(sqlDate);

                    scanner.nextLine();
                    String preco = "";
                    do{
                        System.out.print("Preço (9.99): ");
                        preco = scanner.nextLine();
                        if(!preco.matches("\\b\\d+\\.\\d{2}\\b")){
                            System.out.println("Preço inválido, não use virgula e não esqueça de colocar os centavos");
                        }
                    }while (!preco.matches("\\b\\d+\\.\\d{2}\\b"));
                    novoLivro.setPreco(Double.parseDouble(preco));

                    System.out.println("Editoras Disponíveis: ");
                    List<Editora> editoras = Editora.buscarEditoras(banco);
                    System.out.println("0 | Voltar ao Menu Inicial");
                    for (Editora editora: editoras) {
                        System.out.println(editora.getCodEditora() + " | " + editora.getNomeEditora());
                    }
                    System.out.print("Escolha uma editora: ");
                    int escolhaEditora = Integer.parseInt(scanner.nextLine());

                    if(escolhaEditora == 0) break; // Volta ao Menu Inicial

                    boolean hasEditora = false;
                    for (Editora editora : editoras) {
                        if (editora.getCodEditora() == escolhaEditora) {
                            hasEditora = true;
                            break;
                        }
                    }

                    if(!hasEditora){
                        System.out.println("Código da Editora inválido!");
                        break;
                    }
                    novoLivro.setCodEditora(escolhaEditora);

                    System.out.print("Quantidade em estoque: ");
                    int qtd = -1;
                    do{
                        qtd = Integer.parseInt(scanner.nextLine());
                        if(qtd < 0){
                            System.out.println("Escolha uma quantidade maior que 0!");
                            System.out.print("Quantidade em estoque: ");
                        }
                    }while(qtd < 0);

                    novoLivro.setQtdEstoque(qtd);

                    if (Livro.adicionarLivro(novoLivro, banco)) {
                        System.out.println("Novo livro adicionado com sucesso!");
                    } else {
                        System.out.println("Falha ao adicionar novo livro.");
                    }
                } catch (Exception e) {
                    System.out.println("Informação inválida, cancelando operação! " + e.getMessage());
                }
                break;
            case "2": // Editar Livro
                System.out.print("Digite o código do livro que deseja editar: ");
                try{
                    int codigoEditar = Integer.parseInt(scanner.nextLine());
                    Livro livroEditar = null;
                    for (Livro livro : livros) {
                        if (livro.getCodLivro() == codigoEditar) {
                            livroEditar = livro;
                            break;
                        }
                    }

                    if (livroEditar != null) {
                        livroEditar.printLivroSemFormatacao();

                        System.out.print("Escolha o número da informação que deseja editar: ");
                        try {
                            int escolha = Integer.parseInt(scanner.nextLine());
                            boolean editar = true;
                            switch (escolha) {
                                case 0:
                                    break;
                                case 1:
                                    System.out.print("Novo Título: ");
                                    String novoTitulo = scanner.nextLine();
                                    if (!novoTitulo.isEmpty()) {
                                        livroEditar.setTitulo(novoTitulo);
                                    } else {
                                        System.out.println("Título não pode ser vazio. Operação cancelada.");
                                        editar = false;
                                    }
                                    break;
                                case 2:
                                    System.out.print("Novo Gênero: ");
                                    String novoGenero = scanner.nextLine();
                                    if (!novoGenero.isEmpty()) {
                                        livroEditar.setGenero(novoGenero);
                                    } else {
                                        System.out.println("Gênero não pode ser vazio. Operação cancelada.");
                                        editar = false;
                                    }
                                    break;
                                case 3:
                                    System.out.print("Novo Autor: ");
                                    String novoAutor = scanner.nextLine();
                                    if (!novoAutor.isEmpty()) {
                                        livroEditar.setAutor(novoAutor);
                                    } else {
                                        System.out.println("Autor não pode ser vazio. Operação cancelada.");
                                        editar = false;
                                    }
                                    break;
                                case 4:
                                    System.out.print("Novo ISBN: ");
                                    long novoISBN = Long.parseLong(scanner.nextLine());
                                    livroEditar.setIsbn(novoISBN);
                                    break;
                                case 5:
                                    String data = "";
                                    do{
                                        System.out.print("Novo Ano de Publicação (DD/MM/AAAA): ");
                                        data = scanner.next();
                                        if (!data.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
                                            System.out.println("Formato de data inválido! Use DD/MM/AAAA.");
                                        }
                                    }while(!data.matches("^\\d{2}/\\d{2}/\\d{4}$"));

                                    SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy");
                                    java.util.Date parsedDate = sdfInput.parse(data);
                                    Date sqlDate = new Date(parsedDate.getTime());

                                    livroEditar.setAnoPublicacao(sqlDate);
                                    break;
                                case 6:
                                    System.out.print("Novo Preço (9.99): ");
                                    String preco = "";
                                    do{
                                        System.out.print("Novo Preço (9.99): ");
                                        preco = scanner.nextLine();
                                        if(!preco.matches("\\b\\d+\\.\\d{2}\\b")){
                                            System.out.println("Preço inválido, não use virgula e não esqueça de colocar os centavos");
                                        }
                                    }while (!preco.matches("\\b\\d+\\.\\d{2}\\b"));

                                    livroEditar.setPreco(Double.parseDouble(preco));
                                    break;
                                case 7:
                                    System.out.println("Editoras Disponíveis: ");
                                    List<Editora> editoras = Editora.buscarEditoras(banco);
                                    System.out.println("0 | Voltar ao Menu Inicial");
                                    for (Editora editora: editoras) {
                                        System.out.println(editora.getCodEditora() + " | " + editora.getNomeEditora());
                                    }
                                    System.out.print("Escolha uma editora: ");
                                    int escolhaEditora = Integer.parseInt(scanner.nextLine());

                                    if(escolhaEditora == 0) break; // Volta ao Menu Inicial

                                    boolean hasEditora = false;
                                    for (Editora editora : editoras) {
                                        if (editora.getCodEditora() == escolhaEditora) {
                                            hasEditora = true;
                                            break;
                                        }
                                    }

                                    if(!hasEditora){
                                        System.out.println("Código da Editora inválido!");
                                        break;
                                    }

                                    livroEditar.setCodEditora(escolhaEditora);
                                    break;
                                default:
                                    System.out.println("Opção inválida. Voltando ao menu anterior...");
                                    editar = false;
                            }

                            if (editar && escolha > 0 && escolha < 8) {
                                Livro.editarLivro(livroEditar, banco);
                            }
                        } catch (Exception e) {
                            System.out.println("Opção Inválida!");
                        }
                    } else {
                        System.out.println("Livro não encontrado.");
                    }
                } catch (Exception e){
                    System.out.println("Opção Inválida!");
                }
                break;
            case "3": // Excluir Livro
                System.out.print("Digite o código do livro que deseja excluir: ");
                try {
                    int codigoExcluir = Integer.parseInt(scanner.nextLine());
                    Livro livroExcluir = null;
                    for (Livro livro : livros) {
                        if (livro.getCodLivro() == codigoExcluir) {
                            livroExcluir = livro;
                            break;
                        }
                    }

                    if (livroExcluir != null) {
                        livroExcluir.printLivroSemFormatacao();
                        System.out.println("O livro será excluido do estoque também!");
                        System.out.print("Tem certeza que deseja excluir o Livro acima? (s/n) ");
                        String resposta = scanner.nextLine();
                        if (resposta.toUpperCase().equals("SIM") || resposta.toUpperCase().equals("S")) {
                            Livro.excluirLivro(livroExcluir, banco);
                        }
                    } else {
                        System.out.println("Livro não encontrado.");
                    }
                } catch (Exception e) {
                    System.out.println("Código Inválido!");
                }
                break;
            default:
                System.out.println("Carregando Menu Inicial...");
        }
    }

    private static void opcaoEstoque(DataBaseConection banco){
        Scanner scanner = new Scanner(System.in);

        List<Estoque> estoques = Estoque.buscarEstoque(banco);
        Menu.estoque(estoques);
        System.out.println("* Ao adicionar um Livro ele automaticamente aparece em estoque.");
        System.out.println("* Não é possivel excluir diretamente no estoque.");
        System.out.println("* Acesse o Menu de Livros e exclua um Livro para o seu estoque ser excluido.");
        System.out.println(" 0 - Menu Inicial | 1 - Editar Estoque ");
        System.out.print("Escolha uma opção: ");
        String escolha = scanner.nextLine();
        switch (escolha){
            case "0":
                break;
            case "1":
                System.out.print("Digite o código do livro que deseja alterar o estoque:");
                try{
                    int escolhaEst = Integer.parseInt(scanner.nextLine());
                    boolean hasEstoque = false;
                    Estoque estoqueEditar = new Estoque();
                    for (Estoque est : estoques) {
                        if(est.getCodLivro() == escolhaEst){
                            hasEstoque = true;
                            estoqueEditar = est;
                            break;
                        }
                    }
                    if(hasEstoque){
                        estoqueEditar.printEstoqueSemFormatacao();
                        System.out.print("Digite a nova quantidade no estoque: ");
                        int quantidade = Integer.parseInt(scanner.nextLine());
                        while(quantidade < 0){
                            System.out.println("A quantidade deve ser maior que 0!");
                            System.out.print("Digite a nova quantidade no estoque: ");
                            quantidade = Integer.parseInt(scanner.nextLine());

                        }
                        estoqueEditar.setQtdEstoque(quantidade);
                        Estoque.editarEstoque(estoqueEditar, banco);
                    }
                    else {
                        System.out.println("Livro não encontrado no estoque!");
                    }
                } catch (Exception e){
                    System.out.println("Opção Inválida");
                }
                break;
            default:
                System.out.println("Opção Inválida!");
        }
    }

    private static void opcaoVendas(DataBaseConection banco){
        Scanner scanner = new Scanner(System.in);

        List<ItemVenda> itensVendas = ItemVenda.buscarItensVenda(banco);
        Menu.vendas(itensVendas);
        System.out.println(" 0 - Menu Inicial | 1 - Adicionar Venda | 2 - Editar Venda | 3 - Excluir Venda ");
        System.out.print("Escolha uma opção: ");
        String escolhaVenda = scanner.nextLine();
        switch (escolhaVenda) {
            case "1":
                List<Cliente> clientes = Cliente.buscarClientes(banco);
                System.out.println("-------------------------------------------------------------------");
                System.out.println("Código| Nome                 | Sobrenome            | CPF");
                for (Cliente cliente: clientes) {
                    cliente.printClienteFormatado();
                }
                boolean clienteValido = false;
                int codCliente = 0;

                while (!clienteValido){
                    try {
                        System.out.println("Digite 0 para voltar ao Menu Inicial.");
                        System.out.print("Digite o código do cliente que realizou a compra: ");
                        codCliente = Integer.parseInt(scanner.nextLine());

                        if(codCliente == 0) break; // Volta ao Menu Inicial

                        for (Cliente cliente: clientes) {
                            if (cliente.getCodCliente() == codCliente) {
                                clienteValido = true;
                                break;
                            }
                        }

                        if(!clienteValido) System.out.println("Código Inválido!");
                    } catch (Exception e){
                        System.out.println("Código Inválido!");
                    }
                };
                if(!clienteValido) break;

                List<Livro> livros = Livro.buscarLivros(banco);
                System.out.println("-------------------------------------------------------------------");
                System.out.println("Código| Título               | Editora              | ISBN");
                for (Livro livro: livros) {
                    livro.printLivroFormatado();
                }
                boolean livroValido = false;
                int codLivro = 0;
                float precoTotal = 0;
                List<String> livrosVendidos = new ArrayList<>();

                while(true){
                    try {
                        Livro escolha = new Livro();
                        if(!livrosVendidos.isEmpty()) System.out.println("Digite -1 para não adicionar mais livros.");
                        System.out.println("Digite 0 para voltar ao Menu Inicial.");
                        System.out.print("Digite o código do livro vendido: ");
                        codLivro = Integer.parseInt(scanner.nextLine());

                        if(codLivro == 0){ // Volta ao Menu Inicial
                            livroValido = false;
                            break;
                        }
                        if(!livrosVendidos.isEmpty() && codLivro == -1){ // Adiciona livros
                            break;
                        }

                        livroValido = false;
                        for (Livro livro: livros) {
                            if (livro.getCodLivro() == codLivro) {
                                livroValido = true;
                                escolha = livro;
                                break;
                            }
                        }

                        if(!livroValido) System.out.println("Código Inválido!");
                        else{
                            int qtdLivro = 0;
                            while(qtdLivro < 1){
                                try{
                                    System.out.print("Digite a quantidade de livros: ");
                                    qtdLivro = Integer.parseInt(scanner.nextLine());
                                }
                                catch (Exception e){
                                    System.out.println("Valor Inválido!");
                                }
                            }
                            precoTotal += (qtdLivro * escolha.getPreco());
                            livrosVendidos.add(codLivro + "|" + qtdLivro);
                        }
                    } catch (Exception e){
                        System.out.println("Código Inválido!");
                    }
                }
                if(!livroValido || livrosVendidos.isEmpty()) break;

                int metodoPag = 0;
                while(metodoPag < 1){
                    try{
                        System.out.println("Metodos de Pagamento: ");
                        int i = 1;
                        for (String pagamento: Venda.metodosPagamento) {
                            System.out.println(i + " | " + pagamento);
                            i++;
                        }
                        System.out.print("Selecione o método de pagamento: ");
                        metodoPag = Integer.parseInt(scanner.nextLine());
                        if(metodoPag > 5) metodoPag = 0;
                    } catch (Exception e){
                        System.out.println("Valor Inválido!");
                    }
                }
                Venda.adicionarItemVenda(banco, codCliente, metodoPag, precoTotal, livrosVendidos);
                break;
            case "2":
                try{
                    int escolha = 0;
                    ItemVenda editarVenda = new ItemVenda();
                    int codLivroOriginal = 0;
                    boolean editarExiste = false;

                    while (!editarExiste){
                        System.out.print("Digite o código da venda que deseja editar: ");
                        escolha = Integer.parseInt(scanner.nextLine());

                        for (ItemVenda venda: itensVendas) {
                            if(escolha == venda.getCodPedido()){
                                venda.printItemVendaFormatado();
                                editarExiste = true;
                            }
                        }
                    }
                    int escolhaLivro = 0;
                    while(escolhaLivro < 1){
                        try{
                            System.out.print("Digite o código do livro que deseja editar: ");
                            escolhaLivro = Integer.parseInt(scanner.nextLine());

                            for (ItemVenda venda: itensVendas) {
                                if(escolha == venda.getCodPedido() && escolhaLivro == venda.livro.getCodLivro()){
                                    venda.printItemVendaSemFormatacao();
                                    editarVenda = venda;
                                    codLivroOriginal = escolhaLivro;
                                    break;
                                }
                            }
                        } catch (Exception e){
                            System.out.println("Opção Inválida!");
                        }
                    }

                    escolha = 0;
                    while(escolha < 1 || escolha > 5){
                        try{
                            System.out.print("Digite o número do item que deseja editar: ");
                            escolha = Integer.parseInt(scanner.nextLine());
                        }catch (Exception e){
                            System.out.println("Opção Inválida!");
                        }
                    }

                    switch (escolha){
                        case 1:
                            List<Livro> livros1 = Livro.buscarLivros(banco);
                            System.out.println("----------------------------------------------------------------------");
                            for (Livro livro: livros1) {
                                livro.printLivroFormatado();
                            }
                            escolhaLivro = 0;
                            boolean valido = false;
                            while(!valido){
                                try{
                                    System.out.print("Digite o código do livro: ");
                                    escolhaLivro = Integer.parseInt(scanner.nextLine());

                                    for (Livro livro: livros1) {
                                        if(escolhaLivro == livro.getCodLivro()){
                                            valido = true;
                                            editarVenda.livro = livro;
                                            break;
                                        }
                                    }
                                } catch (Exception e){
                                    System.out.println("Opção Inválida!");
                                }
                            }
                            ItemVenda.editarItemVenda(banco, editarVenda, codLivroOriginal);
                            break;
                        case 2:
                            int qtdNova = 0;
                            while(qtdNova < 1){
                                try{
                                    System.out.print("Digite a nova quantidade: ");
                                    qtdNova = Integer.parseInt(scanner.nextLine());
                                } catch (Exception e){
                                    System.out.println("Opção Inválida!");
                                }
                            }
                            editarVenda.setQtdLivros(qtdNova);
                            ItemVenda.editarItemVenda(banco, editarVenda, codLivroOriginal);
                            break;
                        case 3:
                            int i = 1;
                            for (String pagamento: Venda.metodosPagamento) {
                                System.out.println(i + " | " + pagamento);
                                i++;
                            }
                            int novoMetodoPag = 0;
                            while(novoMetodoPag < 1 || novoMetodoPag > 5){
                                try{
                                    System.out.print("Escolha o método de pagamento: ");
                                    novoMetodoPag = Integer.parseInt(scanner.nextLine());
                                } catch (Exception e){
                                    System.out.println("Opção Inválida!");
                                }
                            }
                            editarVenda.venda.setMetodoPag(Venda.metodosPagamento[novoMetodoPag-1]);
                            ItemVenda.editarItemVenda(banco, editarVenda, codLivroOriginal);
                            break;
                        case 4:
                            String data = "";
                            do{
                                System.out.print("Nova data de venda (DD/MM/AAAA): ");
                                data = scanner.next();
                                if (!data.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
                                    System.out.println("Formato de data inválido! Use DD/MM/AAAA.");
                                }
                            }while(!data.matches("^\\d{2}/\\d{2}/\\d{4}$"));

                            SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy");
                            java.util.Date parsedDate = sdfInput.parse(data);
                            Date sqlDate = new Date(parsedDate.getTime());

                            editarVenda.venda.setDataVenda(sqlDate);
                            ItemVenda.editarItemVenda(banco, editarVenda, codLivroOriginal);
                            break;
                        case 5:
                            List<Cliente> clientes1 = Cliente.buscarClientes(banco);
                            System.out.println("----------------------------------------------------------------------");
                            for (Cliente cliente: clientes1) {
                                cliente.printClienteFormatado();
                            }
                            int escolhaCliente = 0;
                            boolean validoC = false;
                            while(!validoC){
                                try{
                                    System.out.print("Digite o código do cliente: ");
                                    escolhaCliente = Integer.parseInt(scanner.nextLine());

                                    for (Cliente cliente: clientes1) {
                                        if(escolhaCliente == cliente.getCodCliente()){
                                            validoC = true;
                                            editarVenda.cliente = cliente;
                                            break;
                                        }
                                    }
                                } catch (Exception e){
                                    System.out.println("Opção Inválida!");
                                }
                            }
                            ItemVenda.editarItemVenda(banco, editarVenda, codLivroOriginal);
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("Opção Inválida!");
                }
                System.out.println("Ao alterar a venda o estoque não é alterado!");
                break;
            case "3":
                int escolha = 0;
                boolean excluirValido = false;
                ItemVenda excluirVenda = new ItemVenda();
                while (!excluirValido){
                    try{
                        System.out.print("Digite o código da venda que deseja excluir: ");
                        escolha = Integer.parseInt(scanner.nextLine());

                        for (ItemVenda venda: itensVendas) {
                            if(escolha == venda.getCodPedido()){
                                excluirVenda = venda;
                                excluirValido = true;
                                break;
                            }
                        }
                    } catch (Exception e){
                        System.out.println("Opção Inválida!");
                    }
                }

                System.out.println("Você escolheu a venda: ");
                excluirVenda.printItemVendaFormatado();
                System.out.println("Ao excluir a venda o estoque não é alterado!");
                System.out.print("Tem certeza que deseja excluir essa venda (s/n)? ");
                String resposta = scanner.nextLine();
                if(resposta.toUpperCase().equals("SIM") || resposta.toUpperCase().equals("S")){
                    ItemVenda.excluirItemVenda(banco, excluirVenda);
                }
                break;
            default:
                System.out.println("Carregando Menu Inicial...");
        }
    }
}