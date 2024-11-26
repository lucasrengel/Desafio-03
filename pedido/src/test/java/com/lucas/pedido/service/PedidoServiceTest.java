package com.lucas.pedido.service;

import com.lucas.pedido.model.Pedido;
import com.lucas.pedido.model.Produto;
import com.lucas.pedido.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private EstoqueClient estoqueClient;

    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriarPedido() {
        Long clienteId = 1L;
        Produto produto = new Produto();
        produto.setNome("Produto 1");
        produto.setQuantidade(10);
        produto.setPreco(100.0);
        List<Produto> produtos = Collections.singletonList(produto);
        Pedido pedido = new Pedido();
        pedido.setClienteId(clienteId);
        pedido.setProdutos(produtos);
        pedido.setTotal(1000.0); //

        when(estoqueClient.verificarDisponibilidade(produtos)).thenReturn(true);

        when(estoqueClient.getPreco(produto.getProdutoId())).thenReturn(100.0);

        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        Pedido pedidoCriado = pedidoService.criarPedido(clienteId, produtos);

        assertNotNull(pedidoCriado);
        assertEquals(clienteId, pedidoCriado.getClienteId());
        assertEquals(1000.0, pedidoCriado.getTotal(), 0.001);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }
}
