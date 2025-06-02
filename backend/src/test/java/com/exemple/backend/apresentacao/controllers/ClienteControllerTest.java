package com.exemple.backend.apresentacao.controllers;

import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.dominio.services.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito; // Importe Mockito
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration; // Para configuração de beans de teste
import org.springframework.context.annotation.Bean; // Para definir um bean
import org.springframework.context.annotation.Primary; // Para garantir que este mock seja usado
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(ClienteController.class) // Foca apenas no ClienteController
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // O ClienteService agora será injetado a partir da TestConfig
    @Autowired
    private ClienteService clienteServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente cliente1;
    private Cliente cliente2;
    private Endereco endereco;

    // Classe de configuração de Teste para fornecer o mock do ClienteService
    // Pode ser uma classe aninhada estática ou uma classe separada.
    @TestConfiguration
    static class ClienteControllerTestConfig {

        @Bean
        @Primary // Garante que esta implementação mockada seja prioritária
        public ClienteService clienteService() {
            // Cria e retorna um mock do ClienteService usando Mockito
            return Mockito.mock(ClienteService.class);
        }
    }

    @BeforeEach
    void setUp() {
        endereco = new Endereco("Rua Controller", "Bairro Ctrl", "Cidade Ctrl", "CT");
        cliente1 = new Cliente(1, "Cliente Um Ctrl", "senhaCtrl1", "umctrl@email.com", "111222", endereco);
        cliente2 = new Cliente(2, "Cliente Dois Ctrl", "senhaCtrl2", "doisctrl@email.com", "333444", endereco);

        // É uma boa prática resetar o mock antes de cada teste para evitar interferência entre eles,
        // especialmente se o contexto do Spring não for recriado para cada método de teste.
        Mockito.reset(clienteServiceMock);
    }

    @Test
    void deveRetornarClientePorIdQuandoExistir() throws Exception {
        when(clienteServiceMock.findById(1)).thenReturn(Optional.of(cliente1));

        mockMvc.perform(get("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("Cliente Um Ctrl")));
    }

    @Test
    void deveRetornarNotFoundQuandoClientePorIdNaoExistir() throws Exception {
        when(clienteServiceMock.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/clientes/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornarListaDeTodosOsClientes() throws Exception {
        List<Cliente> listaClientes = Arrays.asList(cliente1, cliente2);
        when(clienteServiceMock.findAll()).thenReturn(listaClientes);

        mockMvc.perform(get("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    void deveCriarNovoClienteComSucesso() throws Exception {
        Cliente clienteInput = new Cliente(null, "Novo Cliente Ctrl", "senhaNovaCtrl", "novoctrl@email.com", "555666", endereco);
        Cliente clienteSalvoPeloService = new Cliente(3, "Novo Cliente Ctrl", "senhaNovaCtrl", "novoctrl@email.com", "555666", endereco);

        when(clienteServiceMock.create(any(Cliente.class))).thenReturn(clienteSalvoPeloService);

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteInput)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.nome", is("Novo Cliente Ctrl")));
    }

    @Test
    void deveAtualizarClienteExistenteComSucesso() throws Exception {
        Cliente clienteAtualizadoInfo = new Cliente(1, "Cliente Um Ctrl Atualizado", "senhaCtrl1Nova", "umctrl_novo@email.com", "111222novo", endereco);
        // O controller chama clienteService.update(). O mock para este método deve cobrir o retorno esperado.
        when(clienteServiceMock.update(any(Cliente.class))).thenReturn(clienteAtualizadoInfo);

        mockMvc.perform(put("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteAtualizadoInfo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("Cliente Um Ctrl Atualizado")));
    }


    @Test
    void deveRemoverClienteComSucesso() throws Exception {
        doNothing().when(clienteServiceMock).delete(anyInt());

        mockMvc.perform(delete("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}