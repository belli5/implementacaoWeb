package com.exemple.backend.infraestrutura.jparepositorys;

import com.exemple.backend.infraestrutura.jpamodels.PrestadorJpa;
import com.exemple.backend.infraestrutura.jpamodels.compartilhados.EnderecoJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PrestadorJpaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PrestadorJpaRepository prestadorJpaRepository;

    private PrestadorJpa prestadorJpa1;

    @BeforeEach
    void setUp() {
        prestadorJpaRepository.deleteAll();

        EnderecoJpa endereco = new EnderecoJpa("Rua JPA Prest", "Bairro JP", "Cidade JP", "JP");
        prestadorJpa1 = new PrestadorJpa("Prestador JPA Um", "sjpa1", "jpa1@email.com", "teljpa1", endereco);
    }

    @Test
    void deveSalvarEEncontrarPrestador() {
        PrestadorJpa salvo = entityManager.persistAndFlush(prestadorJpa1);
        Optional<PrestadorJpa> encontrado = prestadorJpaRepository.findById(salvo.getId());
        assertTrue(encontrado.isPresent());
        assertEquals(salvo.getNome(), encontrado.get().getNome());
    }

    @Test
    void deveEncontrarTodosOsPrestadores() {
        entityManager.persist(prestadorJpa1);
        EnderecoJpa endereco2 = new EnderecoJpa("Rua JPA Prest2", "Bairro JP2", "Cidade JP2", "J2");
        PrestadorJpa prestadorJpa2 = new PrestadorJpa("Prestador JPA Dois", "sjpa2", "jpa2@email.com", "teljpa2", endereco2);
        entityManager.persist(prestadorJpa2);
        entityManager.flush();

        List<PrestadorJpa> todos = prestadorJpaRepository.findAll();
        assertEquals(2, todos.size());
    }
}