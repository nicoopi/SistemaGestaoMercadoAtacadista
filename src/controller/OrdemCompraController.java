package controller;
import exceptions.RegistroNaoEncontradoException;
import model.Fornecedor;
import model.OrdemCompra;
import model.Produto;
import util.ArquivoUtil;
import util.LoggerService;

import java.util.ArrayList;
import java.util.List;

public class OrdemCompraController {
    private List<OrdemCompra> listaOrdensCompra;
    private final ArquivoUtil arquivoUtil = new ArquivoUtil();
    private EstoqueController estoqueController;
    private FornecedorController fornecedorController;
    private ProdutoController produtoController;

    public OrdemCompraController(EstoqueController estoqueController, FornecedorController fornecedorController, ProdutoController produtoController) {
        this.estoqueController = estoqueController;
        this.fornecedorController = fornecedorController;
        this.produtoController = produtoController;

        Object dadosRecebidos = arquivoUtil.lerDados("ordens_compra.dat");
        if (dadosRecebidos != null) {
            this.listaOrdensCompra = (List<OrdemCompra>) dadosRecebidos;
        } else {
            this.listaOrdensCompra = new ArrayList<>();
        }
    }

    public void registrarOrdemCompra(String cnpjFornecedor, int idProduto, int quantidade, double custoUnitario) throws RegistroNaoEncontradoException, IllegalArgumentException {
        Fornecedor fornecedor = fornecedorController.buscarFornecedorPorCnpj(cnpjFornecedor);
        Produto produto = produtoController.buscarProdutoPorId(idProduto);

        OrdemCompra novaOrdem = new OrdemCompra(fornecedor, produto, quantidade, custoUnitario);

        listaOrdensCompra.add(novaOrdem);

        estoqueController.adicionarQuantidadeEmEstoque(idProduto, quantidade);

        arquivoUtil.salvarDados(this.listaOrdensCompra, "ordens_compra.dat");
        LoggerService.log("INFO", "Ordem de compra registrada - Fornecedor: " + fornecedor.getCnpj() + " | Produto ID: " + idProduto + " | Quantidade: " + quantidade);
    }

    public List<OrdemCompra> listarOrdensCompra() {
        LoggerService.log("INFO", "Listagem de ordens de compra executada.");
        return listaOrdensCompra;
    }
}