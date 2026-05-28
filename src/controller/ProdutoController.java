package controller;

import exceptions.RegistroNaoEncontradoException;
import model.Produto;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ProdutoController {
    private LinkedHashMap<Integer, Produto> mapaProduto = new LinkedHashMap<>();
    private Produto ultimoProdutoCadastrado = null;

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
    public Produto buscarProdutoPorId(int id) throws RegistroNaoEncontradoException {
        Produto produtoEncontrado = mapaProduto.get(id);

        if(produtoEncontrado == null){
            throw new  RegistroNaoEncontradoException("ERRO: Nenhum produto em estoque encontrado pelo ID digitado! Tente novamente!");
        }

        return produtoEncontrado;
    }

    public HashMap<Integer,Produto> listarProdutos(){
        return mapaProduto;
    }
}


