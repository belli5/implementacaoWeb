package com.exemple.backend.infraestrutura.jparepositorysimpl;

import com.exemple.backend.dominio.models.AvaliacaoSobreCliente;
import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.infraestrutura.jpamodels.AvaliacaoSobreClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.ClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.PrestadorJpa;
import com.exemple.backend.infraestrutura.jpamodels.compartilhados.EnderecoJpa;
import com.exemple.backend.infraestrutura.jparepositorys.AvaliacaoSobreClienteJpaRepository;
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
@Import(AvaliacaoSobreClienteRepositoryImpl.class)
class AvaliacaoSobreClienteRepositoryImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AvaliacaoSobreClienteRepositoryImpl avaliacaoRepositoryImpl;

    @Autowired
    private AvaliacaoSobreClienteJpaRepository avaliacaoJpaRepository;
    @Autowired
    private ClienteJpaRepository clienteJpaRepository;
    @Autowired
    private PrestadorJpaRepository prestadorJpaRepository;

    private Cliente clienteDominio;
    private Prestador prestadorDominio;
    private AvaliacaoSobreCliente avaliacaoDominioParaSalvar;

    private ClienteJpa clienteJpaPersistido;
    private PrestadorJpa prestadorJpaPersistido;

    @BeforeEach
    void setUp() {

        avaliacaoJpaRepository.deleteAll();

        clienteJpaRepository.deleteAll();
        prestadorJpaRepository.deleteAll();
        entityManager.flush();
        entityManager.clear();

        Endereco enderecoModeloDominio = new Endereco("Rua AvalC Impl Setup", "Bairro Setup", "Cidade Setup", "ST");
        EnderecoJpa enderecoModeloJpa = new EnderecoJpa(enderecoModeloDominio.getRua(), enderecoModeloDominio.getBairro(), enderecoModeloDominio.getCidade(), enderecoModeloDominio.getEstado());

        ClienteJpa cJpa = new ClienteJpa();
        cJpa.setNome("Cliente Para Avaliacao Teste");
        cJpa.setSenha("senhaCliente123");
        cJpa.setEmail("cliente.aval.teste@example.com");
        cJpa.setTelefone("111111111");
        cJpa.setEndereco(enderecoModeloJpa);
        clienteJpaPersistido = entityManager.persistFlushFind(cJpa);

        PrestadorJpa pJpa = new PrestadorJpa();
        pJpa.setNome("Prestador Para Avaliacao Teste");
        pJpa.setSenha("senhaPrestador123");
        pJpa.setEmail("prestador.aval.teste@example.com");
        pJpa.setTelefone("222222222");
        pJpa.setEndereco(enderecoModeloJpa);
        prestadorJpaPersistido = entityManager.persistFlushFind(pJpa);

        // Criar objetos de domínio com IDs dos objetos JPA persistidos
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

        // O ID da avaliação é gerado, então 0 ou null é apropriado para um novo objeto de domínio
        avaliacaoDominioParaSalvar = new AvaliacaoSobreCliente(0, prestadorDominio, "Comentário inicial sobre cliente para salvar.", 4, clienteDominio);
    }

    @Test
    void deveSalvarAvaliacaoERetornarComDadosCorretos() {
        AvaliacaoSobreCliente salvo = avaliacaoRepositoryImpl.save(avaliacaoDominioParaSalvar);

        assertNotNull(salvo, "A avaliação salva não deveria ser nula.");
        assertNotNull(salvo.getId(), "O ID da avaliação salva não deveria ser nulo.");
        assertTrue(salvo.getId() > 0, "O ID da avaliação salva deveria ser maior que zero.");
        assertEquals(avaliacaoDominioParaSalvar.getComentario(), salvo.getComentario(), "Comentário não corresponde.");
        assertEquals(avaliacaoDominioParaSalvar.getNota(), salvo.getNota(), "Nota não corresponde.");

        // Verifica se os objetos Cliente e Prestador dentro da avaliação salva (de domínio)
        // foram populados corretamente pelo mapper (espera-se que tenham pelo menos os IDs)
        assertNotNull(salvo.getCliente(), "Cliente dentro da avaliação salva não deveria ser nulo.");
        assertEquals(clienteDominio.getId(), salvo.getCliente().getId(), "ID do cliente na avaliação não corresponde.");

        assertNotNull(salvo.getPrestador(), "Prestador dentro da avaliação salva não deveria ser nulo.");
        assertEquals(prestadorDominio.getId(), salvo.getPrestador().getId(), "ID do prestador na avaliação não corresponde.");

        // Verifica o que foi realmente salvo no banco através do JpaRepository da entidade JPA
        Optional<AvaliacaoSobreClienteJpa> persistidoOpt = avaliacaoJpaRepository.findById(salvo.getId());
        assertTrue(persistidoOpt.isPresent(), "A avaliação não foi encontrada no banco após salvar.");
        AvaliacaoSobreClienteJpa persistidoJpa = persistidoOpt.get();
        assertEquals(avaliacaoDominioParaSalvar.getComentario(), persistidoJpa.getComentario());
        assertEquals(avaliacaoDominioParaSalvar.getNota(), persistidoJpa.getNota());
        assertNotNull(persistidoJpa.getCliente(), "FK do cliente na entidade JPA não deveria ser nula.");
        assertEquals(clienteDominio.getId(), persistidoJpa.getCliente().getId(), "FK ID do cliente na entidade JPA não corresponde.");
        assertNotNull(persistidoJpa.getPrestador(), "FK do prestador na entidade JPA não deveria ser nula.");
        assertEquals(prestadorDominio.getId(), persistidoJpa.getPrestador().getId(), "FK ID do prestador na entidade JPA não corresponde.");
    }

    // Helper para persistir uma avaliação JPA completa para testes de busca
    private AvaliacaoSobreClienteJpa persistAvaliacaoJpaCompleta(ClienteJpa c, PrestadorJpa p, String comentario, int nota) {
        AvaliacaoSobreClienteJpa aval = new AvaliacaoSobreClienteJpa();
        aval.setCliente(c);
        aval.setPrestador(p);
        aval.setComentario(comentario);
        aval.setNota(nota);
        return entityManager.persistFlushFind(aval);
    }

    @Test
    void deveEncontrarAvaliacaoPorId() {
        AvaliacaoSobreClienteJpa avalJpa = persistAvaliacaoJpaCompleta(clienteJpaPersistido, prestadorJpaPersistido, "Teste Encontrar Por ID", 3);
        Optional<AvaliacaoSobreCliente> encontrado = avaliacaoRepositoryImpl.findById(avalJpa.getId());

        assertTrue(encontrado.isPresent(), "Avaliação deveria ser encontrada pelo ID.");
        AvaliacaoSobreCliente avalDominio = encontrado.get();
        assertEquals(avalJpa.getId(), avalDominio.getId());
        assertEquals("Teste Encontrar Por ID", avalDominio.getComentario());
        assertEquals(3, avalDominio.getNota());

        assertNotNull(avalDominio.getCliente());
        assertEquals(clienteJpaPersistido.getId(), avalDominio.getCliente().getId());
        assertNotNull(avalDominio.getPrestador());
        assertEquals(prestadorJpaPersistido.getId(), avalDominio.getPrestador().getId());
    }

    @Test
    void deveRetornarOptionalVazioSeIdNaoExistir() {
        Optional<AvaliacaoSobreCliente> encontrado = avaliacaoRepositoryImpl.findById(99999); // ID inexistente
        assertFalse(encontrado.isPresent(), "Não deveria encontrar avaliação para ID inexistente.");
    }

    @Test
    void deveEncontrarAvaliacoesPorClienteIdCorretamente() {
        // Avaliação 1 para clienteJpaPersistido (já criado no setUp e salvo por persistAvaliacaoJpaCompleta)
        persistAvaliacaoJpaCompleta(clienteJpaPersistido, prestadorJpaPersistido, "Comentário Cliente1-Prestador1", 5);

        // Outro prestador
        PrestadorJpa outroPrestadorJpa = new PrestadorJpa("Outro Prestador Teste", "sOP", "op@test.com", "telOP", new EnderecoJpa("Rua OP","Bairro OP","Cidade OP","OP"));
        outroPrestadorJpa = entityManager.persistFlushFind(outroPrestadorJpa);
        // Avaliação 2 para clienteJpaPersistido, mas por outroPrestadorJpa
        persistAvaliacaoJpaCompleta(clienteJpaPersistido, outroPrestadorJpa, "Comentário Cliente1-Prestador2", 4);

        // Avaliação para outro cliente, para garantir que o filtro funciona
        ClienteJpa outroClienteJpa = new ClienteJpa("Outro Cliente Teste", "sOC", "oc@test.com", "telOC", new EnderecoJpa("Rua OC","Bairro OC","Cidade OC","OC"));
        outroClienteJpa = entityManager.persistFlushFind(outroClienteJpa);
        persistAvaliacaoJpaCompleta(outroClienteJpa, prestadorJpaPersistido, "Comentário Cliente2-Prestador1", 3);

        List<AvaliacaoSobreCliente> encontradas = avaliacaoRepositoryImpl.findByClienteId(clienteJpaPersistido.getId());
        assertNotNull(encontradas, "Lista de avaliações não deveria ser nula.");
        assertEquals(2, encontradas.size(), "Deveria encontrar 2 avaliações para o cliente especificado.");
        for(AvaliacaoSobreCliente aval : encontradas) {
            assertNotNull(aval.getCliente(), "Cliente na avaliação retornada não deveria ser nulo.");
            assertEquals(clienteJpaPersistido.getId(), aval.getCliente().getId(), "ID do cliente não corresponde ao esperado.");
            assertNotNull(aval.getPrestador(), "Prestador na avaliação retornada não deveria ser nulo.");
        }
    }

    @Test
    void deveDeletarAvaliacaoPorId() {
        AvaliacaoSobreClienteJpa avalJpa = persistAvaliacaoJpaCompleta(clienteJpaPersistido, prestadorJpaPersistido, "Avaliação Para Deletar", 1);
        int idParaDeletar = avalJpa.getId();

        assertTrue(avaliacaoJpaRepository.existsById(idParaDeletar), "Avaliação deveria existir antes de deletar.");
        avaliacaoRepositoryImpl.delete(idParaDeletar);
        entityManager.flush(); // Força a execução do delete no banco
        entityManager.clear(); // Limpa o contexto para que a próxima verificação seja do banco

        assertFalse(avaliacaoJpaRepository.existsById(idParaDeletar), "Avaliação não deveria existir após deletar.");
    }

    @Test
    void deveListarTodasAsAvaliacoes() {
        persistAvaliacaoJpaCompleta(clienteJpaPersistido, prestadorJpaPersistido, "Comentário Todas 1", 5);

        PrestadorJpa outroPrestadorJpa = new PrestadorJpa("Outro Prestador Todas", "sOPT", "opt@test.com", "telOPT", new EnderecoJpa("Rua OPT","Bairro OPT","Cidade OPT","OPT"));
        outroPrestadorJpa = entityManager.persistFlushFind(outroPrestadorJpa);
        ClienteJpa outroClienteJpa = new ClienteJpa("Outro Cliente Todas", "sOCT", "oct@test.com", "telOCT", new EnderecoJpa("Rua OCT","Bairro OCT","Cidade OCT","OCT"));
        outroClienteJpa = entityManager.persistFlushFind(outroClienteJpa);
        persistAvaliacaoJpaCompleta(outroClienteJpa, outroPrestadorJpa, "Comentário Todas 2", 4);

        List<AvaliacaoSobreCliente> todas = avaliacaoRepositoryImpl.findAll();
        assertNotNull(todas, "A lista de todas as avaliações não deveria ser nula.");
        assertEquals(2, todas.size(), "Deveria haver 2 avaliações no total.");
    }
}