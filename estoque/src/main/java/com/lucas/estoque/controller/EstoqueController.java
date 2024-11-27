package com.lucas.estoque.controller;

import com.lucas.estoque.model.Produto;
import com.lucas.estoque.service.EstoqueService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @Operation(
            summary = "Verifica a disponibilidade de um produto no estoque",
            description = "Este endpoint verifica se há quantidade suficiente de um produto específico no estoque."
    )
    @PostMapping("/verificar")
    public boolean verificarDisponibilidade(@RequestBody List<Produto> produtos) {
        return estoqueService.verificarDisponibilidade(produtos);
    }

    @Operation(
            summary = "Atualiza a quantidade de produtos no estoque",
            description = "Este endpoint atualiza o estoque com a quantidade fornecida para um produto específico."
    )
    @PostMapping("/atualizar")
    public void atualizarEstoque(@RequestBody List<Produto> produtos) {
        estoqueService.atualizarEstoque(produtos);
    }
}
