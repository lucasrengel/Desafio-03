package com.lucas.pedido.controller;

import com.lucas.pedido.model.Pedido;
import com.lucas.pedido.model.Produto;
import com.lucas.pedido.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(
            summary = "Cria um novo pedido",
            description = "Este endpoint permite criar um novo pedido com os produtos e informações fornecidas."
    )
    @PostMapping
    public Pedido criarPedido(@RequestParam Long clienteId, @RequestBody List<Produto> produtos) {
        return pedidoService.criarPedido(clienteId, produtos);
    }

    @Operation(
            summary = "Recupera detalhes de um pedido pelo ID",
            description = "Este endpoint retorna as informações detalhadas de um pedido específico, identificado pelo ID."
    )
    @GetMapping("/{id}")
    public Pedido obterPedido(@PathVariable Long id) {
        return pedidoService.getPedidoById(id);
    }
}