package com.exemple.backend.infraestrutura.jparepositorys;

import com.exemple.backend.infraestrutura.jpamodels.ServicoJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ServicoJpaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ServicoJpaRepository servicoJpaRepository;

    private ServicoJpa servicoJpa1;

    @BeforeEach
    void setUp() {
        servicoJpaRepository.deleteAll();
        servicoJpa1 = new ServicoJpa("Limpeza Pesada JPA", "LIMPEZA_JPA", "Limpeza profunda com JPA.");
    }

    @Test
    void deveSalvarEEncontrarServicoPorNome() {
        // ServicoJpa usa nome como ID, não é auto-gerado.
        entityManager.persistAndFlush(servicoJpa1);
        Optional<ServicoJpa> encontrado = servicoJpaRepository.findByNome(servicoJpa1.getNome());
        assertTrue(encontrado.isPresent());
        assertEquals(servicoJpa1.getCategoria(), encontrado.get().getCategoria());
    }

    @Test
    void naoDeveSalvarServicoComMesmoNome() {
        entityManager.persistAndFlush(servicoJpa1);
        ServicoJpa servicoJpaDuplicado = new ServicoJpa("Limpeza Pesada JPA", "OUTRA_CAT", "Outra desc");

        // Espera-se uma exceção de violação de integridade (chave primária única)
        assertThrows(DataIntegrityViolationException.class, () -> {
            servicoJpaRepository.saveAndFlush(servicoJpaDuplicado);
        });
    }

    @Test
    void deveEncontrarServicosPorCategoria() {
        entityManager.persistAndFlush(servicoJpa1);
        ServicoJpa servicoJpa2 = new ServicoJpa("Jardinagem JPA", "JARDIM_JPA", "Corte de grama JPA.");
        entityManager.persistAndFlush(servicoJpa2);
        ServicoJpa servicoJpa3 = new ServicoJpa("Limpeza Leve JPA", "LIMPEZA_JPA", "Limpeza leve com JPA.");
        entityManager.persistAndFlush(servicoJpa3);

        List<ServicoJpa> encontrados = servicoJpaRepository.findByCategoria("LIMPEZA_JPA");
        assertEquals(2, encontrados.size());
        assertTrue(encontrados.stream().allMatch(s -> s.getCategoria().equals("LIMPEZA_JPA")));
    }

    @Test
    void deveDeletarServicoPorNome() {
        entityManager.persistAndFlush(servicoJpa1);
        assertTrue(servicoJpaRepository.findByNome(servicoJpa1.getNome()).isPresent());

        servicoJpaRepository.deleteByNome(servicoJpa1.getNome());
        servicoJpaRepository.flush(); // Garante que a deleção foi processada

        assertFalse(servicoJpaRepository.findByNome(servicoJpa1.getNome()).isPresent());
    }
}