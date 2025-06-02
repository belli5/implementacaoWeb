package com.exemple.backend.apresentacao.controllers;

import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.dominio.services.PrestadorService;
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

@WebMvcTest(PrestadorController.class)
class PrestadorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PrestadorService prestadorServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    private Prestador prestador1;
    private Endereco endereco;

    @TestConfiguration
    static class PrestadorControllerTestConfig {
        @Bean
        @Primary
        public PrestadorService prestadorService() {
            return Mockito.mock(PrestadorService.class);
        }
    }

    @BeforeEach
    void setUp() {
        endereco = new Endereco("Rua Prest Ctrl", "Bairro PCtrl", "Cidade PCtrl", "PC");
        prestador1 = new Prestador(1, "Prestador Ctrl Um", "spc1", "pc1@email.com", "111c", endereco);
        Mockito.reset(prestadorServiceMock);
    }

    @Test
    void deveEncontrarPrestadorPorId() throws Exception {
        when(prestadorServiceMock.findById(1)).thenReturn(Optional.of(prestador1));
        mockMvc.perform(get("/prestador/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is(prestador1.getNome())));
    }

    @Test
    void deveRetornarNotFoundParaPrestadorInexistente() throws Exception {
        when(prestadorServiceMock.findById(99)).thenReturn(Optional.empty());
        mockMvc.perform(get("/prestador/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveListarTodosOsPrestadores() throws Exception {
        Prestador prestador2 = new Prestador(2, "Prestador Ctrl Dois", "spc2", "pc2@email.com", "222c", endereco);
        List<Prestador> lista = Arrays.asList(prestador1, prestador2);
        when(prestadorServiceMock.findAll()).thenReturn(lista);

        mockMvc.perform(get("/prestador"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(prestador1.getId())));
    }

    @Test
    void deveSalvarNovoPrestador() throws Exception {
        when(prestadorServiceMock.save(any(Prestador.class))).thenReturn(prestador1);
        mockMvc.perform(post("/prestador")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prestador1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(prestador1.getId())));
    }

    @Test
    void deveAtualizarPrestadorExistente() throws Exception {
        Prestador prestadorAtualizado = new Prestador(1, "Nome Atualizado", "novaSenha", "novo@email.com", "333c", endereco);
        when(prestadorServiceMock.findById(1)).thenReturn(Optional.of(prestador1)); // Simula que existe
        when(prestadorServiceMock.update(any(Prestador.class))).thenReturn(prestadorAtualizado);

        mockMvc.perform(put("/prestador/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prestadorAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Nome Atualizado")));
    }

    @Test
    void deveRetornarNotFoundAoAtualizarPrestadorInexistente() throws Exception {
        Prestador prestadorInfoUpdate = new Prestador(99, "Nome Atualizado", "novaSenha", "novo@email.com", "333c", endereco);
        when(prestadorServiceMock.findById(99)).thenReturn(Optional.empty()); // Simula que NÃO existe

        mockMvc.perform(put("/prestador/99/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prestadorInfoUpdate)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveDeletarPrestadorExistente() throws Exception {
        when(prestadorServiceMock.findById(1)).thenReturn(Optional.of(prestador1));
        doNothing().when(prestadorServiceMock).delete(1);
        mockMvc.perform(delete("/prestador/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveRetornarNotFoundAoDeletarPrestadorInexistente() throws Exception {
        when(prestadorServiceMock.findById(99)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/prestador/99"))
                .andExpect(status().isNotFound());
    }
}