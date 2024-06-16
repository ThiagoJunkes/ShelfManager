package model;

import dao.DataBaseConection;

import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cliente {
    private int codCliente;
    private String nome;
    private String sobrenome;
    private long cpf;
    private String emailCliente;
    private String telefoneCliente;
    private Date dataCadastro;
    private int codEndereco;

    public Endereco endereco;

    // Getters e Setters
    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public long getCpf() {
        return cpf;
    }

    public void setCpf(long cpf) {
        this.cpf = cpf;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getTelefoneCliente() {
        return telefoneCliente;
    }

    public void setTelefoneCliente(String telefoneCliente) {
        this.telefoneCliente = telefoneCliente;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public int getCodEndereco() {
        return codEndereco;
    }

    public void setCodEndereco(int codEndereco) {
        this.codEndereco = codEndereco;
    }

    public void printClienteFormatado(){
        String nomeFormatado = nome;
        nomeFormatado = nomeFormatado.length() > 20 ? nomeFormatado.substring(0, 20) : nomeFormatado;
        String sobrenomeFormatado = sobrenome;
        sobrenomeFormatado = sobrenomeFormatado.length() > 20 ? sobrenomeFormatado.substring(0, 20) : sobrenomeFormatado;
        System.out.printf("%-5d | %-20s | %-20s | %-20s \n", // total 70 carac
                codCliente,
                nomeFormatado,
                sobrenomeFormatado,
                cpf
        );
    }
    public void printClienteSemFormatacao() {
        System.out.println("Cliente: " + codCliente + " | Data Cadastro: " + dataCadastro);
        System.out.println("1 - Nome:          " + nome);
        System.out.println("2 - Sobrenome:     " + sobrenome);
        System.out.println("3 - CPF:           " + cpf);
        System.out.println("4 - Email:         " + emailCliente);
        System.out.println("5 - Telefone:      " + telefoneCliente);

        if (endereco != null) {
            System.out.println("Endereço:");
            System.out.println("6 - Rua:           " + endereco.getRua());
            System.out.println("7 - Cidade:        " + endereco.getCidade());
            System.out.println("8 - Estado:        " + endereco.getEstado());
            System.out.println("9 - CEP:           " + endereco.getCep());
            System.out.println("10- Complemento:   " + endereco.getComplemento());
        }
    }

    public static boolean editarCliente(Cliente clienteAtualizado, DataBaseConection banco) {
        try (Session session = banco.getSession()) {
            String query = "MATCH (cliente:Cliente {cpf: " + clienteAtualizado.getCpf() + "}) " +
                    "SET cliente.nome = '" + clienteAtualizado.getNome() + "', " +
                    "    cliente.sobrenome = '" + clienteAtualizado.getSobrenome() + "', " +
                    "    cliente.email_cliente = '" + clienteAtualizado.getEmailCliente() + "', " +
                    "    cliente.telefone_cliente = '" + clienteAtualizado.getTelefoneCliente() + "' " +
                    "RETURN cliente";

            List<Record> result = session.writeTransaction(tx -> {
                Result resultSet = tx.run(query);
                return resultSet.list();
            });

            if (!result.isEmpty()) {
                System.out.println("Cliente atualizado com sucesso!");
                return true;
            } else {
                System.out.println("Nenhum cliente atualizado.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Falha ao atualizar cliente.");
            return false;
        }
    }



    public static List<Cliente> buscarClientes(DataBaseConection banco){
        List<Cliente> clientes = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try (Session session = banco.getSession()) {
            String query = "MATCH (cliente:Cliente)-[:mora_em]->(endereco:Endereco) RETURN cliente, endereco ORDER BY cliente.cpf";
            try (Transaction tx = session.beginTransaction()) {
                Result result = tx.run(query);

                while (result.hasNext()) {
                    Record record = result.next();
                    Node clienteNode = record.get("cliente").asNode();
                    Node enderecoNode = record.get("endereco").asNode();

                    Cliente cliente = new Cliente();
                    Endereco endereco = new Endereco();

                    cliente.setCodCliente(clienteNode.get("codCliente").asInt());
                    cliente.setNome(clienteNode.get("nome").asString());
                    cliente.setSobrenome(clienteNode.get("sobrenome").asString());
                    cliente.setCpf(clienteNode.get("cpf").asLong());
                    cliente.setEmailCliente(clienteNode.get("email").asString());
                    cliente.setTelefoneCliente(clienteNode.get("telefone").asString());
                    cliente.setDataCadastro(sdf.parse(clienteNode.get("data_cadastro").asString()));

                    endereco.setRua(enderecoNode.get("rua").asString());
                    endereco.setCidade(enderecoNode.get("cidade").asString());
                    endereco.setEstado(enderecoNode.get("estado").asString());
                    endereco.setCep(enderecoNode.get("cep").asInt());
                    endereco.setComplemento(enderecoNode.get("complemento").asString());
                    cliente.endereco = endereco;
                    clientes.add(cliente);
                }
            }
        }catch (Exception e) {
            System.out.println("Erro ao buscar Clientes: " + e.getMessage());
        }

        return  clientes;
    }

    public static boolean adicionarCliente(Cliente cliente, DataBaseConection banco) {
        try (Session session = banco.getSession()) {
            // Cria a query para adicionar Cliente e Endereço
            String query = "CREATE (endereco:Endereco {rua: '" + cliente.endereco.getRua() + "', " +
                    "cidade: '" + cliente.endereco.getCidade() + "', " +
                    "estado: '" + cliente.endereco.getEstado() + "', " +
                    "cep: " + cliente.endereco.getCep() + ", " +
                    "complemento: '" + cliente.endereco.getComplemento() + "'}) " +
                    "CREATE (cliente:Cliente {nome: '" + cliente.getNome() + "', " +
                    "sobrenome: '" + cliente.getSobrenome() + "', " +
                    "cpf: " + cliente.getCpf() + ", " +
                    "email: '" + cliente.getEmailCliente() + "', " +
                    "telefone: '" + cliente.getTelefoneCliente() + "', " +
                    "data_cadastro: '" + cliente.getDataCadastro().toString() + "'}) " +
                    "CREATE (cliente)-[:MORA_EM]->(endereco)";

            // Executa a query em uma transação
            session.writeTransaction(tx -> {
                tx.run(query);
                return null;
            });

            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static boolean excluirCliente(Cliente clienteExcluir, DataBaseConection banco) {
        try (Session session = banco.getSession()) {
            String verificarComprasQuery = "MATCH (cliente:Cliente {cpf: " + clienteExcluir.getCpf() + "})-[:REALIZOU_COMPRA]->(venda:Venda) RETURN venda";

            boolean possuiCompras = session.readTransaction(tx -> tx.run(verificarComprasQuery).hasNext());

            if (possuiCompras) {
                System.out.println("Não é possível excluir o cliente pois ele já realizou uma compra.");
                return false;
            } else {
                String query = "MATCH (cliente:Cliente {cpf: " + clienteExcluir.getCpf() + "}) " +
                        "OPTIONAL MATCH (cliente)-[r:MORA_EM]->(endereco:Endereco) " +
                        "DELETE cliente, r, endereco";

                session.writeTransaction(tx -> {
                    tx.run(query);
                    return null;
                });

                System.out.println("Cliente excluído com sucesso!");
                return true;
            }
        } catch (Exception e) {
            System.out.println("Falha ao excluir cliente.");
            return false;
        }
    }

}

