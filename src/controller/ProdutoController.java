package controller;

import model.Produto;
import view.ProdutoView;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ProdutoController {
    private LinkedHashMap<Integer, Produto> mapaProduto;
    private Produto ultimoProdutoCadastrado;


    public ProdutoController(ProdutoView view) {
        this.mapaProduto = new LinkedHashMap<>();
        this.ultimoProdutoCadastrado = null;
    }

    public void cadastrarProduto(String nomeProduto , double precoBase, int id) throws IllegalArgumentException{
            if(mapaProduto.containsKey(id)) {
                throw new IllegalArgumentException("ERRO: ID já cadastrado! digite um id válido!");
            }

            Produto novoProduto = new Produto(nomeProduto, precoBase, id);

            ultimoProdutoCadastrado = novoProduto;
            mapaProduto.put(id, novoProduto);
    }

    public Produto exibirUltimoProdutoCadastrado() throws IllegalArgumentException {
        if (ultimoProdutoCadastrado == null) {
            throw new IllegalArgumentException("Estoque vazio! Cadastre um produto.");
        } else {
            return ultimoProdutoCadastrado;
        }
    }

    public HashMap<Integer,Produto> listarProdutos(){
        return mapaProduto;
    }
}


