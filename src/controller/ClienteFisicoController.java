package controller;

import exceptions.RegistroNaoEncontradoException;
import model.ClienteFisico;
import view.ClienteFisicoView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class ClienteFisicoController {
    private ClienteFisicoView view;
    private Map<String, ClienteFisico> mapaClientes;

    public ClienteFisicoController (ClienteFisicoView view) {
        this.view = view;
        this.mapaClientes = new HashMap<>();
    }

    public void cadastrarClienteFisico() {
        try {
            view.exibirMensagem("----- Cadastro de Cliente Físico -----");
            String nome = view.lerNome();
            String telefone = view.lerTelefone();
            String email = view.lerEmail();
            String dataCadastroTexto = view.lerDataCadastro();
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataCadastro = LocalDate.parse(dataCadastroTexto, formatador);
            String cpf = view.lerCpf();

            if(mapaClientes.containsKey(cpf)) {
                view.exibirMensagem("ERRO: CPF já cadastrado! Digite um CPF válido!");
                return;
            }

            ClienteFisico novoClienteFisico = new ClienteFisico(nome, telefone, email, dataCadastro, cpf);
            mapaClientes.put(cpf, novoClienteFisico);
            view.exibirMensagem("Sucesso! Cliente físico cadastrado.");
        } catch (DateTimeParseException e) {
            view.exibirMensagem("ERRO: Formato de data inválido. Certifique-se de usar barras (DD/MM/AAAA).");
        } catch (IllegalArgumentException e) {
            view.exibirMensagem(e.getMessage());
        }
    }

    public ClienteFisico buscarPorCpf(String cpf) throws RegistroNaoEncontradoException {
        ClienteFisico clienteEncontrado = mapaClientes.get(cpf);

        if(clienteEncontrado == null) {
            throw new RegistroNaoEncontradoException("ERRO: Nenhum cliente físico encontrado com o CPF informado.");
        }

        return clienteEncontrado;
    }

    public Map<String, ClienteFisico> listarClientesFisicos() {
        return mapaClientes;
    }

    public void removerPorCpf(String cpf) throws RegistroNaoEncontradoException{
        ClienteFisico clienteRemovido = mapaClientes.remove(cpf);

        if(clienteRemovido == null) {
            throw new RegistroNaoEncontradoException("ERRO: Não foi possível remover. Nenhum cliente físico encontrado com o CPF informado");
        }
    }
}
