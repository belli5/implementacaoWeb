package com.exemple.backend.apresentacao.controllers;

import com.exemple.backend.dominio.services.PedidoService;
// import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@WebMvcTest(OfereceController.class) // Controller está vazio
class OfereceControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PedidoService pedidoServiceMock; // Mock mesmo que o controller não use ainda

    // @Autowired
    // private ObjectMapper objectMapper;

    @TestConfiguration
    static class PedidoControllerTestConfig {
        @Bean
        @Primary
        public PedidoService pedidoService() {
            return Mockito.mock(PedidoService.class);
        }
    }

    @BeforeEach
    void setUp(){
        Mockito.reset(pedidoServiceMock);
    }

    @Test
    void contextLoads() {
        // Teste básico para verificar se o contexto do controller (vazio) carrega
        assertNotNull(mockMvc);
        assertNotNull(pedidoServiceMock); // Verifica se o mock foi injetado
    }

    // Adicionar testes para endpoints quando forem implementados no PedidoController
    // Exemplo:
    // @Test
    // void deveCriarPedido() throws Exception {
    //     // Arrange
    //     // Pedido pedidoInput = new Pedido(...);
    //     // Pedido pedidoSalvo = new Pedido(...);
    //     // when(pedidoServiceMock.salvar(any(Pedido.class))).thenReturn(pedidoSalvo);
    //
    //     // mockMvc.perform(post("/api/pedidos") // Definir o path correto
    //     //                 .contentType(MediaType.APPLICATION_JSON)
    //     //                 .content(objectMapper.writeValueAsString(pedidoInput)))
    //     //         .andExpect(status().isCreated())
    //     //         .andExpect(jsonPath("$.id", is(pedidoSalvo.getId())));
    // }
}