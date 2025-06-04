package com.exemple.backend.infraestrutura.jparepositorysimpl;

import com.exemple.backend.dominio.models.AvaliacaoSobrePrestador;
import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.infraestrutura.jpamodels.AvaliacaoSobrePrestadorJpa;
import com.exemple.backend.infraestrutura.jpamodels.ClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.PrestadorJpa;
import com.exemple.backend.infraestrutura.jpamodels.compartilhados.EnderecoJpa;
import com.exemple.backend.infraestrutura.jparepositorys.AvaliacaoSobrePrestadorJpaRepository;
import com.exemple.backend.infraestrutura.jparepositorys.ClienteJpaRepository;
import com.exemple.backend.infraestrutura.jparepositorys.PrestadorJpaRepository;
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
@Import(AvaliacaoSobrePrestadorRepositoryImpl.class)
class AvaliacaoSobrePrestadorRepositoryImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AvaliacaoSobrePrestadorRepositoryImpl avaliacaoRepositoryImpl;

    @Autowired
    private AvaliacaoSobrePrestadorJpaRepository avaliacaoJpaRepository;
    @Autowired
    private ClienteJpaRepository clienteJpaRepository;
    @Autowired
    private PrestadorJpaRepository prestadorJpaRepository;

    private Cliente clienteDominio;
    private Prestador prestadorDominio;
    private AvaliacaoSobrePrestador avaliacaoDominioParaSalvar;

    private ClienteJpa clienteJpaPersistido;
    private PrestadorJpa prestadorJpaPersistido;

    @BeforeEach
    void setUp() {
        avaliacaoJpaRepository.deleteAll();
        clienteJpaRepository.deleteAll();
        prestadorJpaRepository.deleteAll();
        entityManager.flush();
        entityManager.clear();

        Endereco enderecoModeloDominio = new Endereco("Rua AvalP Impl Setup", "Bairro APS", "Cidade APS", "APS");
        EnderecoJpa enderecoModeloJpa = new EnderecoJpa(enderecoModeloDominio.getRua(), enderecoModeloDominio.getBairro(), enderecoModeloDominio.getCidade(), enderecoModeloDominio.getEstado());

        ClienteJpa cJpa = new ClienteJpa();
        cJpa.setNome("Cliente Para AvalP Teste");
        cJpa.setSenha("senhaClienteAPS");
        cJpa.setEmail("cliente.avalP.teste@example.com");
        cJpa.setTelefone("333333333");
        cJpa.setEndereco(enderecoModeloJpa);
        clienteJpaPersistido = entityManager.persistFlushFind(cJpa);

        PrestadorJpa pJpa = new PrestadorJpa();
        pJpa.setNome("Prestador Para AvalP Teste");
        pJpa.setSenha("senhaPrestadorAPS");
        pJpa.setEmail("prestador.avalP.teste@example.com");
        pJpa.setTelefone("444444444");
        pJpa.setEndereco(enderecoModeloJpa);
        prestadorJpaPersistido = entityManager.persistFlushFind(pJpa);

        clienteDominio = new Cliente(
                clienteJpaPersistido.getId(),
                clienteJpaPersistido.getNome(),
                clienteJpaPersistido.getSenha(),
                clienteJpaPersistido.getEmail(),
                clienteJpaPersistido.getTelefone(),
                enderecoModeloDominio
        );
        prestadorDominio = new Prestador(
                prestadorJpaPersistido.getId(),
                prestadorJpaPersistido.getNome(),
                prestadorJpaPersistido.getSenha(),
                prestadorJpaPersistido.getEmail(),
                prestadorJpaPersistido.getTelefone(),
                enderecoModeloDominio
        );

        // CORREÇÃO AQUI: Use o construtor que não exige ID
        avaliacaoDominioParaSalvar = new AvaliacaoSobrePrestador(
                clienteDominio,
                "Comentário inicial sobre prestador para salvar.",
                5, // Nota
                prestadorDominio
        );
    }

    @Test
    void deveSalvarAvaliacaoERetornarComDadosCorretos() {
        AvaliacaoSobrePrestador salvo = avaliacaoRepositoryImpl.save(avaliacaoDominioParaSalvar);

        assertNotNull(salvo, "A avaliação salva não deveria ser nula.");
        assertNotNull(salvo.getId(), "O ID da avaliação salva não deveria ser nulo após a persistência.");
        assertTrue(salvo.getId() > 0, "O ID da avaliação salva deveria ser maior que zero após a persistência.");
        assertEquals(avaliacaoDominioParaSalvar.getComentario(), salvo.getComentario(), "Comentário não corresponde.");
        assertEquals(avaliacaoDominioParaSalvar.getNota(), salvo.getNota(), "Nota não corresponde.");

        assertNotNull(salvo.getCliente(), "Cliente dentro da avaliação salva não deveria ser nulo.");
        assertEquals(clienteDominio.getId(), salvo.getCliente().getId(), "ID do cliente na avaliação não corresponde.");

        assertNotNull(salvo.getPrestador(), "Prestador dentro da avaliação salva não deveria ser nulo.");
        assertEquals(prestadorDominio.getId(), salvo.getPrestador().getId(), "ID do prestador na avaliação não corresponde.");

        Optional<AvaliacaoSobrePrestadorJpa> persistidoOpt = avaliacaoJpaRepository.findById(salvo.getId());
        assertTrue(persistidoOpt.isPresent(), "A avaliação não foi encontrada no banco após salvar.");
        AvaliacaoSobrePrestadorJpa persistidoJpa = persistidoOpt.get();
        assertEquals(avaliacaoDominioParaSalvar.getComentario(), persistidoJpa.getComentario());
        assertEquals(avaliacaoDominioParaSalvar.getNota(), persistidoJpa.getNota());
        assertNotNull(persistidoJpa.getCliente(), "FK do cliente na entidade JPA não deveria ser nula.");
        assertEquals(clienteDominio.getId(), persistidoJpa.getCliente().getId(), "FK ID do cliente na entidade JPA não corresponde.");
        assertNotNull(persistidoJpa.getPrestador(), "FK do prestador na entidade JPA não deveria ser nula.");
        assertEquals(prestadorDominio.getId(), persistidoJpa.getPrestador().getId(), "FK ID do prestador na entidade JPA não corresponde.");
    }

    private AvaliacaoSobrePrestadorJpa persistAvaliacaoJpaCompleta(ClienteJpa c, PrestadorJpa p, String comentario, int nota) {
        AvaliacaoSobrePrestadorJpa aval = new AvaliacaoSobrePrestadorJpa();
        aval.setCliente(c);
        aval.setPrestador(p);
        aval.setComentario(comentario);
        aval.setNota(nota);
        return entityManager.persistFlushFind(aval);
    }

    @Test
    void deveEncontrarAvaliacaoPorId() {
        AvaliacaoSobrePrestadorJpa avalJpa = persistAvaliacaoJpaCompleta(clienteJpaPersistido, prestadorJpaPersistido, "Teste Encontrar Por ID AP", 4);
        Optional<AvaliacaoSobrePrestador> encontrado = avaliacaoRepositoryImpl.findById(avalJpa.getId());

        assertTrue(encontrado.isPresent(), "Avaliação deveria ser encontrada pelo ID.");
        AvaliacaoSobrePrestador avalDominio = encontrado.get();
        assertEquals(avalJpa.getId(), avalDominio.getId());
        assertEquals("Teste Encontrar Por ID AP", avalDominio.getComentario());
        assertEquals(4, avalDominio.getNota());

        assertNotNull(avalDominio.getCliente());
        assertEquals(clienteJpaPersistido.getId(), avalDominio.getCliente().getId());
        assertNotNull(avalDominio.getPrestador());
        assertEquals(prestadorJpaPersistido.getId(), avalDominio.getPrestador().getId());
    }

    @Test
    void deveRetornarOptionalVazioSeIdNaoExistirAP() {
        Optional<AvaliacaoSobrePrestador> encontrado = avaliacaoRepositoryImpl.findById(88888);
        assertFalse(encontrado.isPresent(), "Não deveria encontrar avaliação para ID inexistente.");
    }

    @Test
    void deveEncontrarAvaliacoesPorPrestadorIdCorretamente() {
        persistAvaliacaoJpaCompleta(clienteJpaPersistido, prestadorJpaPersistido, "Comentário P1-C1 AP", 5);

        ClienteJpa outroClienteJpa = new ClienteJpa("Outro Cliente AP", "sOCAP", "ocap@test.com", "telOCAP", new EnderecoJpa("Rua OCAP","Bairro OCAP","Cidade OCAP","OCAP"));
        outroClienteJpa = entityManager.persistFlushFind(outroClienteJpa);
        persistAvaliacaoJpaCompleta(outroClienteJpa, prestadorJpaPersistido, "Comentário P1-C2 AP", 4);

        PrestadorJpa outroPrestadorJpa = new PrestadorJpa("Outro Prestador AP", "sOPAP", "opap@test.com", "telOPAP", new EnderecoJpa("Rua OPAP","Bairro OPAP","Cidade OPAP","OPAP"));
        outroPrestadorJpa = entityManager.persistFlushFind(outroPrestadorJpa);
        persistAvaliacaoJpaCompleta(clienteJpaPersistido, outroPrestadorJpa, "Comentário P2-C1 AP", 3);

        List<AvaliacaoSobrePrestador> encontradas = avaliacaoRepositoryImpl.findByPrestadorId(prestadorJpaPersistido.getId());
        assertNotNull(encontradas, "Lista de avaliações não deveria ser nula.");
        assertEquals(2, encontradas.size(), "Deveria encontrar 2 avaliações para o prestador especificado.");
        for(AvaliacaoSobrePrestador aval : encontradas) {
            assertNotNull(aval.getPrestador());
            assertEquals(prestadorJpaPersistido.getId(), aval.getPrestador().getId(), "ID do prestador não corresponde ao esperado.");
            assertNotNull(aval.getCliente());
        }
    }

    @Test
    void deveDeletarAvaliacaoPorIdAP() {
        AvaliacaoSobrePrestadorJpa avalJpa = persistAvaliacaoJpaCompleta(clienteJpaPersistido, prestadorJpaPersistido, "Avaliação Para Deletar AP", 2);
        int idParaDeletar = avalJpa.getId();

        assertTrue(avaliacaoJpaRepository.existsById(idParaDeletar), "Avaliação deveria existir antes de deletar.");
        avaliacaoRepositoryImpl.delete(idParaDeletar);
        entityManager.flush();
        entityManager.clear();

        assertFalse(avaliacaoJpaRepository.existsById(idParaDeletar), "Avaliação não deveria existir após deletar.");
    }

    @Test
    void deveListarTodasAsAvaliacoesAP() {
        persistAvaliacaoJpaCompleta(clienteJpaPersistido, prestadorJpaPersistido, "Comentário Todas AP 1", 5);

        PrestadorJpa outroPrestadorJpa = new PrestadorJpa("Outro Prestador Todas AP", "sOPTAP", "optap@test.com", "telOPTAP", new EnderecoJpa("Rua OPTAP","Bairro OPTAP","Cidade OPTAP","OPTAP"));
        outroPrestadorJpa = entityManager.persistFlushFind(outroPrestadorJpa);
        ClienteJpa outroClienteJpa = new ClienteJpa("Outro Cliente Todas AP", "sOCTAP", "octap@test.com", "telOCTAP", new EnderecoJpa("Rua OCTAP","Bairro OCTAP","Cidade OCTAP","OCTAP"));
        outroClienteJpa = entityManager.persistFlushFind(outroClienteJpa);
        persistAvaliacaoJpaCompleta(outroClienteJpa, outroPrestadorJpa, "Comentário Todas AP 2", 4);

        List<AvaliacaoSobrePrestador> todas = avaliacaoRepositoryImpl.findAll();
        assertNotNull(todas, "A lista de todas as avaliações não deveria ser nula.");
        assertEquals(2, todas.size(), "Deveria haver 2 avaliações no total.");
    }
}