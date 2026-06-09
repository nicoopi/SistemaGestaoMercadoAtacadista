package view;

import controller.CarrinhoController;
import controller.ClienteFisicoController;
import controller.ClienteJuridicoController;
import controller.PedidoController;
import exceptions.CarrinhoVazioException;
import exceptions.EstoqueInsuficienteException;
import exceptions.RegistroNaoEncontradoException;
import model.Cliente;
import model.ItemPedido;
import util.LoggerService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CarrinhoView {
    private Scanner sc = new Scanner(System.in);
    private CarrinhoController controller;
    private PedidoController pedidoController;
    private ClienteFisicoController clienteFisicoController;
    private ClienteJuridicoController clienteJuridicoController;

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
                        exibirCarrinho(controller.listarItensCarrinho());
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
                LoggerService.log("ERROR", "Usuário digitou um caractere inválido no menu.");
                limparBuffer();
            }
        } while (opcao != 0);
    }

    public void exibirCadastroDeProduto() {
        try {
            System.out.println("----- Adicionando Produto ao Carrinho -----");
            controller.adicionarProdutonoCarrinho(lerIDProduto(), lerQuantidadeDesejada());
            System.out.println("\nSucesso! Produto adicionado ao carrinho!");
        } catch (IllegalArgumentException | RegistroNaoEncontradoException e) {
            System.out.println(e.getMessage());
            LoggerService.log("ERROR", e.getMessage());
        } catch (EstoqueInsuficienteException e) {
            System.out.println("Aviso do Estoque: " + e.getMessage());
            LoggerService.log("ERROR", e.getMessage());
        }
    }

    public void exibirCarrinho(List<ItemPedido> listaItens) {
        if( listaItens == null || listaItens.isEmpty()) {
            System.out.println("----- O CARRINHO ESTÁ VAZIO -----");
        } else {
            System.out.println("----- ITENS NO CARRINHO -----");
            for(ItemPedido item : listaItens) {
                System.out.println(item);
            }
        }
    }

    public void exibirRemocaoProduto() {
        try {
            System.out.println("----- Remoção de Produto -----");
            int id = lerIDProduto();

            System.out.print("Digite a quantidade que deseja remover deste item: ");
            int qtd = sc.nextInt();
            limparBuffer();

            int resultado = controller.removerProdutodoCarrinho(id, qtd);

            if(resultado == -1) {
                System.out.println("\nAviso: Você digitou um valor maior do que possuía. O item foi removido totalmente do carrinho!");
            } else if (resultado == 0) {
                System.out.println("\nSucesso! O item foi removido totalmente do carrinho!");
            } else {
                System.out.println("\nSucesso! Quantidade atualizada. Restam " + resultado + " unidades deste item no carrinho.");
            }
        } catch (RegistroNaoEncontradoException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            LoggerService.log("ERROR", e.getMessage());
        }
    }

    public void exibirFinalizacaoCompra() {
        try {
            System.out.println("----- Finalização da Compra -----");

            System.out.println("Qual o tipo de cliente?");
            System.out.println("1 - Pessoa Física (CPF)");
            System.out.println("2 - Pessoa Jurídica (CNPJ)");
            System.out.print("Opção: ");
            int tipoCliente = sc.nextInt();
            limparBuffer();

            Cliente clienteEncontrado = null;

            if (tipoCliente == 1) {
                System.out.print("Digite o CPF do cliente: ");
                String cpf = sc.nextLine();
                clienteEncontrado = clienteFisicoController.buscarPorCpf(cpf);
            } else if (tipoCliente == 2) {
                System.out.print("Digite o CNPJ do cliente: ");
                String cnpj = sc.nextLine();
                clienteEncontrado = clienteJuridicoController.buscarPorCnpj(cnpj);
            } else {
                System.out.println("ERRO: Opção de cliente inválida. Compra cancelada.");
                return;
            }

            controller.definirClientedaCompra(clienteEncontrado);

            Cliente clienteDaVez = controller.getClienteAtual();
            List<ItemPedido> itensComprados = controller.listarItensCarrinho();

            pedidoController.cadastrarPedido(clienteDaVez, itensComprados);

            double valorTotal = controller.finalizarCompra();
            System.out.printf("Sucesso! Compra finalizada do Cliente: %s. Valor Total: R$%.2f\n" , clienteDaVez.getNome(), valorTotal);

        } catch (CarrinhoVazioException | RegistroNaoEncontradoException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            LoggerService.log("ERROR", e.getMessage());
        }
    }


    public void setController(CarrinhoController controller) {
        this.controller = controller;
    }

    public void setPedidoController(PedidoController pedidoController) {
        this.pedidoController = pedidoController;
    }

    public void setClienteFisicoController(ClienteFisicoController clienteFisicoController) {
        this.clienteFisicoController = clienteFisicoController;
    }

    public void setClienteJuridicoController(ClienteJuridicoController clienteJuridicoController) {
        this.clienteJuridicoController = clienteJuridicoController;
    }
}
