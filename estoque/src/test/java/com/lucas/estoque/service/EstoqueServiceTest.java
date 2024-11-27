package com.lucas.estoque.service;

import com.lucas.estoque.model.Produto;
import com.lucas.estoque.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EstoqueServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private EstoqueService estoqueService;

    private Produto produto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto A");
        produto.setQuantidade(10);
    }

    @Test
    public void testVerificarDisponibilidadeProdutoComQuantidadeSuficiente() {
        Produto pedido = new Produto();
        pedido.setId(1L);
        pedido.setQuantidade(5);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        boolean disponibilidade = estoqueService.verificarDisponibilidade(Arrays.asList(pedido));

        assertTrue(disponibilidade);
        verify(produtoRepository, times(1)).findById(1L);
    }

    @Test
    public void testVerificarDisponibilidadeProdutoSemQuantidadeSuficiente() {
        Produto pedido = new Produto();
        pedido.setId(1L);
        pedido.setQuantidade(15);  // Quantidade maior que a disponível no estoque

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        boolean disponibilidade = estoqueService.verificarDisponibilidade(Arrays.asList(pedido));

        assertFalse(disponibilidade);
        verify(produtoRepository, times(1)).findById(1L);
    }

    @Test
    public void testVerificarDisponibilidadeProdutoNaoEncontrado() {
        Produto pedido = new Produto();
        pedido.setId(1L);
        pedido.setQuantidade(5);

        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            estoqueService.verificarDisponibilidade(Arrays.asList(pedido));
        });

        assertEquals("Produto não encontrado: 1", exception.getMessage());
        verify(produtoRepository, times(1)).findById(1L);
    }

    @Test
    public void testAtualizarEstoque() {
        Produto pedido = new Produto();
        pedido.setId(1L);
        pedido.setQuantidade(5); // Pedindo 5 unidades

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(produto)).thenReturn(produto);

        estoqueService.atualizarEstoque(Arrays.asList(pedido));

        assertEquals(5, produto.getQuantidade());  // A quantidade após a atualização deve ser 5 (10 - 5)
        verify(produtoRepository, times(1)).findById(1L);
        verify(produtoRepository, times(1)).save(produto);
    }

    @Test
    public void testAtualizarEstoqueProdutoNaoEncontrado() {
        Produto pedido = new Produto();
        pedido.setId(1L);
        pedido.setQuantidade(5);

        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            estoqueService.atualizarEstoque(Arrays.asList(pedido));
        });

        assertEquals("Produto não encontrado: 1", exception.getMessage());
        verify(produtoRepository, times(1)).findById(1L);
        verify(produtoRepository, times(0)).save(any());
    }
}
