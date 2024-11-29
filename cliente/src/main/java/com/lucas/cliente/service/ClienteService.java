package com.lucas.cliente.service;

import com.lucas.cliente.model.Cliente;
import com.lucas.cliente.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PedidoClient pedidoClient;

    public ClienteService(ClienteRepository clienteRepository, PedidoClient pedidoClient) {
        this.clienteRepository = clienteRepository;
        this.pedidoClient = pedidoClient;
    }

    public Cliente cadastrarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> obterCliente(Long id) {
        return clienteRepository.findById(id);
    }

    public Object obterHistoricoPedidos(Long clienteId) {
        return pedidoClient.obterPedidosPorCliente(clienteId);
    }
}
