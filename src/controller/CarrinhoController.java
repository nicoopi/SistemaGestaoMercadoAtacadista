package controller;

import model.Cliente;
import model.Estoque;
import model.ItemPedido;
import model.Produto;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoController {
    private List<ItemPedido> itensCarrinho;
    private Cliente clienteAtual;
    private Estoque estoque;

    public CarrinhoController(Estoque estoque) throws IllegalArgumentException{
        if(estoque == null) {
            throw new IllegalArgumentException("ERRO: O estoque não pode ser nulo. O carrinho precisa de conexão com o estoque para funcionar!");
        }

        this.estoque = estoque;

        this.itensCarrinho = new ArrayList<>();
        this.clienteAtual = null;
    }

    public void adicionarProdutonoCarrinho(Produto produto, int quantidadeDesejada)  throws IllegalArgumentException{
        if(produto == null) {
            throw new IllegalArgumentException("ERRO: O produto selecionado é inválido.");
        }
        if (quantidadeDesejada <= 0) {
            throw new IllegalArgumentException("ERRO: A quantidade deve ser maior que zero.");
        }

        if(quantidadeDesejada <= estoque.getQuantidadeAtual()) {
            ItemPedido novoItem = new ItemPedido(produto, quantidadeDesejada);
            this.itensCarrinho.add(novoItem);
        } else {
            throw new IllegalArgumentException("ERRO: Estoque insuficiente! Não temos " + quantidadeDesejada + " unidades na prateleira.");
        }
    }

    public List<ItemPedido> getItensCarrinho() {
        return this.itensCarrinho;
    }
}
