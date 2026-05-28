package view;

import controller.EstoqueController;
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
    private ProdutoView view;

    public void mostrarMenuEstoque() {
        int opcao = -1;
        do {
            try {
                System.out.println("\n ====== MENU ESTOQUE ======");
                System.out.println("1 - CADASTRAR PRODUTO NO ESTOQUE: ");
                System.out.println("2 - EXIBIR PRODUTO NO ESTOQUE: ");
                System.out.println("3 - LOCALIZAR PRODUTOS POR LOTE: ");
                System.out.println("4 - MOSTRAR PRODUTOS COM DATA DE VÁLIDADE PRÓXIMA: ");
                System.out.println("5 - RELÁTORIO DE VALOR TOTAL EM ESTOQUE: ");
                System.out.println("6 - REMOVER LOTE VENCIDO: ");
                System.out.println("0 - SAIR ");
                opcao = sc.nextInt();
                limparBuffer();
                System.out.println("===========================\n");

                switch (opcao) {
                    case 1:
                        break;
                    case 2:
                        exibirProdutoNoEstoque();
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
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
        } while ( opcao != 0);
    }

    public void limparBuffer(){
        sc.nextLine();
    }

    public LocalDate lerDataDeValidade() {
        System.out.print("Digite a data de validade (formato DD/MM/AAAA): ");
        String dataTexto = sc.nextLine();

        // O formatador garante que o Java entenda o nosso formato brasileiro
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return LocalDate.parse(dataTexto, formatador);
    }
    public int lerQuantidadeAtual(){
        System.out.println("Digite a quantidade que deseja cadastrar: ");
        return sc.nextInt();
    }
    public String lerLote(){
        System.out.println("Digite o lote do produto: ");
        return sc.nextLine();
    }
    public int lerId(){
        System.out.println("Digite o ID do produto que deseja exibir: ");
        return sc.nextInt();
    }
    public void exibirProdutoNoEstoque(){
        lerId();
        view.exibirBuscaPorId();
    }
    public void exibirCadastroProdutoEmEstoque() {
        try {
            System.out.println("----- Cadastro de Produto -----");
            controller.cadastrarProdutoEstoque(view.lerIDProduto(), lerQuantidadeAtual(), lerLote(), lerDataDeValidade());
            System.out.println("\nSucesso: Produto cadastrado!");
        }
        catch (InputMismatchException e) {
            System.out.println("ERRO: Formato de preço ou ID inválido! Digite apenas números no preço e no ID.");
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (RegistroNaoEncontradoException e) {
            throw new RuntimeException(e);
        }
    }
}
