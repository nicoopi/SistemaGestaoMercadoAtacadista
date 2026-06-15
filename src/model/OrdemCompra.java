package model;

import java.io.Serializable;

public class OrdemCompra implements Serializable {
    private Fornecedor fornecedor;
    private Produto produto;
    private int quantidade;
    private double custoUnitario;

    public OrdemCompra(Fornecedor fornecedor, Produto produto, int quantidade, double custoUnitario) throws IllegalArgumentException {
        if (fornecedor == null) {
            throw new IllegalArgumentException("ERRO: O fornecedor não pode ser nulo! Insira um fornecedor válido.");
        }
        if (produto == null) {
            throw new IllegalArgumentException("ERRO: O produto não pode ser nulo! Insira um produto válido.");
        }

        this.fornecedor = fornecedor;
        this.produto = produto;
        setQuantidade(quantidade);
        setCustoUnitario(custoUnitario);
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getCustoUnitario() {
        return custoUnitario;
    }

    public void setQuantidade(int quantidade) throws IllegalArgumentException{
        if (quantidade <= 0) {
            throw new IllegalArgumentException("ERRO: A quantidade deve ser maior que zero! Coloque uma quantidade válida.");
        }
        this.quantidade = quantidade;
    }

    public void setCustoUnitario(double custoUnitario) throws IllegalArgumentException{
        if (custoUnitario <= 0){
            throw new IllegalArgumentException("ERRO: O custo unitário deve ser maior que zero! Coloque um custo válido.");
        }
        this.custoUnitario = custoUnitario;
    }

    public double calcularTotal(){
        return getCustoUnitario() * getQuantidade();
    }

    @Override
    public String toString() {
        return "Fornecedor: " + fornecedor.getRazaoSocial()
                + " | Quantidade: " + getQuantidade()
                + " | Custo Unitário: R$ " + getCustoUnitario()
                + " | Custo Total: R$ " + calcularTotal() + "\n"
                + produto.toString() + "\n";
    }
}