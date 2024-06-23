import dao.DataBaseConection;
//import model.*;
import model.Cliente;
import model.Livro;
import model.Editora;
import view.Menu;

import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//import controller.Relatorio;

public class MainTeste {

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
                    //opcaoClientes(banco);
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
                    //Relatorio.main(new String[0]);
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

        System.out.println("Clientes");
    }

    private static void opcaoEditoras(DataBaseConection banco){
        Scanner scanner = new Scanner(System.in);

        System.out.println(" 0 - Menu Inicial | 1 - Adicionar Editora | 2 - Editar Editora | 3 - Excluir Editora ");
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
                            novoLivro.editora = editora;
                            break;
                        }
                    }

                    if(!hasEditora){
                        System.out.println("Código da Editora inválido!");
                        break;
                    }


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
                                case 5:
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
                                case 6:
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
                                            livroEditar.editora = editora;
                                            break;
                                        }
                                    }

                                    if(!hasEditora){
                                        System.out.println("Código da Editora inválido!");
                                        break;
                                    }

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

        System.out.println("* Ao adicionar um Livro ele automaticamente aparece em estoque.");
        System.out.println("* Não é possivel excluir diretamente no estoque.");
        System.out.println("* Acesse o Menu de Livros e exclua um Livro para o seu estoque ser excluido.");
        System.out.println(" 0 - Menu Inicial | 1 - Editar Estoque ");
    }

    private static void opcaoVendas(DataBaseConection banco){
        Scanner scanner = new Scanner(System.in);

        System.out.println(" 0 - Menu Inicial | 1 - Adicionar Venda | 2 - Editar Venda | 3 - Excluir Venda ");

    }
}