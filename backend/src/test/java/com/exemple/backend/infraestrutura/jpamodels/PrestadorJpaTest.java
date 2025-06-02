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
        assertEquals(0, prestador.getId(), "ID (int primitivo) deveria ser 0 por padrão");
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
    void testConstrutorComArgumentos() {
        EnderecoJpa endereco = new EnderecoJpa("Rua Prest Arg", "Bairro PArg", "Cidade PArg", "PA");
        PrestadorJpa prestador = new PrestadorJpa("Ana Prestadora Arg", "senhaPArg", "anaarg@prest.com", "333000111", endereco);

        assertEquals(0, prestador.getId()); // ID não é setado neste construtor
        assertEquals("Ana Prestadora Arg", prestador.getNome());
        assertEquals("senhaPArg", prestador.getSenha());
        assertEquals("anaarg@prest.com", prestador.getEmail());
        assertEquals("333000111", prestador.getTelefone());
        assertSame(endereco, prestador.getEndereco());
        assertNull(prestador.getServicosOferecidos());
    }

    @Test
    void testGettersAndSetters() {
        PrestadorJpa prestador = new PrestadorJpa();
        prestador.setId(20);
        prestador.setNome("Pedro Servicos Set");
        prestador.setSenha("pedroSet123");
        prestador.setEmail("pedroset@serv.com");
        prestador.setTelefone("444777888");

        EnderecoJpa endereco = new EnderecoJpa("Rua PSet", "Bairro PSet", "Cidade PSet", "PS");
        prestador.setEndereco(endereco);

        List<OfereceJpa> servOferecidos = new ArrayList<>();
        OfereceJpa o1 = new OfereceJpa();
        servOferecidos.add(o1);
        prestador.setServicosOferecidos(servOferecidos);

        List<AvaliacaoSobreClienteJpa> avalFeitas = new ArrayList<>();
        AvaliacaoSobreClienteJpa ac1 = new AvaliacaoSobreClienteJpa();
        avalFeitas.add(ac1);
        prestador.setAvaliacoesFeitas(avalFeitas);

        List<AvaliacaoSobrePrestadorJpa> avalRecebidas = new ArrayList<>();
        AvaliacaoSobrePrestadorJpa ap1 = new AvaliacaoSobrePrestadorJpa();
        avalRecebidas.add(ap1);
        prestador.setAvaliacoesRecebidas(avalRecebidas);

        List<FavoritadoJpa> favoritadoPor = new ArrayList<>();
        FavoritadoJpa f1 = new FavoritadoJpa();
        favoritadoPor.add(f1);
        prestador.setFavoritadoPor(favoritadoPor);

        List<PedidoJpa> pedidosRecebidos = new ArrayList<>();
        PedidoJpa p1 = new PedidoJpa();
        pedidosRecebidos.add(p1);
        prestador.setPedidosRecebidos(pedidosRecebidos);

        assertEquals(20, prestador.getId());
        assertEquals("Pedro Servicos Set", prestador.getNome());
        assertEquals("pedroSet123", prestador.getSenha());
        assertEquals("pedroset@serv.com", prestador.getEmail());
        assertEquals("444777888", prestador.getTelefone());
        assertSame(endereco, prestador.getEndereco());

        assertNotNull(prestador.getServicosOferecidos());
        assertEquals(1, prestador.getServicosOferecidos().size());
        assertSame(o1, prestador.getServicosOferecidos().get(0));

        assertNotNull(prestador.getAvaliacoesFeitas());
        assertEquals(1, prestador.getAvaliacoesFeitas().size());
        assertSame(ac1, prestador.getAvaliacoesFeitas().get(0));

        assertNotNull(prestador.getAvaliacoesRecebidas());
        assertEquals(1, prestador.getAvaliacoesRecebidas().size());
        assertSame(ap1, prestador.getAvaliacoesRecebidas().get(0));

        assertNotNull(prestador.getFavoritadoPor());
        assertEquals(1, prestador.getFavoritadoPor().size());
        assertSame(f1, prestador.getFavoritadoPor().get(0));

        assertNotNull(prestador.getPedidosRecebidos());
        assertEquals(1, prestador.getPedidosRecebidos().size());
        assertSame(p1, prestador.getPedidosRecebidos().get(0));
    }

    @Test
    void testToString() {
        EnderecoJpa endereco = new EnderecoJpa("Av. Serv ToString", "B. ServTS", "C. ServTS", "TS");
        PrestadorJpa prestador = new PrestadorJpa("Empresa X TS", " forteTS", "contatots@x.com", "000111", endereco);
        prestador.setId(5);
        String enderecoStr = endereco.toString();
        String expected = "PrestadorJpa{id=5, nome='Empresa X TS', senha=' forteTS', email='contatots@x.com', telefone='000111', endereco=" + enderecoStr + "}"; //
        assertEquals(expected, prestador.toString());
    }
}