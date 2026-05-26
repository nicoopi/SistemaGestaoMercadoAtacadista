package controller;

import exceptions.RegistroNaoEncontradoException;
import model.ClienteJuridico;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class ClienteJuridicoController {
    private Map<String, ClienteJuridico> mapaClientesJuridicos = new HashMap<>();

    public void cadastrarClienteJuridico(String nome, String telefone, String email, String dataCadastroTexto, String cnpj) throws DateTimeParseException, IllegalArgumentException {
        if (mapaClientesJuridicos.containsKey(cnpj)) {
            throw new IllegalArgumentException("ERRO: CNPJ já cadastrado! Digite um CNPJ válido!");
        }

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataCadastro = LocalDate.parse(dataCadastroTexto, formatador);

        ClienteJuridico novoClienteJuridico = new ClienteJuridico(nome, telefone, email, dataCadastro, cnpj);
        mapaClientesJuridicos.put(cnpj, novoClienteJuridico);
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
    }
}
