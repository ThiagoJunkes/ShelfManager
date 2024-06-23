package view;

import model.*;

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

        System.out.println("|Cod Cliente | Nome            | Sobrenome       | CPF            | Email                     | Telefone        |Data Cadastro | Rua             | Cidade          | Estado | CEP      | Complemento       |");
        System.out.println("|------------|-----------------|-----------------|----------------|---------------------------|-----------------|--------------|-----------------|-----------------|--------|----------|-------------------|");

        for (Cliente cliente : clientes) {
            // Limitando o tamanho dos campos
            String email = cliente.getEmailCliente().length() > 25 ? cliente.getEmailCliente().substring(0, 25) : cliente.getEmailCliente();
            String nome = cliente.getNome().length() > 15 ? cliente.getNome().substring(0, 15) : cliente.getNome();
            String sobrenome = cliente.getSobrenome().length() > 15 ? cliente.getSobrenome().substring(0, 15) : cliente.getSobrenome();
            String telefone = cliente.getTelefoneCliente().length() > 15 ? cliente.getTelefoneCliente().substring(0, 15) : cliente.getTelefoneCliente();
            String rua = cliente.endereco.getRua().length() > 15 ? cliente.endereco.getRua().substring(0, 15) : cliente.endereco.getRua();
            String cidade = cliente.endereco.getCidade().length() > 15 ? cliente.endereco.getCidade().substring(0, 15) : cliente.endereco.getCidade();
            String estado = cliente.endereco.getEstado().length() > 6 ? cliente.endereco.getEstado().substring(0, 6) : cliente.endereco.getEstado();
            String complemento = cliente.endereco.getComplemento().length() > 15 ? cliente.endereco.getComplemento().substring(0, 15) : cliente.endereco.getComplemento();

            System.out.printf("| %-10d | %-15s | %-15s | %-14d | %-25s | %-15s | %-12s | %-15s | %-15s | %-6s | %-8d | %-17s |\n",
                    cliente.getCodCliente(),
                    nome,
                    sobrenome,
                    cliente.getCpf(),
                    email,
                    telefone,
                    dateFormat.format(cliente.getDataCadastro()),
                    rua,
                    cidade,
                    estado,
                    cliente.endereco.getCep(),
                    complemento);
        }
        System.out.println("|------------|-----------------|-----------------|----------------|---------------------------|-----------------|--------------|-----------------|-----------------|--------|----------|-------------------|");
    }

    public static void editoras(List<Editora> editoras){
        System.out.println("|Cod Editora | Nome da Editora         | Nome do Contato    | Email da Editora               | Telefone da Editora  |");
        System.out.println("|------------|-------------------------|--------------------|--------------------------------|----------------------|");

        for (Editora editora : editoras) {
            // Limitando o tamanho dos campos
            String nomeEditora = editora.getNomeEditora().length() > 25 ? editora.getNomeEditora().substring(0, 25) : editora.getNomeEditora();
            String nomeContato = editora.getNomeContato().length() > 20 ? editora.getNomeContato().substring(0, 20) : editora.getNomeContato();
            String email = editora.getEmailEditora().length() > 30 ? editora.getEmailEditora().substring(0, 30) : editora.getEmailEditora();
            String telefone = editora.getTelefoneEditora().length() > 20 ? editora.getTelefoneEditora().substring(0, 20) : editora.getTelefoneEditora();

            System.out.printf("| %-10d | %-23s | %-18s | %-30s | %-20s |\n",
                    editora.getCodEditora(),
                    nomeEditora,
                    nomeContato,
                    email,
                    telefone);
        }
        System.out.println("|------------|-------------------------|--------------------|--------------------------------|----------------------|");
    }


    public static void estoque(List<Livro> estoque){
        System.out.println("| Cod Livro |  Quantidade  |  Título do Livro          | ISBN            |");
        System.out.println("|-----------|--------------|---------------------------|-----------------|");

        for (Livro livro : estoque) {
            // Limitando o tamanho dos campos
            String quantidade = String.valueOf(livro.getQtdEstoque());
            String titulo = livro.getTitulo().length() > 25 ? livro.getTitulo().substring(0, 25) : livro.getTitulo();
            String isbn = String.valueOf(livro.getIsbn());

            System.out.printf("| %-9d | %-12s | %-25s | %-15s |\n",
                    livro.getCodLivro(),
                    quantidade,
                    titulo,
                    isbn);
        }
        System.out.println("|-----------|--------------|---------------------------|-----------------|");
    }

    public static void livros(List<Livro> livros) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("|Cod Livro | Título                  | Gênero               | Autor           | ISBN            | Ano Publicação  | Preço    | Cod Editora | Nome da Editora         |");
        System.out.println("|----------|-------------------------|----------------------|-----------------|-----------------|-----------------|----------|-------------|-------------------------|");

        for (Livro livro : livros) {
            // Limitando o tamanho dos campos
            String titulo = livro.getTitulo().length() > 20 ? livro.getTitulo().substring(0, 20) : livro.getTitulo();
            String genero = livro.getGenero().length() > 18 ? livro.getGenero().substring(0, 18) : livro.getGenero();
            String autor = livro.getAutor().length() > 15 ? livro.getAutor().substring(0, 15) : livro.getAutor();
            String isbn = String.valueOf(livro.getIsbn());
            String anoPublicacao = dateFormat.format(livro.getAnoPublicacao());
            String preco = String.valueOf(livro.getPreco());
            String codEditora = String.valueOf(livro.getCodEditora());
            String nomeEditora = livro.editora.getNomeEditora().length() > 25 ? livro.editora.getNomeEditora().substring(0, 25) : livro.editora.getNomeEditora();

            System.out.printf("| %-8d | %-23s | %-20s | %-15s | %-15s | %-15s | %-8s | %-11s | %-23s |\n",
                    livro.getCodLivro(),
                    titulo,
                    genero,
                    autor,
                    isbn,
                    anoPublicacao,
                    preco,
                    codEditora,
                    nomeEditora);
        }
        System.out.println("|----------|-------------------------|----------------------|-----------------|-----------------|-----------------|----------|-------------|-------------------------|");
    }




    public static void vendas(List<ItemVenda> itemVendas) {
        int codPedido = 0;
        boolean primeiraLinha = true;
        for (ItemVenda venda : itemVendas) {
            if (codPedido != venda.getCodPedido()) {
                String nomeCompleto = venda.cliente.getNome() + " " + venda.cliente.getSobrenome();
                nomeCompleto = nomeCompleto.length() > 20 ? nomeCompleto.substring(0, 17) + "..." : nomeCompleto;

                String valorVenda = "R$" + venda.venda.getValorVenda();
                valorVenda = valorVenda.length() > 12 ? valorVenda.substring(0, 9) + "..." : valorVenda;

                String metodoPag = venda.venda.getMetodoPag();
                metodoPag = metodoPag.length() > 21 ? metodoPag.substring(0, 18) + "..." : metodoPag;
                if(!primeiraLinha) {
                    System.out.println("------------------------------------------------------------------------------");
                    System.out.println();
                }
                System.out.println("------------------------------------------------------------------------------");
                System.out.printf("| Venda: %-5d | %-20s | %-12s | %-21s |\n", // total 70 carac
                        venda.getCodPedido(),
                        nomeCompleto,
                        valorVenda,
                        metodoPag
                );
                codPedido = venda.getCodPedido();
                System.out.println("------------------------------------------------------------------------------");
                primeiraLinha = false;
            }

            String precoLivro = "R$" + String.valueOf(venda.livro.getPreco());
            String qtdLivros = String.valueOf(venda.getQtdLivros());
            String tituloLivro = venda.livro.getTitulo();
            tituloLivro = tituloLivro.length() > 46 ? tituloLivro.substring(0, 43) + "..." : tituloLivro;

            System.out.printf("| %-14s | %-8s | %-46s |\n", precoLivro, qtdLivros, tituloLivro);
        }
        System.out.println("------------------------------------------------------------------------------");
    }


    public static void relatorios(){
        System.out.println("Cliente");
    }
}
