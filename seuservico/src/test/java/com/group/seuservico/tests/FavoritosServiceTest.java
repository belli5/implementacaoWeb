package com.group.seuservico.tests;

import com.group.seuservico.domain.Usuarios.model.Prestador;
import com.group.seuservico.domain.Usuarios.model.Endereco;
import com.group.seuservico.domain.Usuarios.service.FavoritosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class FavoritosServiceTest {

    private FavoritosService favoritosService;

    @BeforeEach
    public void setUp() {
        favoritosService = new FavoritosService();
    }

    @Test
    public void testAdicionarFavorito() {
        Endereco endereco = new Endereco("Rua das Flores", "Boa Viagem", "Recife", "PE");
        Prestador prestador = new Prestador(1, "João", "Serviço de Consultoria", "joao@email.com", "123456789", endereco);
        favoritosService.adicionarFavorito(prestador);

        List<Prestador> favoritos = favoritosService.getFavoritos();
        assertTrue(favoritos.contains(prestador));
    }

    @Test
    public void testAdicionarNovamenteFavorito() {
        Endereco endereco = new Endereco("Rua das Flores", "Boa Viagem", "Recife", "PE");
        Prestador prestador = new Prestador(1, "João", "Serviço de Consultoria", "joao@email.com", "123456789", endereco);
        favoritosService.adicionarFavorito(prestador);
        favoritosService.adicionarFavorito(prestador);

        List<Prestador> favoritos = favoritosService.getFavoritos();
        assertTrue(favoritos.contains(prestador));

        assertEquals(1, favoritos.size());
    }

    @Test
    public void testVisualizarFavoritos() {
        Endereco endereco = new Endereco("Rua das Flores", "Boa Viagem", "Recife", "PE");
        Prestador prestador = new Prestador(1, "João", "Serviço de Consultoria", "joao@email.com", "123456789", endereco);

        Endereco endereco2 = new Endereco("Avenida Brasil", "Pina", "Recife", "PE");
        Prestador prestador2 = new Prestador(2, "Maria", "Serviço de Reformas", "maria@email.com", "987654321", endereco2);

        favoritosService.adicionarFavorito(prestador);
        favoritosService.adicionarFavorito(prestador2);

        List<Prestador> favoritos = favoritosService.getFavoritos();

        System.out.println("Favoritos: ");
        for (Prestador f : favoritos) {
            System.out.println(f);
        }

        assertTrue(favoritos.contains(prestador));
        assertTrue(favoritos.contains(prestador2));
    }
}
