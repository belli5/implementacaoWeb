package com.exemple.backend.apresentacao.controllers;

import com.exemple.backend.dominio.models.AvaliacaoSobrePrestador;
import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.dominio.services.AvaliacaoSobrePrestadorService;
import com.exemple.backend.dominio.services.ClienteService;
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

    // Estes mocks serão fornecidos pela TestConfig abaixo
    @Autowired
    private AvaliacaoSobrePrestadorService avaliacaoServiceMock;
    @Autowired
    private PrestadorService prestadorServiceMock;
    @Autowired
    private ClienteService clienteServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente clienteValidoParaTeste; // Renomeado para clareza
    private Prestador prestadorValidoParaTeste; // Renomeado para clareza
    private AvaliacaoSobrePrestador avaliacaoExistenteSimulada; // Representa uma avaliação que já existe
    private AvaliacaoSobrePrestador avaliacaoInputParaPost;    // Representa os dados que seriam enviados num POST

    // Configuração de teste para fornecer os mocks dos services
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
    }

    @BeforeEach
    void setUp() {
        // Registrar o módulo JavaTime se seus modelos usam LocalDate, LocalDateTime, etc.
        // para correta serialização/desserialização JSON.
        objectMapper.registerModule(new JavaTimeModule());

        // Resetar os mocks antes de cada teste para garantir isolamento
        Mockito.reset(avaliacaoServiceMock, prestadorServiceMock, clienteServiceMock);

        Endereco enderecoComum = new Endereco("Rua Teste Setup", "Bairro Setup", "Cidade Setup", "TS");

        // Cliente e Prestador que simulamos existir no sistema
        clienteValidoParaTeste = new Cliente(1, "Cliente Setup", "senhaC", "cliente.setup@teste.com", "111000", enderecoComum);
        prestadorValidoParaTeste = new Prestador(1, "Prestador Setup", "senhaP", "prestador.setup@teste.com", "222000", enderecoComum);

        // Simula uma avaliação que já existe no sistema (ex: para GET /id)
        avaliacaoExistenteSimulada = new AvaliacaoSobrePrestador(
                Integer.valueOf(10), // ID da avaliação existente
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
                Integer.valueOf(0),
                clienteParaCorpoJson,
                "Este é um novo comentário para POST",
                Integer.valueOf(4),
                prestadorParaCorpoJson
        );
    }

    // --- Início dos Métodos de Teste ---

    @Test
    void deveCriarAvaliacaoSobrePrestadorComSucesso() throws Exception {
        // Configura mocks para os services que são chamados antes do save
        when(prestadorServiceMock.findById(prestadorValidoParaTeste.getId())).thenReturn(Optional.of(prestadorValidoParaTeste));
        when(clienteServiceMock.findById(clienteValidoParaTeste.getId())).thenReturn(Optional.of(clienteValidoParaTeste));

        AvaliacaoSobrePrestador avaliacaoRetornadaPeloService = new AvaliacaoSobrePrestador(
                Integer.valueOf(20), // Novo ID gerado
                clienteValidoParaTeste, // Cliente real encontrado
                avaliacaoInputParaPost.getComentario(), // Comentário do input
                avaliacaoInputParaPost.getNota(),       // Nota do input
                prestadorValidoParaTeste  // Prestador real encontrado
        );
        when(avaliacaoServiceMock.save(any(AvaliacaoSobrePrestador.class))).thenReturn(avaliacaoRetornadaPeloService);

        // Executa a requisição POST
        mockMvc.perform(post("/avaliacaoSobrePrestador/novaAvaliacaoSobrePrestador")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(avaliacaoInputParaPost))) // Serializa o objeto de input
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(20))) // Verifica o ID retornado pelo service
                .andExpect(jsonPath("$.comentario", is(avaliacaoInputParaPost.getComentario())))
                .andExpect(jsonPath("$.nota", is(avaliacaoInputParaPost.getNota())))
                // Verifica se os IDs de cliente e prestador no JSON de resposta correspondem
                .andExpect(jsonPath("$.cliente.id", is(clienteValidoParaTeste.getId())))
                .andExpect(jsonPath("$.prestador.id", is(prestadorValidoParaTeste.getId())));

        // Verifica se os services foram chamados
        verify(prestadorServiceMock, times(1)).findById(prestadorValidoParaTeste.getId());
        verify(clienteServiceMock, times(1)).findById(clienteValidoParaTeste.getId());
        verify(avaliacaoServiceMock, times(1)).save(any(AvaliacaoSobrePrestador.class));
    }

    @Test
    void naoDeveCriarAvaliacaoSePrestadorNaoEncontrado() throws Exception {
        // Configura o prestadorService para retornar Optional.empty()
        when(prestadorServiceMock.findById(avaliacaoInputParaPost.getPrestador().getId())).thenReturn(Optional.empty());

        mockMvc.perform(post("/avaliacaoSobrePrestador/novaAvaliacaoSobrePrestador")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(avaliacaoInputParaPost)))
                .andExpect(status().isNotFound()); // Espera 404

        verify(prestadorServiceMock, times(1)).findById(avaliacaoInputParaPost.getPrestador().getId());
        verify(clienteServiceMock, never()).findById(anyInt()); // Não deve chegar a chamar o clienteService
        verify(avaliacaoServiceMock, never()).save(any(AvaliacaoSobrePrestador.class)); // Nem o save
    }

    @Test
    void naoDeveCriarAvaliacaoSeClienteNaoEncontrado() throws Exception {
        // Configura prestadorService para encontrar o prestador
        when(prestadorServiceMock.findById(avaliacaoInputParaPost.getPrestador().getId())).thenReturn(Optional.of(prestadorValidoParaTeste));
        // Configura clienteService para NÃO encontrar o cliente
        when(clienteServiceMock.findById(avaliacaoInputParaPost.getCliente().getId())).thenReturn(Optional.empty());

        mockMvc.perform(post("/avaliacaoSobrePrestador/novaAvaliacaoSobrePrestador")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(avaliacaoInputParaPost)))
                .andExpect(status().isNotFound()); // Espera 404

        verify(prestadorServiceMock, times(1)).findById(avaliacaoInputParaPost.getPrestador().getId());
        verify(clienteServiceMock, times(1)).findById(avaliacaoInputParaPost.getCliente().getId());
        verify(avaliacaoServiceMock, never()).save(any(AvaliacaoSobrePrestador.class)); // Não deve chegar ao save
    }

    @Test
    void deveEncontrarAvaliacaoSobrePrestadorPorId() throws Exception {
        when(avaliacaoServiceMock.findById(10)).thenReturn(Optional.of(avaliacaoExistenteSimulada));

        mockMvc.perform(get("/avaliacaoSobrePrestador/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(10)))
                .andExpect(jsonPath("$.comentario", is(avaliacaoExistenteSimulada.getComentario())));
        verify(avaliacaoServiceMock, times(1)).findById(10);
    }

    @Test
    void deveRetornarNotFoundSeAvaliacaoPorIdNaoExistir() throws Exception {
        when(avaliacaoServiceMock.findById(99)).thenReturn(Optional.empty());
        mockMvc.perform(get("/avaliacaoSobrePrestador/99"))
                .andExpect(status().isNotFound());
        verify(avaliacaoServiceMock, times(1)).findById(99);
    }

    @Test
    void deveListarTodasAvaliacoesSobrePrestador() throws Exception {
        List<AvaliacaoSobrePrestador> lista = Arrays.asList(avaliacaoExistenteSimulada);
        when(avaliacaoServiceMock.findAll()).thenReturn(lista);

        mockMvc.perform(get("/avaliacaoSobrePrestador/todas_as_avaliacoes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(avaliacaoExistenteSimulada.getId())));
        verify(avaliacaoServiceMock, times(1)).findAll();
    }

    @Test
    void deveRetornarListaVaziaSeNaoHouverAvaliacoes() throws Exception {
        when(avaliacaoServiceMock.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/avaliacaoSobrePrestador/todas_as_avaliacoes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
        verify(avaliacaoServiceMock, times(1)).findAll();
    }

    @Test
    void deveListarAvaliacoesPorPrestadorId() throws Exception {
        when(prestadorServiceMock.findById(prestadorValidoParaTeste.getId())).thenReturn(Optional.of(prestadorValidoParaTeste));
        List<AvaliacaoSobrePrestador> lista = Arrays.asList(avaliacaoExistenteSimulada); // Supondo que esta avaliação é do prestadorValidoParaTeste
        when(avaliacaoServiceMock.findByPrestadorId(prestadorValidoParaTeste.getId())).thenReturn(lista);

        mockMvc.perform(get("/avaliacaoSobrePrestador/avaliacoes_por_prestador/" + prestadorValidoParaTeste.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(avaliacaoExistenteSimulada.getId())));
        verify(prestadorServiceMock, times(1)).findById(prestadorValidoParaTeste.getId());
        verify(avaliacaoServiceMock, times(1)).findByPrestadorId(prestadorValidoParaTeste.getId());
    }

    @Test
    void deveRetornarNotFoundSePrestadorNaoExistirAoListarAvaliacoesPorPrestador() throws Exception {
        int prestadorIdInexistente = 999;
        when(prestadorServiceMock.findById(prestadorIdInexistente)).thenReturn(Optional.empty());

        mockMvc.perform(get("/avaliacaoSobrePrestador/avaliacoes_por_prestador/" + prestadorIdInexistente))
                .andExpect(status().isNotFound());
        verify(prestadorServiceMock, times(1)).findById(prestadorIdInexistente);
        verify(avaliacaoServiceMock, never()).findByPrestadorId(anyInt());
    }

    @Test
    void deveDeletarAvaliacaoComSucesso() throws Exception {
        when(avaliacaoServiceMock.findById(10)).thenReturn(Optional.of(avaliacaoExistenteSimulada));
        doNothing().when(avaliacaoServiceMock).delete(10);

        mockMvc.perform(delete("/avaliacaoSobrePrestador/10"))
                .andExpect(status().isNoContent());
        verify(avaliacaoServiceMock, times(1)).findById(10);
        verify(avaliacaoServiceMock, times(1)).delete(10);
    }

    @Test
    void deveRetornarNotFoundAoTentarDeletarAvaliacaoInexistente() throws Exception {
        when(avaliacaoServiceMock.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/avaliacaoSobrePrestador/99"))
                .andExpect(status().isNotFound());
        verify(avaliacaoServiceMock, times(1)).findById(99);
        verify(avaliacaoServiceMock, never()).delete(anyInt());
    }
}