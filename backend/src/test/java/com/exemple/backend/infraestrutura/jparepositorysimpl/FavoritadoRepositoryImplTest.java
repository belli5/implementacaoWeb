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
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({FavoritadoRepositoryImpl.class, ClienteJpaRepository.class, PrestadorJpaRepository.class})
// Precisa importar ClienteJpaRepository e PrestadorJpaRepository para que o FavoritadoRepositoryImpl possa injetá-los.
// Alternativamente, poderia mocká-los se não quisesse testar a busca real deles dentro do save.
// Mas como estamos com @DataJpaTest, testar a interação real é mais apropriado.
class FavoritadoRepositoryImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FavoritadoRepositoryImpl favoritadoRepositoryImpl;

    @Autowired
    private FavoritadoJpaRepository favoritadoJpaRepository; // Para limpar

    // Repositórios injetados no FavoritadoRepositoryImpl
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
        clienteJpaRepository.deleteAll(); // Limpar dependências também
        prestadorJpaRepository.deleteAll();


        Endereco endereco = new Endereco("Rua FavImpl", "BFI", "CFI", "FI");

        // Persistir ClienteJpa e PrestadorJpa para que o save do FavoritadoRepositoryImpl os encontre
        EnderecoJpa endJpa = new EnderecoJpa(endereco.getRua(), endereco.getBairro(), endereco.getCidade(), endereco.getEstado());
        ClienteJpa cJpa = new ClienteJpa("Cli FavImpl", "sCFI", "cfi@e.com", "tcfi", endJpa);
        clienteJpaPersistido = entityManager.persistFlushFind(cJpa);

        PrestadorJpa pJpa = new PrestadorJpa("Pre FavImpl", "sPFI", "pfi@e.com", "tpfi", endJpa);
        prestadorJpaPersistido = entityManager.persistFlushFind(pJpa);

        // Criar objetos de domínio com IDs corretos
        clienteDominio = new Cliente(clienteJpaPersistido.getId(), cJpa.getNome(), cJpa.getSenha(), cJpa.getEmail(), cJpa.getTelefone(), endereco);
        prestadorDominio = new Prestador(prestadorJpaPersistido.getId(), pJpa.getNome(), pJpa.getSenha(), pJpa.getEmail(), pJpa.getTelefone(), endereco);

        favoritadoDominioParaSalvar = new Favoritado(0, clienteDominio, prestadorDominio);
    }

    @Test
    void deveSalvarFavoritadoCorretamente() {
        Favoritado salvo = favoritadoRepositoryImpl.save(favoritadoDominioParaSalvar);

        assertNotNull(salvo);
        assertTrue(salvo.getId() > 0);
        // O FavoritadoMapper.toFavoritado retorna cliente e prestador como null
        assertNull(salvo.getCliente());
        assertNull(salvo.getPrestador());

        // Verifica se o FavoritadoJpa foi salvo com as FKs corretas
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
        Cliente clienteInexistente = new Cliente(999, "Inexistente", "s", "i@e.com", "t", new Endereco("R","B","C","E"));
        Favoritado favComCliInexistente = new Favoritado(0, clienteInexistente, prestadorDominio);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            favoritadoRepositoryImpl.save(favComCliInexistente);
        });
        assertEquals("Cliente não encontrado", exception.getMessage());
    }

    // Testes para findById, findAll, findClientesQueFavoritaramPrestadorByPrestadorId,
    // findPrestadoresFavoritadosByClienteId e delete seguiriam padrões similares,
    // lembrando que os mappers podem retornar objetos de domínio com sub-entidades nulas.
}