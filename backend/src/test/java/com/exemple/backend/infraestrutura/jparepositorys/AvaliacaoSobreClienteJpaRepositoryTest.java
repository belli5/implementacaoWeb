package com.exemple.backend.infraestrutura.jparepositorys;

import com.exemple.backend.infraestrutura.jpamodels.AvaliacaoSobreClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.ClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.PrestadorJpa;
import com.exemple.backend.infraestrutura.jpamodels.compartilhados.EnderecoJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AvaliacaoSobreClienteJpaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AvaliacaoSobreClienteJpaRepository repository;

    private ClienteJpa clienteJpa1;
    private ClienteJpa clienteJpa2;
    private PrestadorJpa prestadorJpa;
    private AvaliacaoSobreClienteJpa avaliacao1;

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        EnderecoJpa endereco = new EnderecoJpa("Rua ASC", "Bairro ASC", "Cidade ASC", "ASC");
        prestadorJpa = new PrestadorJpa("Prestador ASC", "s", "pasc@email.com", "telpasc", endereco);
        prestadorJpa = entityManager.persistAndFlush(prestadorJpa);

        clienteJpa1 = new ClienteJpa("Cliente ASC 1", "s", "casc1@email.com", "telcasc1", endereco);
        clienteJpa1 = entityManager.persistAndFlush(clienteJpa1);

        clienteJpa2 = new ClienteJpa("Cliente ASC 2", "s", "casc2@email.com", "telcasc2", endereco);
        clienteJpa2 = entityManager.persistAndFlush(clienteJpa2);

        avaliacao1 = new AvaliacaoSobreClienteJpa();
        avaliacao1.setPrestador(prestadorJpa);
        avaliacao1.setCliente(clienteJpa1);
        avaliacao1.setComentario("Cliente pontual.");
        avaliacao1.setNota(4);
    }

    @Test
    void deveSalvarEEncontrarAvaliacao() {
        AvaliacaoSobreClienteJpa salvo = entityManager.persistAndFlush(avaliacao1);
        assertNotNull(salvo.getId());
        assertTrue(repository.findById(salvo.getId()).isPresent());
    }

    @Test
    void deveEncontrarAvaliacoesPorClienteId() {
        // Avaliacao 1 para clienteJpa1
        entityManager.persistAndFlush(avaliacao1);

        // Avaliacao 2 também para clienteJpa1, por outro prestador (ou mesmo)
        PrestadorJpa outroPrestador = new PrestadorJpa("Prestador ASC 2", "s", "pasc2@email.com", "telpasc2", new EnderecoJpa());
        outroPrestador = entityManager.persistAndFlush(outroPrestador);
        AvaliacaoSobreClienteJpa avaliacao2 = new AvaliacaoSobreClienteJpa();
        avaliacao2.setPrestador(outroPrestador);
        avaliacao2.setCliente(clienteJpa1);
        avaliacao2.setComentario("Comunicativo.");
        avaliacao2.setNota(5);
        entityManager.persistAndFlush(avaliacao2);

        // Avaliacao para outro cliente (clienteJpa2)
        AvaliacaoSobreClienteJpa avaliacao3 = new AvaliacaoSobreClienteJpa();
        avaliacao3.setPrestador(prestadorJpa);
        avaliacao3.setCliente(clienteJpa2);
        avaliacao3.setComentario("Responde rápido.");
        avaliacao3.setNota(4);
        entityManager.persistAndFlush(avaliacao3);

        List<AvaliacaoSobreClienteJpa> encontradas = repository.findByClienteId(clienteJpa1.getId());
        assertEquals(2, encontradas.size());
        assertTrue(encontradas.stream().allMatch(a -> a.getCliente().getId() == clienteJpa1.getId()));
    }
}