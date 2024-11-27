package com.lucas.estoque.service;

import com.lucas.estoque.model.Produto;
import com.lucas.estoque.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstoqueService {

    private final ProdutoRepository produtoRepository;

    public EstoqueService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public boolean verificarDisponibilidade(List<Produto> produtos) {
        for (Produto pedido : produtos) {
            Produto produto = produtoRepository.findById(pedido.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + pedido.getId()));

            if (produto.getQuantidade() < pedido.getQuantidade()) {
                return false;
            }
        }
        return true;
    }

    public void atualizarEstoque(List<Produto> produtos) {
        for (Produto pedido : produtos) {
            Produto produto = produtoRepository.findById(pedido.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + pedido.getId()));

            produto.setQuantidade(produto.getQuantidade() - pedido.getQuantidade());
            produtoRepository.save(produto);
        }
    }
}