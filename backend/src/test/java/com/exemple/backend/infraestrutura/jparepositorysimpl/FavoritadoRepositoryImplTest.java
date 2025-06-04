package com.exemple.backend.infraestrutura.jparepositorysimpl;

import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Favoritado;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.infraestrutura.jpamodels.ClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.FavoritadoJpa;
import com.exemple.backend.infraestrutura.jpamodels.PrestadorJpa;
import com.exemple.backend.infraestrutura.jpamodels.compartilhados.EnderecoJpa;
import com.exemple.backend.infraestrutura.jparepositorys.ClienteJpaRepository;
import com.exemple.backend.infraestrutura.jparepositorys.FavoritadoJpaRepository;
import com.exemple.backend.infraestrutura.jparepositorys.PrestadorJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import; // Certifique-se que esta importação está presente
import org.springframework.dao.DataIntegrityViolationException; // Para outros testes, se necessário

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(FavoritadoRepositoryImpl.class)
class FavoritadoRepositoryImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FavoritadoRepositoryImpl favoritadoRepositoryImpl;

    @Autowired
    private FavoritadoJpaRepository favoritadoJpaRepository;

    @Autowired
    private ClienteJpaRepository clienteJpaRepository;
    @Autowired
    private PrestadorJpaRepository prestadorJpaRepository;

    private Cliente clienteDominio;
    private Prestador prestadorDominio;
    private Favoritado favoritadoDominioParaSalvar;

    private ClienteJpa clienteJpaPersistido;
    private PrestadorJpa prestadorJpaPersistido;


    @BeforeEach
    void setUp() {
        favoritadoJpaRepository.deleteAll();
        clienteJpaRepository.deleteAll();
        prestadorJpaRepository.deleteAll();
        entityManager.flush();
        entityManager.clear();

        Endereco endereco = new Endereco("Rua FavImpl", "BFI", "CFI", "FI");
        EnderecoJpa endJpa = new EnderecoJpa(endereco.getRua(), endereco.getBairro(), endereco.getCidade(), endereco.getEstado());

        ClienteJpa cJpa = new ClienteJpa();
        cJpa.setNome("Cli FavImpl");
        cJpa.setSenha("sCFI");
        cJpa.setEmail("cfi@e.com");
        cJpa.setTelefone("tcfi");
        cJpa.setEndereco(endJpa);
        clienteJpaPersistido = entityManager.persistFlushFind(cJpa);

        PrestadorJpa pJpa = new PrestadorJpa();
        pJpa.setNome("Pre FavImpl");
        pJpa.setSenha("sPFI");
        pJpa.setEmail("pfi@e.com");
        pJpa.setTelefone("tpfi");
        pJpa.setEndereco(endJpa);
        prestadorJpaPersistido = entityManager.persistFlushFind(pJpa);

        clienteDominio = new Cliente(clienteJpaPersistido.getId(), cJpa.getNome(), cJpa.getSenha(), cJpa.getEmail(), cJpa.getTelefone(), endereco);
        prestadorDominio = new Prestador(prestadorJpaPersistido.getId(), pJpa.getNome(), pJpa.getSenha(), pJpa.getEmail(), pJpa.getTelefone(), endereco);

        favoritadoDominioParaSalvar = new Favoritado(0, clienteDominio, prestadorDominio);
    }

    @Test
    void deveSalvarFavoritadoCorretamente() {
        Favoritado salvo = favoritadoRepositoryImpl.save(favoritadoDominioParaSalvar);

        assertNotNull(salvo);
        assertTrue(salvo.getId() > 0, "ID do Favoritado salvo deveria ser maior que zero.");

        // AJUSTE: Esperar que Cliente e Prestador NÃO sejam nulos, e verificar seus IDs
        assertNotNull(salvo.getCliente(), "Cliente no Favoritado de domínio retornado NÃO deveria ser nulo.");
        assertEquals(clienteDominio.getId(), salvo.getCliente().getId(), "ID do Cliente no Favoritado de domínio não corresponde.");

        assertNotNull(salvo.getPrestador(), "Prestador no Favoritado de domínio retornado NÃO deveria ser nulo.");
        assertEquals(prestadorDominio.getId(), salvo.getPrestador().getId(), "ID do Prestador no Favoritado de domínio não corresponde.");

        Optional<FavoritadoJpa> persistidoOpt = favoritadoJpaRepository.findById(salvo.getId());
        assertTrue(persistidoOpt.isPresent());
        FavoritadoJpa persistido = persistidoOpt.get();
        assertNotNull(persistido.getCliente());
        assertEquals(clienteJpaPersistido.getId(), persistido.getCliente().getId());
        assertNotNull(persistido.getPrestador());
        assertEquals(prestadorJpaPersistido.getId(), persistido.getPrestador().getId());
    }

    @Test
    void deveLancarExcecaoAoSalvarFavoritadoComClienteInexistente() {
        Cliente clienteInexistente = new Cliente();
        clienteInexistente.setId(99999); // ID que não existe
        clienteInexistente.setNome("Cliente Fantasma");
        // Preencha outros campos obrigatórios do Cliente se o construtor ou setters validarem
        clienteInexistente.setEmail("fantasma@example.com");
        clienteInexistente.setSenha("senhafantasma");
        clienteInexistente.setTelefone("00000");
        clienteInexistente.setEndereco(new Endereco("Rua Nula", "Bairro Nulo", "Cidade Nula", "NN"));

        Favoritado favComCliInexistente = new Favoritado(0, clienteInexistente, prestadorDominio);

        // A exceção depende da implementação de FavoritadoRepositoryImpl.save()
        // Geralmente, ao não encontrar uma FK, pode lançar DataIntegrityViolationException no flush,
        // ou uma exceção customizada antes disso. O seu log indica IllegalArgumentException.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            favoritadoRepositoryImpl.save(favComCliInexistente);
        });
        // AJUSTE: Corrigir a mensagem da exceção esperada
        assertEquals("Cliente não encontrado", exception.getMessage(), "A mensagem de exceção para cliente não encontrado não corresponde.");
    }

    // TODO: Implementar testes para findById, findAll, findClientesQueFavoritaramPrestadorByPrestadorId,
    // findPrestadoresFavoritadosByClienteId e delete, ajustando as asserções conforme o comportamento real dos mappers.
    // Exemplo para findById:
    @Test
    void deveEncontrarFavoritadoPorId() {
        FavoritadoJpa favJpa = new FavoritadoJpa();
        favJpa.setCliente(clienteJpaPersistido);
        favJpa.setPrestador(prestadorJpaPersistido);
        favJpa = entityManager.persistFlushFind(favJpa);

        Optional<Favoritado> encontradoOpt = favoritadoRepositoryImpl.findById(favJpa.getId());
        assertTrue(encontradoOpt.isPresent());
        Favoritado encontrado = encontradoOpt.get();
        assertEquals(favJpa.getId(), encontrado.getId());
        assertNotNull(encontrado.getCliente());
        assertEquals(clienteJpaPersistido.getId(), encontrado.getCliente().getId());
        assertNotNull(encontrado.getPrestador());
        assertEquals(prestadorJpaPersistido.getId(), encontrado.getPrestador().getId());
    }
}