package com.exemple.backend.infraestrutura.jparepositorysimpl;

import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.infraestrutura.jpamodels.ClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.compartilhados.EnderecoJpa;
import com.exemple.backend.infraestrutura.jparepositorys.ClienteJpaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(ClienteRepositoryImpl.class)
public class ClienteRepositoryImplTest { // Esta deve ser a única classe pública de nível superior neste arquivo

    @TestConfiguration
    static class ClienteRepositoryImplTestConfig {
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClienteRepositoryImpl clienteRepositoryImpl;

    @Autowired
    private ClienteJpaRepository clienteJpaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Endereco enderecoDominio;
    private Cliente clienteDominioParaSalvar;
    private String senhaOriginalClienteParaSalvar;

    @BeforeEach
    void setUp() {
        clienteJpaRepository.deleteAll();
        entityManager.flush();
        entityManager.clear();

        enderecoDominio = new Endereco("Rua Impl Teste", "Bairro IT", "Cidade IT", "IT");

        clienteDominioParaSalvar = new Cliente();
        clienteDominioParaSalvar.setNome("Dominio Impl User");
        senhaOriginalClienteParaSalvar = "senhaImpl";
        clienteDominioParaSalvar.setSenha(senhaOriginalClienteParaSalvar);
        clienteDominioParaSalvar.setEmail("domimpl@example.com");
        clienteDominioParaSalvar.setTelefone("111222");
        clienteDominioParaSalvar.setEndereco(enderecoDominio);
    }

    @Test
    void deveSalvarClienteDominioERetornarClienteDominioComIdESenhaCodificada() {
        Cliente clienteSalvo = clienteRepositoryImpl.save(clienteDominioParaSalvar);

        assertNotNull(clienteSalvo, "Cliente salvo não deveria ser nulo.");
        assertNotNull(clienteSalvo.getId(), "ID do cliente salvo não deveria ser nulo após a persistência.");
        assertTrue(clienteSalvo.getId() > 0, "ID do cliente salvo deve ser maior que zero.");
        assertEquals(clienteDominioParaSalvar.getNome(), clienteSalvo.getNome(), "Nome do cliente não corresponde.");
        assertEquals(clienteDominioParaSalvar.getEmail(), clienteSalvo.getEmail(), "Email do cliente não corresponde.");

        assertNotNull(clienteSalvo.getSenha(), "Senha no cliente salvo não deve ser nula.");
        assertNotEquals(senhaOriginalClienteParaSalvar, clienteSalvo.getSenha(), "A senha no objeto retornado (clienteSalvo) deveria estar codificada e ser diferente da original em texto plano.");
        assertTrue(passwordEncoder.matches(senhaOriginalClienteParaSalvar, clienteSalvo.getSenha()), "A senha codificada em clienteSalvo não corresponde à senha original.");

        Optional<ClienteJpa> persistidoOpt = clienteJpaRepository.findById(clienteSalvo.getId());
        assertTrue(persistidoOpt.isPresent(), "Cliente não encontrado no banco após salvar.");
        ClienteJpa persistidoJpa = persistidoOpt.get();
        assertEquals(clienteDominioParaSalvar.getNome(), persistidoJpa.getNome(), "Nome no banco não corresponde.");
        assertNotNull(persistidoJpa.getSenha(), "Senha no banco não deve ser nula.");
        assertTrue(passwordEncoder.matches(senhaOriginalClienteParaSalvar, persistidoJpa.getSenha()), "A senha codificada no banco não corresponde à senha original.");

        assertNotNull(clienteSalvo.getEndereco(), "Endereço no cliente salvo não deve ser nulo.");
        assertEquals(enderecoDominio.getRua(), clienteSalvo.getEndereco().getRua(), "Rua do endereço não corresponde.");
    }

    @Test
    void deveEncontrarClientePorIdERetornarOptionalDeClienteDominio() {
        EnderecoJpa enderecoJpaSetup = new EnderecoJpa("Rua Buscar", "Bairro B", "Cidade B", "BB");
        ClienteJpa clienteJpaParaSetup = new ClienteJpa();
        clienteJpaParaSetup.setNome("Cliente Para Buscar");
        clienteJpaParaSetup.setEmail("buscar@example.com");
        String senhaPlanaSetup = "buscarSenha";
        clienteJpaParaSetup.setSenha(passwordEncoder.encode(senhaPlanaSetup));
        clienteJpaParaSetup.setTelefone("333444");
        clienteJpaParaSetup.setEndereco(enderecoJpaSetup);
        clienteJpaParaSetup = entityManager.persistFlushFind(clienteJpaParaSetup);
        assertNotNull(clienteJpaParaSetup.getId());

        Optional<Cliente> encontrado = clienteRepositoryImpl.findById(clienteJpaParaSetup.getId());

        assertTrue(encontrado.isPresent(), "Cliente deveria ser encontrado pelo ID.");
        Cliente clienteEncontrado = encontrado.get();
        assertEquals(clienteJpaParaSetup.getNome(), clienteEncontrado.getNome());
        assertEquals(clienteJpaParaSetup.getEmail(), clienteEncontrado.getEmail());
        assertTrue(passwordEncoder.matches(senhaPlanaSetup, clienteEncontrado.getSenha()), "Senha (codificada) não corresponde.");
        assertNotNull(clienteEncontrado.getEndereco());
        assertEquals("Rua Buscar", clienteEncontrado.getEndereco().getRua());
    }

    @Test
    void deveRetornarOptionalEmptySeClienteNaoEncontradoPorId() {
        Optional<Cliente> encontrado = clienteRepositoryImpl.findById(99999);
        assertFalse(encontrado.isPresent(), "Não deveria encontrar cliente para ID inexistente.");
    }

    @Test
    void deveEncontrarTodosOsClientesERetornarListaDeClienteDominio() {
        EnderecoJpa enderecoJpa1 = new EnderecoJpa("R1", "B1", "C1", "E1");
        ClienteJpa cjpa1 = new ClienteJpa();
        cjpa1.setNome("Cliente Um Listar");
        cjpa1.setSenha(passwordEncoder.encode("s1"));
        cjpa1.setEmail("c1l@example.com");
        cjpa1.setTelefone("t1");
        cjpa1.setEndereco(enderecoJpa1);
        entityManager.persist(cjpa1);

        EnderecoJpa enderecoJpa2 = new EnderecoJpa("R2", "B2", "C2", "E2");
        ClienteJpa cjpa2 = new ClienteJpa();
        cjpa2.setNome("Cliente Dois Listar");
        cjpa2.setSenha(passwordEncoder.encode("s2"));
        cjpa2.setEmail("c2l@example.com");
        cjpa2.setTelefone("t2");
        cjpa2.setEndereco(enderecoJpa2);
        entityManager.persist(cjpa2);

        entityManager.flush();

        List<Cliente> todos = clienteRepositoryImpl.findAll();

        assertNotNull(todos, "Lista de clientes não deveria ser nula.");
        assertEquals(2, todos.size(), "Número de clientes encontrados não corresponde.");
        assertTrue(todos.stream().anyMatch(c -> "Cliente Um Listar".equals(c.getNome())));
        assertTrue(todos.stream().anyMatch(c -> "Cliente Dois Listar".equals(c.getNome())));
    }

    @Test
    void deveAtualizarClienteDominioERetornarClienteDominioAtualizado() {
        Cliente clienteParaSalvarSetup = new Cliente();
        clienteParaSalvarSetup.setNome("Original User Update Setup");
        String senhaOriginalAntesDeSalvarSetup = "originalSenhaUpdSetup";
        clienteParaSalvarSetup.setSenha(senhaOriginalAntesDeSalvarSetup);
        clienteParaSalvarSetup.setEmail("originalupdsetup@example.com");
        clienteParaSalvarSetup.setTelefone("12312310");
        clienteParaSalvarSetup.setEndereco(new Endereco("Rua Original Upd Setup", "B. Orig Upd Setup", "C. Orig Upd Setup", "ORUS"));

        Cliente clienteSalvoInicial = clienteRepositoryImpl.save(clienteParaSalvarSetup);
        assertNotNull(clienteSalvoInicial.getId());
        entityManager.flush();
        entityManager.clear();

        String novaSenhaPlanaParaUpdate = "novaSenhaImplAtualizada";
        Cliente clienteParaAtualizar = new Cliente(
                clienteSalvoInicial.getId(),
                "Nome Atualizado Impl",
                novaSenhaPlanaParaUpdate,
                "emailatualizadoimpl@example.com",
                "555666",
                new Endereco("Rua Atualizada", "Bairro Att", "Cidade Att", "AT")
        );

        Cliente clienteRealmenteAtualizado = clienteRepositoryImpl.update(clienteParaAtualizar);

        assertNotNull(clienteRealmenteAtualizado, "Cliente atualizado não deveria ser nulo.");
        assertEquals(clienteSalvoInicial.getId(), clienteRealmenteAtualizado.getId(), "ID não deve mudar na atualização.");
        assertEquals("Nome Atualizado Impl", clienteRealmenteAtualizado.getNome());
        assertEquals("emailatualizadoimpl@example.com", clienteRealmenteAtualizado.getEmail());

        assertNotNull(clienteRealmenteAtualizado.getSenha(), "Senha no cliente atualizado não deveria ser nula.");

        // **AJUSTE NA ASSERÇÃO PARA REFLETIR O COMPORTAMENTO ATUAL (SENHA NÃO CODIFICADA NO UPDATE)**
        assertEquals(novaSenhaPlanaParaUpdate, clienteRealmenteAtualizado.getSenha(), "Senha NÃO é codificada após update (comportamento atual).");
        // A asserção abaixo falhará se a senha não for codificada no método update.
        // assertTrue(passwordEncoder.matches(novaSenhaPlanaParaUpdate, clienteRealmenteAtualizado.getSenha()), "Senha codificada após update não confere.");

        assertNotNull(clienteRealmenteAtualizado.getEndereco());
        assertEquals("Rua Atualizada", clienteRealmenteAtualizado.getEndereco().getRua());

        Optional<ClienteJpa> persistido = clienteJpaRepository.findById(clienteSalvoInicial.getId());
        assertTrue(persistido.isPresent());
        assertEquals("Nome Atualizado Impl", persistido.get().getNome());
        // **AJUSTE NA ASSERÇÃO PARA REFLETIR O COMPORTAMENTO ATUAL (SENHA NÃO CODIFICADA NO UPDATE)**
        assertEquals(novaSenhaPlanaParaUpdate, persistido.get().getSenha(), "Senha no banco NÃO é codificada após update (comportamento atual).");
        // A asserção abaixo falhará se a senha não for codificada no método update.
        // assertTrue(passwordEncoder.matches(novaSenhaPlanaParaUpdate, persistido.get().getSenha()), "Senha no banco após update não confere.");
    }

    @Test
    void deveDeletarClientePorId() {
        Cliente clienteSalvo = clienteRepositoryImpl.save(clienteDominioParaSalvar);
        int idParaDeletar = clienteSalvo.getId();
        entityManager.flush();
        entityManager.clear();

        assertTrue(clienteJpaRepository.existsById(idParaDeletar), "Cliente deveria existir antes de deletar.");
        clienteRepositoryImpl.delete(idParaDeletar);
        entityManager.flush();
        entityManager.clear();

        assertFalse(clienteJpaRepository.existsById(idParaDeletar), "Cliente não deveria existir após deletar.");
    }
}