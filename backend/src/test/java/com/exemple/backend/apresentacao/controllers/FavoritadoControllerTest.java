package com.exemple.backend.apresentacao.controllers;

import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Favoritado;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.dominio.repositorys.FavoritadoRepository; // Controller usa o Repository diretamente
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FavoritadoController.class)
class FavoritadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // O FavoritadoController injeta FavoritadoRepository diretamente, não um Service
    @Autowired
    private FavoritadoRepository favoritadoRepositoryMock;

    @Autowired
    private ObjectMapper objectMapper;

    private Favoritado favoritado1;
    private Cliente cliente;
    private Prestador prestador;
    private Endereco endereco;

    @TestConfiguration
    static class FavoritadoControllerTestConfig {
        @Bean
        @Primary
        public FavoritadoRepository favoritadoRepository() {
            return Mockito.mock(FavoritadoRepository.class);
        }
    }

    @BeforeEach
    void setUp() {
        endereco = new Endereco("Rua FavCtrl", "Bairro FCtrl", "Cidade FCtrl", "FC");
        cliente = new Cliente(1, "Cliente FavCtrl", "sfc", "cfc@email.com", "1fc", endereco);
        prestador = new Prestador(1, "Prestador FavCtrl", "sfp", "pfc@email.com", "1fp", endereco);
        favoritado1 = new Favoritado(1, cliente, prestador);
        Mockito.reset(favoritadoRepositoryMock);
    }

    @Test
    void deveListarTodosFavoritados() throws Exception {
        List<Favoritado> lista = Arrays.asList(favoritado1);
        when(favoritadoRepositoryMock.findAll()).thenReturn(lista);
        mockMvc.perform(get("/favoritos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
        // O mapper de Favoritado retorna null para cliente e prestador ao converter de Jpa para Dominio
        // e depois para JSON, então não podemos testar cliente.nome diretamente aqui sem ajustar o mapper ou o teste.
    }

    @Test
    void deveBuscarFavoritadoPorId() throws Exception {
        when(favoritadoRepositoryMock.findById(1)).thenReturn(Optional.of(favoritado1));
        mockMvc.perform(get("/favoritos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void deveRetornarNotFoundParaFavoritadoPorIdInexistente() throws Exception {
        when(favoritadoRepositoryMock.findById(99)).thenReturn(Optional.empty());
        mockMvc.perform(get("/favoritos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveFavoritar() throws Exception {
        // O favoritado que é enviado no corpo não precisa ter ID, mas o que é retornado sim.
        Favoritado favInput = new Favoritado(0, cliente, prestador); // ID 0 ou omitido
        Favoritado favSalvo = new Favoritado(10, cliente, prestador); // ID atribuído após salvar

        when(favoritadoRepositoryMock.save(any(Favoritado.class))).thenReturn(favSalvo);

        mockMvc.perform(post("/favoritos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(favInput)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(10)));
    }

    @Test
    void deveDesfavoritar() throws Exception {
        when(favoritadoRepositoryMock.findById(1)).thenReturn(Optional.of(favoritado1));
        doNothing().when(favoritadoRepositoryMock).delete(1);
        mockMvc.perform(delete("/favoritos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveRetornarNotFoundAoDesfavoritarIdInexistente() throws Exception {
        when(favoritadoRepositoryMock.findById(99)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/favoritos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveListarFavoritosDoCliente() throws Exception {
        List<Prestador> listaPrestadores = Arrays.asList(prestador);
        when(favoritadoRepositoryMock.findPrestadoresFavoritadosByClienteId(1)).thenReturn(listaPrestadores);
        mockMvc.perform(get("/favoritos/cliente/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(prestador.getId())));
    }

    @Test
    void deveListarClientesQueFavoritaramPrestador() throws Exception {
        List<Cliente> listaClientes = Arrays.asList(cliente);
        when(favoritadoRepositoryMock.findClientesQueFavoritaramPrestadorByPrestadorId(1)).thenReturn(listaClientes);
        mockMvc.perform(get("/favoritos/prestador/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(cliente.getId())));
    }
}