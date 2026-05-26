package controller;

import exceptions.CarrinhoVazioException;
import exceptions.EstoqueInsuficienteException;
import exceptions.RegistroNaoEncontradoException;
import model.Cliente;
import model.Estoque;
import model.ItemPedido;
import model.Produto;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoController {
    private List<ItemPedido> itensCarrinho;
    private Estoque estoque;
    private Cliente clienteAtual;

    public CarrinhoController(Estoque estoque, Cliente clienteAtual) throws IllegalArgumentException{
        if(estoque == null) {
            throw new IllegalArgumentException("ERRO: O estoque não pode ser nulo. O carrinho precisa de conexão com o estoque para funcionar!");
        }

        if(clienteAtual == null) {
            throw new IllegalArgumentException("ERRO: O cliente não pode ser nulo. Todo carrinho precisa ter um dono!");
        }
        this.estoque = estoque;
        this.itensCarrinho = new ArrayList<>();
        this.clienteAtual = clienteAtual;
    }

   public void adicionarProdutonoCarrinho(int idProduto, int quantidade) throws IllegalArgumentException, EstoqueInsuficienteException {
       if (quantidade <= 0) {
           throw new IllegalArgumentException("ERRO: A quantidade deve ser maior que zero.");
       }

       Produto produtoProvisorio = estoque.getProduto();

       if (quantidade > estoque.getQuantidadeAtual()) {
           throw new EstoqueInsuficienteException("ERRO: Estoque insuficiente! Só temos " + estoque.getQuantidadeAtual() + " unidades disponíveis.");
       }

       ItemPedido novoItem = new ItemPedido(produtoProvisorio, quantidade);
       this.itensCarrinho.add(novoItem);
   }


    public List<ItemPedido> getItensCarrinho() {
        return this.itensCarrinho;
    }

    public Cliente getClienteAtual() {
        return this.clienteAtual;
    }

    public void removerProdutodoCarrinho(int idRemovida) throws RegistroNaoEncontradoException {
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

        itensCarrinho.remove(itemParaRemover);
    }

    public double finalizarCompra() throws CarrinhoVazioException {
        if(itensCarrinho.isEmpty()) {
            throw new CarrinhoVazioException("ERRO: O carrinho está vazio. Adicione produtos antes de finalizar!");
        }

        double totalCompra = 0;
        for(ItemPedido item : itensCarrinho) {
            totalCompra += item.calcularSubTotal();
        }

        itensCarrinho.clear();

        return totalCompra;
    }
}
