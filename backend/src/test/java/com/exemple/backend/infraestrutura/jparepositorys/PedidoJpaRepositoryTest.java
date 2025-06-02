package com.exemple.backend.infraestrutura.jparepositorys;

import com.exemple.backend.infraestrutura.jpamodels.ClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.PedidoJpa;
import com.exemple.backend.infraestrutura.jpamodels.PrestadorJpa;
import com.exemple.backend.infraestrutura.jpamodels.ServicoJpa;
import com.exemple.backend.infraestrutura.jpamodels.compartilhados.EnderecoJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PedidoJpaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PedidoJpaRepository pedidoJpaRepository;

    private ClienteJpa clienteJpa;
    private PrestadorJpa prestadorJpa;
    private ServicoJpa servicoJpa;
    private PedidoJpa pedidoJpa1;

    @BeforeEach
    void setUp() {
        pedidoJpaRepository.deleteAll();
        // É preciso persistir as entidades relacionadas antes de persistir PedidoJpa
        // ou configurar o PedidoJpa para persistir em cascata (o que não é o caso aqui).

        EnderecoJpa endereco = new EnderecoJpa("Rua PedTest", "Bairro PedT", "Cidade PedT", "PT");

        clienteJpa = new ClienteJpa();
        clienteJpa.setNome("Cliente Pedido Teste");
        clienteJpa.setSenha("senha");
        clienteJpa.setEmail("cliente.pedido@test.com");
        clienteJpa.setTelefone("123456");
        clienteJpa.setEndereco(endereco);
        clienteJpa = entityManager.persist(clienteJpa);

        prestadorJpa = new PrestadorJpa();
        prestadorJpa.setNome("Prestador Pedido Teste");
        prestadorJpa.setSenha("senhaP");
        prestadorJpa.setEmail("prestador.pedido@test.com");
        prestadorJpa.setTelefone("654321");
        prestadorJpa.setEndereco(endereco);
        prestadorJpa = entityManager.persist(prestadorJpa);

        servicoJpa = new ServicoJpa("Servico Pedido Teste", "PED_CAT", "Desc Pedido Teste");
        // ServicoJpa tem 'nome' como ID, então não é gerado.
        servicoJpa = entityManager.persist(servicoJpa);

        entityManager.flush(); // Garante que os IDs estão disponíveis

        pedidoJpa1 = new PedidoJpa();
        pedidoJpa1.setData(LocalDate.now());
        pedidoJpa1.setCliente(clienteJpa);
        pedidoJpa1.setPrestador(prestadorJpa);
        pedidoJpa1.setServico(servicoJpa);
        pedidoJpa1.setStatus("PENDENTE");
    }

    @Test
    void deveSalvarEEncontrarPedidoPorId() {
        PedidoJpa salvo = entityManager.persistAndFlush(pedidoJpa1);
        Optional<PedidoJpa> encontrado = pedidoJpaRepository.findById(salvo.getId());

        assertTrue(encontrado.isPresent());
        assertEquals(salvo.getStatus(), encontrado.get().getStatus());
        assertEquals(clienteJpa.getId(), encontrado.get().getCliente().getId());
        assertEquals(prestadorJpa.getId(), encontrado.get().getPrestador().getId());
        assertEquals(servicoJpa.getNome(), encontrado.get().getServico().getNome());
    }

    @Test
    void deveEncontrarPedidosPorPrestadorId() {
        entityManager.persistAndFlush(pedidoJpa1);

        // Criar outro pedido com o mesmo prestador
        PedidoJpa pedidoJpa2 = new PedidoJpa();
        pedidoJpa2.setData(LocalDate.now().minusDays(1));
        pedidoJpa2.setCliente(clienteJpa); // Pode ser outro cliente
        pedidoJpa2.setPrestador(prestadorJpa);
        pedidoJpa2.setServico(servicoJpa);
        pedidoJpa2.setStatus("CONCLUIDO");
        entityManager.persistAndFlush(pedidoJpa2);

        // Criar pedido com outro prestador para garantir que o filtro funciona
        PrestadorJpa outroPrestador = new PrestadorJpa();
        outroPrestador.setNome("Outro Prestador");
        outroPrestador.setSenha("senhaOP");
        outroPrestador.setEmail("outro.p@test.com");
        outroPrestador.setTelefone("111");
        outroPrestador.setEndereco(new EnderecoJpa());
        outroPrestador = entityManager.persistAndFlush(outroPrestador);

        PedidoJpa pedidoJpa3 = new PedidoJpa();
        pedidoJpa3.setData(LocalDate.now());
        pedidoJpa3.setCliente(clienteJpa);
        pedidoJpa3.setPrestador(outroPrestador);
        pedidoJpa3.setServico(servicoJpa);
        pedidoJpa3.setStatus("AGUARDANDO");
        entityManager.persistAndFlush(pedidoJpa3);


        List<PedidoJpa> encontrados = pedidoJpaRepository.findByPrestadorId(prestadorJpa.getId());
        assertEquals(2, encontrados.size());
        assertTrue(encontrados.stream().allMatch(p -> p.getPrestador().getId() == prestadorJpa.getId()));
    }

    @Test
    void deveEncontrarPedidosPorClienteId() {
        entityManager.persistAndFlush(pedidoJpa1);

        // Criar outro pedido com o mesmo cliente
        PedidoJpa pedidoJpa2 = new PedidoJpa();
        pedidoJpa2.setData(LocalDate.now().minusDays(2));
        pedidoJpa2.setCliente(clienteJpa);
        // Pode ser outro prestador/serviço
        PrestadorJpa outroPrestador = new PrestadorJpa();
        outroPrestador.setNome("Prestador P2"); outroPrestador.setSenha("s"); outroPrestador.setEmail("p2@e.com"); outroPrestador.setTelefone("t"); outroPrestador.setEndereco(new EnderecoJpa());
        outroPrestador = entityManager.persistAndFlush(outroPrestador);
        pedidoJpa2.setPrestador(outroPrestador);
        pedidoJpa2.setServico(servicoJpa);
        pedidoJpa2.setStatus("CANCELADO");
        entityManager.persistAndFlush(pedidoJpa2);

        // Criar pedido com outro cliente
        ClienteJpa outroCliente = new ClienteJpa();
        outroCliente.setNome("Outro Cliente"); outroCliente.setSenha("s"); outroCliente.setEmail("oc@e.com"); outroCliente.setTelefone("t"); outroCliente.setEndereco(new EnderecoJpa());
        outroCliente = entityManager.persistAndFlush(outroCliente);
        PedidoJpa pedidoJpa3 = new PedidoJpa();
        pedidoJpa3.setData(LocalDate.now());
        pedidoJpa3.setCliente(outroCliente);
        pedidoJpa3.setPrestador(prestadorJpa);
        pedidoJpa3.setServico(servicoJpa);
        pedidoJpa3.setStatus("EM_ANDAMENTO");
        entityManager.persistAndFlush(pedidoJpa3);

        List<PedidoJpa> encontrados = pedidoJpaRepository.findByClienteId(clienteJpa.getId());
        assertEquals(2, encontrados.size());
        assertTrue(encontrados.stream().allMatch(p -> p.getCliente().getId() == clienteJpa.getId()));
    }

    @Test
    void deveListarTodosOsPedidos() {
        entityManager.persistAndFlush(pedidoJpa1);

        PedidoJpa pedidoJpa2 = new PedidoJpa();
        pedidoJpa2.setData(LocalDate.now().minusDays(1));
        pedidoJpa2.setCliente(clienteJpa);
        pedidoJpa2.setPrestador(prestadorJpa);
        pedidoJpa2.setServico(servicoJpa);
        pedidoJpa2.setStatus("FINALIZADO");
        entityManager.persistAndFlush(pedidoJpa2);

        List<PedidoJpa> todos = pedidoJpaRepository.findAll();
        assertEquals(2, todos.size());
    }
}