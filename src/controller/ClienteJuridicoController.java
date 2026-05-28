package controller;

import exceptions.RegistroNaoEncontradoException;
import model.ClienteFisico;
import model.ClienteJuridico;
import util.ArquivoUtil;

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
    }

    public ClienteJuridico buscarPorCnpj(String cnpj) throws RegistroNaoEncontradoException {
        ClienteJuridico clienteEncontrado = mapaClientesJuridicos.get(cnpj);

        if (clienteEncontrado == null) {
            throw new RegistroNaoEncontradoException("ERRO: Nenhum cliente jurídico encontrado com o CNPJ informado.");
        }

        return clienteEncontrado;
    }

    public Map<String, ClienteJuridico> listarClientesJuridicos() {
        return mapaClientesJuridicos;
    }

    public void removerPorCnpj(String cnpj) throws RegistroNaoEncontradoException{
        ClienteJuridico clienteRemovido = mapaClientesJuridicos.remove(cnpj);

        if(clienteRemovido == null) {
            throw new RegistroNaoEncontradoException("ERRO: Não foi possível remover. Nenhum cliente jurídico encontrado com o CNPJ informado!");
        }

        arquivoUtil.salvarDados(mapaClientesJuridicos, "clientes_juridicos.dat");
    }
}
