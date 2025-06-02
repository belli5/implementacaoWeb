package com.exemple.backend.apresentacao.controllers;

import com.exemple.backend.dominio.models.AvaliacaoSobreCliente;
import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.dominio.services.AvaliacaoSobreClienteService;
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

// O RequestMapping no controller é '/clienteAvaliacao'
// Isso não é um path válido (deveria ser, por exemplo, "/clienteAvaliacao"). Vou assumir "/clienteAvaliacao".
@WebMvcTest(AvaliacaoSobreClienteController.class)
class AvaliacaoSobreClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AvaliacaoSobreClienteService avaliacaoServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    private AvaliacaoSobreCliente avaliacao1;
    private Cliente cliente;
    private Prestador prestador;
    private Endereco endereco;

    @TestConfiguration
    static class AvaliacaoSobreClienteControllerTestConfig {
        @Bean
        @Primary
        public AvaliacaoSobreClienteService avaliacaoSobreClienteService() {
            return Mockito.mock(AvaliacaoSobreClienteService.class);
        }
    }

    @BeforeEach
    void setUp() {
        endereco = new Endereco("Rua AvalC Ctrl", "Bairro ACtrl", "Cidade ACtrl", "AC");
        cliente = new Cliente(1, "Cliente ACtrl", "sac", "cac@email.com", "1ac", endereco);
        prestador = new Prestador(1, "Prestador ACtrl", "sap", "pac@email.com", "1ap", endereco);
        avaliacao1 = new AvaliacaoSobreCliente(1, prestador, "Cliente bom!", 5, cliente);
        Mockito.reset(avaliacaoServiceMock);
    }

    // O GetMapping no controller é '/{clienteId}'
    // Isso também não é um path válido. Vou assumir "/{clienteId}"
    @Test
    void deveRetornarAvaliacoesPorClienteId() throws Exception {
        List<AvaliacaoSobreCliente> lista = Arrays.asList(avaliacao1);
        when(avaliacaoServiceMock.contarAvaliacoesPorCliente(1)).thenReturn(lista);
        mockMvc.perform(get("/clienteAvaliacao/1")) // Ajustado o path
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    void deveRetornarNoContentQuandoNenhumaAvaliacaoPorClienteEncontrada() throws Exception {
        when(avaliacaoServiceMock.contarAvaliacoesPorCliente(2)).thenReturn(Arrays.asList()); // Lista vazia
        mockMvc.perform(get("/clienteAvaliacao/2"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveCriarAvaliacao() throws Exception {
        AvaliacaoSobreCliente avaliacaoInput = new AvaliacaoSobreCliente(0, prestador, "Comentario input", 4, cliente);
        AvaliacaoSobreCliente avaliacaoSalva = new AvaliacaoSobreCliente(10, prestador, "Comentario input", 4, cliente);
        when(avaliacaoServiceMock.salvarAvaliacao(any(AvaliacaoSobreCliente.class))).thenReturn(avaliacaoSalva);

        mockMvc.perform(post("/clienteAvaliacao") // Ajustado o path
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(avaliacaoInput)))
                .andExpect(status().isCreated()) // Espera 201 Created
                .andExpect(jsonPath("$.id", is(10)))
                .andExpect(jsonPath("$.comentario", is("Comentario input")));
    }

    @Test
    void deveAtualizarAvaliacaoExistente() throws Exception {
        AvaliacaoSobreCliente avaliacaoUpdate = new AvaliacaoSobreCliente(1, prestador, "Comentario atualizado", 3, cliente);
        when(avaliacaoServiceMock.buscarPorId(1)).thenReturn(Optional.of(avaliacao1));
        when(avaliacaoServiceMock.salvarAvaliacao(any(AvaliacaoSobreCliente.class))).thenReturn(avaliacaoUpdate);

        mockMvc.perform(put("/clienteAvaliacao/1") // Ajustado o path
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(avaliacaoUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comentario", is("Comentario atualizado")));
    }

    @Test
    void deveRetornarNotFoundAoAtualizarAvaliacaoInexistente() throws Exception {
        AvaliacaoSobreCliente avaliacaoUpdate = new AvaliacaoSobreCliente(99, prestador, "Comentario atualizado", 3, cliente);
        when(avaliacaoServiceMock.buscarPorId(99)).thenReturn(Optional.empty());

        mockMvc.perform(put("/clienteAvaliacao/99") // Ajustado o path
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(avaliacaoUpdate)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveDeletarAvaliacaoExistente() throws Exception {
        when(avaliacaoServiceMock.buscarPorId(1)).thenReturn(Optional.of(avaliacao1));
        doNothing().when(avaliacaoServiceMock).deletarAvaliacao(1);

        mockMvc.perform(delete("/clienteAvaliacao/1")) // Ajustado o path
                .andExpect(status().isNoContent());
    }

    @Test
    void deveRetornarNotFoundAoDeletarAvaliacaoInexistente() throws Exception {
        when(avaliacaoServiceMock.buscarPorId(99)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/clienteAvaliacao/99")) // Ajustado o path
                .andExpect(status().isNotFound());
    }
}