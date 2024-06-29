package model;

import dao.DataBaseConection;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Estoque {
    private int codLivro;
    private int qtdEstoque;

    public Livro livro;

    // Getters e Setters
    public int getCodLivro() {
        return this.codLivro;
    }

    public void setCodLivro(int codLivro) {
        this.codLivro = codLivro;
    }

    public int getQtdEstoque() {
        return this.qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public void printEstoqueSemFormatacao(){
        System.out.println("Código Livro: " + codLivro);
        System.out.println("Título:       " + livro.getTitulo());
        System.out.println("ISBN:         " + livro.getIsbn());
        System.out.println("Quantidade:   " + qtdEstoque);
    }
}