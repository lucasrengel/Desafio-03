package com.lucas.cliente.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "pedido", url = "http://localhost:8080")
public interface PedidoClient {

    @GetMapping("/pedidos/cliente")
    Object obterPedidosPorCliente(@RequestParam Long clienteId);
}
