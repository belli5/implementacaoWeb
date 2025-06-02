package com.exemple.backend.infraestrutura.jparepositorysimpl;

import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.infraestrutura.jpamodels.ClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.compartilhados.EnderecoJpa;
import com.exemple.backend.infraestrutura.jparepositorys.ClienteJpaRepository; // Para limpar e verificar
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import; // Importante

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(ClienteRepositoryImpl.class) // Importa a implementação para o contexto de teste
class ClienteRepositoryImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClienteRepositoryImpl clienteRepositoryImpl; // A classe sendo testada

    @Autowired
    private ClienteJpaRepository clienteJpaRepository; // Para setup/teardown direto se necessário

    private Endereco enderecoDominio;
    private Cliente clienteDominioParaSalvar;

    @BeforeEach
    void setUp() {
        clienteJpaRepository.deleteAll(); // Garante um estado limpo

        enderecoDominio = new Endereco("Rua Impl Teste", "Bairro IT", "Cidade IT", "IT");
        // Cliente de domínio para ser usado nos métodos 'save' e 'update'
        // ID é nulo aqui, pois esperamos que seja gerado ao salvar
        clienteDominioParaSalvar = new Cliente(null, "Dominio Impl User", "senhaImpl", "domimpl@example.com", "111222", enderecoDominio);
    }

    @Test
    void deveSalvarClienteDominioERetornarClienteDominioComId() {
        Cliente clienteSalvo = clienteRepositoryImpl.save(clienteDominioParaSalvar);

        assertNotNull(clienteSalvo);
        assertNotNull(clienteSalvo.getId(), "ID deveria ser gerado após salvar");
        assertEquals(clienteDominioParaSalvar.getNome(), clienteSalvo.getNome());
        assertEquals(clienteDominioParaSalvar.getEmail(), clienteSalvo.getEmail());
        assertNotNull(clienteSalvo.getEndereco());
        assertEquals(enderecoDominio.getRua(), clienteSalvo.getEndereco().getRua());

        // Verifica se foi realmente salvo no banco
        Optional<ClienteJpa> persistido = clienteJpaRepository.findById(clienteSalvo.getId());
        assertTrue(persistido.isPresent());
        assertEquals(clienteDominioParaSalvar.getNome(), persistido.get().getNome());
    }

    @Test
    void deveEncontrarClientePorIdERetornarOptionalDeClienteDominio() {
        // Setup: Salva um cliente JPA diretamente para ter um ID conhecido
        ClienteJpa clienteJpaPersistido = new ClienteJpa();
        clienteJpaPersistido.setNome("Cliente Para Buscar");
        clienteJpaPersistido.setEmail("buscar@example.com");
        clienteJpaPersistido.setSenha("buscarSenha");
        clienteJpaPersistido.setTelefone("333444");
        clienteJpaPersistido.setEndereco(new EnderecoJpa("Rua Buscar", "Bairro B", "Cidade B", "BB"));
        clienteJpaPersistido = entityManager.persistFlushFind(clienteJpaPersistido);
        assertNotNull(clienteJpaPersistido.getId());

        Optional<Cliente> encontrado = clienteRepositoryImpl.findById(clienteJpaPersistido.getId());

        assertTrue(encontrado.isPresent());
        assertEquals(clienteJpaPersistido.getNome(), encontrado.get().getNome());
        assertEquals(clienteJpaPersistido.getEmail(), encontrado.get().getEmail());
        assertNotNull(encontrado.get().getEndereco());
        assertEquals("Rua Buscar", encontrado.get().getEndereco().getRua());
    }

    @Test
    void deveRetornarOptionalEmptySeClienteNaoEncontradoPorId() {
        Optional<Cliente> encontrado = clienteRepositoryImpl.findById(99999); // ID inexistente
        assertFalse(encontrado.isPresent());
    }

    @Test
    void deveEncontrarTodosOsClientesERetornarListaDeClienteDominio() {
        // Setup: Salva alguns clientes JPA
        ClienteJpa cjpa1 = new ClienteJpa("Cliente Um Listar", "s1", "c1l@example.com", "t1", new EnderecoJpa("R1", "B1", "C1", "E1"));
        ClienteJpa cjpa2 = new ClienteJpa("Cliente Dois Listar", "s2", "c2l@example.com", "t2", new EnderecoJpa("R2", "B2", "C2", "E2"));
        entityManager.persist(cjpa1);
        entityManager.persist(cjpa2);
        entityManager.flush();

        List<Cliente> todos = clienteRepositoryImpl.findAll();

        assertNotNull(todos);
        assertEquals(2, todos.size());
        assertTrue(todos.stream().anyMatch(c -> "Cliente Um Listar".equals(c.getNome())));
        assertTrue(todos.stream().anyMatch(c -> "Cliente Dois Listar".equals(c.getNome())));
    }

    @Test
    void deveAtualizarClienteDominioERetornarClienteDominioAtualizado() {
        // Primeiro, salva um cliente
        Cliente clienteSalvoInicial = clienteRepositoryImpl.save(clienteDominioParaSalvar);
        assertNotNull(clienteSalvoInicial.getId());

        // Cria um objeto de domínio com informações atualizadas, usando o ID do salvo
        Cliente clienteParaAtualizar = new Cliente(
                clienteSalvoInicial.getId(),
                "Nome Atualizado Impl",
                "novaSenhaImpl",
                "emailatualizadoimpl@example.com",
                "555666",
                new Endereco("Rua Atualizada", "Bairro Att", "Cidade Att", "AT")
        );

        Cliente clienteRealmenteAtualizado = clienteRepositoryImpl.update(clienteParaAtualizar);

        assertNotNull(clienteRealmenteAtualizado);
        assertEquals(clienteSalvoInicial.getId(), clienteRealmenteAtualizado.getId());
        assertEquals("Nome Atualizado Impl", clienteRealmenteAtualizado.getNome());
        assertEquals("emailatualizadoimpl@example.com", clienteRealmenteAtualizado.getEmail());
        assertNotNull(clienteRealmenteAtualizado.getEndereco());
        assertEquals("Rua Atualizada", clienteRealmenteAtualizado.getEndereco().getRua());

        // Verifica no banco
        Optional<ClienteJpa> persistido = clienteJpaRepository.findById(clienteSalvoInicial.getId());
        assertTrue(persistido.isPresent());
        assertEquals("Nome Atualizado Impl", persistido.get().getNome());
    }

    @Test
    void deveDeletarClientePorId() {
        // Salva um cliente para poder deletá-lo
        Cliente clienteSalvo = clienteRepositoryImpl.save(clienteDominioParaSalvar);
        int idParaDeletar = clienteSalvo.getId();

        assertTrue(clienteJpaRepository.existsById(idParaDeletar), "Cliente deveria existir antes de deletar");

        clienteRepositoryImpl.delete(idParaDeletar);

        assertFalse(clienteJpaRepository.existsById(idParaDeletar), "Cliente não deveria existir após deletar");
    }
}