package controller;

import exceptions.RegistroNaoEncontradoException;
import model.Cliente;
import model.ItemPedido;
import model.Pedido;
import util.ArquivoUtil;
import util.LoggerService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PedidoController {
    private Map<Integer, Pedido> mapaPedidos;
    private final ArquivoUtil arquivoUtil = new ArquivoUtil();

    public PedidoController() {
        Object dadosRecebidos = arquivoUtil.lerDados("pedidos.dat");

        if (dadosRecebidos != null) {
            this.mapaPedidos = (Map<Integer, Pedido>) dadosRecebidos;
        } else {
            this.mapaPedidos = new HashMap<>();
        }
    }

    private int gerarProximoID() {
        if (mapaPedidos.isEmpty()) {
            return 1;
        }

        int maiorID = 0;

        for (Integer idExistente : mapaPedidos.keySet()) {
            if (idExistente > maiorID) {
                maiorID = idExistente;
            }
        }
        return maiorID + 1;
    }

    public void cadastrarPedido(Cliente clienteDaVez, List<ItemPedido> itensCarrinhos) throws IllegalArgumentException {
        if(clienteDaVez == null) {
            throw new IllegalArgumentException("ERRO: O cliente não pode ser nulo para gerar um pedido.");
        }

        if(itensCarrinhos == null || itensCarrinhos.isEmpty()) {
            throw new IllegalArgumentException("ERRO: O carrinho não pode estar vazio.");
        }
        int idNovo = gerarProximoID();
        LocalDate dataDeHoje = LocalDate.now();

        Pedido pedido = new Pedido(clienteDaVez, dataDeHoje, idNovo);

        for(ItemPedido itemAtual : itensCarrinhos) {
            pedido.adicionarItem(itemAtual);
        }

        mapaPedidos.put(idNovo, pedido);
        arquivoUtil.salvarDados(this.mapaPedidos, "pedidos.dat");
        LoggerService.log("INFO", "Pedido #" + idNovo + " registrado com sucesso.");
    }

    public Map<Integer, Pedido> listarPedidos() {
        LoggerService.log("INFO", "Listagem dos Pedidos executada.");
        return this.mapaPedidos;
    }

    public Pedido buscarPedidoEspecifico(int idBuscado) throws RegistroNaoEncontradoException {
        Pedido pedidoEncontrado = mapaPedidos.get(idBuscado);
        if(pedidoEncontrado == null) {
            throw new RegistroNaoEncontradoException("ERRO: O pedido de ID " + idBuscado + " não foi encontrado no arquivo.");
        }

        LoggerService.log("INFO", "Consulta executada com sucesso - Pedido: #" + pedidoEncontrado.getIdPedido() + " localizado.");
        return pedidoEncontrado;
    }
}
