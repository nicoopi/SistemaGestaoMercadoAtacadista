package model;

import interfaces.Descontavel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Serializable {
    private Cliente cliente;
    private List<ItemPedido> listaItens;
    private LocalDate dataPedido;

    public Pedido(Cliente cliente, LocalDate dataPedido) throws IllegalArgumentException{
        if(cliente == null) {
            throw new IllegalArgumentException("ERRO: O cliente não pode ser nulo! Insira um cliente válido.");
        }
        this.cliente = cliente;
        this.listaItens = new ArrayList<>();
        setDataPedido(dataPedido);
    }


    public List<ItemPedido> getListaItens() {
        return listaItens;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) throws IllegalArgumentException{
        if(dataPedido == null) {
            throw new IllegalArgumentException("ERRO: A data de pedido não pode ser nula!");
        }
        if(dataPedido.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("ERRO: A data de pedido não pode ser uma data no futuro!");
        }
        if(dataPedido.isBefore(LocalDate.of(2026, 1, 1))) {
            throw new IllegalArgumentException("ERRO: A data do pedido é anterior à fundação do atacadista (01/01/2026)!");
        }

        this.dataPedido = dataPedido;
    }

    public void adicionarItem(ItemPedido item) throws IllegalArgumentException{
        if(item == null) {
            throw new IllegalArgumentException("ERRO: O item não pode ser nulo! Insira um item válido.");
        }

        this.listaItens.add(item);
    }

    public double calcularTotalBruto() {
        double total = 0;

        for(ItemPedido i : listaItens) {
            total += i.calcularSubTotal();
        }

        return total;
    }

    public double obterValorDesconto() {
        if (cliente instanceof Descontavel) {
            return (((Descontavel) cliente).calcularDesconto(calcularTotalBruto()));
        }

        return 0;
    }

    public double calcularTotalLiquido() {
        return calcularTotalBruto() - obterValorDesconto();
    }


    @Override
    public String toString() {
        String cupom = "Cliente: " + cliente.getNome() + " | Data da Compra: " + getDataPedido() + "\n";
        for(ItemPedido i : listaItens) {
            cupom += i.toString() + "\n";
        }
        cupom += "\nTotal Bruto: R$" + calcularTotalBruto() + " | Desconto: R$" + obterValorDesconto() + " | Total Líquido: R$" + calcularTotalLiquido();

        return cupom;
    }
}
