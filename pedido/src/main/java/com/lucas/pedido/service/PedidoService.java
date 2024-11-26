package com.lucas.pedido.service;

import com.lucas.pedido.model.Pedido;
import com.lucas.pedido.model.Produto;
import com.lucas.pedido.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final EstoqueClient estoqueClient;

    public PedidoService(PedidoRepository pedidoRepository, EstoqueClient estoqueClient) {
        this.pedidoRepository = pedidoRepository;
        this.estoqueClient = estoqueClient;
    }


    public Pedido getPedidoById(Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.orElseThrow(() -> new RuntimeException("Pedido não encontrado com o ID: " + id));
    }

    public Pedido criarPedido(Long clienteId, List<Produto> produtos) {
        boolean estoqueDisponivel = estoqueClient.verificarDisponibilidade(produtos);

        if (!estoqueDisponivel) {
            throw new IllegalArgumentException("Estoque insuficiente para os produtos selecionados.");
        }

        double total = produtos.stream()
                .mapToDouble(produto -> produto.getQuantidade() * estoqueClient.getPreco(produto.getProdutoId()))
                .sum();

        Pedido pedido = new Pedido();
        pedido.setClienteId(clienteId);
        pedido.setProdutos(produtos);
        pedido.setTotal(total);

        return pedidoRepository.save(pedido);
    }
}