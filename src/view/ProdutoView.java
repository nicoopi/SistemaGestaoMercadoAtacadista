package view;

import controller.EstoqueController;
import controller.ProdutoController;
import exceptions.RegistroNaoEncontradoException;
import util.LoggerService;

import model.Produto;
import java.util.InputMismatchException;
import java.util.HashMap;
import java.util.Scanner;


public class ProdutoView {
     private Scanner sc = new Scanner(System.in);
     private ProdutoController controller;
     private ProdutoView view;
     private EstoqueController estoqueController;

     public void mostrarMenuProduto() {
         int opcao = -1;
         do {
             try {
                 System.out.println("\n===== MENU PRODUTO =====");
                 System.out.println("1 - CADASTRAR PRODUTO ");
                 System.out.println("2 - EXIBIR PRODUTOS CADASTRADOS");
                 System.out.println("3 - EXIBIR ÚLTIMO PRODUTO CADASTRADO");
                 System.out.println("4 - EXIBIR PRODUTO POR ID");
                 System.out.println("5 - REMOVER PRODUTO POR ID");
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
                         exibirProduto();
                         break;
                     case 4:
                         exibirBuscaPorId();
                         break;
                     case 5:
                         exibirProdutosRemovidos();
                         break;
                     case 0:
                         System.out.println("Saindo do Menu cadastro de PRODUTO...");
                         break;
                     default:
                         System.out.println("Opção inválida, tente novamente!");
                    }
             } catch (InputMismatchException e) {
                 System.out.println("ERRO: Digite somente números!");
                 LoggerService.log("ERROR", "Usuário digitou um caractere inválido no menu.");
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
            System.out.println("----- Cadastro de Produto -----");
            controller.cadastrarProduto(lerNomeProduto(), lerPrecoProduto(), lerIDProduto());
            System.out.println("\nSucesso: Produto cadastrado!");
        }  catch (InputMismatchException e) {
            System.out.println("ERRO: Formato de preço ou ID inválido! Digite apenas números no preço e no ID.");
            LoggerService.log("ERROR", e.getMessage());
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            LoggerService.log("ERROR", e.getMessage());
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
    public void exibirProduto() {
         try {
             Produto produto = controller.exibirUltimoProdutoCadastrado();

             System.out.println("----- INFORMAÇÕES DO ÚLTIMO PRODUTO -----");
             System.out.println(produto);

         } catch (IllegalArgumentException e) {
             System.out.println(e.getMessage());
             LoggerService.log("ERROR", e.getMessage());
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
     public void exibirBuscaPorId(){
         try {
             System.out.println("----- Busca de Produto -----");
             Produto produto = controller.buscarProdutoPorId(lerIDProduto());
             System.out.println("\nSucesso: Produto encontrado!");
             System.out.println(produto);

         } catch (InputMismatchException e) {
             System.out.println("ERRO: O ID deve conter apenas números!");
             LoggerService.log("ERROR", e.getMessage());
             limparBuffer();

         }catch (IllegalArgumentException | RegistroNaoEncontradoException e) {
             System.out.println(e.getMessage());
             LoggerService.log("ERROR", e.getMessage());
         }
     }
    public void exibirProdutosRemovidos() {
        try {
            System.out.println("----- Remoção de Produto -----");
            int id = lerIDProduto();
            limparBuffer();
            if(estoqueController == null && estoqueController.existeProdutoNoEstoque(id)){
                System.out.println("ERRO: Operação bloqueda");
                System.out.println("O sistema identificou que este produto ainda existe no estoque");
                System.out.println("Por gentileza, remova do estoque antes de excluí-lo do cátalogo");
                return;
            }
            controller.removerProdutoPorID(id);
            System.out.println("\nSucesso! Produto removido");
        } catch (RegistroNaoEncontradoException e) {
            System.out.println(e.getMessage());
            LoggerService.log("ERROR", e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("ERRO: Formato inválido! Por favor, digite apenas números.");
            LoggerService.log("ERROR", e.getMessage());
            sc.nextLine();
        }
    }
    public void setController(ProdutoController controller) {
        this.controller = controller;
    }

    public void setEstoqueController(EstoqueController estoqueController) {
        this.estoqueController = estoqueController;
    }
}
