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
@Import({AvaliacaoSobrePrestadorRepositoryImpl.class, ClienteJpaRepository.class, PrestadorJpaRepository.class})
class AvaliacaoSobrePrestadorRepositoryImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AvaliacaoSobrePrestadorRepositoryImpl avaliacaoRepositoryImpl;

    @Autowired
    private AvaliacaoSobrePrestadorJpaRepository avaliacaoJpaRepository; // Para limpar

    // Repositórios para persistir ClienteJpa e PrestadorJpa
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

        Endereco endereco = new Endereco("Rua AvalP Impl", "BAPI", "CAPI", "API");

        // Persistir ClienteJpa e PrestadorJpa
        EnderecoJpa endJpa = new EnderecoJpa(endereco.getRua(), endereco.getBairro(), endereco.getCidade(), endereco.getEstado());
        ClienteJpa cJpa = new ClienteJpa("Cli AvalP Impl", "sCAPI", "capi@e.com", "tcapi", endJpa);
        clienteJpaPersistido = entityManager.persistFlushFind(cJpa);

        PrestadorJpa pJpa = new PrestadorJpa("Pre AvalP Impl", "sPAPI", "papi@e.com", "tpapi", endJpa);
        prestadorJpaPersistido = entityManager.persistFlushFind(pJpa);

        // Criar objetos de domínio com IDs corretos
        clienteDominio = new Cliente(clienteJpaPersistido.getId(), cJpa.getNome(), cJpa.getSenha(), cJpa.getEmail(), cJpa.getTelefone(), endereco);
        prestadorDominio = new Prestador(prestadorJpaPersistido.getId(), pJpa.getNome(), pJpa.getSenha(), pJpa.getEmail(), pJpa.getTelefone(), endereco);

        // O ID da avaliação é gerado, então pode ser null ou 0 no objeto de domínio
        avaliacaoDominioParaSalvar = new AvaliacaoSobrePrestador(null, clienteDominio, "Comentário Impl", 5, prestadorDominio);
    }

    @Test
    void deveSalvarAvaliacaoERetornarComId() {
        // A implementação AvaliacaoSobrePrestadorRepositoryImpl.save faz o seguinte:
        // 1. Mapeia AvaliacaoSobrePrestador (domínio) para AvaliacaoSobrePrestadorJpa.
        //    O AvaliacaoSobrePrestadorMapper.toAvaliacaoSobrePrestadorJpa seta cliente e prestador como null no Jpa.
        // 2. Salva o AvaliacaoSobrePrestadorJpa. Isso significa que as FKs para cliente e prestador serão nulas no banco.
        //    Isso está alinhado com o schema V1__initial.sql que permite FKs nulas para AvaliacaoSobrePrestador (ON DELETE SET NULL).
        // 3. Mapeia o Jpa salvo de volta para o domínio.
        //    O AvaliacaoSobrePrestadorMapper.toAvaliacaoSobrePrestador retorna cliente e prestador como null no domínio.

        AvaliacaoSobrePrestador salvo = avaliacaoRepositoryImpl.save(avaliacaoDominioParaSalvar);

        assertNotNull(salvo);
        assertNotNull(salvo.getId());
        assertEquals(avaliacaoDominioParaSalvar.getComentario(), salvo.getComentario());
        assertEquals(avaliacaoDominioParaSalvar.getNota(), salvo.getNota());

        // Devido ao comportamento do mapper, cliente e prestador no objeto de domínio retornado serão null
        assertNull(salvo.getCliente());
        assertNull(salvo.getPrestador());

        // Verifica o que foi realmente salvo no banco
        Optional<AvaliacaoSobrePrestadorJpa> persistidoOpt = avaliacaoJpaRepository.findById(salvo.getId());
        assertTrue(persistidoOpt.isPresent());
        AvaliacaoSobrePrestadorJpa persistido = persistidoOpt.get();
        assertEquals(avaliacaoDominioParaSalvar.getComentario(), persistido.getComentario());
        assertEquals(avaliacaoDominioParaSalvar.getNota(), persistido.getNota());
        // As FKs no banco serão nulas devido ao mapeamento
        assertNull(persistido.getCliente());
        assertNull(persistido.getPrestador());
    }

    // Para testar findById, findAll, findByPrestadorId, precisamos persistir AvaliacaoSobrePrestadorJpa
    // com ClienteJpa e PrestadorJpa associados (se quisermos testar o filtro por eles no banco).
    // No entanto, o AvaliacaoSobrePrestadorRepositoryImpl.findByPrestadorId usa o
    // avaliacaoSobrePrestadorJpaRepository.findByPrestadorId, que filtra pelo ID do prestador no banco.
    // E o mapper, ao converter de volta para o domínio, anulará as referências a cliente e prestador.

    private AvaliacaoSobrePrestadorJpa persistAvaliacaoCompleta(ClienteJpa c, PrestadorJpa p, String comentario, int nota) {
        AvaliacaoSobrePrestadorJpa aval = new AvaliacaoSobrePrestadorJpa();
        aval.setCliente(c); // Associando para que a busca no banco funcione
        aval.setPrestador(p); // Associando para que a busca no banco funcione
        aval.setComentario(comentario);
        aval.setNota(nota);
        return entityManager.persistFlushFind(aval);
    }

    @Test
    void deveEncontrarAvaliacaoPorId() {
        AvaliacaoSobrePrestadorJpa avalJpa = persistAvaliacaoCompleta(clienteJpaPersistido, prestadorJpaPersistido, "Teste FindById", 4);

        Optional<AvaliacaoSobrePrestador> encontrado = avaliacaoRepositoryImpl.findById(avalJpa.getId());

        assertTrue(encontrado.isPresent());
        assertEquals(avalJpa.getId(), encontrado.get().getId());
        assertEquals("Teste FindById", encontrado.get().getComentario());
        assertEquals(4, encontrado.get().getNota());
        assertNull(encontrado.get().getCliente()); // Devido ao mapper
        assertNull(encontrado.get().getPrestador());// Devido ao mapper
    }

    @Test
    void deveEncontrarAvaliacoesPorPrestadorId() {
        // Avaliação 1 para prestadorJpaPersistido
        persistAvaliacaoCompleta(clienteJpaPersistido, prestadorJpaPersistido, "Comentário P1-C1", 5);
        // Avaliação 2 para prestadorJpaPersistido por outro cliente
        ClienteJpa outroClienteJpa = new ClienteJpa("Outro Cli", "s", "oc@e.com", "t", new EnderecoJpa());
        outroClienteJpa = entityManager.persistFlushFind(outroClienteJpa);
        persistAvaliacaoCompleta(outroClienteJpa, prestadorJpaPersistido, "Comentário P1-C2", 4);

        // Avaliação para outro prestador
        PrestadorJpa outroPrestadorJpa = new PrestadorJpa("Outro Pre", "s", "op@e.com", "t", new EnderecoJpa());
        outroPrestadorJpa = entityManager.persistFlushFind(outroPrestadorJpa);
        persistAvaliacaoCompleta(clienteJpaPersistido, outroPrestadorJpa, "Comentário P2-C1", 3);


        List<AvaliacaoSobrePrestador> encontradas = avaliacaoRepositoryImpl.findByPrestadorId(prestadorJpaPersistido.getId());

        assertNotNull(encontradas);
        assertEquals(2, encontradas.size());
        // Todos os objetos AvaliacaoSobrePrestador na lista terão cliente e prestador nulos devido ao mapper
        assertTrue(encontradas.stream().allMatch(a -> a.getCliente() == null && a.getPrestador() == null));
        // Podemos verificar os comentários para distinguir, se necessário
        assertTrue(encontradas.stream().anyMatch(a -> "Comentário P1-C1".equals(a.getComentario())));
        assertTrue(encontradas.stream().anyMatch(a -> "Comentário P1-C2".equals(a.getComentario())));
    }

    @Test
    void deveDeletarAvaliacao() {
        AvaliacaoSobrePrestadorJpa avalJpa = persistAvaliacaoCompleta(clienteJpaPersistido, prestadorJpaPersistido, "Para Deletar", 2);
        int idParaDeletar = avalJpa.getId();

        assertTrue(avaliacaoJpaRepository.existsById(idParaDeletar));
        avaliacaoRepositoryImpl.delete(idParaDeletar);
        assertFalse(avaliacaoJpaRepository.existsById(idParaDeletar));
    }
}