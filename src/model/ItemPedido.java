package model;

import java.io.Serializable;

public class ItemPedido implements Serializable {
    private Produto produto;
    private int quantidadePedida;
    private double precoUnitario;

    public ItemPedido (Produto produto, int quantidadePedida) throws IllegalArgumentException{
        if(produto == null) {
            throw new IllegalArgumentException("ERRO: O produto não pode ser nulo! Insira um produto válido.");
        }

        this.produto = produto;
        setQuantidadePedida(quantidadePedida);
        setPrecoUnitario(produto.getPrecoBase());
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidadePedida() {
        return quantidadePedida;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setQuantidadePedida(int quantidadePedida) throws IllegalArgumentException {
        if(quantidadePedida <= 0) {
            throw new IllegalArgumentException("ERRO: A quantidade deve ser maior que zero! Coloque uma quantidade válida.");
        }
        this.quantidadePedida = quantidadePedida;
    }

    public void setPrecoUnitario(double precoUnitario) throws IllegalArgumentException {
        if(precoUnitario <= 0) {
            throw new IllegalArgumentException("ERRO: O preço unitário do produto deve ser maior que zero!");
        }
        this.precoUnitario = precoUnitario;
    }

    public double calcularSubTotal() {
        return getQuantidadePedida() * getPrecoUnitario();
    }

    @Override
    public String toString() {
        return "Produto: " + produto.getNomeProduto() + " | Quantidade Pedida: " + getQuantidadePedida() + " | Preço Unitário: R$ " + getPrecoUnitario() + " | Subtotal: R$ " + calcularSubTotal();
    }
}