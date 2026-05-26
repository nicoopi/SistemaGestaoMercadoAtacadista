package view;

import controller.EstoqueController;
import model.Produto;
import model.Estoque;
import exceptions.RegistroNaoEncontradoException;
import controller.ProdutoController;

import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.HashMap;
import java.util.Scanner;

public class EstoqueView {
    private Scanner sc = new Scanner(System.in);
    private EstoqueController controller;

    public String lerDataDeValidade(){
        System.out.println("Digite a data de validade (formato DD/MM/AAAA): \"");
        return sc.nextLine();
    }
    public int lerQuantidadeAtual(){
        System.out.println("Digite a quantidade que deseja cadastrar: ");
        return sc.nextInt();
    }
    public String lerLote(){
        System.out.println("Digite o lote do produto: ");
        return sc.nextLine();
    }
}
