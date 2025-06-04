package com.exemple.backend.infraestrutura.jparepositorysimpl;

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
import com.exemple.backend.infraestrutura.jparepositorys.ClienteJpaRepository;
import com.exemple.backend.infraestrutura.jparepositorys.PrestadorJpaRepository;
import com.exemple.backend.infraestrutura.jparepositorys.ServicoJpaRepository;
import com.exemple.backend.infraestrutura.jparepositorys.PedidoJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(PedidoRepositoryImpl.class)
class PedidoRepositoryImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PedidoRepositoryImpl pedidoRepositoryImpl;

    @Autowired
    private PedidoJpaRepository pedidoJpaRepository;
    @Autowired
    private ClienteJpaRepository clienteJpaRepository;
    @Autowired
    private PrestadorJpaRepository prestadorJpaRepository;
    @Autowired
    private ServicoJpaRepository servicoJpaRepository;

    private Cliente clienteDominio;
    private Prestador prestadorDominio;
    private Servico servicoDominio;
    private Pedido pedidoDominioParaSalvar;

    private ClienteJpa clienteJpaPersistido;
    private PrestadorJpa prestadorJpaPersistido;
    private ServicoJpa servicoJpaPersistido;

    @BeforeEach
    void setUp() {
        pedidoJpaRepository.deleteAll();
        clienteJpaRepository.deleteAll();
        prestadorJpaRepository.deleteAll();
        servicoJpaRepository.deleteAll();
        entityManager.flush();
        entityManager.clear();

        Endereco enderecoComum = new Endereco("Rua Teste Pedido", "Bairro Pedido", "Cidade Pedido", "PD");
        EnderecoJpa enderecoJpaComum = new EnderecoJpa(enderecoComum.getRua(), enderecoComum.getBairro(), enderecoComum.getCidade(), enderecoComum.getEstado());

        ClienteJpa cJpa = new ClienteJpa();
        cJpa.setNome("Cliente Pedido");
        cJpa.setSenha("senhaCliPed");
        cJpa.setEmail("cli.ped@example.com");
        cJpa.setTelefone("123450");
        cJpa.setEndereco(enderecoJpaComum);
        clienteJpaPersistido = entityManager.persistFlushFind(cJpa);

        PrestadorJpa pJpa = new PrestadorJpa();
        pJpa.setNome("Prestador Pedido");
        pJpa.setSenha("senhaPrePed");
        pJpa.setEmail("pre.ped@example.com");
        pJpa.setTelefone("678900");
        pJpa.setEndereco(enderecoJpaComum);
        prestadorJpaPersistido = entityManager.persistFlushFind(pJpa);

        ServicoJpa sJpa = new ServicoJpa("Serviço de Pedido", "TESTE_PED", "Descrição do serviço de pedido");
        servicoJpaPersistido = entityManager.persistFlushFind(sJpa);

        clienteDominio = new Cliente(
                clienteJpaPersistido.getId(),
                clienteJpaPersistido.getNome(),
                clienteJpaPersistido.getSenha(),
                clienteJpaPersistido.getEmail(),
                clienteJpaPersistido.getTelefone(),
                enderecoComum
        );

        prestadorDominio = new Prestador(
                prestadorJpaPersistido.getId(),
                prestadorJpaPersistido.getNome(),
                prestadorJpaPersistido.getSenha(),
                prestadorJpaPersistido.getEmail(),
                prestadorJpaPersistido.getTelefone(),
                enderecoComum
        );

        servicoDominio = new Servico(
                servicoJpaPersistido.getNome(),
                servicoJpaPersistido.getCategoria(),
                servicoJpaPersistido.getDescricao()
        );

        pedidoDominioParaSalvar = new Pedido(
                0,
                LocalDate.now(),
                servicoDominio,
                prestadorDominio,
                clienteDominio,
                "PENDENTE_IMPL"
        );
    }

    @Test
    void deveSalvarPedidoERetornarComId() {
        Pedido salvo = pedidoRepositoryImpl.save(pedidoDominioParaSalvar);
        assertNotNull(salvo);
        assertNotNull(salvo.getId());
        assertTrue(salvo.getId() > 0);
        assertEquals("PENDENTE_IMPL", salvo.getStatus());
        assertEquals(clienteDominio.getId(), salvo.getCliente().getId());
        assertEquals(prestadorDominio.getId(), salvo.getPrestador().getId());
        assertEquals(servicoDominio.getNome(), salvo.getServico().getNome());
        assertEquals(pedidoDominioParaSalvar.getData(), salvo.getData());

        assertTrue(pedidoJpaRepository.existsById(salvo.getId()));
    }

    @Test
    void deveEncontrarPedidoPorId() {
        Pedido pedidoSalvoSetup = pedidoRepositoryImpl.save(pedidoDominioParaSalvar);
        entityManager.flush();
        entityManager.clear();

        Optional<Pedido> encontrado = pedidoRepositoryImpl.findById(pedidoSalvoSetup.getId());
        assertTrue(encontrado.isPresent());
        Pedido pedidoEncontrado = encontrado.get();
        assertEquals(pedidoSalvoSetup.getStatus(), pedidoEncontrado.getStatus());
        assertEquals(pedidoSalvoSetup.getCliente().getId(), pedidoEncontrado.getCliente().getId());
        assertEquals(pedidoSalvoSetup.getData(), pedidoEncontrado.getData());
    }

    @Test
    void deveEncontrarPedidosPorPrestadorId() {
        pedidoRepositoryImpl.save(pedidoDominioParaSalvar);

        Pedido pedido2Dominio = new Pedido(
                0,
                LocalDate.now().minusDays(1),
                servicoDominio,
                prestadorDominio,
                clienteDominio,
                "CONCLUIDO_IMPL"
        );
        pedidoRepositoryImpl.save(pedido2Dominio);
        entityManager.flush();
        entityManager.clear();

        List<Pedido> encontrados = pedidoRepositoryImpl.findByPrestadorId(prestadorDominio.getId());
        assertNotNull(encontrados);
        assertEquals(2, encontrados.size());
        assertTrue(encontrados.stream().allMatch(p -> p.getPrestador().getId().equals(prestadorDominio.getId())));
    }

    @Test
    void deveEncontrarPedidosPorClienteId() {
        pedidoRepositoryImpl.save(pedidoDominioParaSalvar);
        entityManager.flush();
        entityManager.clear();

        List<Pedido> encontrados = pedidoRepositoryImpl.findByClienteId(clienteDominio.getId());
        assertNotNull(encontrados);
        assertEquals(1, encontrados.size());
        assertEquals(clienteDominio.getId(), encontrados.get(0).getCliente().getId());
    }

    @Test
    void deveAtualizarPedido() {
        Pedido salvoInicial = pedidoRepositoryImpl.save(pedidoDominioParaSalvar);
        entityManager.flush();
        entityManager.clear();

        Pedido paraAtualizar = new Pedido(
                salvoInicial.getId(),
                salvoInicial.getData(),
                salvoInicial.getServico(),
                salvoInicial.getPrestador(),
                salvoInicial.getCliente(),
                "ATUALIZADO_IMPL"
        );
        Pedido atualizado = pedidoRepositoryImpl.update(paraAtualizar);
        assertNotNull(atualizado);
        assertEquals("ATUALIZADO_IMPL", atualizado.getStatus());
        assertEquals(salvoInicial.getId(), atualizado.getId());

        Optional<PedidoJpa> verificado = pedidoJpaRepository.findById(salvoInicial.getId());
        assertTrue(verificado.isPresent());
        assertEquals("ATUALIZADO_IMPL", verificado.get().getStatus());
    }

    @Test
    void deveDeletarPedido() {
        Pedido salvo = pedidoRepositoryImpl.save(pedidoDominioParaSalvar);
        int id = salvo.getId();
        entityManager.flush();
        entityManager.clear();

        assertTrue(pedidoJpaRepository.existsById(id));
        pedidoRepositoryImpl.delete(id);
        entityManager.flush();
        entityManager.clear();

        assertFalse(pedidoJpaRepository.existsById(id));
    }
}