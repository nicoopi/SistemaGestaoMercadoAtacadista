package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrdemCompra implements Serializable {
    private Fornecedor fornecedor;
    private List<Produto> produtos;
    private int quantidade;
    private double custoUnitario;


    public OrdemCompra(Fornecedor fornecedor, int quantidade, double custoUnitario) throws IllegalArgumentException {
        if (fornecedor == null) {
            throw new IllegalArgumentException("ERRO: O fornecedor não pode ser nulo! Insira um fornecedor válido.");
        }

        this.fornecedor = fornecedor;
        this.produtos = new ArrayList<>();
        setQuantidade(quantidade);
        setCustoUnitario(custoUnitario);
    }

    public void adicionarProduto(Produto produto) throws IllegalArgumentException{
        if (produto == null) {
            throw new IllegalArgumentException("ERRO: O produto não pode ser nulo! Insira um produto válido.");
        }
        this.produtos.add(produto);
    }

    public void removerProduto(Produto produto) throws IllegalArgumentException {
        if (produto == null) {
            throw new IllegalArgumentException("ERRO: O produto não pode ser nulo! Insira um produto válido.");
        }
        boolean encontrado = false;
        for (Produto p : produtos) {
            if (p == produto) {
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            throw new IllegalArgumentException("ERRO: Produto não encontrado!");
        }
        this.produtos.remove(produto);
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public List<Produto> getProdutos() {
        return produtos;
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

    public void setCustoUnitario(double custoUnitario)throws IllegalArgumentException{
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
        String ordemCompra = "Fornecedor: " + fornecedor.getRazaoSocial()
                + " | Quantidade: " + getQuantidade()
                + " | Custo Unitário: R$ " + getCustoUnitario()
                + " | Custo Total: R$ " + calcularTotal() + "\n";
        for (Produto p : produtos) {
            ordemCompra += p.toString() + "\n";
        }
        return ordemCompra;
    }
}