package controller;

import exceptions.RegistroNaoEncontradoException;
import model.Fornecedor;
import view.FornecedorView;

import java.util.HashMap;
import java.util.Map;

public class FornecedorController {
    private FornecedorView view;
    private Map<String, Fornecedor> mapFornecedor;

    public FornecedorController(FornecedorView view) {
        this.view = view;
        this.mapFornecedor = new HashMap<>();
    }

    public void cadastrarFornecedor() {
        try {
            view.exibirMensagem("----- Cadastro do Fornecedor -----");
            String razaoSocial = view.lerRazaoSocial();
            String cnpj = view.lerCnpj();
            String telefone = view.lerTelefone();

            if (mapFornecedor.containsKey(cnpj.toUpperCase())) {
                throw new IllegalArgumentException("ERRO: Já existe um fornecedor cadastrado com esse CNPJ!");
            }

            Fornecedor novoFornecedor = new Fornecedor(razaoSocial, cnpj, telefone);
            mapFornecedor.put(novoFornecedor.getCnpj(), novoFornecedor);
            view.exibirMensagem("Sucesso! Fornecedor cadastrado");
        } catch (IllegalArgumentException e) {
            view.exibirMensagem(e.getMessage());
        }
    }

    public Map<String, Fornecedor> listarFornecedores() {
        return mapFornecedor;
    }

    public void removerFornecedorPorCnpj(String cnpj) throws RegistroNaoEncontradoException {
        Fornecedor fornecedor = mapFornecedor.remove(cnpj.toUpperCase());

        if (fornecedor == null) {
            throw new RegistroNaoEncontradoException("ERRO: Nenhum fornecedor encontrado com esse CNPJ");
        }
    }

    public Fornecedor buscarFornecedorPorCnpj(String cnpj) throws RegistroNaoEncontradoException {
        Fornecedor fornecedor = mapFornecedor.get(cnpj.toUpperCase());

        if (fornecedor == null) {
            throw new RegistroNaoEncontradoException("ERRO: Nenhum fornecedor encontrado com esse CNPJ");
        }

        return fornecedor;
    }
}