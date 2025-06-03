// Arquivo: src/test/java/com/exemple/backend/apresentacao/controllers/ClienteControllerTest.java

package com.exemple.backend.apresentacao.controllers;

import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.dominio.services.ClienteService;
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
import org.springframework.security.crypto.password.PasswordEncoder; // Importe PasswordEncoder
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate; // Se usado em Cliente
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteService clienteServiceMock;

    // Se o seu ClienteController agora depende de PasswordEncoder,
    // ele também será injetado a partir da TestConfig.
    // Não é necessário @Autowired aqui para o PasswordEncoder se você não o usa diretamente no teste,
    // mas o @Bean na TestConfig é crucial.
    // @Autowired
    // private PasswordEncoder passwordEncoderMock;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente cliente1;
    private Cliente cliente2;
    private Endereco endereco;

    @TestConfiguration
    static class ClienteControllerTestConfig {
        @Bean
        @Primary
        public ClienteService clienteService() {
            return Mockito.mock(ClienteService.class);
        }

        // ADICIONE ESTE BEAN PARA MOCKAR O PASSWORDENCODER
        @Bean
        @Primary
        public PasswordEncoder passwordEncoder() {
            return Mockito.mock(PasswordEncoder.class);
            // Alternativamente, para testes simples onde você não se importa com a codificação real:
            // return org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder();
            // Ou um NoOpPasswordEncoder (obsoleto, mas simples se a codificação não é o foco do teste do controller):
            // return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
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

        // Resetar todos os mocks
        Mockito.reset(clienteServiceMock);
        // Se você tivesse injetado passwordEncoderMock:
        // if (passwordEncoderMock != null) { Mockito.reset(passwordEncoderMock); }


        endereco = new Endereco("Rua Cliente Ctrl", "Bairro CCtrl", "Cidade CCtrl", "CC");
        // Certifique-se que o construtor do Cliente está correto com os tipos de ID.
        cliente1 = new Cliente(Integer.valueOf(1), "Cliente Um Ctrl", "senhaCtrl1", "umctrl@email.com", "111222", endereco);
        cliente2 = new Cliente(Integer.valueOf(2), "Cliente Dois Ctrl", "senhaCtrl2", "doisctrl@email.com", "333444", endereco);
    }

    @Test
    void testGetById_ClienteExists() throws Exception {
        when(clienteServiceMock.findById(1)).thenReturn(Optional.of(cliente1));

        mockMvc.perform(get("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("Cliente Um Ctrl")));
        verify(clienteServiceMock).findById(1);
    }

    @Test
    void testGetById_ClienteNotExists() throws Exception {
        when(clienteServiceMock.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/clientes/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(clienteServiceMock).findById(99);
    }

    @Test
    void testGetAll() throws Exception {
        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);
        when(clienteServiceMock.findAll()).thenReturn(clientes);

        mockMvc.perform(get("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
        verify(clienteServiceMock).findAll();
    }

    @Test
    void testCriarCliente() throws Exception {
        // Cliente que será enviado no corpo (sem ID, ou com ID placeholder se o construtor exigir)
        Cliente novoClienteInput = new Cliente(
                Integer.valueOf(0), // Placeholder ID se o construtor do modelo Cliente exigir
                "Novo Cliente",
                "senhaNova", // Senha em texto plano no input
                "novo@email.com",
                "33333",
                endereco
        );

        // Cliente que o serviço mockado retornará (com ID e senha possivelmente "hasheada")
        Cliente clienteSalvo = new Cliente(
                Integer.valueOf(3),
                "Novo Cliente",
                "senhaHasheadaSimulada", // Simula senha hasheada
                "novo@email.com",
                "33333",
                endereco
        );

        // Se o seu controller usa passwordEncoder.encode():
        // when(passwordEncoderMock.encode(novoClienteInput.getSenha())).thenReturn("senhaHasheadaSimulada");
        when(clienteServiceMock.create(any(Cliente.class))).thenReturn(clienteSalvo);

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoClienteInput)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.nome", is("Novo Cliente")));
        // Não verificamos a senha na resposta JSON, pois geralmente não é retornada.

        // Verifica se o create foi chamado, possivelmente com o cliente contendo a senha original ou hasheada,
        // dependendo de onde o encode acontece no seu controller/service.
        verify(clienteServiceMock).create(argThat(c ->
                c.getNome().equals("Novo Cliente") &&
                        // Se o encode é feito ANTES de chamar service.create:
                        // c.getSenha().equals("senhaHasheadaSimulada")
                        // Se o encode é feito DENTRO do service.create (o que é mais comum):
                        c.getSenha().equals("senhaNova")
        ));
        // Se o encode é no controller antes de chamar create:
        // verify(passwordEncoderMock).encode("senhaNova");
    }

    @Test
    void testAtualizarCliente() throws Exception {
        int clienteId = 1;
        Cliente clienteAtualizadoInfo = new Cliente(
                Integer.valueOf(clienteId),
                "Cliente Um Atualizado",
                "senha1nova",
                "um_novo@email.com",
                "11111novo",
                endereco
        );

        // Simula o retorno do serviço de atualização
        when(clienteServiceMock.update(any(Cliente.class))).thenReturn(clienteAtualizadoInfo);

        mockMvc.perform(put("/api/clientes/" + clienteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteAtualizadoInfo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(clienteId)))
                .andExpect(jsonPath("$.nome", is("Cliente Um Atualizado")))
                .andExpect(jsonPath("$.email", is("um_novo@email.com")));

        verify(clienteServiceMock).update(argThat(c ->
                c.getId().equals(clienteId) &&
                        c.getNome().equals("Cliente Um Atualizado")
        ));
    }

    @Test
    void testRemoverCliente() throws Exception {
        int clienteId = 1;
        // findById é chamado primeiro no ClienteService.delete
        when(clienteServiceMock.findById(clienteId)).thenReturn(Optional.of(cliente1));
        doNothing().when(clienteServiceMock).delete(clienteId);


        mockMvc.perform(delete("/api/clientes/" + clienteId))
                .andExpect(status().isNoContent());

        verify(clienteServiceMock).delete(clienteId);
    }
}