package model;

import interfaces.Descontavel;

import java.time.LocalDate;

public class ClienteJuridico extends Cliente implements Descontavel {
    private String cnpj;

    public ClienteJuridico (String nome, String telefone, String email, LocalDate dataCadastro, String cnpj) {
        super(nome, telefone, email, dataCadastro);
        setCnpj(cnpj);
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) throws IllegalArgumentException{
        if(cnpj == null || cnpj.isBlank()) {
            throw new IllegalArgumentException("ERRO: O CNPJ é um campo obrigatório. O valor recebido não pode ser nulo ou vazio.");
        }

        cnpj = cnpj.toUpperCase();
        if(!cnpj.matches("[0-9A-Z]{12}[0-9]{2}")) {
            throw new IllegalArgumentException("ERRO: CNPJ inválido! O valor recebido não pode ter pontuação ou símbolos.");
        }

        this.cnpj = cnpj;
    }

    @Override
    public double calcularDesconto(double valorTotalCarrinho) {
        if (valorTotalCarrinho >= 500.0) {
            return valorTotalCarrinho * 0.1;
        }

        return 0.0;
    }

    @Override
    public String getTipo() {
        return "Pessoa Jurídica";
    }

    @Override
    public String toString() {
        return super.toString() + " | CNPJ: " + getCnpj();
    }
}
