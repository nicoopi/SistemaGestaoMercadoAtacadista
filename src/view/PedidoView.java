package view;

import controller.PedidoController;
import exceptions.RegistroNaoEncontradoException;
import model.Pedido;
import util.LoggerService;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class PedidoView {
    private Scanner sc = new Scanner(System.in);
    private PedidoController controller;

    public int lerIDPedido() {
        System.out.print("Digite o ID do pedido que deseja buscar:");
        return sc.nextInt();
    }

    public void limparBuffer() {
        sc.nextLine();
    }

    public void exibirMenu() {
        int opcao = -1;

        do {
            try {
                System.out.println("\n===== MENU DO PEDIDO =====");
                System.out.println("1 - Listar todos os pedidos gerados");
                System.out.println("2 - Buscar um pedido específico por ID");
                System.out.println("0 - Voltar ao Menu Principal");
                System.out.print("Digite a opção desejada: ");
                opcao = sc.nextInt();
                limparBuffer();
                System.out.println("===================================\n");

                switch (opcao) {
                    case 1:
                        exibirlistadeTodosOsPedidos();
                        break;
                    case 2:
                        exibirBuscaPedidoPorId();
                        break;
                    case 0:
                        break;

                }
            }  catch (InputMismatchException e) {
                System.out.println("ERRO: Digite somente números!");
                LoggerService.log("ERROR", "Usuário digitou um caractere inválido no menu.");
                limparBuffer();
            }
        } while (opcao != 0);
    }

    public void exibirlistadeTodosOsPedidos() {
        Map<Integer, Pedido> mapaPedidos = controller.listarPedidos();
        if(mapaPedidos.isEmpty()) {
            System.out.println("----- NENHUM PEDIDO REGISTRADO -----");
            return;
        }

        System.out.println("----- HISTÓRICO DE PEDIDOS -----");
        for(Pedido pedidoAtual : mapaPedidos.values()) {
            System.out.println("Pedido #" + pedidoAtual.getIdPedido() + " | Cliente: " + pedidoAtual.getCliente().getNome());
        }
    }

    public void exibirBuscaPedidoPorId() {
        try {
            int idBuscado = lerIDPedido();
            limparBuffer();

            Pedido pedidoEncontrado = controller.buscarPedidoEspecifico(idBuscado);

            System.out.println("----- DETALHES DO PEDIDO -----");
            System.out.println(pedidoEncontrado);

        } catch (RegistroNaoEncontradoException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("ERRO: Por favor, digite apenas números para buscar um ID.");
            limparBuffer();
        }
    }

    public void setController(PedidoController controller) {
        this.controller = controller;
    }
}