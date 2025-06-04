package com.exemple.backend.infraestrutura.jparepositorys;

import com.exemple.backend.infraestrutura.jpamodels.AvaliacaoSobrePrestadorJpa;
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
class AvaliacaoSobrePrestadorJpaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AvaliacaoSobrePrestadorJpaRepository repository;

    private ClienteJpa clienteJpa;
    private PrestadorJpa prestadorJpa1;
    private PrestadorJpa prestadorJpa2;
    private AvaliacaoSobrePrestadorJpa avaliacao1;

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        EnderecoJpa endereco = new EnderecoJpa("Rua ASP", "Bairro ASP", "Cidade ASP", "ASP");
        clienteJpa = new ClienteJpa("Cliente ASP", "s", "casp@email.com", "telcasp", endereco);
        clienteJpa = entityManager.persistAndFlush(clienteJpa);

        prestadorJpa1 = new PrestadorJpa("Prestador ASP 1", "s", "pasp1@email.com", "telpasp1", endereco);
        prestadorJpa1 = entityManager.persistAndFlush(prestadorJpa1);

        prestadorJpa2 = new PrestadorJpa("Prestador ASP 2", "s", "pasp2@email.com", "telpasp2", endereco);
        prestadorJpa2 = entityManager.persistAndFlush(prestadorJpa2);

        avaliacao1 = new AvaliacaoSobrePrestadorJpa();
        avaliacao1.setCliente(clienteJpa);
        avaliacao1.setPrestador(prestadorJpa1);
        avaliacao1.setComentario("Muito bom!");
        avaliacao1.setNota(5);
    }

    @Test
    void deveSalvarEEncontrarAvaliacao() {
        AvaliacaoSobrePrestadorJpa salvo = entityManager.persistAndFlush(avaliacao1);
        assertNotNull(salvo.getId());
        assertTrue(repository.findById(salvo.getId()).isPresent());
    }

    @Test
    void deveEncontrarAvaliacoesPorPrestadorId() {
        // Avaliacao 1 para prestadorJpa1
        entityManager.persistAndFlush(avaliacao1);

        // Avaliacao 2 também para prestadorJpa1, por outro cliente (ou mesmo)
        ClienteJpa outroCliente = new ClienteJpa("Cliente ASP 2", "s", "casp2@email.com", "telcasp2", new EnderecoJpa());
        outroCliente = entityManager.persistAndFlush(outroCliente);
        AvaliacaoSobrePrestadorJpa avaliacao2 = new AvaliacaoSobrePrestadorJpa();
        avaliacao2.setCliente(outroCliente);
        avaliacao2.setPrestador(prestadorJpa1);
        avaliacao2.setComentario("Ok.");
        avaliacao2.setNota(3);
        entityManager.persistAndFlush(avaliacao2);

        // Avaliacao para outro prestador (prestadorJpa2)
        AvaliacaoSobrePrestadorJpa avaliacao3 = new AvaliacaoSobrePrestadorJpa();
        avaliacao3.setCliente(clienteJpa);
        avaliacao3.setPrestador(prestadorJpa2);
        avaliacao3.setComentario("Excelente!");
        avaliacao3.setNota(5);
        entityManager.persistAndFlush(avaliacao3);

        List<AvaliacaoSobrePrestadorJpa> encontradas = repository.findByPrestadorId(prestadorJpa1.getId());
        assertEquals(2, encontradas.size());
        assertTrue(encontradas.stream().allMatch(a -> a.getPrestador().getId() == prestadorJpa1.getId()));
    }
}