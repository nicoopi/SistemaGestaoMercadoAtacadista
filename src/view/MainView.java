package view;

import controller.*;
import model.Cliente;
import util.LoggerService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainView {
    private ClienteFisicoController clienteFisicoController;
    private ClienteFisicoView clienteFisicoView;
    private ClienteJuridicoController clienteJuridicoController;
    private ClienteJuridicoView clienteJuridicoView;
    private FornecedorController fornecedorController;
    private FornecedorView fornecedorView;
    private OrdemCompraController ordemCompraController;
    private OrdemCompraView ordemCompraView;
    private CarrinhoController carrinhoController;
    private CarrinhoView carrinhoView;
    private EstoqueController estoqueController;
    private EstoqueView estoqueView;
    private ProdutoController produtoController;
    private ProdutoView produtoView;
    private PedidoController pedidoController;
    private PedidoView pedidoView;
    private Scanner sc = new Scanner(System.in);


    public MainView() {
        produtoController = new ProdutoController();
        estoqueController = new EstoqueController(produtoController);
        fornecedorController = new FornecedorController();
        clienteFisicoController = new ClienteFisicoController();
        clienteJuridicoController = new ClienteJuridicoController();
        pedidoController = new PedidoController();
        carrinhoController = new CarrinhoController(estoqueController);
        ordemCompraController = new OrdemCompraController(estoqueController, fornecedorController, produtoController);

        clienteFisicoView = new ClienteFisicoView();
        clienteJuridicoView = new ClienteJuridicoView();
        fornecedorView = new FornecedorView();
        ordemCompraView = new OrdemCompraView();
        carrinhoView = new CarrinhoView();
        produtoView = new ProdutoView();
        pedidoView = new PedidoView();
        estoqueView = new EstoqueView();

        clienteFisicoView.setController(clienteFisicoController);
        clienteJuridicoView.setController(clienteJuridicoController);
        fornecedorView.setController(fornecedorController);
        ordemCompraView.setController(ordemCompraController);
        carrinhoView.setController(carrinhoController);
        carrinhoView.setClienteFisicoController(clienteFisicoController);
        carrinhoView.setClienteJuridicoController(clienteJuridicoController);
        carrinhoView.setPedidoController(pedidoController);
        produtoView.setController(produtoController);
        produtoView.setEstoqueController(estoqueController);

        pedidoView.setController(pedidoController);
        estoqueView.setController(estoqueController);
        estoqueView.setProdutoController(produtoController);
    }

    public void exibirMenuPrincipal() {
        int opcao = -1;
        do {
            try {
                System.out.println("\n===== SISTEMA GESTÃO DE ATACADISTA =====");
                System.out.println("----- Setor de Cadastros -----");
                System.out.println("1 - Gerenciar Clientes Físicos");
                System.out.println("2 - Gerenciar Clientes Jurídicos");
                System.out.println("3 - Gerenciar Fornecedores");
                System.out.println("4 - Gerenciar Produtos");
                System.out.println("----- Setor de Operações -----");
                System.out.println("5 - Gerenciar Estoque");
                System.out.println("6 - Gerenciar Ordens de Compra");
                System.out.println("----- Setor de Vendas -----");
                System.out.println("7 - Acessar Caixa (Carrinho)");
                System.out.println("8 - Acessar Sala do Gerente (Pedidos)");
                System.out.println("0 - Sair do Sistema");
                System.out.print("Digite a opção desejada: ");

                opcao = sc.nextInt();
                sc.nextLine();
                System.out.println("===================================\n");

                switch (opcao) {
                    case 1:
                        clienteFisicoView.exibirMenu();
                        break;
                    case 2:
                        clienteJuridicoView.exibirMenu();
                        break;
                    case 3:
                        fornecedorView.exibirMenuFornecedor();
                        break;
                    case 4:
                        produtoView.mostrarMenuProduto();
                        break;
                    case 5:
                        estoqueView.mostrarMenuEstoque();
                        break;
                    case 6:
                        ordemCompraView.exibirMenuOrdemCompra();
                        break;
                    case 7:
                        carrinhoView.exibirMenu();
                        break;
                    case 8:
                        pedidoView.exibirMenu();
                        break;
                    case 0:
                        System.out.println("Encerrando o sistema... Até logo!");
                        break;
                    default:
                        System.out.println("Opção inválida! Escolha um número do menu.");
                }
            } catch (InputMismatchException e) {
                System.out.println("ERRO: Digite somente números!");
                LoggerService.log("ERROR", "Usuário digitou um caractere inválido no menu.");
                sc.nextLine();
            }
        } while (opcao != 0);

        sc.close();
    }
}
