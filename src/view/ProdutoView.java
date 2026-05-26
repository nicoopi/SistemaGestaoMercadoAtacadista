package view;

import controller.ProdutoController;
import model.ClienteFisico;
import model.Produto;

import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;


public class ProdutoView {
     private Scanner sc = new Scanner(System.in);
     private ProdutoController controller;

     public void mostrarMenuProduto() {
         int opcao = -1;
         do {
             try {
                 System.out.println("\n===== MENU PRODUTO =====");
                 System.out.println("1 - CADASTRAR PRODUTO ");
                 System.out.println("2 - EXIBIR PRODUTOS CADASTRADOS");
                 System.out.println("3 - EXIBIR ÚLTIMO PRODUTO CADASTRADO");
                 System.out.println("0 - SAIR");
                 System.out.print("Digite a opção que deseja: ");
                 opcao = sc.nextInt();
                 limparBuffer();
                 System.out.println("===========================\n");

                 switch (opcao) {
                     case 1:
                        exibirCadastroProduto();
                         break;
                     case 2:
                         exibirListaProdutos(controller.listarProdutos());
                         break;
                     case 3:
                         exibirProduto(controller.exibirUltimoProdutoCadastrado());
                         break;
                     case 0:
                         System.out.println("Saindo do Menu cadastro de PRODUTO...");
                         break;
                     default:
                         System.out.println("Opção inválida, tente novamente!");
                    }
             } catch (InputMismatchException e) {
                 System.out.println("ERRO: Digite somente números!");
                 limparBuffer();
             }
         }while (opcao != 0) ;
     }

     public void limparBuffer(){
         sc.nextLine();
     }
     public void exibirMensagem(String mensagem){
         System.out.println(mensagem);
     }
     public void fecharScanner() {
         sc.close();
     }
    public void exibirCadastroProduto() {
        try {
            System.out.println("----- Cadastro de Cliente Físico -----");
            controller.cadastrarProduto(lerNomeProduto(), lerPrecoProduto(), lerIDProduto());
            System.out.println("\nSucesso: Cliente físico cadastrado!");
        }  catch (InputMismatchException e) {
            System.out.println("ERRO: Formato de preço ou ID inválido! Digite apenas números no preço e no ID.");
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

     public String lerNomeProduto(){
         System.out.print("Digite um nome válido para o produto: ");
         return sc.nextLine();
     }
     public double lerPrecoProduto() {
         System.out.print("Digite o preço base do produto: ");
         return sc.nextDouble();
     }
     public int lerIDProduto() {
         System.out.print("Digite um ID válido para o produto: ");
         return sc.nextInt();
     }

     public void exibirProduto(Produto p) {
         if (p == null) {
             System.out.println("PRODUTO VAZIO");
         } else {
             System.out.println("----- INFORMAÇÕES DO PRODUTO -----");
             System.out.println(p);
         }
     }

     public void exibirListaProdutos(HashMap<Integer, Produto> mapa) {
         if (mapa.isEmpty()) {
             System.out.println("Nenhum produto cadastrado no momento.");
         } else {
             System.out.println("----- Lista de PRODUTOS -----");
             for (Produto produto : mapa.values()) {
                 System.out.println(produto);
             }
         }
     }

    public void setController(ProdutoController controller) {
        this.controller = controller;
    }
}
