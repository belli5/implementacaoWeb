// src/test/java/com/exemple/backend/infraestrutura/jpamodels/PrestadorJpaTest.java

package com.exemple.backend.infraestrutura.jpamodels;

import com.exemple.backend.infraestrutura.jpamodels.compartilhados.EnderecoJpa;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PrestadorJpaTest {

    @Test
    void testConstrutorPadrao() {
        PrestadorJpa prestador = new PrestadorJpa();
        assertNotNull(prestador);
        // Se PrestadorJpa.id é Integer e o construtor padrão o deixa null:
        assertNull(prestador.getId(), "ID (como Integer) deveria ser nulo por padrão");

        assertNull(prestador.getNome(), "Nome deveria ser nulo por padrão");
        assertNull(prestador.getSenha(), "Senha deveria ser nula por padrão");
        assertNull(prestador.getEmail(), "Email deveria ser nulo por padrão");
        assertNull(prestador.getTelefone(), "Telefone deveria ser nulo por padrão");
        assertNull(prestador.getEndereco(), "Endereco deveria ser nulo por padrão");
        assertNull(prestador.getServicosOferecidos(), "ServicosOferecidos deveria ser nulo por padrão");
        assertNull(prestador.getAvaliacoesFeitas(), "AvaliacoesFeitas deveria ser nulo por padrão");
        assertNull(prestador.getAvaliacoesRecebidas(), "AvaliacoesRecebidas deveria ser nulo por padrão");
        assertNull(prestador.getFavoritadoPor(), "FavoritadoPor deveria ser nulo por padrão");
        assertNull(prestador.getPedidosRecebidos(), "PedidosRecebidos deveria ser nulo por padrão");
    }

    @Test
    void testConstrutorComArgumentosSemId() {
        EnderecoJpa endereco = new EnderecoJpa("Rua Prest Arg", "Bairro PArg", "Cidade PArg", "PA");
        // Este construtor não define o ID.
        PrestadorJpa prestador = new PrestadorJpa("Ana Prestadora Arg", "senhaPArg", "anaarg@prest.com", "333000111", endereco);

        // Se o campo id é Integer, ele será null aqui, pois este construtor não o define.
        assertNull(prestador.getId(), "ID (como Integer) deveria ser nulo se o construtor não o define");
        assertEquals("Ana Prestadora Arg", prestador.getNome());
        assertEquals("senhaPArg", prestador.getSenha());
        assertEquals("anaarg@prest.com", prestador.getEmail());
        assertEquals("333000111", prestador.getTelefone());
        assertSame(endereco, prestador.getEndereco());
        assertNull(prestador.getServicosOferecidos()); // Este construtor não inicializa listas
    }

    // Se você tiver um construtor que ACEITA um ID (e o campo id é Integer):
    // @Test
    // void testConstrutorComIdEArgumentos() {
    //     EnderecoJpa endereco = new EnderecoJpa("Rua Prest Arg ID", "Bairro PArgID", "Cidade PArgID", "PI");
    //     PrestadorJpa prestador = new PrestadorJpa(Integer.valueOf(1), "Carlos Prestador ID", "senhaPID", "carlosid@prest.com", "444111222", endereco);
    //     assertEquals(Integer.valueOf(1), prestador.getId());
    //     assertEquals("Carlos Prestador ID", prestador.getNome());
    //     // ... assert outros campos
    // }


    @Test
    void testGettersAndSetters() {
        PrestadorJpa prestador = new PrestadorJpa();

        // Testando com Integer para id
        prestador.setId(Integer.valueOf(20));
        assertEquals(Integer.valueOf(20), prestador.getId());

        prestador.setNome("Pedro Servicos Set");
        assertEquals("Pedro Servicos Set", prestador.getNome());

        prestador.setSenha("pedroSet123");
        assertEquals("pedroSet123", prestador.getSenha());

        prestador.setEmail("pedroset@serv.com");
        assertEquals("pedroset@serv.com", prestador.getEmail());

        prestador.setTelefone("444777888");
        assertEquals("444777888", prestador.getTelefone());

        EnderecoJpa endereco = new EnderecoJpa("Rua PSet", "Bairro PSet", "Cidade PSet", "PS");
        prestador.setEndereco(endereco);
        assertSame(endereco, prestador.getEndereco());

        List<OfereceJpa> servOferecidos = new ArrayList<>();
        prestador.setServicosOferecidos(servOferecidos);
        assertSame(servOferecidos, prestador.getServicosOferecidos());

        List<AvaliacaoSobreClienteJpa> avalFeitas = new ArrayList<>();
        prestador.setAvaliacoesFeitas(avalFeitas);
        assertSame(avalFeitas, prestador.getAvaliacoesFeitas());

        List<AvaliacaoSobrePrestadorJpa> avalRecebidas = new ArrayList<>();
        prestador.setAvaliacoesRecebidas(avalRecebidas);
        assertSame(avalRecebidas, prestador.getAvaliacoesRecebidas());

        List<FavoritadoJpa> favoritadoPor = new ArrayList<>();
        prestador.setFavoritadoPor(favoritadoPor);
        assertSame(favoritadoPor, prestador.getFavoritadoPor());

        List<PedidoJpa> pedidosRecebidos = new ArrayList<>();
        prestador.setPedidosRecebidos(pedidosRecebidos);
        assertSame(pedidosRecebidos, prestador.getPedidosRecebidos());
    }

    @Test
    void testToString() {
        EnderecoJpa endereco = new EnderecoJpa("Av. Serv ToString", "B. ServTS", "C. ServTS", "TS");
        PrestadorJpa prestador = new PrestadorJpa();
        prestador.setId(Integer.valueOf(5)); // Usando Integer
        prestador.setNome("Empresa X TS");
        prestador.setSenha("forteTS");
        prestador.setEmail("contatots@x.com");
        prestador.setTelefone("000111");
        prestador.setEndereco(endereco);

        String enderecoStr = endereco.toString();
        // Se id for Integer, usar %s para ele no String.format
        String expected = String.format(
                "PrestadorJpa{id=%s, nome='Empresa X TS', senha='forteTS', email='contatots@x.com', telefone='000111', endereco=%s}",
                prestador.getId(), enderecoStr
        );
        assertEquals(expected, prestador.toString());
    }

    @Test
    void testToStringComIdNulo() {
        EnderecoJpa endereco = new EnderecoJpa("Rua Sem ID", "Bairro SID", "Cidade SID", "SI");
        PrestadorJpa prestador = new PrestadorJpa("Prestador Sem ID", "senhaSemID", "semid@prest.com", "777", endereco);
        // ID é nulo por padrão se o construtor não o define e o campo é Integer

        String enderecoStr = endereco.toString();
        String expected = String.format(
                "PrestadorJpa{id=%s, nome='Prestador Sem ID', senha='senhaSemID', email='semid@prest.com', telefone='777', endereco=%s}",
                null, // Espera "null" para o ID no toString
                enderecoStr
        );
        assertEquals(expected, prestador.toString());
    }
}