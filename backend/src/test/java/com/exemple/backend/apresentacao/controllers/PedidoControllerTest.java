// Arquivo: src/test/java/com/exemple/backend/apresentacao/controllers/PedidoControllerTest.java

package com.exemple.backend.apresentacao.controllers;

import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Pedido;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.Servico;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.dominio.services.PedidoService;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PedidoController.class)
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PedidoService pedidoServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    private Pedido pedido1;
    private Pedido pedido2;
    private Cliente clienteValido;
    private Prestador prestadorValido;
    private Servico servicoValido;
    private Endereco enderecoComum;


    @TestConfiguration
    static class PedidoControllerTestConfig {
        @Bean
        @Primary
        public PedidoService pedidoService() {
            return Mockito.mock(PedidoService.class);
        }
    }

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.findAndRegisterModules();

        Mockito.reset(pedidoServiceMock);

        enderecoComum = new Endereco("Rua Pedido Teste Ctrl", "Bairro PTC", "Cidade PTC", "PT");
        clienteValido = new Cliente(1, "Cliente Teste PedCtrl", "senhaC", "clienteped@teste.com", "111222", enderecoComum);
        prestadorValido = new Prestador(1, "Prestador Teste PedCtrl", "senhaP", "prestadorped@teste.com", "333444", enderecoComum);
        servicoValido = new Servico("Serviço Teste PedCtrl", "CATEGORIA_PED_CTRL", "Descrição do serviço para teste de pedido controller");

        pedido1 = new Pedido(Integer.valueOf(1), LocalDate.now().minusDays(5), servicoValido, prestadorValido, clienteValido, "PENDENTE_CTRL");
        pedido2 = new Pedido(Integer.valueOf(2), LocalDate.now().minusDays(2), servicoValido, prestadorValido, clienteValido, "CONCLUIDO_CTRL");
    }

    @Test
    void deveListarTodosOsPedidosCorretamente() throws Exception {
        List<Pedido> listaDePedidos = Arrays.asList(pedido1, pedido2);
        when(pedidoServiceMock.buscarTodos()).thenReturn(listaDePedidos);

        mockMvc.perform(get("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(pedido1.getId())))
                .andExpect(jsonPath("$[1].id", is(pedido2.getId())));

        verify(pedidoServiceMock, times(1)).buscarTodos();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaPedidosParaListar() throws Exception {
        when(pedidoServiceMock.buscarTodos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
        verify(pedidoServiceMock, times(1)).buscarTodos();
    }

    @Test
    void deveBuscarPedidoPorIdExistenteCorretamente() throws Exception {
        when(pedidoServiceMock.buscarPorId(pedido1.getId())).thenReturn(Optional.of(pedido1));

        mockMvc.perform(get("/pedidos/" + pedido1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(pedido1.getId())))
                .andExpect(jsonPath("$.status", is(pedido1.getStatus())));
        verify(pedidoServiceMock, times(1)).buscarPorId(pedido1.getId());
    }

    @Test
    void deveRetornarNotFoundQuandoBuscarPedidoPorIdInexistente() throws Exception {
        int idInexistente = 999;
        when(pedidoServiceMock.buscarPorId(idInexistente)).thenReturn(Optional.empty());

        mockMvc.perform(get("/pedidos/" + idInexistente)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(pedidoServiceMock, times(1)).buscarPorId(idInexistente);
    }

    @Test
    void deveBuscarPedidosPorPrestadorIdCorretamente() throws Exception {
        List<Pedido> pedidosDoPrestador = Arrays.asList(pedido1);
        // Certifique-se que prestadorValido.getId() não é nulo
        assertNotNull(prestadorValido.getId(), "ID do prestadorValido não pode ser nulo para o teste");
        when(pedidoServiceMock.buscarPorPrestador(prestadorValido.getId())).thenReturn(pedidosDoPrestador);

        mockMvc.perform(get("/pedidos/prestador/" + prestadorValido.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(pedido1.getId())));
        verify(pedidoServiceMock, times(1)).buscarPorPrestador(prestadorValido.getId());
    }
    @Test
    void deveRetornarListaVaziaSeNenhumPedidoParaPrestadorId() throws Exception {
        int idPrestadorSemPedidos = 999;
        when(pedidoServiceMock.buscarPorPrestador(idPrestadorSemPedidos)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/pedidos/prestador/" + idPrestadorSemPedidos))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
        verify(pedidoServiceMock, times(1)).buscarPorPrestador(idPrestadorSemPedidos);
    }


    @Test
    void deveBuscarPedidosPorClienteIdCorretamente() throws Exception {
        List<Pedido> pedidosDoCliente = Arrays.asList(pedido2);
        // Certifique-se que clienteValido.getId() não é nulo
        assertNotNull(clienteValido.getId(), "ID do clienteValido não pode ser nulo para o teste");
        when(pedidoServiceMock.buscarPorCliente(clienteValido.getId())).thenReturn(pedidosDoCliente);

        mockMvc.perform(get("/pedidos/cliente/" + clienteValido.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(pedido2.getId())));
        verify(pedidoServiceMock, times(1)).buscarPorCliente(clienteValido.getId());
    }

    @Test
    void deveCriarNovoPedidoComSucesso() throws Exception {
        // Para o input, o ID do Pedido em si (primeiro argumento do construtor Pedido)
        // não viria do cliente para uma nova entidade. No entanto, o construtor do modelo
        // Pedido EXIGE um ID não nulo. Fornecemos um placeholder (ex: Integer.valueOf(0))
        // para que a instanciação do objeto de input no teste não falhe.
        Pedido pedidoInput = new Pedido(
                Integer.valueOf(0), // ID Placeholder para satisfazer o construtor no teste
                LocalDate.now(),
                servicoValido,
                prestadorValido,
                clienteValido,
                "NOVO_PEDIDO_CTRL"
        );

        // O service.salvar deve retornar o pedido com o ID real gerado.
        Pedido pedidoSalvoPeloService = new Pedido(
                Integer.valueOf(3), // Simula um novo ID gerado
                pedidoInput.getData(),
                pedidoInput.getServico(),
                pedidoInput.getPrestador(),
                pedidoInput.getCliente(),
                pedidoInput.getStatus()
        );

        when(pedidoServiceMock.salvar(any(Pedido.class))).thenReturn(pedidoSalvoPeloService);

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoInput)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.status", is("NOVO_PEDIDO_CTRL")));

        verify(pedidoServiceMock, times(1)).salvar(argThat(p ->
                p.getId() != null && p.getId().equals(0) &&
                        p.getStatus().equals("NOVO_PEDIDO_CTRL")
        ));
    }

    @Test
    void deveAtualizarPedidoExistenteComSucesso() throws Exception {
        int idPedidoParaAtualizar = pedido1.getId();
        Pedido pedidoComNovosDados = new Pedido(
                idPedidoParaAtualizar,
                LocalDate.now(),
                servicoValido,
                prestadorValido,
                clienteValido,
                "STATUS_ATUALIZADO_CTRL"
        );
        Pedido pedidoRetornadoPeloService = new Pedido(
                idPedidoParaAtualizar,
                pedidoComNovosDados.getData(),
                pedidoComNovosDados.getServico(),
                pedidoComNovosDados.getPrestador(),
                pedidoComNovosDados.getCliente(),
                pedidoComNovosDados.getStatus()
        );

        when(pedidoServiceMock.atualizar(any(Pedido.class))).thenReturn(pedidoRetornadoPeloService);

        mockMvc.perform(put("/pedidos/" + idPedidoParaAtualizar)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoComNovosDados)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(idPedidoParaAtualizar)))
                .andExpect(jsonPath("$.status", is("STATUS_ATUALIZADO_CTRL")));

        verify(pedidoServiceMock, times(1)).atualizar(argThat(pedidoArg ->
                pedidoArg.getId().equals(idPedidoParaAtualizar) &&
                        pedidoArg.getStatus().equals("STATUS_ATUALIZADO_CTRL")
        ));
    }

    @Test
    void deveDeletarPedidoComSucesso() throws Exception {
        int idPedidoParaDeletar = pedido1.getId();
        doNothing().when(pedidoServiceMock).deletar(idPedidoParaDeletar);

        mockMvc.perform(delete("/pedidos/" + idPedidoParaDeletar))
                .andExpect(status().isNoContent());

        verify(pedidoServiceMock, times(1)).deletar(idPedidoParaDeletar);
    }
}