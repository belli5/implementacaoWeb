package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.Oferece;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.Servico;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.infraestrutura.jpamodels.OfereceJpa;
import com.exemple.backend.infraestrutura.jpamodels.PrestadorJpa;
import com.exemple.backend.infraestrutura.jpamodels.ServicoJpa;
import com.exemple.backend.infraestrutura.jpamodels.compartilhados.EnderecoJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OfereceMapperTest {

    private Prestador prestadorValido;
    private Servico servicoValido;

    @BeforeEach
    void setUp() {
        Endereco enderecoComum = new Endereco("Rua Teste", "Bairro Teste", "Cidade Teste", "TS");
        prestadorValido = new Prestador(1, "Prestador Teste", "senhaP", "prestador.teste@email.com", "111111111", enderecoComum);
        servicoValido = new Servico("Servico Teste", "CATEGORIA_TESTE", "Descrição do serviço de teste.");
    }

    @Test
    void deveMapearOfereceDominioParaOfereceJpa() {
        Oferece ofereceDominio = new Oferece(10, prestadorValido, servicoValido);

        OfereceJpa ofereceJpa = OfereceMapper.toOfereceJpa(ofereceDominio);

        assertNotNull(ofereceJpa);
        assertEquals(10, ofereceJpa.getId());
        assertNotNull(ofereceJpa.getPrestador(), "PrestadorJpa não deveria ser nulo após o mapeamento.");
        assertEquals(prestadorValido.getId(), ofereceJpa.getPrestador().getId());
        assertEquals(prestadorValido.getNome(), ofereceJpa.getPrestador().getNome());

        assertNotNull(ofereceJpa.getServico(), "ServicoJpa não deveria ser nulo após o mapeamento.");
        assertEquals(servicoValido.getNome(), ofereceJpa.getServico().getNome());
        assertEquals(servicoValido.getCategoria(), ofereceJpa.getServico().getCategoria());
    }

    @Test
    void deveMapearOfereceJpaParaOfereceDominio() {
        OfereceJpa ofereceJpa = new OfereceJpa();
        ofereceJpa.setId(20);

        EnderecoJpa enderecoJpaComum = new EnderecoJpa("Rua JPA", "Bairro JPA", "Cidade JPA", "JP");

        PrestadorJpa prestadorJpaMock = new PrestadorJpa();
        prestadorJpaMock.setId(101);
        prestadorJpaMock.setNome("Prestador Mock JPA");
        prestadorJpaMock.setSenha("senhaP");
        prestadorJpaMock.setEmail("pjpa@mock.com");
        prestadorJpaMock.setTelefone("444555666");
        prestadorJpaMock.setEndereco(enderecoJpaComum);
        ofereceJpa.setPrestador(prestadorJpaMock);

        ServicoJpa servicoJpaMock = new ServicoJpa();
        servicoJpaMock.setNome("Servico Mock JPA");
        servicoJpaMock.setCategoria("CAT_MOCK");
        servicoJpaMock.setDescricao("Descrição do serviço mock.");
        ofereceJpa.setServico(servicoJpaMock);

        Oferece ofereceDominio = OfereceMapper.toOferece(ofereceJpa);

        assertNotNull(ofereceDominio);
        assertEquals(20, ofereceDominio.getId());
        assertNotNull(ofereceDominio.getPrestador());
        assertEquals(101, ofereceDominio.getPrestador().getId());
        assertEquals("Prestador Mock JPA", ofereceDominio.getPrestador().getNome());
        assertNotNull(ofereceDominio.getServico());
        assertEquals("Servico Mock JPA", ofereceDominio.getServico().getNome());
    }

    @Test
    void deveLancarExcecaoAoMapearOfereceDominioNuloParaJpa() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            OfereceMapper.toOfereceJpa(null);
        });
        assertEquals("Oferece não pode ser nulo", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoMapearOfereceJpaNuloParaDominio() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            OfereceMapper.toOferece(null);
        });
        assertEquals("OfereceJpa não pode ser nulo", exception.getMessage());
    }
}