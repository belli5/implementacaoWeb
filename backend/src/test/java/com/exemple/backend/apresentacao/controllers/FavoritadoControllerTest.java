package com.exemple.backend.apresentacao.controllers;

import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Favoritado;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.dominio.services.ClienteService;
import com.exemple.backend.dominio.services.FavoritadoService;
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

@WebMvcTest(FavoritadoController.class)
class FavoritadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FavoritadoService favoritadoServiceMock;
    @Autowired
    private PrestadorService prestadorServiceMock;
    @Autowired
    private ClienteService clienteServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente clienteValido;
    private Prestador prestadorValido;
    private Favoritado favoritadoExistente;
    private Favoritado favoritadoInputParaPost; // Para simular o corpo da requisição POST
    private Endereco enderecoComum;

    @TestConfiguration
    static class FavoritadoControllerTestConfig {
        @Bean
        @Primary
        public FavoritadoService favoritadoService() {
            return Mockito.mock(FavoritadoService.class);
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

        Mockito.reset(favoritadoServiceMock, prestadorServiceMock, clienteServiceMock);

        enderecoComum = new Endereco("Rua Favorito Teste", "Bairro FT", "Cidade FT", "FT");
        clienteValido = new Cliente(1, "Cliente Fav Teste", "sCF", "cfav@teste.com", "111", enderecoComum);
        prestadorValido = new Prestador(1, "Prestador Fav Teste", "sPF", "pfav@teste.com", "222", enderecoComum);

        // Simula um 'Favoritado' que já existe no sistema
        favoritadoExistente = new Favoritado(10, clienteValido, prestadorValido);

        // Simula os dados de entrada para criar um NOVO 'Favoritado' via POST.
        // O ID do 'Favoritado' em si (primeiro argumento) não viria do cliente.
        // Para que a instanciação não falhe no setUp do teste devido à validação no construtor do modelo,
        // passamos um ID placeholder.
        Cliente clienteParaCorpoJson = new Cliente();
        clienteParaCorpoJson.setId(clienteValido.getId()); // ID do cliente é importante

        Prestador prestadorParaCorpoJson = new Prestador();
        prestadorParaCorpoJson.setId(prestadorValido.getId()); // ID do prestador é importante

        favoritadoInputParaPost = new Favoritado(
                0, // ID Placeholder para satisfazer o construtor no teste
                clienteParaCorpoJson,
                prestadorParaCorpoJson
        );
    }

    @Test
    void deveListarTodosOsFavoritados() throws Exception {
        List<Favoritado> lista = Arrays.asList(favoritadoExistente);
        when(favoritadoServiceMock.listarTodos()).thenReturn(lista);

        mockMvc.perform(get("/favoritos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(favoritadoExistente.getId())));
        // O FavoritadoMapper.toFavoritado retorna cliente e prestador como null.
        // Se você quiser verificar os IDs de cliente/prestador aqui, precisaria
        // de um DTO ou de um mapper que os preenchesse.

        verify(favoritadoServiceMock, times(1)).listarTodos();
    }

    @Test
    void deveBuscarFavoritadoPorIdExistente() throws Exception {
        when(favoritadoServiceMock.buscarPorId(favoritadoExistente.getId())).thenReturn(Optional.of(favoritadoExistente));

        mockMvc.perform(get("/favoritos/" + favoritadoExistente.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(favoritadoExistente.getId())));
        verify(favoritadoServiceMock, times(1)).buscarPorId(favoritadoExistente.getId());
    }

    @Test
    void deveRetornarNotFoundAoBuscarFavoritadoPorIdInexistente() throws Exception {
        when(favoritadoServiceMock.buscarPorId(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/favoritos/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(favoritadoServiceMock, times(1)).buscarPorId(999);
    }

    @Test
    void deveListarFavoritosDoClienteComSucesso() throws Exception {
        List<Prestador> listaPrestadores = Arrays.asList(prestadorValido);
        when(clienteServiceMock.findById(clienteValido.getId())).thenReturn(Optional.of(clienteValido));
        when(favoritadoServiceMock.listarFavoritosDoCliente(clienteValido.getId())).thenReturn(listaPrestadores);

        mockMvc.perform(get("/favoritos/favoritosDoCliente/" + clienteValido.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(prestadorValido.getId())));
        verify(clienteServiceMock, times(1)).findById(clienteValido.getId());
        verify(favoritadoServiceMock, times(1)).listarFavoritosDoCliente(clienteValido.getId());
    }

    @Test
    void deveRetornarNotFoundAoListarFavoritosDeClienteInexistente() throws Exception {
        int clienteIdInexistente = 999;
        when(clienteServiceMock.findById(clienteIdInexistente)).thenReturn(Optional.empty());

        mockMvc.perform(get("/favoritos/favoritosDoCliente/" + clienteIdInexistente)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(clienteServiceMock, times(1)).findById(clienteIdInexistente);
        verify(favoritadoServiceMock, never()).listarFavoritosDoCliente(anyInt());
    }

    @Test
    void deveListarClientesQueFavoritaramPrestadorComSucesso() throws Exception {
        List<Cliente> listaClientes = Arrays.asList(clienteValido);
        when(prestadorServiceMock.findById(prestadorValido.getId())).thenReturn(Optional.of(prestadorValido));
        when(favoritadoServiceMock.listarClientesQueFavoritaramPrestador(prestadorValido.getId())).thenReturn(listaClientes);

        mockMvc.perform(get("/favoritos/clientesQueFavoritaram/" + prestadorValido.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(clienteValido.getId())));
        verify(prestadorServiceMock, times(1)).findById(prestadorValido.getId());
        verify(favoritadoServiceMock, times(1)).listarClientesQueFavoritaramPrestador(prestadorValido.getId());
    }

    @Test
    void deveRetornarNotFoundAoListarClientesQueFavoritaramPrestadorInexistente() throws Exception {
        int prestadorIdInexistente = 999;
        when(prestadorServiceMock.findById(prestadorIdInexistente)).thenReturn(Optional.empty());

        mockMvc.perform(get("/favoritos/clientesQueFavoritaram/" + prestadorIdInexistente)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(prestadorServiceMock, times(1)).findById(prestadorIdInexistente);
        verify(favoritadoServiceMock, never()).listarClientesQueFavoritaramPrestador(anyInt());
    }


    @Test
    void deveFavoritarComSucesso() throws Exception {
        when(prestadorServiceMock.findById(favoritadoInputParaPost.getPrestador().getId())).thenReturn(Optional.of(prestadorValido));
        when(clienteServiceMock.findById(favoritadoInputParaPost.getCliente().getId())).thenReturn(Optional.of(clienteValido));

        // O controller cria `new Favoritado(cliente.get(), prestador.get())`
        // O service.favoritar deve retornar o objeto Favoritado salvo (com ID)
        Favoritado favoritadoSalvoPeloService = new Favoritado(
                20, // Novo ID gerado
                clienteValido,
                prestadorValido
        );
        // O problema de construtor no controller pode fazer este teste falhar com 500
        // se a lógica do controller não for corrigida.
        when(favoritadoServiceMock.favoritar(any(Favoritado.class))).thenReturn(favoritadoSalvoPeloService);

        mockMvc.perform(post("/favoritos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(favoritadoInputParaPost)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(20)));
        // Novamente, o mapper padrão para Favoritado pode anular cliente/prestador no JSON de resposta.

        verify(prestadorServiceMock).findById(favoritadoInputParaPost.getPrestador().getId());
        verify(clienteServiceMock).findById(favoritadoInputParaPost.getCliente().getId());
        verify(favoritadoServiceMock).favoritar(any(Favoritado.class));
    }

    @Test
    void naoDeveFavoritarSePrestadorNaoEncontrado() throws Exception {
        when(prestadorServiceMock.findById(favoritadoInputParaPost.getPrestador().getId())).thenReturn(Optional.empty());

        mockMvc.perform(post("/favoritos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(favoritadoInputParaPost)))
                .andExpect(status().isNotFound());
        verify(prestadorServiceMock).findById(favoritadoInputParaPost.getPrestador().getId());
        verify(clienteServiceMock, never()).findById(anyInt());
        verify(favoritadoServiceMock, never()).favoritar(any(Favoritado.class));
    }

    @Test
    void naoDeveFavoritarSeClienteNaoEncontrado() throws Exception {
        when(prestadorServiceMock.findById(favoritadoInputParaPost.getPrestador().getId())).thenReturn(Optional.of(prestadorValido));
        when(clienteServiceMock.findById(favoritadoInputParaPost.getCliente().getId())).thenReturn(Optional.empty());

        mockMvc.perform(post("/favoritos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(favoritadoInputParaPost)))
                .andExpect(status().isNotFound());
        verify(prestadorServiceMock).findById(favoritadoInputParaPost.getPrestador().getId());
        verify(clienteServiceMock).findById(favoritadoInputParaPost.getCliente().getId());
        verify(favoritadoServiceMock, never()).favoritar(any(Favoritado.class));
    }


    @Test
    void deveDesfavoritarComSucesso() throws Exception {
        when(favoritadoServiceMock.buscarPorId(favoritadoExistente.getId())).thenReturn(Optional.of(favoritadoExistente));
        doNothing().when(favoritadoServiceMock).desfavoritar(favoritadoExistente.getId());

        mockMvc.perform(delete("/favoritos/" + favoritadoExistente.getId()))
                .andExpect(status().isNoContent());

        verify(favoritadoServiceMock, times(1)).buscarPorId(favoritadoExistente.getId());
        verify(favoritadoServiceMock, times(1)).desfavoritar(favoritadoExistente.getId());
    }

    @Test
    void deveRetornarNotFoundAoTentarDesfavoritarInexistente() throws Exception {
        when(favoritadoServiceMock.buscarPorId(999)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/favoritos/999"))
                .andExpect(status().isNotFound());

        verify(favoritadoServiceMock, times(1)).buscarPorId(999);
        verify(favoritadoServiceMock, never()).desfavoritar(anyInt());
    }
}