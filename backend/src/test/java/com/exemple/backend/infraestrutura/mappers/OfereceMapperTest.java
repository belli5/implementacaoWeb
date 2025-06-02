package com.exemple.backend.infraestrutura.mappers;

import com.exemple.backend.dominio.models.Oferece;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.Servico;
import com.exemple.backend.infraestrutura.jpamodels.OfereceJpa;
import com.exemple.backend.infraestrutura.jpamodels.PrestadorJpa;
import com.exemple.backend.infraestrutura.jpamodels.ServicoJpa;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OfereceMapperTest {

    @Test
    void deveMapearOfereceDominioParaOfereceJpa() {
        // Arrange
        // O construtor de Oferece (domínio) requer Prestador e Servico não nulos.
        // Embora o mapper toOfereceJpa anule essas referências no Jpa.
        Prestador prestadorDominio = new Prestador(); // Pode ser um mock ou objeto simples
        Servico servicoDominio = new Servico();   // Pode ser um mock ou objeto simples
        Oferece ofereceDominio = new Oferece(10, prestadorDominio, servicoDominio);

        // Act
        OfereceJpa ofereceJpa = OfereceMapper.toOfereceJpa(ofereceDominio);

        // Assert
        assertNotNull(ofereceJpa);
        assertEquals(10, ofereceJpa.getId()); // ID é mapeado
        assertNull(ofereceJpa.getPrestador(), "Mapper toOfereceJpa seta PrestadorJpa como null."); //
        assertNull(ofereceJpa.getServico(), "Mapper toOfereceJpa seta ServicoJpa como null.");   //
    }

    @Test
    void deveMapearOfereceJpaParaOfereceDominio() {
        // Arrange
        OfereceJpa ofereceJpa = new OfereceJpa();
        ofereceJpa.setId(20);
        // Mesmo que PrestadorJpa e ServicoJpa sejam setados aqui, o mapper toOferece os ignora e retorna null.
        ofereceJpa.setPrestador(new PrestadorJpa());
        ofereceJpa.setServico(new ServicoJpa());

        // Act
        Oferece ofereceDominio = OfereceMapper.toOferece(ofereceJpa);

        // Assert
        assertNotNull(ofereceDominio);
        assertEquals(20, ofereceDominio.getId()); // ID é mapeado
        assertNull(ofereceDominio.getPrestador(), "Mapper toOferece retorna Prestador como null."); //
        assertNull(ofereceDominio.getServico(), "Mapper toOferece retorna Servico como null.");   //
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