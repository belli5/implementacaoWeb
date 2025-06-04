package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.AvaliacaoSobrePrestador;
import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.infraestrutura.jpamodels.AvaliacaoSobrePrestadorJpa;
import com.exemple.backend.infraestrutura.jpamodels.ClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.PrestadorJpa;
import com.exemple.backend.infraestrutura.jpamodels.compartilhados.EnderecoJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AvaliacaoSobrePrestadorMapperTest {

    private Cliente clienteDominio;
    private Prestador prestadorDominio;

    @BeforeEach
    void setUp() {
        Endereco enderecoComum = new Endereco("Rua Teste", "Bairro Teste", "Cidade Teste", "TS");
        clienteDominio = new Cliente(1, "Cliente Teste", "senhaC", "cliente.teste@email.com", "111111111", enderecoComum);
        prestadorDominio = new Prestador(1, "Prestador Teste", "senhaP", "prestador.teste@email.com", "222222222", enderecoComum);
    }

    @Test
    void deveMapearAvaliacaoSobrePrestadorDominioParaJpa() {
        AvaliacaoSobrePrestador dominio = new AvaliacaoSobrePrestador(1, clienteDominio, "Excelente!", 5, prestadorDominio);

        AvaliacaoSobrePrestadorJpa jpa = AvaliacaoSobrePrestadorMapper.toAvaliacaoSobrePrestadorJpa(dominio);

        assertNotNull(jpa);
        assertEquals(1, jpa.getId());
        assertEquals("Excelente!", jpa.getComentario());
        assertEquals(5, jpa.getNota());

        assertNotNull(jpa.getCliente(), "ClienteJpa não deveria ser nulo após o mapeamento.");
        assertEquals(clienteDominio.getId(), jpa.getCliente().getId(), "ID do ClienteJpa não corresponde ao ID do domínio.");
        assertEquals(clienteDominio.getNome(), jpa.getCliente().getNome(), "Nome do ClienteJpa não corresponde.");

        assertNotNull(jpa.getPrestador(), "PrestadorJpa não deveria ser nulo após o mapeamento.");
        assertEquals(prestadorDominio.getId(), jpa.getPrestador().getId(), "ID do PrestadorJpa não corresponde ao ID do domínio.");
        assertEquals(prestadorDominio.getNome(), jpa.getPrestador().getNome(), "Nome do PrestadorJpa não corresponde.");
    }

    @Test
    void deveMapearAvaliacaoSobrePrestadorJpaParaDominio() {
        AvaliacaoSobrePrestadorJpa jpa = new AvaliacaoSobrePrestadorJpa();
        jpa.setId(2);
        jpa.setComentario("Bom, mas pode melhorar.");
        jpa.setNota(4);

        EnderecoJpa enderecoJpaComum = new EnderecoJpa("Rua JPA", "Bairro JPA", "Cidade JPA", "JP");

        ClienteJpa clienteJpaMock = new ClienteJpa();
        clienteJpaMock.setId(101);
        clienteJpaMock.setNome("Cliente Mock JPA");
        clienteJpaMock.setSenha("senhaC");
        clienteJpaMock.setEmail("cjpa@mock.com");
        clienteJpaMock.setTelefone("111222333");
        clienteJpaMock.setEndereco(enderecoJpaComum);
        jpa.setCliente(clienteJpaMock);

        PrestadorJpa prestadorJpaMock = new PrestadorJpa();
        prestadorJpaMock.setId(202);
        prestadorJpaMock.setNome("Prestador Mock JPA");
        prestadorJpaMock.setSenha("senhaP");
        prestadorJpaMock.setEmail("pjpa@mock.com");
        prestadorJpaMock.setTelefone("444555666");
        prestadorJpaMock.setEndereco(enderecoJpaComum);
        jpa.setPrestador(prestadorJpaMock);

        AvaliacaoSobrePrestador dominio = AvaliacaoSobrePrestadorMapper.toAvaliacaoSobrePrestador(jpa);

        assertNotNull(dominio);
        assertEquals(2, dominio.getId());
        assertEquals("Bom, mas pode melhorar.", dominio.getComentario());
        assertEquals(4, dominio.getNota());

        assertNotNull(dominio.getCliente());
        assertEquals(101, dominio.getCliente().getId());
        assertEquals("Cliente Mock JPA", dominio.getCliente().getNome());

        assertNotNull(dominio.getPrestador());
        assertEquals(202, dominio.getPrestador().getId());
        assertEquals("Prestador Mock JPA", dominio.getPrestador().getNome());
    }

    @Test
    void deveLancarExcecaoAoMapearAvaliacaoSobrePrestadorDominioNuloParaJpa() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AvaliacaoSobrePrestadorMapper.toAvaliacaoSobrePrestadorJpa(null);
        });
        assertEquals("AvaliacaoSobrePrestador não pode ser nula", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoMapearAvaliacaoSobrePrestadorJpaNuloParaDominio() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AvaliacaoSobrePrestadorMapper.toAvaliacaoSobrePrestador(null);
        });
        assertEquals("AvaliacaoSobrePrestadorJpa não pode ser nula", exception.getMessage());
    }
}