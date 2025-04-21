package com.exemple.implementacaoweb2;


import com.exemple.implementacaoweb2.cliente.FavoritosService;
import com.exemple.implementacaoweb2.compartilhados.Endereco;
import com.exemple.implementacaoweb2.prestador.Prestador;
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

    @Test
    public void testLimiteFavoritos() {
        for (int i = 0; i < 50; i++) {
            Endereco endereco = new Endereco("Rua " + i, "Bairro " + i, "Recife", "PE");
            Prestador prestador = new Prestador(i, "Prestador " + i, "Serviço " + i, "email" + i + "@teste.com", "123456789", endereco);
            String resposta = favoritosService.adicionarFavorito(prestador);
            assertEquals("Prestador adicionado com sucesso!", resposta);
        }

        Endereco enderecoExtra = new Endereco("Rua Extra", "Bairro Extra", "Recife", "PE");
        Prestador prestadorExtra = new Prestador(100, "Extra", "Serviço Extra", "extra@email.com", "999999999", enderecoExtra);
        String respostaFinal = favoritosService.adicionarFavorito(prestadorExtra);

        assertEquals("Limite de favoritos atingido.", respostaFinal);

        List<Prestador> favoritos = favoritosService.getFavoritos();
        assertEquals(50, favoritos.size());
    }

}
