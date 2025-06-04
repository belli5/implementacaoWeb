package com.exemple.backend.infraestrutura.jparepositorys;

import com.exemple.backend.infraestrutura.jpamodels.ServicoJpa;
import org.hibernate.exception.ConstraintViolationException; // Importe a exceção correta
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
// Remova o import do Spring DAO se não for mais usado:
// import org.springframework.dao.DataIntegrityViolationException;

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
        entityManager.flush();
        entityManager.clear();

        servicoJpa1 = new ServicoJpa("Limpeza Pesada JPA", "LIMPEZA_JPA", "Limpeza profunda com JPA.");
    }

    @Test
    void deveSalvarEEncontrarServicoPorNome() {
        entityManager.persist(servicoJpa1);
        entityManager.flush();
        Optional<ServicoJpa> encontrado = servicoJpaRepository.findByNome(servicoJpa1.getNome());
        assertTrue(encontrado.isPresent(), "Serviço deveria ser encontrado após persistir e dar flush.");
        assertEquals(servicoJpa1.getCategoria(), encontrado.get().getCategoria());
    }

    @Test
    void naoDeveSalvarServicoComMesmoNome() {
        // 1. Persiste a primeira entidade e sincroniza com o banco.
        entityManager.persist(servicoJpa1);
        entityManager.flush();

        // 2. Limpa o contexto de persistência de primeiro nível.
        entityManager.clear();

        // 3. Cria uma nova instância de objeto com o mesmo valor de chave primária.
        ServicoJpa servicoJpaDuplicado = new ServicoJpa("Limpeza Pesada JPA", "OUTRA_CAT", "Outra desc");

        // 4. Tenta persistir e dar flush na entidade duplicada usando o entityManager.
        //    Isso forçará uma tentativa de INSERT que violará a PK no banco.
        //    A exceção do banco será encapsulada pelo Hibernate.
        assertThrows(ConstraintViolationException.class, () -> { // ESPERANDO A EXCEÇÃO DO HIBERNATE
            entityManager.persist(servicoJpaDuplicado);
            entityManager.flush();
        }, "Deveria lançar ConstraintViolationException (Hibernate) ao tentar inserir nome duplicado diretamente com entityManager.");
    }

    @Test
    void deveEncontrarServicosPorCategoria() {
        entityManager.persist(servicoJpa1);
        ServicoJpa servicoJpa2 = new ServicoJpa("Jardinagem JPA", "JARDIM_JPA", "Corte de grama JPA.");
        entityManager.persist(servicoJpa2);
        ServicoJpa servicoJpa3 = new ServicoJpa("Limpeza Leve JPA", "LIMPEZA_JPA", "Limpeza leve com JPA.");
        entityManager.persist(servicoJpa3);
        entityManager.flush();

        List<ServicoJpa> encontrados = servicoJpaRepository.findByCategoria("LIMPEZA_JPA");
        assertEquals(2, encontrados.size(), "Deveria encontrar 2 serviços para a categoria LIMPEZA_JPA.");
        assertTrue(encontrados.stream().allMatch(s -> s.getCategoria().equals("LIMPEZA_JPA")));
    }

    @Test
    void deveDeletarServicoPorNome() {
        entityManager.persist(servicoJpa1);
        entityManager.flush();
        assertTrue(servicoJpaRepository.findByNome(servicoJpa1.getNome()).isPresent(), "Serviço deveria existir antes de deletar.");

        servicoJpaRepository.deleteByNome(servicoJpa1.getNome());
        entityManager.flush();
        entityManager.clear();

        assertFalse(servicoJpaRepository.findByNome(servicoJpa1.getNome()).isPresent(), "Serviço não deveria ser encontrado após deletar.");
    }
}