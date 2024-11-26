package com.lucas.pedido.service;

import com.lucas.pedido.model.Produto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@FeignClient(name = "estoque", url = "http://localhost:8080")
public interface EstoqueClient {

    @PostMapping("/estoque/verificar")
    boolean verificarDisponibilidade(@RequestBody List<Produto> produtos);

    @PostMapping("/estoque/preco")
    double getPreco(@RequestBody Long produtoId);

    @GetMapping("/verificar/{id}")
    boolean verificarEstoque(@PathVariable("id") Long id);
}