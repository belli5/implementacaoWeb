package com.exemple.backend.infraestrutura.jpamodels;

import com.exemple.backend.infraestrutura.jpamodels.compartilhados.EnderecoJpa;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ClienteJpaTest {

    @Test
    void testConstrutorPadrao() {
        ClienteJpa cliente = new ClienteJpa();
        assertNotNull(cliente);
        assertEquals(0, cliente.getId(), "ID (int primitivo) deveria ser 0 por padrão");
        assertNull(cliente.getNome(), "Nome deveria ser nulo por padrão");
        assertNull(cliente.getSenha(), "Senha deveria ser nula por padrão");
        assertNull(cliente.getEmail(), "Email deveria ser nulo por padrão");
        assertNull(cliente.getTelefone(), "Telefone deveria ser nulo por padrão");
        assertNull(cliente.getEndereco(), "Endereco deveria ser nulo por padrão");
        assertNull(cliente.getAvaliacoesRecebidas(), "AvaliacoesRecebidas deveria ser nulo por padrão");
        assertNull(cliente.getAvaliacoesFeitas(), "AvaliacoesFeitas deveria ser nulo por padrão");
        assertNull(cliente.getFavoritados(), "Favoritados deveria ser nulo por padrão");
        assertNull(cliente.getPedidosFeitos(), "PedidosFeitos deveria ser nulo por padrão");
    }

    @Test
    void testConstrutorComArgumentos() {
        EnderecoJpa endereco = new EnderecoJpa("Rua Cliente Arg", "Bairro CliArg", "Cidade CliArg", "CA");
        ClienteJpa cliente = new ClienteJpa("João Argumento", "senhaArg", "joaoarg@teste.com", "987000000", endereco);

        // ID não é definido por este construtor, será 0
        assertEquals(0, cliente.getId());
        assertEquals("João Argumento", cliente.getNome());
        assertEquals("senhaArg", cliente.getSenha());
        assertEquals("joaoarg@teste.com", cliente.getEmail());
        assertEquals("987000000", cliente.getTelefone());
        assertSame(endereco, cliente.getEndereco());
        // Listas não são inicializadas por este construtor
        assertNull(cliente.getAvaliacoesRecebidas());
        assertNull(cliente.getAvaliacoesFeitas());
        assertNull(cliente.getFavoritados());
        assertNull(cliente.getPedidosFeitos());
    }

    @Test
    void testGettersAndSetters() {
        ClienteJpa cliente = new ClienteJpa();
        cliente.setId(10);
        cliente.setNome("Maria Silva");
        cliente.setSenha("outrasenha");
        cliente.setEmail("maria@silva.com");
        cliente.setTelefone("123123123");

        EnderecoJpa endereco = new EnderecoJpa("Rua Set", "Bairro Set", "Cidade Set", "ST");
        cliente.setEndereco(endereco);

        List<AvaliacaoSobreClienteJpa> avalRecebidas = new ArrayList<>();
        AvaliacaoSobreClienteJpa ar1 = new AvaliacaoSobreClienteJpa();
        avalRecebidas.add(ar1);
        cliente.setAvaliacoesRecebidas(avalRecebidas);

        List<AvaliacaoSobrePrestadorJpa> avalFeitas = new ArrayList<>();
        AvaliacaoSobrePrestadorJpa af1 = new AvaliacaoSobrePrestadorJpa();
        avalFeitas.add(af1);
        cliente.setAvaliacoesFeitas(avalFeitas);

        List<FavoritadoJpa> favoritados = new ArrayList<>();
        FavoritadoJpa f1 = new FavoritadoJpa();
        favoritados.add(f1);
        cliente.setFavoritados(favoritados);

        List<PedidoJpa> pedidos = new ArrayList<>();
        PedidoJpa p1 = new PedidoJpa();
        pedidos.add(p1);
        cliente.setPedidosFeitos(pedidos);

        assertEquals(10, cliente.getId());
        assertEquals("Maria Silva", cliente.getNome());
        assertEquals("outrasenha", cliente.getSenha());
        assertEquals("maria@silva.com", cliente.getEmail());
        assertEquals("123123123", cliente.getTelefone());
        assertSame(endereco, cliente.getEndereco());

        assertNotNull(cliente.getAvaliacoesRecebidas());
        assertEquals(1, cliente.getAvaliacoesRecebidas().size());
        assertSame(ar1, cliente.getAvaliacoesRecebidas().get(0));

        assertNotNull(cliente.getAvaliacoesFeitas());
        assertEquals(1, cliente.getAvaliacoesFeitas().size());
        assertSame(af1, cliente.getAvaliacoesFeitas().get(0));

        assertNotNull(cliente.getFavoritados());
        assertEquals(1, cliente.getFavoritados().size());
        assertSame(f1, cliente.getFavoritados().get(0));

        assertNotNull(cliente.getPedidosFeitos());
        assertEquals(1, cliente.getPedidosFeitos().size());
        assertSame(p1, cliente.getPedidosFeitos().get(0));
    }

    @Test
    void testToString() {
        EnderecoJpa endereco = new EnderecoJpa("Rua Xyz", "Bairro Yzx", "Cidade Zxy", "WZY");
        ClienteJpa cliente = new ClienteJpa("Carlos ToString", "abcAbc", "carlostostring@email.com", "111222333", endereco);
        cliente.setId(1);

        String enderecoStr = endereco.toString();
        String expected = "ClienteJpa{id=1, nome='Carlos ToString', email='carlostostring@email.com', telefone='111222333', endereco=" + enderecoStr + "}"; //
        assertEquals(expected, cliente.toString());
    }
}