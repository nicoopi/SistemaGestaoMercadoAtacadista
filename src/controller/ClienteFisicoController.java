package controller;

import exceptions.RegistroNaoEncontradoException;
import model.ClienteFisico;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class ClienteFisicoController {
    private Map<String, ClienteFisico> mapaClientesFisicos = new HashMap<>();

    public void cadastrarClienteFisico(String nome, String telefone, String email, String dataCadastroTexto, String cpf) throws DateTimeParseException, IllegalArgumentException {
        if(mapaClientesFisicos.containsKey(cpf)) {
            throw new IllegalArgumentException("ERRO: CPF já cadastrado! Digite um CPF válido!");
        }

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataCadastro = LocalDate.parse(dataCadastroTexto, formatador);

        ClienteFisico novoClienteFisico = new ClienteFisico(nome, telefone, email, dataCadastro, cpf);
        mapaClientesFisicos.put(cpf, novoClienteFisico);
    }

    public ClienteFisico buscarPorCpf(String cpf) throws RegistroNaoEncontradoException {
        ClienteFisico clienteEncontrado = mapaClientesFisicos.get(cpf);

        if(clienteEncontrado == null) {
            throw new RegistroNaoEncontradoException("ERRO: Nenhum cliente físico encontrado com o CPF informado.");
        }

        return clienteEncontrado;
    }

    public Map<String, ClienteFisico> listarClientesFisicos() {
        return mapaClientesFisicos;
    }

    public void removerPorCpf(String cpf) throws RegistroNaoEncontradoException{
        ClienteFisico clienteRemovido = mapaClientesFisicos.remove(cpf);

        if(clienteRemovido == null) {
            throw new RegistroNaoEncontradoException("ERRO: Não foi possível remover. Nenhum cliente físico encontrado com o CPF informado!");
        }
    }
}
