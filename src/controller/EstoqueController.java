package controller;

import exceptions.EstoqueInsuficienteException;
import exceptions.RegistroNaoEncontradoException;
import model.Estoque;
import model.Produto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class EstoqueController {
    private LinkedHashMap<Integer, Estoque> mapaEstoque = new LinkedHashMap<>();
    private ProdutoController produtoController;

    public void cadastrarProdutoEstoque(int id, int quantidadeAtual, String lote, LocalDate dataDeValidade) throws RegistroNaoEncontradoException {
        Produto produtoEncontradoEmEstoque = produtoController.buscarProdutoPorId(id);

        Estoque novoEstoque = new Estoque(produtoEncontradoEmEstoque, quantidadeAtual, lote, dataDeValidade);

        mapaEstoque.put(id, novoEstoque);
    }

    public Estoque buscarEstoquePorId(int id) throws RegistroNaoEncontradoException{
        Estoque estoque = mapaEstoque.get(id);
        if (estoque == null) {
            throw new RegistroNaoEncontradoException("ERRO: ID não encontrado.");
        }
        return estoque;
    }
    public void removerProdutoPorLote(int iDParaRemover) throws RegistroNaoEncontradoException {
        Estoque produtoParaRemover = mapaEstoque.get(iDParaRemover);
        if (produtoParaRemover == null) {
            throw new RegistroNaoEncontradoException("ERRO: ID não encontrado.");
        }
        mapaEstoque.remove(iDParaRemover);
    }
    public Estoque localizarProdutoPorLote(String lote) throws RegistroNaoEncontradoException {
        if(mapaEstoque.containsKey(lote)){
            return mapaEstoque.get(lote);
        }
        throw new RegistroNaoEncontradoException("ERRO: LOTE NÃO ENCONTRADO! DIGITE UM LOTE VÁLIDO");
    }
    public double valorTotalEmEstoque() throws EstoqueInsuficienteException {
        if(mapaEstoque.isEmpty()) {
            throw new EstoqueInsuficienteException("ERRO: O estoque está vazio. Adicione produtos antes de exibir!");
        }

        double valorTotal = 0;
        for(Estoque estoque : mapaEstoque.values()){
            int quantidade = estoque.getQuantidadeAtual();
            double preco = estoque.getProduto().getPrecoBase();

            valorTotal += (quantidade*preco);
        }
        return  valorTotal;
    }
}


