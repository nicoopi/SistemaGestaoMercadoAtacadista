package view;

import controller.EstoqueController;
import exceptions.EstoqueInsuficienteException;
import model.Estoque;
import exceptions.RegistroNaoEncontradoException;
import controller.ProdutoController;
import view.ProdutoView;
import model.Produto;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import java.util.InputMismatchException;
import java.util.HashMap;
import java.util.Scanner;

public class EstoqueView {
    private Scanner sc = new Scanner(System.in);
    private EstoqueController controller;
    private ProdutoView produtoview;
    private ProdutoController produtoController;

    public EstoqueView(EstoqueController controller) {
        this.controller = controller;
        this.produtoview = new ProdutoView();
    }

    public void mostrarMenuEstoque() {
        int opcao = -1;
        do {
            try {
                System.out.println("\n ====== MENU ESTOQUE ======");
                System.out.println("1 - CADASTRAR PRODUTO NO ESTOQUE: ");
                System.out.println("2 - EXIBIR PRODUTO NO ESTOQUE: ");
                System.out.println("3 - LOCALIZAR PRODUTOS POR LOTE: ");
                System.out.println("4 - RELÁTORIO DE VALOR TOTAL EM ESTOQUE: ");
                System.out.println("5 - REMOVER LOTE VENCIDO: ");
                System.out.println("0 - SAIR ");
                opcao = sc.nextInt();
                limparBuffer();
                System.out.println("===========================\n");

                switch (opcao) {
                    case 1:
                        exibirCadastroProdutoEmEstoque();
                        break;
                    case 2:
                        exibirProdutoNoEstoque();
                        break;
                    case 3:
                        exibirProdutosEncontradosNoLote();
                        break;
                    case 4:
                        exibirValorTotalEmEstoque();
                        break;
                    case 5:
                        exibirProdutosRemovidos();
                        break;
                    case 0:
                        System.out.println("Saindo do menu de ESTOQUE...");
                        break;
                    default:
                        System.out.println("Opção inválida, tente novamente!");
                }
            } catch (InputMismatchException e) {
                System.out.println("ERRO: Digite somente números!");
            }
        } while (opcao != 0);
    }

    public void limparBuffer() {
        sc.nextLine();
    }

    public LocalDate lerDataDeValidade() {
        System.out.print("Digite a data de validade (formato DD/MM/AAAA): ");
        String dataTexto = sc.nextLine();

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return LocalDate.parse(dataTexto, formatador);
    }

    public int lerQuantidadeAtual() {
        System.out.println("Digite a quantidade que deseja cadastrar: ");
        return sc.nextInt();
    }

    public String lerLote() {
        System.out.println("Digite o lote do produto: ");
        return sc.nextLine();
    }

    public int lerId() {
        System.out.println("Digite o ID do produto que deseja exibir: ");
        return sc.nextInt();
    }

    public void exibirProdutoNoEstoque() {
        try {
            int id = lerId();
            limparBuffer();

            Estoque estoque = controller.buscarEstoquePorId(id);
            System.out.println(estoque);
        } catch (RegistroNaoEncontradoException e) {
            throw new RuntimeException(e);
        }
    }

    public void exibirCadastroProdutoEmEstoque() {
        try {
            System.out.println("----- Cadastro de Produto -----");
            controller.cadastrarProdutoEstoque(produtoview.lerIDProduto(), lerQuantidadeAtual(), lerLote(), lerDataDeValidade());
            System.out.println("\nSucesso: Produto cadastrado!");
        } catch (InputMismatchException e) {
            System.out.println("ERRO: Formato de preço ou ID inválido! Digite apenas números no preço e no ID.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (RegistroNaoEncontradoException e) {
            throw new RuntimeException(e);
        }
    }

    public void exibirProdutosRemovidos() {
        try {
            System.out.println("----- Remoção de Produto -----");
            int id = lerId();
            limparBuffer();
            controller.removerProdutoPorLote(id);
            System.out.println("\nSucesso! Produto removido do carrinho!");
        } catch (RegistroNaoEncontradoException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("ERRO: Formato inválido! Por favor, digite apenas números.");
            sc.nextLine();
        }
    }
    public void exibirProdutosEncontradosNoLote(){
        try {
            System.out.println("------ Produtos encontrados ------");
            String lote = lerLote();
            limparBuffer();

            controller.localizarProdutoPorLote(lote);
        } catch (RegistroNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }
    public void exibirValorTotalEmEstoque() {
        try {
            System.out.println("----- Valor total em estoque -----");
            controller.valorTotalEmEstoque();
        } catch (EstoqueInsuficienteException e) {
            throw new RuntimeException(e);
        }
    }
}
