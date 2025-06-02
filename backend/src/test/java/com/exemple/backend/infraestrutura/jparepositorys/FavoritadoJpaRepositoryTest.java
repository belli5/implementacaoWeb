package com.exemple.backend.infraestrutura.jparepositorys;

import com.exemple.backend.infraestrutura.jpamodels.ClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.FavoritadoJpa;
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
class FavoritadoJpaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FavoritadoJpaRepository favoritadoJpaRepository;

    private ClienteJpa clienteJpa1;
    private ClienteJpa clienteJpa2;
    private PrestadorJpa prestadorJpa1;
    private PrestadorJpa prestadorJpa2;
    private FavoritadoJpa favoritadoJpa;

    @BeforeEach
    void setUp() {
        favoritadoJpaRepository.deleteAll();

        EnderecoJpa enderecoComum = new EnderecoJpa("Rua FavTest", "Bairro FT", "Cidade FT", "FT");

        clienteJpa1 = new ClienteJpa("Cliente Fav 1", "sCF1", "cf1@email.com", "telCF1", enderecoComum);
        clienteJpa1 = entityManager.persistAndFlush(clienteJpa1);

        clienteJpa2 = new ClienteJpa("Cliente Fav 2", "sCF2", "cf2@email.com", "telCF2", enderecoComum);
        clienteJpa2 = entityManager.persistAndFlush(clienteJpa2);

        prestadorJpa1 = new PrestadorJpa("Prestador Fav 1", "sPF1", "pf1@email.com", "telPF1", enderecoComum);
        prestadorJpa1 = entityManager.persistAndFlush(prestadorJpa1);

        prestadorJpa2 = new PrestadorJpa("Prestador Fav 2", "sPF2", "pf2@email.com", "telPF2", enderecoComum);
        prestadorJpa2 = entityManager.persistAndFlush(prestadorJpa2);

        favoritadoJpa = new FavoritadoJpa();
        favoritadoJpa.setCliente(clienteJpa1);
        favoritadoJpa.setPrestador(prestadorJpa1);
    }

    @Test
    void deveSalvarEEncontrarFavoritado() {
        FavoritadoJpa salvo = entityManager.persistAndFlush(favoritadoJpa);
        assertNotNull(salvo.getId());
        assertTrue(favoritadoJpaRepository.findById(salvo.getId()).isPresent());
    }

    @Test
    void deveEncontrarFavoritadosPorPrestadorId() {
        // Cliente 1 favorita Prestador 1
        entityManager.persistAndFlush(favoritadoJpa);

        // Cliente 2 também favorita Prestador 1
        FavoritadoJpa favoritado2 = new FavoritadoJpa();
        favoritado2.setCliente(clienteJpa2);
        favoritado2.setPrestador(prestadorJpa1);
        entityManager.persistAndFlush(favoritado2);

        // Cliente 1 favorita outro Prestador (Prestador 2)
        FavoritadoJpa favoritado3 = new FavoritadoJpa();
        favoritado3.setCliente(clienteJpa1);
        favoritado3.setPrestador(prestadorJpa2);
        entityManager.persistAndFlush(favoritado3);

        List<FavoritadoJpa> encontrados = favoritadoJpaRepository.findByPrestadorId(prestadorJpa1.getId());
        assertEquals(2, encontrados.size());
        assertTrue(encontrados.stream().allMatch(f -> f.getPrestador().getId() == prestadorJpa1.getId()));
        assertTrue(encontrados.stream().anyMatch(f -> f.getCliente().getId() == clienteJpa1.getId()));
        assertTrue(encontrados.stream().anyMatch(f -> f.getCliente().getId() == clienteJpa2.getId()));
    }

    @Test
    void deveEncontrarFavoritadosPorClienteId() {
        // Cliente 1 favorita Prestador 1
        entityManager.persistAndFlush(favoritadoJpa);

        // Cliente 1 também favorita Prestador 2
        FavoritadoJpa favoritado2 = new FavoritadoJpa();
        favoritado2.setCliente(clienteJpa1);
        favoritado2.setPrestador(prestadorJpa2);
        entityManager.persistAndFlush(favoritado2);

        // Outro cliente (Cliente 2) favorita Prestador 1
        FavoritadoJpa favoritado3 = new FavoritadoJpa();
        favoritado3.setCliente(clienteJpa2);
        favoritado3.setPrestador(prestadorJpa1);
        entityManager.persistAndFlush(favoritado3);


        List<FavoritadoJpa> encontrados = favoritadoJpaRepository.findByClienteId(clienteJpa1.getId());
        assertEquals(2, encontrados.size());
        assertTrue(encontrados.stream().allMatch(f -> f.getCliente().getId() == clienteJpa1.getId()));
        assertTrue(encontrados.stream().anyMatch(f -> f.getPrestador().getId() == prestadorJpa1.getId()));
        assertTrue(encontrados.stream().anyMatch(f -> f.getPrestador().getId() == prestadorJpa2.getId()));
    }
}