package com.exemple.backend.apresentacao.controllers;

import com.exemple.backend.dominio.models.Servico;
import com.exemple.backend.dominio.services.ServicoService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ServicoController.class)
class ServicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServicoService servicoServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    private Servico servico1;

    @TestConfiguration
    static class ServicoControllerTestConfig {
        @Bean
        @Primary
        public ServicoService servicoService() {
            return Mockito.mock(ServicoService.class);
        }
    }

    @BeforeEach
    void setUp() {
        servico1 = new Servico("Limpeza Ctrl", "LIMPEZA_CTRL", "Limpeza para controller test");
        Mockito.reset(servicoServiceMock);
    }

    @Test
    void deveSalvarServico() throws Exception {
        when(servicoServiceMock.save(any(Servico.class))).thenReturn(servico1);
        mockMvc.perform(post("/servicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(servico1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(servico1.getNome())));
    }

    @Test
    void deveListarTodosServicos() throws Exception {
        Servico servico2 = new Servico("Jardinagem Ctrl", "JARDIM_CTRL", "Jardinagem para controller test");
        List<Servico> lista = Arrays.asList(servico1, servico2);
        when(servicoServiceMock.findAll()).thenReturn(lista);

        mockMvc.perform(get("/servicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is(servico1.getNome())));
    }

    @Test
    void deveEncontrarServicoPorCategoria() throws Exception {
        List<Servico> lista = Arrays.asList(servico1);
        when(servicoServiceMock.findByCategoria("LIMPEZA_CTRL")).thenReturn(lista);

        mockMvc.perform(get("/servicos/categoria/LIMPEZA_CTRL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].categoria", is("LIMPEZA_CTRL")));
    }

    @Test
    void deveEncontrarServicoPorNome() throws Exception {
        when(servicoServiceMock.findByNome("Limpeza Ctrl")).thenReturn(Optional.of(servico1));
        mockMvc.perform(get("/servicos/nome/Limpeza Ctrl"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Limpeza Ctrl")));
    }

    @Test
    void deveRetornarNotFoundParaServicoPorNomeInexistente() throws Exception {
        when(servicoServiceMock.findByNome("Inexistente")).thenReturn(Optional.empty());
        mockMvc.perform(get("/servicos/nome/Inexistente"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveDeletarServicoPorNomeExistente() throws Exception {
        when(servicoServiceMock.deleteByNome("Limpeza Ctrl")).thenReturn(true);
        mockMvc.perform(delete("/servicos/nome/Limpeza Ctrl"))
                .andExpect(status().isOk())
                .andExpect(content().string("Serviço 'Limpeza Ctrl' deletado com sucesso."));
    }

    @Test
    void deveRetornarNotFoundAoDeletarServicoPorNomeInexistente() throws Exception {
        when(servicoServiceMock.deleteByNome("Inexistente")).thenReturn(false);
        mockMvc.perform(delete("/servicos/nome/Inexistente"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Serviço 'Inexistente' não encontrado."));
    }
}