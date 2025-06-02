package com.exemple.backend.dominio.services;

import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Pedido;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.Servico;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.dominio.repositorys.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepositoryMock;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedido1;
    private Cliente cliente;
    private Prestador prestador;
    private Servico servico;
    private Endereco endereco;


    @BeforeEach
    void setUp() {
        endereco = new Endereco("Rua Ped", "Bairro Ped", "Cidade Ped", "PE");
        cliente = new Cliente(1, "Cliente Ped", "s", "c@ped.com", "t", endereco);
        prestador = new Prestador(1, "Prestador Ped", "s", "p@ped.com", "t", endereco);
        servico = new Servico("Serv Ped", "CATPED", "Desc Ped");
        pedido1 = new Pedido(1, LocalDate.now(), servico, prestador, cliente, "PENDENTE");
    }

    @Test
    void deveBuscarPedidoPorIdExistente() {
        when(pedidoRepositoryMock.findById(1)).thenReturn(Optional.of(pedido1));
        Optional<Pedido> resultado = pedidoService.buscarPorId(1);
        assertTrue(resultado.isPresent());
        assertEquals(pedido1.getStatus(), resultado.get().getStatus());
        verify(pedidoRepositoryMock, times(1)).findById(1);
    }

    @Test
    void deveSalvarPedidoComSucesso() {
        when(pedidoRepositoryMock.save(any(Pedido.class))).thenReturn(pedido1);
        Pedido salvo = pedidoService.salvar(pedido1);
        assertNotNull(salvo);
        assertEquals(pedido1.getStatus(), salvo.getStatus());
        verify(pedidoRepositoryMock, times(1)).save(pedido1);
    }

    @Test
    void deveAtualizarPedidoComSucesso() {
        // O método atualizar do service chama update do repositório
        // que por sua vez (na impl) chama save.
        Pedido pedidoAtualizadoInfo = new Pedido(1, LocalDate.now(), servico, prestador, cliente, "CONCLUIDO");
        when(pedidoRepositoryMock.update(any(Pedido.class))).thenReturn(pedidoAtualizadoInfo);

        Pedido atualizado = pedidoService.atualizar(pedidoAtualizadoInfo);

        assertNotNull(atualizado);
        assertEquals("CONCLUIDO", atualizado.getStatus());
        verify(pedidoRepositoryMock, times(1)).update(pedidoAtualizadoInfo);
    }

    @Test
    void deveDeletarPedidoComSucesso() {
        doNothing().when(pedidoRepositoryMock).delete(1);
        pedidoService.deletar(1);
        verify(pedidoRepositoryMock, times(1)).delete(1);
    }

    @Test
    void deveBuscarPedidosPorPrestadorId() {
        List<Pedido> lista = Arrays.asList(pedido1);
        when(pedidoRepositoryMock.findByPrestadorId(1)).thenReturn(lista);

        List<Pedido> resultado = pedidoService.buscarPorPrestador(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(pedido1.getId(), resultado.get(0).getId());
        verify(pedidoRepositoryMock, times(1)).findByPrestadorId(1);
    }

    @Test
    void deveBuscarPedidosPorClienteId() {
        List<Pedido> lista = Arrays.asList(pedido1);
        when(pedidoRepositoryMock.findByClienteId(1)).thenReturn(lista);

        List<Pedido> resultado = pedidoService.buscarPorCliente(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(pedido1.getId(), resultado.get(0).getId());
        verify(pedidoRepositoryMock, times(1)).findByClienteId(1);
    }

    @Test
    void deveBuscarTodosOsPedidos() {
        Pedido pedido2 = new Pedido(2, LocalDate.now().minusDays(1), servico, prestador, cliente, "CANCELADO");
        List<Pedido> lista = Arrays.asList(pedido1, pedido2);
        when(pedidoRepositoryMock.findAll()).thenReturn(lista);

        List<Pedido> resultado = pedidoService.buscarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(pedidoRepositoryMock, times(1)).findAll();
    }
}