package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.AvaliacaoSobreCliente;
import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.compartilhados.Endereco; // Import necessário
import com.exemple.backend.infraestrutura.jpamodels.AvaliacaoSobreClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.ClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.PrestadorJpa;
import com.exemple.backend.infraestrutura.jpamodels.compartilhados.EnderecoJpa; // Import necessário
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AvaliacaoSobreClienteMapperTest {

    @Test
    void deveMapearAvaliacaoSobreClienteDominioParaJpa() {
        // Arrange
        // Construtor de AvaliacaoSobreCliente (domínio) requer Prestador e Cliente não nulos.
        // Para o teste, instanciaremos Cliente e Prestador de domínio com IDs para maior clareza no JPA.
        Endereco enderecoDominio = new Endereco("Rua Dom", "Bairro Dom", "Cidade Dom", "DM");
        Prestador prestadorDominio = new Prestador(10, "Prestador Domínio", "psenha", "pdom@email.com", "ptel", enderecoDominio);
        Cliente clienteDominio = new Cliente(20, "Cliente Domínio", "csenha", "cdom@email.com", "ctel", enderecoDominio);
        AvaliacaoSobreCliente dominio = new AvaliacaoSobreCliente(1, prestadorDominio, "Cliente exemplar!", 5, clienteDominio);

        // Act
        AvaliacaoSobreClienteJpa jpa = AvaliacaoSobreClienteMapper.toAvaliacaoSobreClienteJpa(dominio);

        // Assert
        assertNotNull(jpa, "O objeto JPA não deveria ser nulo.");
        assertEquals(1, jpa.getId(), "O ID da avaliação não foi mapeado corretamente.");
        assertEquals("Cliente exemplar!", jpa.getComentario(), "O comentário não foi mapeado corretamente.");
        assertEquals(5, jpa.getNota(), "A nota não foi mapeada corretamente.");

        assertNotNull(jpa.getCliente(), "ClienteJpa não deveria ser nulo após o mapeamento.");
        assertEquals(clienteDominio.getId(), jpa.getCliente().getId(), "ID do ClienteJpa não corresponde ao ID do domínio.");
        assertEquals(clienteDominio.getNome(), jpa.getCliente().getNome(), "Nome do ClienteJpa não corresponde.");


        assertNotNull(jpa.getPrestador(), "PrestadorJpa não deveria ser nulo após o mapeamento.");
        assertEquals(prestadorDominio.getId(), jpa.getPrestador().getId(), "ID do PrestadorJpa não corresponde ao ID do domínio.");
        assertEquals(prestadorDominio.getNome(), jpa.getPrestador().getNome(), "Nome do PrestadorJpa não corresponde.");
    }

    @Test
    void deveMapearAvaliacaoSobreClienteJpaParaDominio() {
        // Arrange
        AvaliacaoSobreClienteJpa jpa = new AvaliacaoSobreClienteJpa();
        jpa.setId(2);
        jpa.setComentario("Comunicação fácil.");
        jpa.setNota(4);

        // Para o mapper toAvaliacaoSobreCliente, PrestadorJpa e ClienteJpa no objeto 'jpa'
        // NÃO PODEM ser nulos e DEVEM ter os campos esperados pelos construtores de domínio.
        EnderecoJpa enderecoJpaComum = new EnderecoJpa("Rua Jpa", "Bairro Jpa", "Cidade Jpa", "JP");

        PrestadorJpa prestadorJpaMock = new PrestadorJpa();
        prestadorJpaMock.setId(101);
        prestadorJpaMock.setNome("Prestador Mock JPA");
        prestadorJpaMock.setSenha("senhaPJpa");
        prestadorJpaMock.setEmail("pjpa@mock.com");
        prestadorJpaMock.setTelefone("111222333");
        prestadorJpaMock.setEndereco(enderecoJpaComum);
        jpa.setPrestador(prestadorJpaMock);

        ClienteJpa clienteJpaMock = new ClienteJpa();
        clienteJpaMock.setId(202);
        clienteJpaMock.setNome("Cliente Mock JPA");
        clienteJpaMock.setSenha("senhaCJpa");
        clienteJpaMock.setEmail("cjpa@mock.com");
        clienteJpaMock.setTelefone("444555666");
        clienteJpaMock.setEndereco(enderecoJpaComum);
        jpa.setCliente(clienteJpaMock);

        // Act
        AvaliacaoSobreCliente dominio = AvaliacaoSobreClienteMapper.toAvaliacaoSobreCliente(jpa);

        // Assert
        assertNotNull(dominio, "O objeto de domínio não deveria ser nulo.");
        assertEquals(2, dominio.getId(), "ID da avaliação no domínio não corresponde.");
        assertEquals("Comunicação fácil.", dominio.getComentario(), "Comentário no domínio não corresponde.");
        assertEquals(4, dominio.getNota(), "Nota no domínio não corresponde.");

        assertNotNull(dominio.getCliente(), "Cliente no domínio não deveria ser nulo.");
        assertEquals(202, dominio.getCliente().getId(), "ID do Cliente no domínio não corresponde.");
        assertEquals("Cliente Mock JPA", dominio.getCliente().getNome(), "Nome do Cliente no domínio não corresponde.");

        assertNotNull(dominio.getPrestador(), "Prestador no domínio não deveria ser nulo.");
        assertEquals(101, dominio.getPrestador().getId(), "ID do Prestador no domínio não corresponde.");
        assertEquals("Prestador Mock JPA", dominio.getPrestador().getNome(), "Nome do Prestador no domínio não corresponde.");
    }

    @Test
    void deveLancarExcecaoAoMapearJpaParaDominioComPrestadorJpaNulo() {
        AvaliacaoSobreClienteJpa jpa = new AvaliacaoSobreClienteJpa();
        jpa.setId(3);
        jpa.setComentario("Teste com prestador nulo");
        jpa.setNota(3);
        jpa.setPrestador(null); // Prestador Jpa é nulo

        // Cliente Jpa válido para isolar o problema no Prestador
        ClienteJpa clienteJpaValido = new ClienteJpa();
        clienteJpaValido.setId(202);
        clienteJpaValido.setNome("Cliente Valido");
        clienteJpaValido.setSenha("senhaCValido");
        clienteJpaValido.setEmail("cvalido@mock.com");
        clienteJpaValido.setTelefone("777888999");
        clienteJpaValido.setEndereco(new EnderecoJpa("Rua CV", "Bairro CV", "Cidade CV", "CV"));
        jpa.setCliente(clienteJpaValido);

        // A exceção virá de PrestadorMapper.toPrestador, que não aceita jpa nulo.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AvaliacaoSobreClienteMapper.toAvaliacaoSobreCliente(jpa);
        });
        assertEquals("PrestadorJpa não pode ser nulo", exception.getMessage(),
                "Mensagem de exceção não corresponde ao esperado para PrestadorJpa nulo.");
    }

    @Test
    void deveLancarExcecaoAoMapearJpaParaDominioComClienteJpaNulo() {
        AvaliacaoSobreClienteJpa jpa = new AvaliacaoSobreClienteJpa();
        jpa.setId(4);
        jpa.setComentario("Teste com cliente nulo");
        jpa.setNota(3);
        jpa.setCliente(null); // Cliente Jpa é nulo

        // Prestador Jpa válido para isolar o problema no Cliente
        PrestadorJpa prestadorJpaValido = new PrestadorJpa();
        prestadorJpaValido.setId(101);
        prestadorJpaValido.setNome("Prestador Valido");
        prestadorJpaValido.setSenha("senhaPValido");
        prestadorJpaValido.setEmail("pvalido@mock.com");
        prestadorJpaValido.setTelefone("000111222");
        prestadorJpaValido.setEndereco(new EnderecoJpa("Rua PV", "Bairro PV", "Cidade PV", "PV"));
        jpa.setPrestador(prestadorJpaValido);

        // A exceção virá de ClienteMapper.toCliente, que não aceita jpa nulo.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AvaliacaoSobreClienteMapper.toAvaliacaoSobreCliente(jpa);
        });
        assertEquals("ClienteJpa não pode ser nulo", exception.getMessage(),
                "Mensagem de exceção não corresponde ao esperado para ClienteJpa nulo.");
    }

    @Test
    void deveLancarExcecaoAoMapearAvaliacaoSobreClienteDominioNuloParaJpa() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AvaliacaoSobreClienteMapper.toAvaliacaoSobreClienteJpa(null);
        });
        assertEquals("AvaliacaoSobreCliente não pode ser nula", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoAoMapearAvaliacaoSobreClienteJpaNuloParaDominio() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AvaliacaoSobreClienteMapper.toAvaliacaoSobreCliente(null);
        });
        assertEquals("AvaliacaoSobreClienteJpa não pode ser nula", exception.getMessage());
    }
}