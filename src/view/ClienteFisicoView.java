package view;

import controller.ClienteFisicoController;
import exceptions.RegistroNaoEncontradoException;
import model.ClienteFisico;

import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class ClienteFisicoView {
    private Scanner sc = new Scanner(System.in);
    private ClienteFisicoController controller;

    public String lerNome() {
        System.out.print("Digite o nome: ");
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
    public String lerCpf() {
        System.out.print("Digite o CPF: ");
        return sc.nextLine();
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

    public void exibirMenu() {
        int option = -1;

        do {
            try {
                System.out.println("\n===== MENU DE CLIENTE FÍSICO =====");
                System.out.println("1 - Cadastrar cliente físico");
                System.out.println("2 - Listar clientes físicos");
                System.out.println("3 - Buscar cliente por cpf");
                System.out.println("4 - Remover cliente físico");
                System.out.println("0 - Sair do menu de clientes físicos");
                System.out.print("Digite a opção que deseja: ");
                option = sc.nextInt();
                limparBuffer();
                System.out.println("===================================\n");

                switch (option) {
                    case 1:
                        exibirCadastroClienteFisico();
                        break;
                    case 2:
                        exibirListaClientesFisicos(controller.listarClientesFisicos());
                        break;
                    case 3:
                        exibirBuscaPorCpf();
                        break;
                    case 4:
                        exibirRemocaoPorCpf();
                        break;
                    case 0:
                        System.out.println("Saindo do menu de clientes físicos...");
                        break;
                    default:
                        System.out.println("Opção inválida, tente novamente!");
                }
            } catch (InputMismatchException e) {
                System.out.println("ERRO: Digite somente números!");
                limparBuffer();
            }
        } while (option != 0);
    }

    public void exibirCadastroClienteFisico() {
        try {
            System.out.println("----- Cadastro de Cliente Físico -----");
            controller.cadastrarClienteFisico(lerNome(), lerTelefone(), lerEmail(), lerDataCadastro(), lerCpf());
            System.out.println("\nSucesso: Cliente físico cadastrado!");
        } catch (DateTimeParseException e){
            System.out.println("ERRO: Formato de data inválido. Certifique-se de usar barras (DD/MM/AAAA).");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void exibirBuscaPorCpf() {
        try {
            System.out.println("----- Busca de Cliente -----");
            ClienteFisico cliente = controller.buscarPorCpf(lerCpf());
            System.out.println("\nSucesso: Cliente encontrado!");
            System.out.println(cliente);
        } catch (RegistroNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void exibirListaClientesFisicos(Map<String, ClienteFisico> mapa) {
        if (mapa.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado no momento.");
        } else {
            System.out.println("----- Lista de Clientes Físicos -----");
            for (ClienteFisico cliente : mapa.values()) {
                System.out.println(cliente);
            }
        }
    }

    public void exibirRemocaoPorCpf() {
        try {
            System.out.println("----- Remoção de cliente -----");
            controller.removerPorCpf(lerCpf());
            System.out.println("Sucesso: Cliente removido!");
        } catch (RegistroNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setController(ClienteFisicoController controller) {
        this.controller = controller;
    }
}
