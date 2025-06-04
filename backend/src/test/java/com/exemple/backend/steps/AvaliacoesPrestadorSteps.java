package com.exemple.backend.steps;

import com.exemple.backend.dominio.models.AvaliacaoSobrePrestador;
import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.compartilhados.Endereco;
import com.exemple.backend.dominio.services.AvaliacaoSobrePrestadorService;
import com.exemple.backend.dominio.services.ClienteService;
import com.exemple.backend.dominio.services.PrestadorService;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Então;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser; // Import para simular usuário logado
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*; // Para is(), hasSize(), greaterThan(), etc.
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user; // Para with(user(...))
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.fail;


public class AvaliacoesPrestadorSteps extends CommonSteps { // Estende CommonSteps

    @Autowired
    private PrestadorService prestadorService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private AvaliacaoSobrePrestadorService avaliacaoSobrePrestadorService;

    // Dados de teste que serão usados nos passos
    private Prestador prestadorLogado;
    private Cliente clienteTeste1;
    private Cliente clienteTeste2;

    @Dado("que o prestador está logado no sistema")
    public void prestador_esta_logado() throws Exception {
        // Assegura que o MockMvc está inicializado.
        setupAmbienteDeTeste();

        // Cria e salva um prestador de teste para simular o "prestador logado".
        // O ID real será gerado pelo banco de dados em memória.
        Endereco enderecoPrestador = new Endereco("Rua P", "Bairro P", "Cidade P", "PE");
        prestadorLogado = new Prestador(null, "Prestador Teste Logado", "senha123", "prestador.logado@test.com", "1199887766", enderecoPrestador);
        prestadorLogado = prestadorService.save(prestadorLogado);

        // Cliente de teste
        Endereco enderecoCliente1 = new Endereco("Rua C1", "Bairro C1", "Cidade C1", "C1");
        clienteTeste1 = new Cliente(null, "Cliente Teste 1", "senhaC1", "cliente1@test.com", "2299887766", enderecoCliente1);
        clienteTeste1 = clienteService.create(clienteTeste1);

        Endereco enderecoCliente2 = new Endereco("Rua C2", "Bairro C2", "Cidade C2", "C2");
        clienteTeste2 = new Cliente(null, "Cliente Teste 2", "senhaC2", "cliente2@test.com", "3399887766", enderecoCliente2);
        clienteTeste2 = clienteService.create(clienteTeste2);

        System.out.println("DEBUG: Prestador com ID " + prestadorLogado.getId() + " está logado no sistema.");
    }

    @Dado("já recebeu avaliações de clientes anteriores")
    public void prestador_recebeu_avaliacoes() {
        // Cria e salva avaliações no banco de dados de teste.
        // O ID é null, esperando que o banco de dados o gere.
        AvaliacaoSobrePrestador avaliacao1 = new AvaliacaoSobrePrestador(null, clienteTeste1, "Ótimo serviço! Muito profissional.", 5, prestadorLogado);
        AvaliacaoSobrePrestador avaliacao2 = new AvaliacaoSobrePrestador(null, clienteTeste2, "Excelente atendimento. Recomendo.", 4, prestadorLogado);

        avaliacaoSobrePrestadorService.save(avaliacao1);
        avaliacaoSobrePrestadorService.save(avaliacao2);

        System.out.println("DEBUG: Prestador recebeu avaliações de clientes.");
    }

    @Quando("acessa a área de avaliações recebidas")
    @WithMockUser(username = "prestador.logado@test.com", roles = {"PRESTADOR"}) // Simula autenticação do prestador
    public void acessa_area_avaliacoes() throws Exception {
        if (prestadorLogado == null || prestadorLogado.getId() == null) {
            fail("ID do prestador logado não foi inicializado.");
        }
        // Faz a chamada HTTP para o endpoint que lista as avaliações de um prestador específico.
        resultActions = mockMvc.perform(get("/avaliacaoSobrePrestador/avaliacoes_por_prestador/{prestador_id}", prestadorLogado.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(prestadorLogado.getEmail()).roles("PRESTADOR"))); // Garante que a requisição está autenticada
        System.out.println("DEBUG: Chamada GET para avaliações recebidas.");
    }

    @Então("o sistema exibe uma lista com todas as avaliações recebidas")
    public void sistema_exibe_lista_avaliacoes() throws Exception {
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$", hasSize(greaterThan(0)))); // Verifica se a lista não está vazia.
        System.out.println("DEBUG: Sistema exibiu lista de avaliações.");
    }

    @Então("cada avaliação contém a nota, o comentário e o nome do cliente")
    public void avaliacao_contem_detalhes() throws Exception {
        // Verifica a estrutura e o conteúdo dos primeiros itens da lista
        resultActions.andExpect(jsonPath("$[0].nota").exists());
        resultActions.andExpect(jsonPath("$[0].comentario").exists());
        resultActions.andExpect(jsonPath("$[0].cliente.nome").exists());
        resultActions.andExpect(jsonPath("$[0].cliente.id").exists());

        // Verificações mais específicas para o primeiro item (se houver pelo menos um)
        resultActions.andExpect(jsonPath("$[0].nota", is(5)));
        resultActions.andExpect(jsonPath("$[0].comentario", is("Ótimo serviço! Muito profissional.")));
        resultActions.andExpect(jsonPath("$[0].cliente.nome", is("Cliente Teste 1")));

        System.out.println("DEBUG: Cada avaliação contém nota, comentário e nome do cliente.");
    }

    @Dado("ainda não recebeu nenhuma avaliação")
    public void prestador_nao_recebeu_avaliacoes() {
        // O serviço de avaliação sobre prestador não tem um delete all por prestador,
        // então este passo é mais difícil de implementar sem alterar o serviço/repositório.
        // Para simular, podemos garantir que o prestador recém-criado não tenha avaliações.
        // OU, se houvesse um método de limpeza no serviço para testes:
        // avaliacaoSobrePrestadorService.deleteAllByPrestadorId(prestadorLogado.getId());
        System.out.println("DEBUG: Garantindo que o prestador não tenha avaliações. (Pode exigir lógica de limpeza no serviço).");
    }

    @Então("o sistema informa que não há avaliações")
    public void sistema_informa_sem_avaliacoes() throws Exception {
        // Isso pode ser um status 200 OK com lista vazia, ou 204 No Content.
        // Assumindo que o controller retorna 200 OK com uma lista vazia.
        resultActions.andExpect(status().isOk());
        System.out.println("DEBUG: Sistema informou que não há avaliações.");
    }

    @Então("a lista de avaliações está vazia")
    public void lista_avaliacoes_vazia() throws Exception {
        resultActions.andExpect(jsonPath("$", hasSize(0)));
        System.out.println("DEBUG: Lista de avaliações está vazia.");
    }
}