package com.lucas.cliente.service;

import com.lucas.cliente.model.Cliente;
import com.lucas.cliente.repository.ClienteRepository;
import com.lucas.cliente.service.PedidoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PedidoClient pedidoClient;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Lucas");
        cliente.setEmail("lucas@example.com");
        cliente.setTelefone("123456789");
    }

    @Test
    public void testCadastrarCliente() {
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente clienteSalvo = clienteService.cadastrarCliente(cliente);

        assertNotNull(clienteSalvo);
        assertEquals(cliente.getNome(), clienteSalvo.getNome());
        assertEquals(cliente.getEmail(), clienteSalvo.getEmail());
        assertEquals(cliente.getTelefone(), clienteSalvo.getTelefone());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    public void testObterCliente() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Optional<Cliente> clienteEncontrado = clienteService.obterCliente(1L);

        assertTrue(clienteEncontrado.isPresent());
        assertEquals(cliente.getId(), clienteEncontrado.get().getId());
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    public void testObterClienteNaoEncontrado() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Cliente> clienteEncontrado = clienteService.obterCliente(1L);

        assertFalse(clienteEncontrado.isPresent());
        verify(clienteRepository, times(1)).findById(1L);
    }
}
