package controller;

import exceptions.RegistroNaoEncontradoException;
import model.Produto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import util.ArquivoUtil;
import util.LoggerService;

public class ProdutoController {
    private LinkedHashMap<Integer, Produto> mapaProduto;
    private Produto ultimoProdutoCadastrado = null;
    private final ArquivoUtil arquivoUtil = new ArquivoUtil();

    public ProdutoController(){
        Object dadosRecebidos = arquivoUtil.lerDados("produto.dat");

        if(dadosRecebidos != null) {
            this.mapaProduto = (LinkedHashMap<Integer, Produto>) dadosRecebidos;
        } else {
            this.mapaProduto = new LinkedHashMap<>();
        }
    }
    public void cadastrarProduto(String nomeProduto , double precoBase, int id) throws IllegalArgumentException{
        if(mapaProduto.containsKey(id)) {
            throw new IllegalArgumentException("ERRO: ID já cadastrado! digite um id válido!");
        }

        Produto novoProduto = new Produto(nomeProduto, precoBase, id);

        ultimoProdutoCadastrado = novoProduto;
        mapaProduto.put(id, novoProduto);
        arquivoUtil.salvarDados(this.mapaProduto, "produto.dat");
        LoggerService.log("INFO", "Produto cadastrado - ID:" + id);
    }

    public Produto exibirUltimoProdutoCadastrado() throws IllegalArgumentException {
        if (ultimoProdutoCadastrado == null) {
            throw new IllegalArgumentException("Estoque vazio! Cadastre um produto.");
        } else {
            LoggerService.log("INFO", "Ultimo produto cadastrado exibido");
            return ultimoProdutoCadastrado;
        }
    }
    public Produto buscarProdutoPorId(int id) throws RegistroNaoEncontradoException {
        Produto produtoEncontrado = mapaProduto.get(id);

        if(produtoEncontrado == null){
            throw new  RegistroNaoEncontradoException("ERRO: Nenhum produto em estoque encontrado pelo ID digitado! Tente novamente!");
        }
        LoggerService.log("INFO", "Busca por ID realizada com sucesso - ID: " + id);
        return produtoEncontrado;
    }
    public void removerProdutoPorID(int iDParaRemover) throws RegistroNaoEncontradoException {
        List<Integer> chavesParaRemover = new ArrayList<>();
        for (Produto produto : mapaProduto.values()) {
            if (produto.getId() == iDParaRemover) {
                chavesParaRemover.add(produto.getId());
            }
        }
        if (chavesParaRemover.isEmpty()) {
            throw new RegistroNaoEncontradoException("ERRO: Nenhum estoque encontrado para o ID " + iDParaRemover);
        }
        for (Integer chave : chavesParaRemover) {
            mapaProduto.remove(chave);
            arquivoUtil.salvarDados(this.mapaProduto, "produto.dat");
            LoggerService.log("INFO", "Produto removido com sucesso - ID: " + iDParaRemover);
        }
    }

    public HashMap<Integer,Produto> listarProdutos(){
        return mapaProduto;
    }
}


