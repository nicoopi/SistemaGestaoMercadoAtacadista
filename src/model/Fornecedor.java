package model;

import java.io.Serializable;

public class Fornecedor implements Serializable {
    private String razaoSocial;
    private String cnpj;
    private String telefone;

    public Fornecedor( String razaoSocial, String cnpj, String telefone){
        setRazaoSocial(razaoSocial);
        setCnpj(cnpj);
        setTelefone(telefone);

    }


    public String getRazaoSocial() {
        return razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getTelefone() {
        return telefone;
    }


    public void setRazaoSocial(String razaoSocial) throws IllegalArgumentException{
        if (razaoSocial == null || razaoSocial.isBlank()){
            throw new IllegalArgumentException("ERRO: A razão social não pode ser NULA ou VAZIA");
        }
        this.razaoSocial = razaoSocial;
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


    public void setTelefone(String telefone) throws IllegalArgumentException{
        if(telefone == null || telefone.isBlank()) {
            throw new IllegalArgumentException("ERRO: O telefone é um campo obrigatório. O valor recebido não pode ser nulo ou vazio.");
        }
        if(!telefone.matches("[0-9()+\\- ]{11,15}")) {
            throw new IllegalArgumentException("ERRO: O telefone ultrapassou a quantidade permitida de caracteres ou está sem DDD. Digite um telefone válido!");
        }

        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Razão Social: " + getRazaoSocial() + " | CNPJ: " + getCnpj() + " | Telefone: " + getTelefone();
    }


}


