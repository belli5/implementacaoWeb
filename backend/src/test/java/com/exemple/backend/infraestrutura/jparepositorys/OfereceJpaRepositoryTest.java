package com.exemple.backend.infraestrutura.jparepositorys;

import com.exemple.backend.infraestrutura.jpamodels.OfereceJpa;
import com.exemple.backend.infraestrutura.jpamodels.PrestadorJpa;
import com.exemple.backend.infraestrutura.jpamodels.ServicoJpa;
import com.exemple.backend.infraestrutura.jpamodels.compartilhados.EnderecoJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OfereceJpaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OfereceJpaRepository ofereceJpaRepository;

    private PrestadorJpa prestadorJpa;
    private ServicoJpa servicoJpa1;
    private ServicoJpa servicoJpa2;
    private OfereceJpa ofereceJpa1;


    @BeforeEach
    void setUp() {
        ofereceJpaRepository.deleteAll(); // Limpa dados de testes anteriores

        EnderecoJpa endereco = new EnderecoJpa("Rua Oferece", "Bairro Of", "Cidade Of", "OF");
        prestadorJpa = new PrestadorJpa("Prestador Oferece", "sOf", "of@email.com", "telOf", endereco);
        prestadorJpa = entityManager.persistAndFlush(prestadorJpa);

        servicoJpa1 = new ServicoJpa("Servico Oferece 1", "OF_CAT1", "Desc Oferece 1");
        servicoJpa1 = entityManager.persistAndFlush(servicoJpa1);

        servicoJpa2 = new ServicoJpa("Servico Oferece 2", "OF_CAT2", "Desc Oferece 2");
        servicoJpa2 = entityManager.persistAndFlush(servicoJpa2);

        ofereceJpa1 = new OfereceJpa();
        ofereceJpa1.setPrestador(prestadorJpa);
        ofereceJpa1.setServico(servicoJpa1);
    }

    @Test
    void deveSalvarEEncontrarOferecePorId() {
        OfereceJpa salvo = entityManager.persistAndFlush(ofereceJpa1);
        assertNotNull(salvo.getId());

        assertTrue(ofereceJpaRepository.findById(salvo.getId()).isPresent());
    }

    @Test
    void deveEncontrarOferecePorPrestadorId() {
        entityManager.persistAndFlush(ofereceJpa1);

        OfereceJpa ofereceJpa2 = new OfereceJpa();
        ofereceJpa2.setPrestador(prestadorJpa);
        ofereceJpa2.setServico(servicoJpa2);
        entityManager.persistAndFlush(ofereceJpa2);

        // Item com outro prestador
        PrestadorJpa outroPrestador = new PrestadorJpa("Outro Prest Of", "sOP", "op@email.com", "telOP", new EnderecoJpa());
        outroPrestador = entityManager.persistAndFlush(outroPrestador);
        OfereceJpa ofereceJpa3 = new OfereceJpa();
        ofereceJpa3.setPrestador(outroPrestador);
        ofereceJpa3.setServico(servicoJpa1);
        entityManager.persistAndFlush(ofereceJpa3);


        List<OfereceJpa> encontrados = ofereceJpaRepository.findByPrestadorId(prestadorJpa.getId());
        assertEquals(2, encontrados.size());
        assertTrue(encontrados.stream().allMatch(o -> o.getPrestador().getId() == prestadorJpa.getId()));
    }

    @Test
    void deveEncontrarOferecePorServicoNome() {
        entityManager.persistAndFlush(ofereceJpa1); // prestadorJpa com servicoJpa1

        // Outro prestador oferecendo o mesmo servicoJpa1
        PrestadorJpa outroPrestador = new PrestadorJpa("Outro Prest Of 2", "sOP2", "op2@email.com", "telOP2", new EnderecoJpa());
        outroPrestador = entityManager.persistAndFlush(outroPrestador);
        OfereceJpa ofereceJpa2 = new OfereceJpa();
        ofereceJpa2.setPrestador(outroPrestador);
        ofereceJpa2.setServico(servicoJpa1);
        entityManager.persistAndFlush(ofereceJpa2);

        // Item com outro serviço
        OfereceJpa ofereceJpa3 = new OfereceJpa();
        ofereceJpa3.setPrestador(prestadorJpa);
        ofereceJpa3.setServico(servicoJpa2);
        entityManager.persistAndFlush(ofereceJpa3);


        List<OfereceJpa> encontrados = ofereceJpaRepository.findByServicoNome(servicoJpa1.getNome());
        assertEquals(2, encontrados.size());
        assertTrue(encontrados.stream().allMatch(o -> o.getServico().getNome().equals(servicoJpa1.getNome())));
    }
}