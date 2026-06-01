package controller;

import exceptions.RegistroNaoEncontradoException;
import model.ClienteJuridico;
import util.ArquivoUtil;
import util.LoggerService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class ClienteJuridicoController {
    private Map<String, ClienteJuridico> mapaClientesJuridicos;
    private final ArquivoUtil arquivoUtil = new ArquivoUtil();

    public ClienteJuridicoController() {
        Object dadosRecebidos = arquivoUtil.lerDados("clientes_juridicos.dat");

        if (dadosRecebidos != null) {
            this.mapaClientesJuridicos = (Map<String, ClienteJuridico>) dadosRecebidos;
        } else {
            this.mapaClientesJuridicos = new HashMap<>();
        }
    }

    public void cadastrarClienteJuridico(String nome, String telefone, String email, String dataCadastroTexto, String cnpj) throws DateTimeParseException, IllegalArgumentException {
        if (mapaClientesJuridicos.containsKey(cnpj)) {
            throw new IllegalArgumentException("ERRO: CNPJ já cadastrado! Digite um CNPJ válido!");
        }

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataCadastro = LocalDate.parse(dataCadastroTexto, formatador);

        ClienteJuridico novoClienteJuridico = new ClienteJuridico(nome, telefone, email, dataCadastro, cnpj);
        mapaClientesJuridicos.put(cnpj, novoClienteJuridico);

        arquivoUtil.salvarDados(mapaClientesJuridicos, "clientes_juridicos.dat");
        LoggerService.log("INFO", "Cliente jurídico cadastrado - CNPJ: " + novoClienteJuridico.getCnpj());
    }

    public ClienteJuridico buscarPorCnpj(String cnpj) throws RegistroNaoEncontradoException {
        ClienteJuridico clienteEncontrado = mapaClientesJuridicos.get(cnpj);

        if (clienteEncontrado == null) {
            throw new RegistroNaoEncontradoException("ERRO: Nenhum cliente jurídico encontrado com o CNPJ informado.");
        }

        LoggerService.log("INFO", "Cliente jurídico encontrado - CNPJ: " + clienteEncontrado.getCnpj());
        return clienteEncontrado;
    }

    public Map<String, ClienteJuridico> listarClientesJuridicos() {
        LoggerService.log("INFO", "Listagem de clientes jurídicos executada.");
        return mapaClientesJuridicos;
    }

    public void removerPorCnpj(String cnpj) throws RegistroNaoEncontradoException{
        ClienteJuridico clienteRemovido = mapaClientesJuridicos.remove(cnpj);

        if(clienteRemovido == null) {
            throw new RegistroNaoEncontradoException("ERRO: Não foi possível remover. Nenhum cliente jurídico encontrado com o CNPJ informado!");
        }

        arquivoUtil.salvarDados(mapaClientesJuridicos, "clientes_juridicos.dat");
        LoggerService.log("INFO", "Cliente jurídico removido - CNPJ: " + clienteRemovido.getCnpj());
    }

    public void alterarClientePorCnpj(String cnpj, String novoNome, String novoTelefone, String novoEmail, String novaDataTexto) throws RegistroNaoEncontradoException, DateTimeParseException {
        ClienteJuridico clienteEncontrado = buscarPorCnpj(cnpj);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate novaData = LocalDate.parse(novaDataTexto, formatador);

        clienteEncontrado.setNome(novoNome);
        clienteEncontrado.setEmail(novoEmail);
        clienteEncontrado.setTelefone(novoTelefone);
        clienteEncontrado.setDataCadastro(novaData);

        arquivoUtil.salvarDados(this.mapaClientesJuridicos, "clientes_juridicos.dat");
        LoggerService.log("INFO", "Cliente jurídico alterado - CNPJ: " + clienteEncontrado.getCnpj());
    }
}
