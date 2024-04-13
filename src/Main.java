import dao.DataBaseConection;
import model.*;
import view.Menu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
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
                    Menu.vendas();
                    break;
                case "5":
                    System.out.println("Opção Estoque selecionada.");
                    Menu.estoque();
                    break;
                case "6":
                    System.out.println("Opção Relatórios selecionada.");
                    Relatorio.main(new String[0]);
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
                                    System.out.println("Nome da Editora atualizado com sucesso!");
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
                                    System.out.println("Nome do Contato atualizado com sucesso!");
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
                                    System.out.println("Email da Editora atualizado com sucesso!");
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
                                    System.out.println("Telefone da Editora atualizado com sucesso!");
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
                                case 1:
                                    System.out.print("Novo Título: ");
                                    String novoTitulo = scanner.nextLine();
                                    if (!novoTitulo.isEmpty()) {
                                        livroEditar.setTitulo(novoTitulo);
                                        System.out.println("Título atualizado com sucesso!");
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
                                        System.out.println("Gênero atualizado com sucesso!");
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
                                        System.out.println("Autor atualizado com sucesso!");
                                    } else {
                                        System.out.println("Autor não pode ser vazio. Operação cancelada.");
                                        editar = false;
                                    }
                                    break;
                                case 4:
                                    System.out.print("Novo ISBN: ");
                                    long novoISBN = Long.parseLong(scanner.nextLine());
                                    livroEditar.setIsbn(novoISBN);
                                    System.out.println("ISBN atualizado com sucesso!");
                                    break;
                                case 5:
                                    System.out.print("Nova Data de Publicação (AAAA-MM-DD): ");
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    java.util.Date parsedDate = sdf.parse(scanner.nextLine());
                                    java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
                                    livroEditar.setAnoPublicacao(sqlDate);
                                    System.out.println("Data de Publicação atualizada com sucesso!");
                                    break;
                                case 6:
                                    System.out.print("Novo Preço: ");
                                    double novoPreco = Double.parseDouble(scanner.nextLine());
                                    livroEditar.setPreco(novoPreco);
                                    System.out.println("Preço atualizado com sucesso!");
                                    break;
                                case 7:
                                    System.out.print("Novo Código da Editora: ");
                                    int novoCodEditora = Integer.parseInt(scanner.nextLine());
                                    livroEditar.setCodEditora(novoCodEditora);
                                    System.out.println("Código da Editora atualizado com sucesso!");
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


}