package controller;

import model.Produto;
import view.ProdutoView;

import java.util.InputMismatchException;
import java.util.LinkedHashMap;

public class ProdutoController {
    private ProdutoView view;
    private LinkedHashMap<Integer, Produto> mapaProduto;
    private Produto ultimoProdutoCadastrado;


    public ProdutoController(ProdutoView view) {
        this.view = view;
        this.mapaProduto = new LinkedHashMap<>();
        this.ultimoProdutoCadastrado = null;
    }

    public void cadastrarProduto() {
        try {
            view.exibirMensagem("----- Cadastro de produto -----");
            String nomeProduto = view.lerNomeProduto();
            double precoBase = view.lerPrecoProduto();
            view.limparBuffer();
            int id = view.lerIDProduto();
            view.limparBuffer();

            if(mapaProduto.containsKey(id)){
                view.exibirMensagem("ERRO: ID já cadastrado! digite um id válido!");
                return;
            }

            Produto novoProduto = new Produto(nomeProduto, precoBase, id);
            ultimoProdutoCadastrado = novoProduto;
            mapaProduto.put(id, novoProduto);
            view.exibirMensagem("-----PRODUTO CADASTRADO COM SUCESSO-----");
        } catch (InputMismatchException e) {
            view.exibirMensagem("ERRO: Formato de preço ou ID inválido! Digite apenas números no preço e no ID.");
        }
        catch (IllegalArgumentException e) {
            view.exibirMensagem(e.getMessage());
        }
    }

    public void exibirUltimoProdutoCadastrado() {
        if (ultimoProdutoCadastrado == null) {
            view.exibirMensagem("Estoque vazio! Cadastre um produto.");
        } else {
            view.exibirProduto(ultimoProdutoCadastrado);
        }
    }

    public LinkedHashMap<Integer,Produto> listarProdutos(){
        return mapaProduto;
    }
}


