// Salve este arquivo como:
// src/test/java/com/exemple/backend/apresentacao/controllers/PrestadorControllerTest.java

package com.exemple.backend.apresentacao.controllers;

import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.dominio.services.PrestadorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PrestadorController.class)
class PrestadorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PrestadorService prestadorServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    private Prestador prestador1;
    private Prestador prestador2;
    private Endereco enderecoComum;

    @TestConfiguration
    static class PrestadorControllerTestConfig {
        @Bean
        @Primary
        public PrestadorService prestadorService() {
            return Mockito.mock(PrestadorService.class);
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
            return http.build();
        }
    }

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.findAndRegisterModules();
        Mockito.reset(prestadorServiceMock);

        enderecoComum = new Endereco("Rua Prest Teste", "Bairro PT", "Cidade PT", "PT");
        // Usando Integer.valueOf() para IDs para corresponder ao tipo de campo no modelo
        prestador1 = new Prestador(Integer.valueOf(1), "Prestador Um Teste", "senhaP1", "p1teste@email.com", "111222", enderecoComum);
        prestador2 = new Prestador(Integer.valueOf(2), "Prestador Dois Teste", "senhaP2", "p2teste@email.com", "333444", enderecoComum);
    }

    @Test
    void deveEncontrarPrestadorPorIdComSucesso() throws Exception {
        when(prestadorServiceMock.findById(1)).thenReturn(Optional.of(prestador1));

        mockMvc.perform(get("/api/prestador/1") // URL CORRIGIDA
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is(prestador1.getNome())));
        verify(prestadorServiceMock, times(1)).findById(1);
    }

    @Test
    void deveRetornarNotFoundParaPrestadorPorIdInexistente() throws Exception {
        when(prestadorServiceMock.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/prestador/99") // URL CORRIGIDA
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(prestadorServiceMock, times(1)).findById(99);
    }

    @Test
    void deveListarTodosOsPrestadores() throws Exception {
        List<Prestador> listaPrestadores = Arrays.asList(prestador1, prestador2);
        when(prestadorServiceMock.findAll()).thenReturn(listaPrestadores);

        mockMvc.perform(get("/api/prestador") // URL CORRIGIDA
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(prestador1.getId())))
                .andExpect(jsonPath("$[1].id", is(prestador2.getId())));
        verify(prestadorServiceMock, times(1)).findAll();
    }

    @Test
    void deveSalvarNovoPrestadorComSucesso() throws Exception {
        Prestador prestadorInput = new Prestador(Integer.valueOf(0), "Novo Prestador", "senhaNova", "novo@prest.com", "555666", enderecoComum);
        Prestador prestadorSalvo = new Prestador(Integer.valueOf(3), prestadorInput.getNome(), prestadorInput.getSenha(), prestadorInput.getEmail(), prestadorInput.getTelefone(), prestadorInput.getEndereco());

        when(prestadorServiceMock.save(any(Prestador.class))).thenReturn(prestadorSalvo);

        mockMvc.perform(post("/api/prestador") // URL CORRIGIDA
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prestadorInput)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.nome", is("Novo Prestador")));

        verify(prestadorServiceMock, times(1)).save(argThat(p ->
                p.getNome().equals("Novo Prestador") && p.getId() != null && p.getId() == 0
        ));
    }

    @Test
    void deveAtualizarPrestadorExistenteComSucesso() throws Exception {
        int prestadorId = 1;
        Prestador prestadorComNovosDados = new Prestador(prestadorId, "Prestador Atualizado", "novaSenhaP", "p_att@email.com", "777888", enderecoComum);

        when(prestadorServiceMock.findById(prestadorId)).thenReturn(Optional.of(prestador1));
        when(prestadorServiceMock.update(any(Prestador.class))).thenReturn(prestadorComNovosDados);

        mockMvc.perform(put("/api/prestador/" + prestadorId + "/update") // URL CORRIGIDA
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prestadorComNovosDados)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(prestadorId)))
                .andExpect(jsonPath("$.nome", is("Prestador Atualizado")));

        verify(prestadorServiceMock, times(1)).findById(prestadorId);
        verify(prestadorServiceMock, times(1)).update(argThat(p ->
                p.getId().equals(prestadorId) && p.getNome().equals("Prestador Atualizado")
        ));
    }

    @Test
    void deveRetornarNotFoundAoAtualizarPrestadorInexistente() throws Exception {
        int idInexistente = 99;
        Prestador prestadorInfoUpdate = new Prestador(idInexistente, "Nome Inexistente", "senha", "inex@email.com", "000", enderecoComum);
        when(prestadorServiceMock.findById(idInexistente)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/prestador/" + idInexistente + "/update") // URL CORRIGIDA
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prestadorInfoUpdate)))
                .andExpect(status().isNotFound());

        verify(prestadorServiceMock, times(1)).findById(idInexistente);
        verify(prestadorServiceMock, never()).update(any(Prestador.class));
    }

    @Test
    void deveDeletarPrestadorExistenteComSucesso() throws Exception {
        int prestadorId = 1;
        when(prestadorServiceMock.findById(prestadorId)).thenReturn(Optional.of(prestador1));
        doNothing().when(prestadorServiceMock).delete(prestadorId);

        mockMvc.perform(delete("/api/prestador/" + prestadorId)) // URL CORRIGIDA
                .andExpect(status().isNoContent());

        verify(prestadorServiceMock, times(1)).findById(prestadorId);
        verify(prestadorServiceMock, times(1)).delete(prestadorId);
    }

    @Test
    void deveRetornarNotFoundAoDeletarPrestadorInexistente() throws Exception {
        int idInexistente = 99;
        when(prestadorServiceMock.findById(idInexistente)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/prestador/" + idInexistente)) // URL CORRIGIDA
                .andExpect(status().isNotFound());

        verify(prestadorServiceMock, times(1)).findById(idInexistente);
        verify(prestadorServiceMock, never()).delete(anyInt());
    }
}