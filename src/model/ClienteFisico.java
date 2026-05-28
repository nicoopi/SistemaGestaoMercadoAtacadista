package model;

import interfaces.Descontavel;
import java.time.LocalDate;
import java.io.Serializable;

public class ClienteFisico extends Cliente implements Descontavel, Serializable {
    private String cpf;

    public ClienteFisico(String nome, String telefone, String email, LocalDate dataCadastro, String cpf) {
        super(nome, telefone, email, dataCadastro);
        setCpf(cpf);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) throws IllegalArgumentException {
        if(cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("ERRO: O CPF é um campo obrigatório. O valor recebido não pode ser nulo ou vazio.");
        }
        if(!cpf.matches("[0-9]{11}")) {
            throw new IllegalArgumentException("ERRO: CPF inválido! O valor recebido não pode ter pontuação ou símbolos.");
        }

        this.cpf = cpf;
    }

    @Override
    public double calcularDesconto(double valorTotalCarrinho) {
        if (valorTotalCarrinho >= 800.0) {
            return valorTotalCarrinho * 0.05;
        }

        return 0.0;
    }

    @Override
    public String getTipo() {
        return "Pessoa Física";
    }

    @Override
    public String toString() {
        return super.toString() + " | CPF: " + getCpf();
    }
}
