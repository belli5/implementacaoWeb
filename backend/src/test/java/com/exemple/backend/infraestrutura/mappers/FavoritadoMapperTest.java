package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Favoritado;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.infraestrutura.jpamodels.ClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.FavoritadoJpa;
import com.exemple.backend.infraestrutura.jpamodels.PrestadorJpa;
import com.exemple.backend.infraestrutura.jpamodels.compartilhados.EnderecoJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FavoritadoMapperTest {
    private Cliente clienteDominio;
    private Prestador prestadorDominio;

    @BeforeEach
    void setUp() {
        Endereco enderecoComum = new Endereco("Rua Teste", "Bairro Teste", "Cidade Teste", "TS");
        clienteDominio = new Cliente(1, "Cliente Teste", "senhaC", "cliente.teste@email.com", "111111111", enderecoComum);
        prestadorDominio = new Prestador(1, "Prestador Teste", "senhaP", "prestador.teste@email.com", "222222222", enderecoComum);
    }

    @Test
    void deveMapearFavoritadoDominioParaFavoritadoJpa() {
        Favoritado favoritadoDominio = new Favoritado(5, clienteDominio, prestadorDominio);

        FavoritadoJpa favoritadoJpa = FavoritadoMapper.toFavoritadoJpa(favoritadoDominio);

        assertNotNull(favoritadoJpa);
        assertEquals(5, favoritadoJpa.getId());
        assertNotNull(favoritadoJpa.getCliente(), "ClienteJpa não deveria ser nulo após o mapeamento.");
        assertEquals(clienteDominio.getId(), favoritadoJpa.getCliente().getId());
        assertNotNull(favoritadoJpa.getPrestador(), "PrestadorJpa não deveria ser nulo após o mapeamento.");
        assertEquals(prestadorDominio.getId(), favoritadoJpa.getPrestador().getId());
    }

    @Test
    void deveMapearFavoritadoJpaParaFavoritadoDominio() {
        FavoritadoJpa favoritadoJpa = new FavoritadoJpa();
        favoritadoJpa.setId(15);

        EnderecoJpa enderecoJpaComum = new EnderecoJpa("Rua JPA", "Bairro JPA", "Cidade JPA", "JP");

        ClienteJpa clienteJpaMock = new ClienteJpa();
        clienteJpaMock.setId(101);
        clienteJpaMock.setNome("Cliente Mock JPA");
        clienteJpaMock.setSenha("senhaC");
        clienteJpaMock.setEmail("cjpa@mock.com");
        clienteJpaMock.setTelefone("111222333");
        clienteJpaMock.setEndereco(enderecoJpaComum);
        favoritadoJpa.setCliente(clienteJpaMock);

        PrestadorJpa prestadorJpaMock = new PrestadorJpa();
        prestadorJpaMock.setId(202);
        prestadorJpaMock.setNome("Prestador Mock JPA");
        prestadorJpaMock.setSenha("senhaP");
        prestadorJpaMock.setEmail("pjpa@mock.com");
        prestadorJpaMock.setTelefone("444555666");
        prestadorJpaMock.setEndereco(enderecoJpaComum);
        favoritadoJpa.setPrestador(prestadorJpaMock);

        Favoritado favoritadoDominio = FavoritadoMapper.toFavoritado(favoritadoJpa);

        assertNotNull(favoritadoDominio);
        assertEquals(15, favoritadoDominio.getId());
        assertNotNull(favoritadoDominio.getCliente());
        assertEquals(101, favoritadoDominio.getCliente().getId());
        assertNotNull(favoritadoDominio.getPrestador());
        assertEquals(202, favoritadoDominio.getPrestador().getId());
    }

    @Test
    void deveLancarExcecaoAoMapearFavoritadoDominioNuloParaJpa() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            FavoritadoMapper.toFavoritadoJpa(null);
        });
        assertEquals("Favoritado não pode ser nulo", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoMapearFavoritadoJpaNuloParaDominio() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            FavoritadoMapper.toFavoritado(null);
        });
        assertEquals("Favoritado não pode ser nulo", exception.getMessage());
    }
}