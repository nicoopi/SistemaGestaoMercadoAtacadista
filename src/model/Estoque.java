package model;

import java.time.LocalDate;

public class Estoque {
    private Produto produto;
    private int quantidadeAtual;
    private String lote;
    private LocalDate dataDeValidade;


    public Estoque (Produto produto, int quantidadeAtual, String lote, LocalDate dataDeValidade) throws IllegalArgumentException{
        if(produto == null) {
            throw new IllegalArgumentException("ERRO: O produto do estoque não pode ser NULO");
        }
        this.produto = produto;
        setQuantidadeAtual(quantidadeAtual);
        setLote(lote);
        setDataDeValidade(dataDeValidade);
    }


    public Produto getProduto() {
        return produto;
    }

    public int getQuantidadeAtual() {
        return quantidadeAtual;
    }

    public String getLote() {
        return lote;
    }

    public LocalDate getDataDeValidade() {
        return dataDeValidade;
    }

    public void setQuantidadeAtual(int quantidadeAtual) throws  IllegalArgumentException{
        if(quantidadeAtual < 0){
            throw new IllegalArgumentException("ERRO: Quantiade em estoque não pode ser menor que 0! Coloque uma quantidade válida");
        }
        this.quantidadeAtual = quantidadeAtual;
    }

    public void setLote(String lote) throws IllegalArgumentException {
        if(lote.isEmpty() || lote.isBlank()) {
            throw new IllegalArgumentException("ERRO: O lote do estoque não pode ser NULO");
        }
        this.lote = lote;
    }

    public void setDataDeValidade(LocalDate dataDeValidade) throws IllegalArgumentException {
        if(dataDeValidade == null){
            throw new IllegalArgumentException("ERRO: A data de válidade não pode ser VAZIA! Coloque uma data válida!");
        }
        if(dataDeValidade.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("ERRO: A data de válidade não pode ser antes de HOJE! Coloque uma válidade que seja comestivél");
        }
        this.dataDeValidade = dataDeValidade;
    }

    @Override
    public String toString(){
        return "Produto: " + produto.getNomeProduto() + " | ID: " + produto.getId()
                + " | Quantidade atual do Produto: " + getQuantidadeAtual() + " | Lote: " + getLote() + " | Data de validade: " + getDataDeValidade();
    }
}