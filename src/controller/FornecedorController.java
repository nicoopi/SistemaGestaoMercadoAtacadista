package controller;

import exceptions.RegistroNaoEncontradoException;
import model.Fornecedor;
import util.ArquivoUtil;
import util.LoggerService;

import java.util.HashMap;
import java.util.Map;

public class FornecedorController {
    private Map<String, Fornecedor> mapFornecedor;
    private final ArquivoUtil arquivoUtil = new ArquivoUtil();

    public FornecedorController() {
        Object dadosRecebidos = arquivoUtil.lerDados("fornecedores.dat");

        if (dadosRecebidos != null) {
            this.mapFornecedor = (Map<String, Fornecedor>) dadosRecebidos;
        } else {
            this.mapFornecedor = new HashMap<>();
        }
    }

    public void cadastrarFornecedor(String razaoSocial, String cnpj, String telefone) throws IllegalArgumentException {
        if (mapFornecedor.containsKey(cnpj.toUpperCase())) {
            throw new IllegalArgumentException("ERRO: Já existe um fornecedor cadastrado com esse CNPJ!");
        }

        Fornecedor novoFornecedor = new Fornecedor(razaoSocial, cnpj, telefone);
        mapFornecedor.put(novoFornecedor.getCnpj(), novoFornecedor);

        arquivoUtil.salvarDados(this.mapFornecedor, "fornecedores.dat");
        LoggerService.log("INFO", "Fornecedor cadastrado - CNPJ: " + novoFornecedor.getCnpj());
    }

    public Map<String, Fornecedor> listarFornecedores() {
        LoggerService.log("INFO", "Listagem de fornecedores executada.");
        return mapFornecedor;
    }

    public void removerFornecedorPorCnpj(String cnpj) throws RegistroNaoEncontradoException {
        Fornecedor fornecedor = mapFornecedor.remove(cnpj.toUpperCase());

        if (fornecedor == null) {
            throw new RegistroNaoEncontradoException("ERRO: Nenhum fornecedor encontrado com esse CNPJ");
        }

        arquivoUtil.salvarDados(this.mapFornecedor, "fornecedores.dat");
        LoggerService.log("INFO", "Fornecedor removido - CNPJ: " + fornecedor.getCnpj());
    }

    public Fornecedor buscarFornecedorPorCnpj(String cnpj) throws RegistroNaoEncontradoException {
        Fornecedor fornecedor = mapFornecedor.get(cnpj.toUpperCase());

        if (fornecedor == null) {
            throw new RegistroNaoEncontradoException("ERRO: Nenhum fornecedor encontrado com esse CNPJ");
        }

        LoggerService.log("INFO", "Fornecedor encontrado - CNPJ: " + fornecedor.getCnpj());
        return fornecedor;
    }

    public void modificarFornecedor(String cnpjAtual, String novaRazaoSocial, String novoCnpj, String novoTelefone) throws RegistroNaoEncontradoException, IllegalArgumentException {
        Fornecedor fornecedor = buscarFornecedorPorCnpj(cnpjAtual);
        String cnpjAtualUpper = cnpjAtual.toUpperCase();
        String novoCnpjUpper = novoCnpj.toUpperCase();

        if (!cnpjAtualUpper.equals(novoCnpjUpper) && mapFornecedor.containsKey(novoCnpjUpper)) {
            throw new IllegalArgumentException("ERRO: Já existe outro fornecedor cadastrado com o novo CNPJ!");
        }

        if (!cnpjAtualUpper.equals(novoCnpjUpper)) {
            mapFornecedor.remove(cnpjAtualUpper);
        }

        fornecedor.setRazaoSocial(novaRazaoSocial);
        fornecedor.setCnpj(novoCnpj);
        fornecedor.setTelefone(novoTelefone);

        mapFornecedor.put(fornecedor.getCnpj(), fornecedor);

        arquivoUtil.salvarDados(this.mapFornecedor, "fornecedores.dat");
        LoggerService.log("INFO", "Fornecedor modificado - CNPJ: " + fornecedor.getCnpj());
    }
}