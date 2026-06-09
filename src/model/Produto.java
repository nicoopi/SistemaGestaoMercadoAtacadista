package model;
import java.io.Serializable;
public class Produto implements Serializable{
    private String nomeProduto;
    private double precoBase;
    private int id;

    public  Produto (String nomeProduto, double precoBase, int id ){
        setNomeProduto(nomeProduto);
        setPrecoBase(precoBase);
        setId(id);
    }

    public String getNomeProduto(){
        return nomeProduto;
    }
    public double getPrecoBase() {
        return precoBase;
    }
    public int getId() {
        return id;
    }

    public void setNomeProduto(String nomeProduto) throws IllegalArgumentException {
        if(nomeProduto == null || nomeProduto.isBlank()) {
            throw new IllegalArgumentException("ERRO: O nome do Produto não pode ser nulo ou vazio. Você o deixou vazio!");
        }

        this.nomeProduto = nomeProduto;
    }

    public void setPrecoBase(double precoBase) throws IllegalArgumentException{
        if (precoBase <= 0) {
            throw new IllegalArgumentException("ERRO: O preço base do produto deve ser maior que zero! Coloque um preço válido");
        }

        this.precoBase = precoBase;
    }

    public void setId(int id) throws IllegalArgumentException {
       if (id <= 0) {
           throw new IllegalArgumentException("ERRO: O ID do produto deve ser um número positivo maior que zero! Coloque um ID válido");
       }

        this.id = id;
    }

    @Override
    public String toString(){
        return "Nome do Produto: " + getNomeProduto() + " | Preço Base: " + getPrecoBase() + " | ID: " + getId();
    }
}

