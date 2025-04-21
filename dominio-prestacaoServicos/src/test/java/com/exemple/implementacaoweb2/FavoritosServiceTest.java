package com.exemple.implementacaoweb2;

import com.exemple.implementacaoweb2.cliente.FavoritosService;
import com.exemple.implementacaoweb2.compartilhados.Endereco;
import com.exemple.implementacaoweb2.prestacaoServico.PrestacaoServico;
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
        PrestacaoServico servico = new PrestacaoServico(1, "Consultoria", "Consultoria especializada", 200.0f, "Boa Viagem");
        List<PrestacaoServico> servicos = List.of(servico);
        Prestador prestador = new Prestador(1, "João", servicos, "joao@email.com", "123456789", endereco);

        favoritosService.adicionarFavorito(prestador);

        List<Prestador> favoritos = favoritosService.getFavoritos();
        assertTrue(favoritos.contains(prestador));
    }

    @Test
    public void testAdicionarNovamenteFavorito() {
        Endereco endereco = new Endereco("Rua das Flores", "Boa Viagem", "Recife", "PE");
        PrestacaoServico servico = new PrestacaoServico(1, "Consultoria", "Consultoria especializada", 200.0f, "Boa Viagem");
        List<PrestacaoServico> servicos = List.of(servico);
        Prestador prestador = new Prestador(1, "João", servicos, "joao@email.com", "123456789", endereco);

        favoritosService.adicionarFavorito(prestador);
        favoritosService.adicionarFavorito(prestador);

        List<Prestador> favoritos = favoritosService.getFavoritos();
        assertTrue(favoritos.contains(prestador));
        assertEquals(1, favoritos.size());
    }

    @Test
    public void testVisualizarFavoritos() {
        Endereco endereco1 = new Endereco("Rua das Flores", "Boa Viagem", "Recife", "PE");
        PrestacaoServico servico1 = new PrestacaoServico(1, "Consultoria", "Consultoria especializada", 200.0f, "Boa Viagem");
        List<PrestacaoServico> servicos1 = List.of(servico1);
        Prestador prestador1 = new Prestador(1, "João", servicos1, "joao@email.com", "123456789", endereco1);

        Endereco endereco2 = new Endereco("Avenida Brasil", "Pina", "Recife", "PE");
        PrestacaoServico servico2 = new PrestacaoServico(2, "Reformas", "Serviços gerais de reforma", 300.0f, "Pina");
        List<PrestacaoServico> servicos2 = List.of(servico2);
        Prestador prestador2 = new Prestador(2, "Maria", servicos2, "maria@email.com", "987654321", endereco2);

        favoritosService.adicionarFavorito(prestador1);
        favoritosService.adicionarFavorito(prestador2);

        List<Prestador> favoritos = favoritosService.getFavoritos();

        assertTrue(favoritos.contains(prestador1));
        assertTrue(favoritos.contains(prestador2));
    }

    @Test
    public void testLimiteFavoritos() {
        for (int i = 0; i < 50; i++) {
            Endereco endereco = new Endereco("Rua " + i, "Bairro " + i, "Recife", "PE");
            PrestacaoServico servico = new PrestacaoServico(i, "Serviço " + i, "Descrição " + i, 100.0f + i, "Bairro " + i);
            List<PrestacaoServico> servicos = List.of(servico);
            Prestador prestador = new Prestador(i, "Prestador " + i, servicos, "email" + i + "@teste.com", "123456789", endereco);

            String resposta = favoritosService.adicionarFavorito(prestador);
            assertEquals("Prestador adicionado com sucesso!", resposta);
        }

        Endereco enderecoExtra = new Endereco("Rua Extra", "Bairro Extra", "Recife", "PE");
        PrestacaoServico servicoExtra = new PrestacaoServico(100, "Serviço Extra", "Descrição Extra", 200.0f, "Bairro Extra");
        List<PrestacaoServico> servicosExtra = List.of(servicoExtra);
        Prestador prestadorExtra = new Prestador(100, "Extra", servicosExtra, "extra@email.com", "999999999", enderecoExtra);

        String respostaFinal = favoritosService.adicionarFavorito(prestadorExtra);
        assertEquals("Limite de favoritos atingido.", respostaFinal);

        List<Prestador> favoritos = favoritosService.getFavoritos();
        assertEquals(50, favoritos.size());
    }


}
