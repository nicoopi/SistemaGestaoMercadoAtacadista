package model;

public class Produto {
    private String nomeProduto;
    private double precoClienteFisico;
    private double precoClienteJuridico;
    private int id;

    public  Produto (String nomeProduto, double precoClienteFisico, double precoClienteJuridico, int id ){
        setNomeProduto(nomeProduto);
        setPrecoClienteFisico(precoClienteFisico);
        setPrecoClienteJuridico(precoClienteJuridico);
        setId(id);
    }
    public String getNomeProduto(){
        return nomeProduto;
    }
    public double getPrecoClienteFisico(){
        return precoClienteFisico;
    }

    public double getPrecoClienteJuridico() {
        return precoClienteJuridico;
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

    public void setPrecoClienteFisico(double precoClienteFisico) throws IllegalArgumentException {
       if (precoClienteFisico <= 0) {
           throw new IllegalArgumentException("ERRO: O preço do produto não pode ser vazio ou negativo! Coloque um preço válido");

       }
        this.precoClienteFisico = precoClienteFisico;
    }

    public void setPrecoClienteJuridico(double precoClienteJuridico) throws IllegalArgumentException{
        if (precoClienteJuridico <= 0) {
            throw new IllegalArgumentException("ERRO: O preço do produto não pode ser vazio ou negativo! Coloque um preço válido");
        }

        this.precoClienteJuridico = precoClienteJuridico;
    }
    public void setId(int id) throws IllegalArgumentException {
       if (id <= 0) {
           throw new IllegalArgumentException("ERRO: O ID do produto não pode ser vazio ou negativo! Coloque um ID válido");
       }

        this.id = id;
    }
    public String toString(){
        return "Nome do Produto: " + getNomeProduto() +
                "\n Preço para Cliente Físico: " +getPrecoClienteFisico()+
                "\n Preço para Cliente Jurídico: " +getPrecoClienteJuridico()+
                "\n ID do produto: " + getId();
    }

}

