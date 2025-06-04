// src/test/java/com/exemple/backend/apresentacao/controllers/ServicoControllerTest.java

package com.exemple.backend.apresentacao.controllers;

import com.exemple.backend.dominio.models.Servico;
import com.exemple.backend.dominio.services.ServicoService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
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
    private Servico servico2;

    @TestConfiguration
    static class ServicoControllerTestConfig {
        @Bean
        @Primary
        public ServicoService servicoService() {
            return Mockito.mock(ServicoService.class);
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
        Mockito.reset(servicoServiceMock);

        servico1 = new Servico("Limpeza Ctrl Test", "LIMPEZA_TEST_CTRL", "Limpeza para teste de controller");
        servico2 = new Servico("Jardinagem Ctrl Test", "JARDIM_TEST_CTRL", "Jardinagem para teste de controller");
    }

    @Test
    void deveSalvarServicoComSucesso() throws Exception {
        when(servicoServiceMock.save(any(Servico.class))).thenReturn(servico1);

        mockMvc.perform(post("/servicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(servico1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(servico1.getNome())))
                .andExpect(jsonPath("$.categoria", is(servico1.getCategoria())));

        verify(servicoServiceMock, times(1)).save(any(Servico.class));
    }

    @Test
    void deveListarTodosOsServicos() throws Exception {
        List<Servico> lista = Arrays.asList(servico1, servico2);
        when(servicoServiceMock.findAll()).thenReturn(lista);

        mockMvc.perform(get("/servicos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is(servico1.getNome())))
                .andExpect(jsonPath("$[1].nome", is(servico2.getNome())));

        verify(servicoServiceMock, times(1)).findAll();
    }

    @Test
    void deveEncontrarServicoPorCategoria() throws Exception {
        List<Servico> listaCategoria = Arrays.asList(servico1);
        when(servicoServiceMock.findByCategoria("LIMPEZA_TEST_CTRL")).thenReturn(listaCategoria);

        mockMvc.perform(get("/servicos/categoria/LIMPEZA_TEST_CTRL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].categoria", is("LIMPEZA_TEST_CTRL")));

        verify(servicoServiceMock, times(1)).findByCategoria("LIMPEZA_TEST_CTRL");
    }

    @Test
    void deveEncontrarServicoPorNomeExistente() throws Exception {
        String nomeServico = servico1.getNome(); // "Limpeza Ctrl Test"
        when(servicoServiceMock.findByNome(nomeServico)).thenReturn(Optional.of(servico1));

        // Use a sintaxe de variável de caminho para que MockMvc/Spring cuidem da codificação
        mockMvc.perform(get("/servicos/nome/{nome}", nomeServico)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(nomeServico)));

        verify(servicoServiceMock, times(1)).findByNome(nomeServico);
    }

    @Test
    void deveRetornarNotFoundParaServicoPorNomeInexistente() throws Exception {
        String nomeInexistente = "ServicoQueNaoExiste";
        when(servicoServiceMock.findByNome(nomeInexistente)).thenReturn(Optional.empty());

        mockMvc.perform(get("/servicos/nome/{nome}", nomeInexistente)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(servicoServiceMock, times(1)).findByNome(nomeInexistente);
    }

    @Test
    void deveDeletarServicoPorNomeExistenteComSucesso() throws Exception {
        String nomeServico = servico1.getNome();
        when(servicoServiceMock.deleteByNome(nomeServico)).thenReturn(true);

        mockMvc.perform(delete("/servicos/nome/{nome}", nomeServico)) // Use a variável de caminho
                .andExpect(status().isOk())
                .andExpect(content().string("Serviço '" + nomeServico + "' deletado com sucesso."));

        verify(servicoServiceMock, times(1)).deleteByNome(nomeServico);
    }

    @Test
    void deveRetornarNotFoundAoDeletarServicoPorNomeInexistente() throws Exception {
        String nomeInexistente = "ServicoInexistenteParaDeletar";
        when(servicoServiceMock.deleteByNome(nomeInexistente)).thenReturn(false);

        mockMvc.perform(delete("/servicos/nome/{nome}", nomeInexistente)) // Use a variável de caminho
                .andExpect(status().isNotFound())
                .andExpect(content().string("Serviço '" + nomeInexistente + "' não encontrado."));

        verify(servicoServiceMock, times(1)).deleteByNome(nomeInexistente);
    }
}