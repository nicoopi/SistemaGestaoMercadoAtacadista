package controller;

import exceptions.EstoqueInsuficienteException;
import exceptions.RegistroNaoEncontradoException;
import model.Estoque;
import model.Produto;
import java.time.LocalDate;
import java.util.*;

import util.ArquivoUtil;
import util.LoggerService;

public class EstoqueController {
    private LinkedHashMap<Integer, Estoque> mapaEstoque;
    private ProdutoController produtoController;
    private final ArquivoUtil arquivoUtil = new ArquivoUtil();

    public EstoqueController(ProdutoController produtoController) {
        Object dadosRecebidos = arquivoUtil.lerDados("estoque.dat");

        this.produtoController = produtoController;

        if(dadosRecebidos != null) {
            this.mapaEstoque = (LinkedHashMap<Integer, Estoque>) dadosRecebidos;
        } else {
            this.mapaEstoque = new LinkedHashMap<>();
        }
    }
    public void cadastrarProdutoEstoque(int id, int quantidadeAtual, String lote, LocalDate dataDeValidade) throws RegistroNaoEncontradoException {
        Produto produtoEncontradoEmEstoque = produtoController.buscarProdutoPorId(id);
        if (produtoEncontradoEmEstoque == null) {
            throw new RegistroNaoEncontradoException("ERRO: Produto não encontrado");
        }
        Estoque novoEstoque = new Estoque(produtoEncontradoEmEstoque, quantidadeAtual, lote, dataDeValidade);
        mapaEstoque.put(id, novoEstoque);

        arquivoUtil.salvarDados(this.mapaEstoque, "estoque.dat");
        LoggerService.log("INFO", "Produto cadastrado - ID: " + produtoEncontradoEmEstoque.getId());
    }

    public Estoque buscarEstoquePorId(int id) throws RegistroNaoEncontradoException {
        for (Estoque estoque : mapaEstoque.values()) {
            if (estoque.getProduto().getId() == id) {
                LoggerService.log("INFO", "Estoque encontrado - ID: " + estoque.getProduto().getId());
                return estoque;
            }
        }
        throw new RegistroNaoEncontradoException("ERRO: Nenhum estoque encontrado para o ID " + id);
    }

    public void removerProdutoPorLote(int iDParaRemover) throws RegistroNaoEncontradoException {
        Estoque estoqueRemovido = mapaEstoque.remove(iDParaRemover);

        if(estoqueRemovido == null) {
            throw new RegistroNaoEncontradoException("ERRO: Nenhum estoque encontrado para o ID " + iDParaRemover);
        }

        arquivoUtil.salvarDados(this.mapaEstoque, "estoque.dat");
        LoggerService.log("INFO", "Produto removido do estoque - ID: " + iDParaRemover);
    }

    public Estoque localizarProdutoPorLote(String lote) throws RegistroNaoEncontradoException {
        for (Estoque estoque : mapaEstoque.values()) {
            if(estoque.getLote().equalsIgnoreCase(lote)) {
                LoggerService.log("INFO", "Estoque encontrado - LOTE: " + lote);
                return estoque;
            }
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

        LoggerService.log("INFO", "Valor total em estoque exibido!");
        return valorTotal;
    }

    public int adicionarQuantidadeEmEstoque(int id, int quantidadeAdicional) throws RegistroNaoEncontradoException {
        Estoque estoqueEncontrado = mapaEstoque.get(id);

        if (estoqueEncontrado == null) {
            throw new RegistroNaoEncontradoException("ERRO: Produto não encontrado em estoque! VOLTE PARA CADASTRAR O PRODUTO");
        }
        int novaQuantidade = estoqueEncontrado.getQuantidadeAtual() + quantidadeAdicional;
        estoqueEncontrado.setQuantidadeAtual(novaQuantidade);

        arquivoUtil.salvarDados(this.mapaEstoque, "estoque.dat");
        LoggerService.log("INFO", "Nova quantidade em estoque cadastrada!");
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
        arquivoUtil.salvarDados(this.mapaEstoque, "estoque.dat");
        LoggerService.log("INFO", "Nova quantidade em estoque cadastraa");
        return novaQuantidade;
    }
    public boolean existeProdutoNoEstoque(int idProduto){
        for (Estoque estoque : mapaEstoque.values()){
            if(estoque.getProduto().getId() == idProduto){
                return true;
            }
        }
        return false;
    }

}



