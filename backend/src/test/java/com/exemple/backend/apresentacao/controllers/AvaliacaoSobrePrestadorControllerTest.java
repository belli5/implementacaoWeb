// Salve este arquivo como:
// src/test/java/com/exemple/backend/apresentacao/controllers/AvaliacaoSobrePrestadorControllerTest.java

package com.exemple.backend.apresentacao.controllers;

import com.exemple.backend.dominio.models.AvaliacaoSobrePrestador;
import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.dominio.services.AvaliacaoSobrePrestadorService;
import com.exemple.backend.dominio.services.ClienteService;
import com.exemple.backend.dominio.services.PrestadorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; // Importe se necessário para datas
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AvaliacaoSobrePrestadorController.class)
class AvaliacaoSobrePrestadorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AvaliacaoSobrePrestadorService avaliacaoServiceMock;
    @Autowired
    private PrestadorService prestadorServiceMock;
    @Autowired
    private ClienteService clienteServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente clienteValidoParaTeste;
    private Prestador prestadorValidoParaTeste;
    private AvaliacaoSobrePrestador avaliacaoExistenteSimulada;
    private AvaliacaoSobrePrestador avaliacaoInputParaPost;

    @TestConfiguration
    static class ControllerTestConfig {
        @Bean
        @Primary
        public AvaliacaoSobrePrestadorService avaliacaoSobrePrestadorService() {
            return Mockito.mock(AvaliacaoSobrePrestadorService.class);
        }

        @Bean
        @Primary
        public PrestadorService prestadorService() {
            return Mockito.mock(PrestadorService.class);
        }

        @Bean
        @Primary
        public ClienteService clienteService() {
            return Mockito.mock(ClienteService.class);
        }

        // Bean para configurar o Spring Security para os testes deste controller
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(AbstractHttpConfigurer::disable) // Desabilita CSRF para os testes
                    .authorizeHttpRequests(auth -> auth
                            .anyRequest().permitAll() // Permite todas as requisições sem autenticação
                    );
            return http.build();
        }
    }

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule()); // Para datas, se aplicável
        objectMapper.findAndRegisterModules();


        Mockito.reset(avaliacaoServiceMock, prestadorServiceMock, clienteServiceMock);

        Endereco enderecoComum = new Endereco("Rua Teste Setup", "Bairro Setup", "Cidade Setup", "TS");

        clienteValidoParaTeste = new Cliente(1, "Cliente Setup", "senhaC", "cliente.setup@teste.com", "111000", enderecoComum);
        prestadorValidoParaTeste = new Prestador(1, "Prestador Setup", "senhaP", "prestador.setup@teste.com", "222000", enderecoComum);

        avaliacaoExistenteSimulada = new AvaliacaoSobrePrestador(
                Integer.valueOf(10),
                clienteValidoParaTeste,
                "Avaliação existente muito boa!",
                Integer.valueOf(5),
                prestadorValidoParaTeste
        );

        Cliente clienteParaCorpoJson = new Cliente();
        clienteParaCorpoJson.setId(clienteValidoParaTeste.getId());
        Prestador prestadorParaCorpoJson = new Prestador();
        prestadorParaCorpoJson.setId(prestadorValidoParaTeste.getId());

        avaliacaoInputParaPost = new AvaliacaoSobrePrestador(
                Integer.valueOf(0), // ID Placeholder
                clienteParaCorpoJson,
                "Este é um novo comentário para POST",
                Integer.valueOf(4),
                prestadorParaCorpoJson
        );
    }

    @Test
    void deveCriarAvaliacaoSobrePrestadorComSucesso() throws Exception {
        when(prestadorServiceMock.findById(prestadorValidoParaTeste.getId())).thenReturn(Optional.of(prestadorValidoParaTeste));
        when(clienteServiceMock.findById(clienteValidoParaTeste.getId())).thenReturn(Optional.of(clienteValidoParaTeste));

        AvaliacaoSobrePrestador avaliacaoRetornadaPeloService = new AvaliacaoSobrePrestador(
                Integer.valueOf(20),
                clienteValidoParaTeste,
                avaliacaoInputParaPost.getComentario(),
                avaliacaoInputParaPost.getNota(),
                prestadorValidoParaTeste
        );
        // O problema de construtor no controller pode fazer este teste falhar com 500
        // se a lógica do controller não for corrigida.
        when(avaliacaoServiceMock.save(any(AvaliacaoSobrePrestador.class))).thenReturn(avaliacaoRetornadaPeloService);

        mockMvc.perform(post("/avaliacaoSobrePrestador/novaAvaliacaoSobrePrestador")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(avaliacaoInputParaPost)))
                .andExpect(status().isOk()) // Mantendo a expectativa original do controller
                .andExpect(jsonPath("$.id", is(20)))
                .andExpect(jsonPath("$.comentario", is(avaliacaoInputParaPost.getComentario())));

        verify(prestadorServiceMock).findById(prestadorValidoParaTeste.getId());
        verify(clienteServiceMock).findById(clienteValidoParaTeste.getId());
        verify(avaliacaoServiceMock).save(any(AvaliacaoSobrePrestador.class));
    }

    @Test
    void naoDeveCriarAvaliacaoSePrestadorNaoEncontrado() throws Exception {
        when(prestadorServiceMock.findById(avaliacaoInputParaPost.getPrestador().getId())).thenReturn(Optional.empty());

        mockMvc.perform(post("/avaliacaoSobrePrestador/novaAvaliacaoSobrePrestador")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(avaliacaoInputParaPost)))
                .andExpect(status().isNotFound());

        verify(prestadorServiceMock).findById(avaliacaoInputParaPost.getPrestador().getId());
        verify(clienteServiceMock, never()).findById(anyInt());
        verify(avaliacaoServiceMock, never()).save(any(AvaliacaoSobrePrestador.class));
    }

    @Test
    void naoDeveCriarAvaliacaoSeClienteNaoEncontrado() throws Exception {
        when(prestadorServiceMock.findById(avaliacaoInputParaPost.getPrestador().getId())).thenReturn(Optional.of(prestadorValidoParaTeste));
        when(clienteServiceMock.findById(avaliacaoInputParaPost.getCliente().getId())).thenReturn(Optional.empty());

        mockMvc.perform(post("/avaliacaoSobrePrestador/novaAvaliacaoSobrePrestador")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(avaliacaoInputParaPost)))
                .andExpect(status().isNotFound());

        verify(prestadorServiceMock).findById(avaliacaoInputParaPost.getPrestador().getId());
        verify(clienteServiceMock).findById(avaliacaoInputParaPost.getCliente().getId());
        verify(avaliacaoServiceMock, never()).save(any(AvaliacaoSobrePrestador.class));
    }

    @Test
    void deveEncontrarAvaliacaoSobrePrestadorPorId() throws Exception {
        when(avaliacaoServiceMock.findById(10)).thenReturn(Optional.of(avaliacaoExistenteSimulada));

        mockMvc.perform(get("/avaliacaoSobrePrestador/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(10)))
                .andExpect(jsonPath("$.comentario", is(avaliacaoExistenteSimulada.getComentario())));
        verify(avaliacaoServiceMock).findById(10);
    }

    @Test
    void deveRetornarNotFoundSeAvaliacaoPorIdNaoExistir() throws Exception {
        when(avaliacaoServiceMock.findById(99)).thenReturn(Optional.empty());
        mockMvc.perform(get("/avaliacaoSobrePrestador/99"))
                .andExpect(status().isNotFound());
        verify(avaliacaoServiceMock).findById(99);
    }

    @Test
    void deveListarTodasAvaliacoesSobrePrestador() throws Exception {
        List<AvaliacaoSobrePrestador> lista = Arrays.asList(avaliacaoExistenteSimulada);
        when(avaliacaoServiceMock.findAll()).thenReturn(lista);

        mockMvc.perform(get("/avaliacaoSobrePrestador/todas_as_avaliacoes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(avaliacaoExistenteSimulada.getId())));
        verify(avaliacaoServiceMock).findAll();
    }

    @Test
    void deveRetornarListaVaziaSeNaoHouverAvaliacoes() throws Exception {
        when(avaliacaoServiceMock.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/avaliacaoSobrePrestador/todas_as_avaliacoes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
        verify(avaliacaoServiceMock).findAll();
    }

    @Test
    void deveListarAvaliacoesPorPrestadorId() throws Exception {
        when(prestadorServiceMock.findById(prestadorValidoParaTeste.getId())).thenReturn(Optional.of(prestadorValidoParaTeste));
        List<AvaliacaoSobrePrestador> lista = Arrays.asList(avaliacaoExistenteSimulada);
        when(avaliacaoServiceMock.findByPrestadorId(prestadorValidoParaTeste.getId())).thenReturn(lista);

        mockMvc.perform(get("/avaliacaoSobrePrestador/avaliacoes_por_prestador/" + prestadorValidoParaTeste.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(avaliacaoExistenteSimulada.getId())));
        verify(prestadorServiceMock).findById(prestadorValidoParaTeste.getId());
        verify(avaliacaoServiceMock).findByPrestadorId(prestadorValidoParaTeste.getId());
    }

    @Test
    void deveRetornarNotFoundSePrestadorNaoExistirAoListarAvaliacoesPorPrestador() throws Exception {
        int prestadorIdInexistente = 999;
        when(prestadorServiceMock.findById(prestadorIdInexistente)).thenReturn(Optional.empty());

        mockMvc.perform(get("/avaliacaoSobrePrestador/avaliacoes_por_prestador/" + prestadorIdInexistente))
                .andExpect(status().isNotFound());
        verify(prestadorServiceMock).findById(prestadorIdInexistente);
        verify(avaliacaoServiceMock, never()).findByPrestadorId(anyInt());
    }

    @Test
    void deveDeletarAvaliacaoComSucesso() throws Exception {
        when(avaliacaoServiceMock.findById(10)).thenReturn(Optional.of(avaliacaoExistenteSimulada));
        doNothing().when(avaliacaoServiceMock).delete(10);

        mockMvc.perform(delete("/avaliacaoSobrePrestador/10"))
                .andExpect(status().isNoContent());
        verify(avaliacaoServiceMock).findById(10);
        verify(avaliacaoServiceMock).delete(10);
    }

    @Test
    void deveRetornarNotFoundAoTentarDeletarAvaliacaoInexistente() throws Exception {
        when(avaliacaoServiceMock.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/avaliacaoSobrePrestador/99"))
                .andExpect(status().isNotFound());
        verify(avaliacaoServiceMock).findById(99);
        verify(avaliacaoServiceMock, never()).delete(anyInt());
    }
}