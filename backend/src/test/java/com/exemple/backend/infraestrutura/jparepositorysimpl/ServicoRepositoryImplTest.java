package com.exemple.backend.infraestrutura.jparepositorysimpl;

import com.exemple.backend.dominio.models.Servico;
import com.exemple.backend.infraestrutura.jpamodels.ServicoJpa;
import com.exemple.backend.infraestrutura.jparepositorys.ServicoJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(ServicoRepositoryImpl.class)
class ServicoRepositoryImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ServicoRepositoryImpl servicoRepositoryImpl;

    @Autowired
    private ServicoJpaRepository servicoJpaRepository;

    private Servico servicoDominioParaSalvar;

    @BeforeEach
    void setUp() {
        servicoJpaRepository.deleteAll(); // Servico usa nome como PK, então deleteAll é mais seguro.
        servicoDominioParaSalvar = new Servico("Servico Dom Impl", "CAT_IMPL", "Desc Impl");
    }

    @Test
    void deveSalvarServicoERetornarServicoDominio() {
        Servico salvo = servicoRepositoryImpl.save(servicoDominioParaSalvar);
        assertNotNull(salvo);
        assertEquals(servicoDominioParaSalvar.getNome(), salvo.getNome());
        assertEquals(servicoDominioParaSalvar.getCategoria(), salvo.getCategoria());

        Optional<ServicoJpa> persistido = servicoJpaRepository.findById(salvo.getNome());
        assertTrue(persistido.isPresent());
        assertEquals(servicoDominioParaSalvar.getCategoria(), persistido.get().getCategoria());
    }

    @Test
    void deveEncontrarServicoPorNome() {
        // Setup
        ServicoJpa servicoJpa = new ServicoJpa("Servico Para Buscar", "CAT_BUSCA", "Desc Busca");
        entityManager.persistAndFlush(servicoJpa);

        Optional<Servico> encontrado = servicoRepositoryImpl.findByNome("Servico Para Buscar");
        assertTrue(encontrado.isPresent());
        assertEquals("Servico Para Buscar", encontrado.get().getNome());
        assertEquals("CAT_BUSCA", encontrado.get().getCategoria());
    }

    @Test
    void deveEncontrarServicosPorCategoria() {
        ServicoJpa s1 = new ServicoJpa("Serv1 CatImpl", "CAT_IMPL_TEST", "D1");
        ServicoJpa s2 = new ServicoJpa("Serv2 CatImpl", "CAT_IMPL_TEST", "D2");
        ServicoJpa s3 = new ServicoJpa("Serv3 OutraCat", "OUTRA_CAT", "D3");
        entityManager.persist(s1);
        entityManager.persist(s2);
        entityManager.persist(s3);
        entityManager.flush();

        List<Servico> encontrados = servicoRepositoryImpl.findByCategoria("CAT_IMPL_TEST");
        assertEquals(2, encontrados.size());
        assertTrue(encontrados.stream().allMatch(s -> "CAT_IMPL_TEST".equals(s.getCategoria())));
    }

    @Test
    void deveEncontrarTodosOsServicos() {
        ServicoJpa s1 = new ServicoJpa("S1 Todos", "C1", "D1");
        ServicoJpa s2 = new ServicoJpa("S2 Todos", "C2", "D2");
        entityManager.persist(s1);
        entityManager.persist(s2);
        entityManager.flush();

        List<Servico> todos = servicoRepositoryImpl.findAll();
        assertEquals(2, todos.size());
    }

    @Test
    void deveDeletarServicoPorNome() {
        entityManager.persistAndFlush(new ServicoJpa(servicoDominioParaSalvar.getNome(), servicoDominioParaSalvar.getCategoria(), servicoDominioParaSalvar.getDescricao()));
        assertTrue(servicoJpaRepository.existsById(servicoDominioParaSalvar.getNome()));

        servicoRepositoryImpl.deleteByNome(servicoDominioParaSalvar.getNome());

        assertFalse(servicoJpaRepository.existsById(servicoDominioParaSalvar.getNome()));
    }
}