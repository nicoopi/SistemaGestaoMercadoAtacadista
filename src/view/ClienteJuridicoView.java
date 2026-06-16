package view;

import controller.ClienteJuridicoController;
import exceptions.RegistroNaoEncontradoException;
import model.ClienteJuridico;
import util.LoggerService;

import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class ClienteJuridicoView {
    private Scanner sc = new Scanner(System.in);
    private ClienteJuridicoController controller;

    public String lerRazaoSocial() {
        System.out.print("Digite a razão social (nome): ");
        return sc.nextLine();
    }

    public String lerTelefone() {
        System.out.print("Digite o telefone: ");
        return sc.nextLine();
    }

    public String lerEmail() {
        System.out.print("Digite o email: ");
        return sc.nextLine();
    }

    public String lerDataCadastro() {
        System.out.print("Digite a data de cadastro (formato DD/MM/AAAA): ");
        return sc.nextLine();
    }

    public String lerCnpj() {
        System.out.print("Digite o CNPJ (sem pontuação): ");
        return sc.nextLine();
    }

    public String lerNovoNome() {
        System.out.print("Digite o novo nome: ");
        return sc.nextLine();
    }

    public String lerNovoTelefone() {
        System.out.print("Digite o novo telefone: ");
        return sc.nextLine();
    }

    public String lerNovoEmail() {
        System.out.print("Digite o novo email: ");
        return sc.nextLine();
    }

    public String lerNovaDataCadastro() {
        System.out.print("Digite a data de cadastro da atualização (formato DD/MM/AAAA): ");
        return sc.nextLine();
    }

    public String lerCnpjCadastroAlteracao() {
        System.out.print("Digite o CNPJ do cadastro que deseja alterar (sem pontuação): ");
        return sc.nextLine();
    }

    public void limparBuffer() {
        sc.nextLine();
    }

    public void exibirMenu() {
        int option = -1;

        do {
            try {
                System.out.println("\n===== MENU DE CLIENTE JURÍDICO =====");
                System.out.println("1 - Cadastrar cliente jurídico");
                System.out.println("2 - Listar clientes jurídicos");
                System.out.println("3 - Buscar cliente por cnpj");
                System.out.println("4 - Remover cliente jurídico");
                System.out.println("5 - Alterar cliente jurídico");
                System.out.println("0 - Sair do menu de clientes jurídicos");
                System.out.print("Digite a opção que deseja: ");
                option = sc.nextInt();
                limparBuffer();
                System.out.println("===================================\n");

                switch (option) {
                    case 1:
                        exibirCadastroClienteJuridico();
                        break;
                    case 2:
                        exibirListaClientesJuridicos(controller.listarClientesJuridicos());
                        break;
                    case 3:
                        exibirBuscaPorCnpj();
                        break;
                    case 4:
                        exibirRemocaoPorCnpj();
                        break;
                    case 5:
                        exibirAlteracaoPorCnpj();
                        break;
                    case 0:
                        System.out.println("Saindo do menu de clientes jurídicos...");
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

    public void exibirCadastroClienteJuridico() {
        try {
            System.out.println("----- Cadastro de Cliente Jurídico -----");
            controller.cadastrarClienteJuridico(lerRazaoSocial(), lerTelefone(), lerEmail(), lerDataCadastro(), lerCnpj());
            System.out.println("\nSucesso: Cliente jurídico cadastrado!");
        } catch (DateTimeParseException e){
            System.out.println("ERRO: Formato de data inválido. Certifique-se de usar barras (DD/MM/AAAA).");
            LoggerService.log("ERROR", e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            LoggerService.log("ERROR", e.getMessage());
        }
    }

    public void exibirBuscaPorCnpj() {
        try {
            System.out.println("----- Busca de Cliente -----");
            ClienteJuridico cliente = controller.buscarPorCnpj(lerCnpj());
            System.out.println("\nSucesso: Cliente encontrado!");
            System.out.println(cliente);
        } catch (RegistroNaoEncontradoException e) {
            System.out.println(e.getMessage());
            LoggerService.log("ERROR", e.getMessage());
        }
    }

    public void exibirListaClientesJuridicos(Map<String, ClienteJuridico> mapa) {
        if (mapa.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado no momento.");
        } else {
            System.out.println("----- Lista de Clientes Jurídicos -----");
            for (ClienteJuridico cliente : mapa.values()) {
                System.out.println(cliente);
            }
        }
    }

    public void exibirRemocaoPorCnpj() {
        try {
            System.out.println("----- Remoção de cliente -----");
            controller.removerPorCnpj(lerCnpj());
            System.out.println("Sucesso: Cliente removido!");
        } catch (RegistroNaoEncontradoException e) {
            System.out.println(e.getMessage());
            LoggerService.log("ERROR", e.getMessage());
        }
    }

    public void exibirAlteracaoPorCnpj() {
        try {
            System.out.println("----- Alteração de cliente -----");
            controller.alterarClientePorCnpj(lerCnpjCadastroAlteracao(), lerNovoNome(), lerNovoTelefone(), lerNovoEmail(), lerNovaDataCadastro());
            System.out.println("Sucesso: Cliente alterado!");
        } catch (DateTimeParseException e){
            System.out.println("ERRO: Formato de data inválido. Certifique-se de usar barras (DD/MM/AAAA).");
            LoggerService.log("ERROR", e.getMessage());
        } catch (RegistroNaoEncontradoException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            LoggerService.log("ERROR", e.getMessage());
        }
    }

    public void setController(ClienteJuridicoController controller) {
        this.controller = controller;
    }
}