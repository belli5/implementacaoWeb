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
import com.exemple.backend.infraestrutura.jparepositorys.PedidoJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
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

    private Cliente clienteDominio;
    private Prestador prestadorDominio;
    private Servico servicoDominio;
    private Pedido pedidoDominioParaSalvar;

    private ClienteJpa clienteJpaPersistido;
    private PrestadorJpa prestadorJpaPersistido;
    private ServicoJpa servicoJpaPersistido;

    @BeforeEach
    void setUp() {
        pedidoJpaRepository.deleteAll(); // Limpa antes de cada teste.

        Endereco endereco = new Endereco("Rua PedImpl", "BPI", "CPI", "PI");
        clienteDominio = new Cliente(null, "Cli PedImpl", "sC", "cpi@e.com", "tc", endereco);
        prestadorDominio = new Prestador(null, "Pre PedImpl", "sP", "ppi@e.com", "tp", endereco);
        servicoDominio = new Servico("Serv PedImpl", "CATPI", "DescPI");

        // Persiste as entidades JPA dependentes para que o Pedido possa referenciá-las
        EnderecoJpa enderecoJpa = new EnderecoJpa(endereco.getRua(), endereco.getBairro(), endereco.getCidade(), endereco.getEstado());

        ClienteJpa cJpa = new ClienteJpa(clienteDominio.getNome(), clienteDominio.getSenha(), clienteDominio.getEmail(), clienteDominio.getTelefone(), enderecoJpa);
        clienteJpaPersistido = entityManager.persistFlushFind(cJpa);

        PrestadorJpa pJpa = new PrestadorJpa(prestadorDominio.getNome(), prestadorDominio.getSenha(), prestadorDominio.getEmail(), prestadorDominio.getTelefone(), enderecoJpa);
        prestadorJpaPersistido = entityManager.persistFlushFind(pJpa);

        ServicoJpa sJpa = new ServicoJpa(servicoDominio.getNome(), servicoDominio.getCategoria(), servicoDominio.getDescricao());
        // ServicoJpa nome é PK, não gerado pelo entityManager.persist, mas o find funcionará se existir.
        // Para garantir, podemos buscar ou salvar pelo JpaRepository de Servico se necessário,
        // mas aqui vamos assumir que ele existe ou será criado.
        servicoJpaPersistido = entityManager.persistFlushFind(sJpa);

        // Recria os objetos de domínio com os IDs persistidos para o pedidoDominioParaSalvar
        clienteDominio = new Cliente(clienteJpaPersistido.getId(), clienteDominio.getNome(), clienteDominio.getSenha(), clienteDominio.getEmail(), clienteDominio.getTelefone(), endereco);
        prestadorDominio = new Prestador(prestadorJpaPersistido.getId(), prestadorDominio.getNome(), prestadorDominio.getSenha(), prestadorDominio.getEmail(), prestadorDominio.getTelefone(), endereco);
        // servicoDominio já tem nome, que é o ID.

        pedidoDominioParaSalvar = new Pedido(null, LocalDate.now(), servicoDominio, prestadorDominio, clienteDominio, "PENDENTE_IMPL");
    }

    @Test
    void deveSalvarPedidoERetornarComId() {
        Pedido salvo = pedidoRepositoryImpl.save(pedidoDominioParaSalvar);
        assertNotNull(salvo);
        assertNotNull(salvo.getId());
        assertEquals("PENDENTE_IMPL", salvo.getStatus());
        assertEquals(clienteDominio.getId(), salvo.getCliente().getId());
        assertEquals(prestadorDominio.getId(), salvo.getPrestador().getId());
        assertEquals(servicoDominio.getNome(), salvo.getServico().getNome());

        assertTrue(pedidoJpaRepository.existsById(salvo.getId()));
    }

    @Test
    void deveEncontrarPedidoPorId() {
        Pedido salvo = pedidoRepositoryImpl.save(pedidoDominioParaSalvar); // Salva e obtém ID
        Optional<Pedido> encontrado = pedidoRepositoryImpl.findById(salvo.getId());
        assertTrue(encontrado.isPresent());
        assertEquals(salvo.getStatus(), encontrado.get().getStatus());
    }

    @Test
    void deveEncontrarPedidosPorPrestadorId() {
        pedidoRepositoryImpl.save(pedidoDominioParaSalvar); // Pedido 1 com prestadorDominio

        // Pedido 2 com mesmo prestadorDominio
        Pedido pedido2Dominio = new Pedido(null, LocalDate.now().minusDays(1), servicoDominio, prestadorDominio, clienteDominio, "CONCLUIDO_IMPL");
        pedidoRepositoryImpl.save(pedido2Dominio);

        List<Pedido> encontrados = pedidoRepositoryImpl.findByPrestadorId(prestadorDominio.getId());
        assertEquals(2, encontrados.size());
        assertTrue(encontrados.stream().allMatch(p -> p.getPrestador().getId().equals(prestadorDominio.getId())));
    }

    @Test
    void deveEncontrarPedidosPorClienteId() {
        pedidoRepositoryImpl.save(pedidoDominioParaSalvar); // Pedido 1 com clienteDominio

        List<Pedido> encontrados = pedidoRepositoryImpl.findByClienteId(clienteDominio.getId());
        assertEquals(1, encontrados.size());
        assertEquals(clienteDominio.getId(), encontrados.get(0).getCliente().getId());
    }

    @Test
    void deveAtualizarPedido() {
        Pedido salvoInicial = pedidoRepositoryImpl.save(pedidoDominioParaSalvar);
        Pedido paraAtualizar = new Pedido(
                salvoInicial.getId(),
                salvoInicial.getData(),
                salvoInicial.getServico(),
                salvoInicial.getPrestador(),
                salvoInicial.getCliente(),
                "ATUALIZADO_IMPL"
        );
        Pedido atualizado = pedidoRepositoryImpl.update(paraAtualizar);
        assertEquals("ATUALIZADO_IMPL", atualizado.getStatus());

        Optional<PedidoJpa> verificado = pedidoJpaRepository.findById(salvoInicial.getId());
        assertTrue(verificado.isPresent());
        assertEquals("ATUALIZADO_IMPL", verificado.get().getStatus());
    }

    @Test
    void deveDeletarPedido() {
        Pedido salvo = pedidoRepositoryImpl.save(pedidoDominioParaSalvar);
        int id = salvo.getId();
        assertTrue(pedidoJpaRepository.existsById(id));
        pedidoRepositoryImpl.delete(id);
        assertFalse(pedidoJpaRepository.existsById(id));
    }
}