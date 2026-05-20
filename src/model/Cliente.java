package model;

import java.time.LocalDate;

public abstract class Cliente {
    private String nome;
    private String telefone;
    private String email;
    private LocalDate dataCadastro;

    public Cliente (String nome, String telefone, String email, LocalDate dataCadastro) {
        setNome(nome);
        setTelefone(telefone);
        setEmail(email);
        setDataCadastro(dataCadastro);
    }

    public String getNome() {
        return nome;
    }
    public String getTelefone() {
        return telefone;
    }
    public String getEmail() {
        return email;
    }
    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setNome(String nome) throws IllegalArgumentException{
        if(nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("ERRO: O nome/razão social não pode ser nulo ou vazio. Você o deixou vazio!");
        }

        this.nome = nome;
    }

    public void setTelefone(String telefone) throws IllegalArgumentException{
        if(telefone == null || telefone.isBlank()) {
            throw new IllegalArgumentException("ERRO: O telefone é um campo obrigatório. O valor recebido não pode ser nulo ou vazio.");
        }
        if(!telefone.matches("[0-9()+\\- ]{11,15}")) {
            throw new IllegalArgumentException("ERRO: O telefone ultrapassou a quantidade permitida de caracteres ou está sem DDD. Digite um telefone válido!");
        }

        this.telefone = telefone;
    }

    public void setEmail(String email) throws IllegalArgumentException{
        if(email == null || email.isBlank()) {
            throw new IllegalArgumentException("ERRO: O e-mail é um campo obrigatório. O valor recebido não pode ser nulo ou vazio.");
        }
        if(!email.matches(".+@.+\\..+")) {
            throw new IllegalArgumentException("ERRO: O e-mail informado é inválido. Formato esperado: exemplo@provedor.com");
        }

        this.email = email;
    }

    public void setDataCadastro(LocalDate dataCadastro) throws IllegalArgumentException{
        if (dataCadastro == null) {
            throw new IllegalArgumentException("ERRO: A data de cadastro não pode ser nula!");
        }
        if(dataCadastro.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("ERRO: A data de cadastro não pode ser uma data no futuro!");
        }
        if(dataCadastro.isBefore(LocalDate.of(2026, 1, 1))) {
            throw new IllegalArgumentException(("ERRO: A data informada é anterior à fundação do atacadista (01/01/2026)!"));
        }

        this.dataCadastro = dataCadastro;
    }

    public abstract String getTipo();

    public abstract String calcularPrecoFinal(Produto produto);

    public String toString() {
        return "Tipo: " + getTipo() + "Nome: " + getNome() + " | Telefone: " + getTelefone() +
                " | E-mail: " + getEmail() + " | Data Cadastro: " + getDataCadastro();
    }
}
