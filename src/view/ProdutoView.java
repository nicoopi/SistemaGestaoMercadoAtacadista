package view;

import model.Produto;
import java.util.Scanner;


public class ProdutoView {
     private Scanner sc = new Scanner(System.in);

        public void mostrarMenuProduto(){
            System.out.println("---MENU PRODUTO---");
            System.out.println("1 - CADASTRAR PRODUTO ");
            System.out.println("2 - EXIBIR PRODUTO");
            System.out.println("3 - SAIR");
        }

        public void limparBuffer(){
            sc.nextLine();
        }
        public int lerOpcaoProduto(){
            System.out.print("Qual é a opção?");
            return sc.nextInt();
        }
        public void exibirMensagem(String mensagem){
            System.out.println(mensagem);
        }
        public void fecharScanner() {
            sc.close();
        }

        public String lerNomeProduto(){
            System.out.print("Digite um nome válido para o produto: ");
            return sc.nextLine();
        }

        public double lerPrecoProduto() {
            System.out.print("Digite o preço base do produto: ");
            return sc.nextDouble();
        }

        public int lerIDProduto() {
            System.out.print("Digite um ID válido para o produto: ");
            return sc.nextInt();
        }

        public void exibirProduto(Produto p) {
            if (p == null) {
                System.out.println("PRODUTO VAZIO");
            } else {
                System.out.println("-----INFORMAÇÕES DO PRODUTO-----");
                System.out.println(p);

            }
        }

}
