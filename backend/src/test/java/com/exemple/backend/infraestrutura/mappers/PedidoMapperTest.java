package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Pedido;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.Servico;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.infraestrutura.jpamodels.ClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.PedidoJpa;
import com.exemple.backend.infraestrutura.jpamodels.PrestadorJpa;
import com.exemple.backend.infraestrutura.jpamodels.ServicoJpa;
import com.exemple.backend.infraestrutura.jpamodels.compartilhados.EnderecoJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PedidoMapperTest {

    private Cliente clienteDominio;
    private Prestador prestadorDominio;
    private Servico servicoDominio;
    private Pedido pedidoDominio;
    private Endereco enderecoComum;

    private ClienteJpa clienteJpa;
    private PrestadorJpa prestadorJpa;
    private ServicoJpa servicoJpa;
    private PedidoJpa pedidoJpaInstancia;
    private EnderecoJpa enderecoJpaComum;


    @BeforeEach
    void setUp() {
        enderecoComum = new Endereco("Rua Comum Dom", "Bairro CD", "Cidade CD", "CD");
        enderecoJpaComum = new EnderecoJpa("Rua Comum Jpa", "Bairro CJ", "Cidade CJ", "CJ");

        clienteDominio = new Cliente(1, "Cliente PedidoDom", "sCD", "cped@dom.com", "telCD", enderecoComum);
        prestadorDominio = new Prestador(2, "Prestador PedidoDom", "sPD", "pped@dom.com", "telPD", enderecoComum);
        servicoDominio = new Servico("Servico PedidoDom", "CAT_PED_DOM", "Desc Pedido Dom");
        pedidoDominio = new Pedido(10, LocalDate.of(2025, 7, 1), servicoDominio, prestadorDominio, clienteDominio, "PENDENTE_DOM");

        clienteJpa = new ClienteJpa("Cliente PedidoJpa", "sCJ", "cped@jpa.com", "telCJ", enderecoJpaComum);
        clienteJpa.setId(11);

        prestadorJpa = new PrestadorJpa("Prestador PedidoJpa", "sPJ", "pped@jpa.com", "telPJ", enderecoJpaComum);
        prestadorJpa.setId(22);

        servicoJpa = new ServicoJpa("Servico PedidoJpa", "CAT_PED_JPA", "Desc Pedido Jpa");

        pedidoJpaInstancia = new PedidoJpa();
        pedidoJpaInstancia.setId(100);
        pedidoJpaInstancia.setData(LocalDate.of(2025, 8, 1));
        pedidoJpaInstancia.setServico(servicoJpa);
        pedidoJpaInstancia.setPrestador(prestadorJpa);
        pedidoJpaInstancia.setCliente(clienteJpa);
        pedidoJpaInstancia.setStatus("PENDENTE_JPA");
    }

    @Test
    void deveMapearPedidoDominioParaPedidoJpaCorretamente() {
        PedidoJpa pedidoJpaConvertido = PedidoMapper.toPedidoJpa(pedidoDominio);

        assertNotNull(pedidoJpaConvertido);
        // Asserção do ID removida, pois o mapper não o define.
        assertEquals(pedidoDominio.getData(), pedidoJpaConvertido.getData());
        assertEquals(pedidoDominio.getStatus(), pedidoJpaConvertido.getStatus());

        assertNotNull(pedidoJpaConvertido.getServico());
        assertEquals(servicoDominio.getNome(), pedidoJpaConvertido.getServico().getNome());
        assertEquals(servicoDominio.getCategoria(), pedidoJpaConvertido.getServico().getCategoria());

        assertNotNull(pedidoJpaConvertido.getCliente());
        assertEquals(clienteDominio.getId(), pedidoJpaConvertido.getCliente().getId());
        assertEquals(clienteDominio.getNome(), pedidoJpaConvertido.getCliente().getNome());
        assertNotNull(pedidoJpaConvertido.getCliente().getEndereco());
        assertEquals(enderecoComum.getRua(), pedidoJpaConvertido.getCliente().getEndereco().getRua());


        assertNotNull(pedidoJpaConvertido.getPrestador());
        assertEquals(prestadorDominio.getId(), pedidoJpaConvertido.getPrestador().getId());
        assertEquals(prestadorDominio.getNome(), pedidoJpaConvertido.getPrestador().getNome());
        assertNotNull(pedidoJpaConvertido.getPrestador().getEndereco());
        assertEquals(enderecoComum.getRua(), pedidoJpaConvertido.getPrestador().getEndereco().getRua());
    }

    @Test
    void deveMapearPedidoJpaParaPedidoDominioCorretamente() {
        Pedido pedidoDominioConvertido = PedidoMapper.toPedido(pedidoJpaInstancia);

        assertNotNull(pedidoDominioConvertido);
        assertEquals(pedidoJpaInstancia.getId(), pedidoDominioConvertido.getId());
        assertEquals(pedidoJpaInstancia.getData(), pedidoDominioConvertido.getData());
        assertEquals(pedidoJpaInstancia.getStatus(), pedidoDominioConvertido.getStatus());

        assertNotNull(pedidoDominioConvertido.getServico());
        assertEquals(servicoJpa.getNome(), pedidoDominioConvertido.getServico().getNome());
        assertEquals(servicoJpa.getCategoria(), pedidoDominioConvertido.getServico().getCategoria());

        assertNotNull(pedidoDominioConvertido.getCliente());
        assertEquals(clienteJpa.getId(), pedidoDominioConvertido.getCliente().getId());
        assertEquals(clienteJpa.getNome(), pedidoDominioConvertido.getCliente().getNome());
        assertNotNull(pedidoDominioConvertido.getCliente().getEndereco());
        assertEquals(enderecoJpaComum.getRua(), pedidoDominioConvertido.getCliente().getEndereco().getRua());

        assertNotNull(pedidoDominioConvertido.getPrestador());
        assertEquals(prestadorJpa.getId(), pedidoDominioConvertido.getPrestador().getId());
        assertEquals(prestadorJpa.getNome(), pedidoDominioConvertido.getPrestador().getNome());
        assertNotNull(pedidoDominioConvertido.getPrestador().getEndereco());
        assertEquals(enderecoJpaComum.getRua(), pedidoDominioConvertido.getPrestador().getEndereco().getRua());
    }

    @Test
    void deveLancarExcecaoAoMapearPedidoDominioNuloParaJpa() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            PedidoMapper.toPedidoJpa(null);
        });
        assertEquals("Pedido não pode ser nulo", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoMapearPedidoJpaNuloParaDominio() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            PedidoMapper.toPedido(null);
        });
        assertEquals("PedidoJpa não pode ser nulo", exception.getMessage());
    }
}