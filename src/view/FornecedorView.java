package view;

import controller.FornecedorController;
import exceptions.RegistroNaoEncontradoException;
import model.Fornecedor;
import util.LoggerService;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class FornecedorView {
    private Scanner sc = new Scanner(System.in);
    private FornecedorController controller;

    public String lerRazaoSocial() {
        System.out.print("Digite a Razão Social: ");
        return sc.nextLine();
    }

    public String lerCnpj() {
        System.out.print("Digite o CNPJ (sem pontuação): ");
        return sc.nextLine();
    }

    public String lerTelefone() {
        System.out.print("Digite o Telefone (com DDD): ");
        return sc.nextLine();
    }

    public void limparBuffer() {
        sc.nextLine();
    }

    public void exibirMenuFornecedor() {
        int option = -1;

        do {
            try {
                System.out.println("\n===== MENU DE FORNECEDORES =====");
                System.out.println("1 - Cadastrar fornecedor");
                System.out.println("2 - Listar fornecedores");
                System.out.println("3 - Buscar fornecedor por CNPJ");
                System.out.println("4 - Remover fornecedor");
                System.out.println("5 - Modificar fornecedor");
                System.out.println("0 - Sair do menu de fornecedores");
                System.out.print("Digite a opção que deseja: ");
                option = sc.nextInt();
                limparBuffer();
                System.out.println("===================================\n");

                switch (option) {
                    case 1:
                        exibirCadastroFornecedor();
                        break;
                    case 2:
                        exibirListaFornecedores(controller.listarFornecedores());
                        break;
                    case 3:
                        exibirFornedorPorCnpj();
                        break;
                    case 4:
                        exibirFornecedorRemovido();
                        break;
                    case 5:
                        exibirModificacaoFornecedor();
                        break;
                    case 0:
                        System.out.println("Saindo do menu de fornecedores...");
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

    public void exibirCadastroFornecedor() {
        try {
            System.out.println("----- Cadastro de Fornecedor -----");
            controller.cadastrarFornecedor(lerRazaoSocial(), lerCnpj(), lerTelefone());
            System.out.println("\nSucesso: Fornecedor cadastrado!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            LoggerService.log("ERROR", e.getMessage());
        }
    }

    public void exibirFornedorPorCnpj() {
        try {
            System.out.println("----- Busca de Fornecedor -----");
            Fornecedor fornecedor = controller.buscarFornecedorPorCnpj(lerCnpj());
            System.out.println("\nSucesso: Fornecedor encontrado!");
            System.out.println(fornecedor);
        } catch (RegistroNaoEncontradoException e) {
            System.out.println(e.getMessage());
            LoggerService.log("ERROR", e.getMessage());
        }
    }

    public void exibirListaFornecedores(Map<String, Fornecedor> map) {
        if (map.isEmpty()) {
            System.out.println("Nenhum fornecedor cadastrado.");
        } else {
            System.out.println("----- Fornecedores -----");
            for (Fornecedor fornecedor : map.values()) {
                System.out.println(fornecedor);
            }
        }
    }

    public void exibirFornecedorRemovido() {
        try {
            System.out.println("----- Remoção de Fornecedor -----");
            controller.removerFornecedorPorCnpj(lerCnpj());
            System.out.println("Sucesso: Fornecedor removido!");
        } catch (RegistroNaoEncontradoException e) {
            System.out.println(e.getMessage());
            LoggerService.log("ERROR", e.getMessage());
        }
    }

    public void exibirModificacaoFornecedor() {
        try {
            System.out.println("----- Modificação de Fornecedor -----");
            System.out.print("Digite o CNPJ atual do fornecedor que deseja modificar: ");
            String cnpjAtual = sc.nextLine();

            System.out.println("--- Digite os Novos Dados ---");
            String novaRazaoSocial = lerRazaoSocial();
            String novoCnpj = lerCnpj();
            String novoTelefone = lerTelefone();

            controller.modificarFornecedor(cnpjAtual, novaRazaoSocial, novoCnpj, novoTelefone);
            System.out.println("\nSucesso: Fornecedor modificado!");
        } catch (RegistroNaoEncontradoException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            LoggerService.log("ERROR", e.getMessage());
        }
    }

    public void setController(FornecedorController controller) {
        this.controller = controller;
    }
}