package com.lucas.cliente.controller;

import com.lucas.cliente.model.Cliente;
import com.lucas.cliente.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Cliente", description = "Operações relacionadas a clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Cria um novo cliente", description = "Este endpoint cria um novo cliente com os dados fornecidos.")
    @PostMapping
    public Cliente cadastrarCliente(@RequestBody Cliente cliente) {
        return clienteService.cadastrarCliente(cliente);
    }

    @Operation(summary = "Obtém cliente pelo ID", description = "Recupera os detalhes de um cliente específico pelo ID.")
    @GetMapping("/{id}")
    public Optional<Cliente> obterCliente(@PathVariable Long id) {
        return clienteService.obterCliente(id);
    }

    @Operation(summary = "Obtém histórico de pedidos", description = "Recupera o histórico de pedidos realizados por um cliente.")
    @GetMapping("/{id}/historico")
    public Object obterHistoricoPedidos(@PathVariable Long id) {
        return clienteService.obterHistoricoPedidos(id);
    }
}
