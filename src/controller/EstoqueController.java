package controller;

import exceptions.RegistroNaoEncontradoException;
import model.Estoque;
import controller.ProdutoController;
import model.Produto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class EstoqueController {
    private LinkedHashMap<Integer, Estoque> mapaEstoque = new LinkedHashMap<>();
    private ProdutoController produtoController;

    public void cadastrarProdutoEstoque(int id, int quantidadeAtual, String lote, LocalDate dataDeValidade) throws RegistroNaoEncontradoException {
        Produto produtoEncontradoEmEstoque = produtoController.buscarProdutoPorId(id);

        Estoque novoEstoque = new Estoque(produtoEncontradoEmEstoque, quantidadeAtual, lote, dataDeValidade);

        mapaEstoque.put(id, novoEstoque);
    }

    public void setProdutoController(ProdutoController produtoController) {
        this.produtoController = produtoController;
    }
}
