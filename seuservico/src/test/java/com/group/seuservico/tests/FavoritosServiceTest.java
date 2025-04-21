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
}
