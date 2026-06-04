package view;

import controller.OrdemCompraController;
import exceptions.RegistroNaoEncontradoException;
import model.OrdemCompra;
import util.LoggerService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class OrdemCompraView {
    private Scanner sc = new Scanner(System.in);
    private OrdemCompraController controller;

    public String lerCnpjFornecedor() {
        System.out.print("Digite o CNPJ do Fornecedor (sem pontuação): ");
        return sc.nextLine();
    }

    public int lerIdProduto() {
        System.out.print("Digite o ID do Produto: ");
        return sc.nextInt();
    }

    public int lerQuantidade() {
        System.out.print("Digite a quantidade recebida: ");
        return sc.nextInt();
    }

    public double lerCustoUnitario() {
        System.out.print("Digite o custo unitário: ");
        return sc.nextDouble();
    }

    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public void fecharScanner() {
        sc.close();
    }

    public void limparBuffer() {
        sc.nextLine();
    }

    public void exibirMenuOrdemCompra() {
        int option = -1;

        do {
            try {
                System.out.println("\n===== MENU DE ORDENS DE COMPRA =====");
                System.out.println("1 - Registrar chegada de mercadoria");
                System.out.println("2 - Ver histórico de compras");
                System.out.println("0 - Sair do menu de ordens de compra");
                System.out.print("Digite a opção que deseja: ");
                option = sc.nextInt();
                limparBuffer();
                System.out.println("===================================\n");

                switch (option) {
                    case 1:
                        exibirRegistroOrdemCompra();
                        break;
                    case 2:
                        exibirListaOrdensCompra(controller.listarOrdensCompra());
                        break;
                    case 0:
                        System.out.println("Saindo do menu de ordens de compra...");
                        break;
                    default:
                        System.out.println("Opção inválida, tente novamente!");
                }
            } catch (InputMismatchException e) {
                System.out.println("ERRO: Digite somente números!");
                LoggerService.log("ERROR", "Usuário digitou um caractere inválido no menu.");
                limparBuffer();
            }
        } while (option != 0);
    }

    public void exibirRegistroOrdemCompra() {
        try {
            System.out.println("----- Registrar chegada de mercadoria -----");
            String cnpj = lerCnpjFornecedor();
            int idProduto = lerIdProduto();
            int quantidade = lerQuantidade();
            double custoUnitario = lerCustoUnitario();
            limparBuffer();

            controller.registrarOrdemCompra(cnpj, idProduto, quantidade, custoUnitario);
            System.out.println("\nSucesso! Mercadoria registrada e estoque atualizado.");
        } catch (RegistroNaoEncontradoException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            LoggerService.log("ERROR", e.getMessage());
        }
    }

    public void exibirListaOrdensCompra(List<OrdemCompra> lista) {
        if (lista.isEmpty()) {
            System.out.println("Nenhuma compra registrada até o momento.");
        } else {
            System.out.println("----- Histórico de Compras -----");
            for (OrdemCompra ordem : lista) {
                System.out.println(ordem);
            }
        }
    }

    public void setController(OrdemCompraController controller) {
        this.controller = controller;
    }
}