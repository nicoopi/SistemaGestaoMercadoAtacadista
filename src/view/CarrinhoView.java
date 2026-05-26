package view;

import controller.CarrinhoController;
import exceptions.CarrinhoVazioException;
import exceptions.EstoqueInsuficienteException;
import exceptions.RegistroNaoEncontradoException;
import model.ItemPedido;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CarrinhoView {
    private Scanner sc = new Scanner(System.in);
    private CarrinhoController controller;

    public int lerIDProduto() {
        System.out.print("Digite o ID do Produto que deseja: ");
        return sc.nextInt();
    }


    public int lerQuantidadeDesejada() {
        System.out.print("Digite a Quantidade Desejada: ");
        return sc.nextInt();
    }

    public void exibirMensagem(String msg) {
        System.out.println(msg);
    }

    public void fecharScanner() {
        sc.close();
    }

    public void limparBuffer() {
        sc.nextLine();
    }

    public void exibirMenu() {
        int opcao = -1;

        do {
            try {
                System.out.println("\n===== MENU DO CARRINHO =====");
                System.out.println("1 - Adicionar produto ao carrinho");
                System.out.println("2 - Exibir itens do carrinho");
                System.out.println("3 - Remover produto do carrinho");
                System.out.println("4 - Finalizar Compra");
                System.out.println("0 - Sair do menu do carrinho");
                System.out.print("Digite a opção que deseja: ");
                opcao = sc.nextInt();
                limparBuffer();
                System.out.println("===================================\n");

                switch (opcao) {
                    case 1:
                        exibirCadastroDeProduto();
                        break;
                    case 2:
                        exibirCarrinho(controller.getItensCarrinho());
                        break;
                    case 3:
                        exibirRemocaoProduto();
                        break;
                    case 4:
                        exibirFinalizacaoCompra();
                        break;
                    case 0:
                        System.out.println("Saindo do menu do carrinho...");
                        break;
                    default:
                        System.out.println("Opção inválida, tente novamente!");
                }
            } catch (InputMismatchException e) {
                System.out.println("ERRO: Digite somente números!");
                limparBuffer();
            }
        } while (opcao != 0);
    }

    public void exibirCadastroDeProduto() {
        try {
            System.out.println("----- Adicionando Produto ao Carrinho -----");
            controller.adicionarProdutonoCarrinho(lerIDProduto(), lerQuantidadeDesejada());
            System.out.println("\nSucesso: Produto adicionado ao carrinho!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (EstoqueInsuficienteException e) {
            System.out.println("Aviso do Estoque: " + e.getMessage());
        }
    }

    public void exibirCarrinho(List<ItemPedido> listaItens) {
        if( listaItens == null || listaItens.isEmpty()) {
            System.out.println("--- O CARRINHO ESTÁ VAZIO ---");
        } else {
            System.out.println("---- ITENS NO CARRINHO ----");
            for(ItemPedido item : listaItens) {
                System.out.println(item);
            }
        }
    }

    public void exibirRemocaoProduto() {
        try {
            System.out.println("---- Remoção de Produto ----");
            controller.removerProdutodoCarrinho();
        } catch (RegistroNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void exibirFinalizacaoCompra() {
        try {
            System.out.println("---- Finalização da Compra ----");

            String nomeCliente = controller.getClienteAtual().getNome();
            System.out.printf("Sucesso! Compra finalizada do Cliente: %s. Valor Total: R$%.2f\n" , nomeCliente, controller.finalizarCompra());
        } catch (CarrinhoVazioException e) {
            System.out.println(e.getMessage());
        }
    }


    public void setController(CarrinhoController controller) {
        this.controller = controller;
    }

}
