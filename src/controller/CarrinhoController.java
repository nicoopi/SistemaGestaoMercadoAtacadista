package controller;

import exceptions.CarrinhoVazioException;
import exceptions.EstoqueInsuficienteException;
import exceptions.RegistroNaoEncontradoException;
import model.Cliente;
import model.Estoque;
import model.ItemPedido;
import model.Produto;
import util.ArquivoUtil;
import util.LoggerService;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoController {
    private List<ItemPedido> itensCarrinho;
    private EstoqueController estoqueController;
    private Cliente clienteAtual;
    private final ArquivoUtil arquivoUtil = new ArquivoUtil();

    public CarrinhoController(EstoqueController estoqueController, Cliente clienteAtual) throws IllegalArgumentException{
        if(estoqueController == null) {
            throw new IllegalArgumentException("ERRO: O Estoque não pode ser nulo. O carrinho precisa de conexão com o estoque para funcionar!");
        }

        if(clienteAtual == null) {
            throw new IllegalArgumentException("ERRO: O cliente não pode ser nulo. Todo carrinho precisa ter um dono!");
        }
        this.estoqueController = estoqueController;
        this.clienteAtual = clienteAtual;

        Object dadosRecebidos = arquivoUtil.lerDados("carrinho.dat");

        if(dadosRecebidos != null) {
            this.itensCarrinho = (List<ItemPedido>) dadosRecebidos;
        } else {
            this.itensCarrinho = new ArrayList<>();
        }
    }

   public void adicionarProdutonoCarrinho(int idProduto, int quantidade) throws IllegalArgumentException, EstoqueInsuficienteException, RegistroNaoEncontradoException {
       if (quantidade <= 0) {
           throw new IllegalArgumentException("ERRO: A quantidade deve ser maior que zero.");
       }

       Estoque estoqueEncontrado = estoqueController.buscarEstoquePorId(idProduto);

       if (quantidade > estoqueEncontrado.getQuantidadeAtual()) {
           throw new EstoqueInsuficienteException("ERRO: Estoque insuficiente! Só temos " + estoqueEncontrado.getQuantidadeAtual() + " unidades disponíveis.");
       }

       Produto produtoReal = estoqueEncontrado.getProduto();
       ItemPedido novoItem = new ItemPedido(produtoReal, quantidade);
       this.itensCarrinho.add(novoItem);

       arquivoUtil.salvarDados(this.itensCarrinho, "carrinho.dat");
       LoggerService.log("INFO", "Produto ID: " + idProduto + " adicionado ao carrinho do Cliente: " + getClienteAtual().getNome());
   }


    public List<ItemPedido> listarItensCarrinho() {
        LoggerService.log("INFO", "Listagem dos itens do carrinho executada.");
        return this.itensCarrinho;
    }

    public Cliente getClienteAtual() {
        return this.clienteAtual;
    }

    public int removerProdutodoCarrinho(int idRemovida, int quantidadeParaRemover) throws RegistroNaoEncontradoException, IllegalArgumentException {
        if(quantidadeParaRemover <= 0) {
            throw new IllegalArgumentException("ERRO: A quantidade para remover deve ser maior que zero.");
        }

        ItemPedido itemParaRemover = null;

        for (ItemPedido item : itensCarrinho) {
            if (item.getProduto().getId() == idRemovida) {
                itemParaRemover = item;
                break;
            }
        }

        if (itemParaRemover == null) {
            throw new RegistroNaoEncontradoException("ERRO: O ID: " + idRemovida + " não foi encontrado no seu carrinho.");
        }

        int quantidadeAtual = itemParaRemover.getQuantidadePedida();
        int statusRetorno;

        if(quantidadeParaRemover > quantidadeAtual) {
            itensCarrinho.remove(itemParaRemover);
            statusRetorno = -1;
        } else if (quantidadeParaRemover == quantidadeAtual) {
            itensCarrinho.remove(itemParaRemover);
            statusRetorno = 0;
        } else {
            int novaQuantidade = quantidadeAtual - quantidadeParaRemover;
            itemParaRemover.setQuantidadePedida(novaQuantidade);
            statusRetorno = novaQuantidade;
        }

        arquivoUtil.salvarDados(this.itensCarrinho, "carrinho.dat");
        LoggerService.log("INFO", "Produto ID: " + idRemovida + " teve sua quantidade removida/alterada do carrinho.");

        return statusRetorno;
    }


    public double finalizarCompra() throws CarrinhoVazioException, RegistroNaoEncontradoException {
        if(itensCarrinho.isEmpty()) {
            throw new CarrinhoVazioException("ERRO: O carrinho está vazio. Adicione produtos antes de finalizar!");
        }

        double totalCompra = 0;
        for(ItemPedido item : itensCarrinho) {
            totalCompra += item.calcularSubTotal();

            estoqueController.removerQuantidadeDeEstoque(item.getProduto().getId(), item.getQuantidadePedida());
        }
        itensCarrinho.clear();
        arquivoUtil.salvarDados(this.itensCarrinho, "carrinho.dat");
        LoggerService.log("INFO", "Compra finalizada com sucesso. Cliente: " + getClienteAtual().getNome() + " | Valor Total: R$" + totalCompra);
        return totalCompra;


    }
}
