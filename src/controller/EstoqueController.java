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

    public EstoqueController() {
    }

    public void cadastrarProdutoEstoque(int id, int quantidadeAtual, String lote, LocalDate dataDeValidade) throws RegistroNaoEncontradoException {
        Produto produtoEncontradoEmEstoque = produtoController.buscarProdutoPorId(id);
        if (produtoEncontradoEmEstoque == null) {
            throw new RegistroNaoEncontradoException("ERRO: Produto não encontrado");
        }
        Estoque novoEstoque = new Estoque(produtoEncontradoEmEstoque, quantidadeAtual, lote, dataDeValidade);

        mapaEstoque.put(id, novoEstoque);
    }

    public Estoque buscarEstoquePorId(int id) throws RegistroNaoEncontradoException {
        for (Estoque estoque : mapaEstoque.values()) {
            if (estoque.getProduto().getId() == id) {
                return estoque;
            }
        }
        throw new RegistroNaoEncontradoException("ERRO: Nenhum estoque encontrado para o ID " + id);
    }

    public void removerProdutoPorLote(int iDParaRemover) throws RegistroNaoEncontradoException {
        List<String> chavesParaRemover = new ArrayList<>();
        for (Estoque estoque : mapaEstoque.values()) {
            if (estoque.getProduto().getId() == iDParaRemover) {
                chavesParaRemover.add(estoque.getLote());
            }
        }
        if (chavesParaRemover.isEmpty()) {
            throw new RegistroNaoEncontradoException("ERRO: Nenhum estoque encontrado para o ID " + iDParaRemover);
        }
        for (String chave : chavesParaRemover) {
            mapaEstoque.remove(chave);
        }
    }

    public Estoque localizarProdutoPorLote(String lote) throws RegistroNaoEncontradoException {
        if (mapaEstoque.containsKey(lote)) {
            return mapaEstoque.get(lote);
        }
        throw new RegistroNaoEncontradoException("ERRO: LOTE NÃO ENCONTRADO! DIGITE UM LOTE VÁLIDO");
    }

    public double valorTotalEmEstoque() throws EstoqueInsuficienteException {
        if (mapaEstoque.isEmpty()) {
            throw new EstoqueInsuficienteException("ERRO: O estoque está vazio. Adicione produtos antes de exibir!");
        }

        double valorTotal = 0;
        for (Estoque estoque : mapaEstoque.values()) {
            int quantidade = estoque.getQuantidadeAtual();
            double preco = estoque.getProduto().getPrecoBase();

            valorTotal += (quantidade * preco);
        }
        return valorTotal;
    }

    public int adicionarQuantidadeEmEstoque(int id, int quantidadeAdicional) throws RegistroNaoEncontradoException {
        Estoque estoqueEncontrado = mapaEstoque.get(id);

        if (estoqueEncontrado == null) {
            throw new RegistroNaoEncontradoException("ERRO: Produto não encontrado em estoque! VOLTE PARA CADASTRAR O PRODUTO");
        }
        int novaQuantidade = estoqueEncontrado.getQuantidadeAtual() + quantidadeAdicional;
        estoqueEncontrado.setQuantidadeAtual(novaQuantidade);
        return novaQuantidade;
    }

    public int removerQuantidadeDeEstoque(int id, int quantidadeRemovida) throws RegistroNaoEncontradoException {
        Estoque estoqueEncontrado = mapaEstoque.get(id);

        if (estoqueEncontrado == null) {
            throw new RegistroNaoEncontradoException("ERRO: Produto não encontrado em estoque");
        }
        if (quantidadeRemovida > estoqueEncontrado.getQuantidadeAtual()) {
            throw new IllegalArgumentException("ERRO: Estoque insuficiente para o produto ID " + id);
        }
                int novaQuantidade = estoqueEncontrado.getQuantidadeAtual() - quantidadeRemovida;
                estoqueEncontrado.setQuantidadeAtual(novaQuantidade);
                return novaQuantidade;
            }

    public void setProdutoController(ProdutoController produtoController) {
        this.produtoController = produtoController;
    }
}



