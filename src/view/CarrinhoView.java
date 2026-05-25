package view;

import model.ItemPedido;
import java.util.List;
import java.util.Scanner;

public class CarrinhoView {
    private Scanner sc = new Scanner(System.in);

    public void mostrarMenuCarrinho() {
        System.out.println("=== MENU CARRINHO ===");
        System.out.println("1 - Adicionar produto ao carrinho.");
        System.out.println("2 - Exibir itens do carrinho.");
        System.out.println("3 - Sair (ou Voltar).");
    }


    public int lerOpcaoCarrinho() {
        System.out.println("Digite a opção desejada: ");
        return sc.nextInt();
    }

    public int lerIDProduto() {
        System.out.print("Digite o ID do Produto que deseja: ");
        return sc.nextInt();
    }

    public int lerQuantidadeDesejada() {
        System.out.print("Digite a Quantidade Desejada: ");
        return sc.nextInt();
    }

    public void exibirCarrinho(List<ItemPedido> listaItens) {
        if( listaItens == null || listaItens.isEmpty()) {
            System.out.println("--- O CARRINHO ESTÁ VAZIO ---");
        } else {
            for(ItemPedido item : listaItens) {
                System.out.println(item);
            }
        }
    }

    public void exibirMensagem(String msg) {
        System.out.println(msg);
    }

    public void limparBuffer() {
        sc.nextLine();
    }

    public void fecharScanner() {
        sc.close();
    }
}
