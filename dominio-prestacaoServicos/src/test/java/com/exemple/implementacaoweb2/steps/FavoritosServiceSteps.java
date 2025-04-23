package com.exemple.implementacaoweb2.steps;

import com.exemple.implementacaoweb2.cliente.FavoritosService;
import com.exemple.implementacaoweb2.compartilhados.Endereco;
import com.exemple.implementacaoweb2.prestacaoServico.PrestacaoServico;
import com.exemple.implementacaoweb2.prestador.Prestador;
import io.cucumber.java.Before;
import io.cucumber.java.pt.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class FavoritosServiceSteps {

    private FavoritosService servicoDeFavoritos;
    private Prestador prestador;
    private String mensagemResposta;

    @Before
    public void configurar() {
        servicoDeFavoritos = new FavoritosService();
    }

    @Dado("o prestador {string} não está na lista de favoritos")
    public void prestador_nao_esta_na_lista(String nome) {
        prestador = criarPrestador(nome);
        assertFalse(servicoDeFavoritos.getFavoritos().contains(prestador));
    }

    @Dado("o prestador {string} já está na lista de favoritos")
    public void prestador_ja_esta_na_lista(String nome) {
        prestador = criarPrestador(nome);
        servicoDeFavoritos.adicionarFavorito(prestador);
        assertTrue(servicoDeFavoritos.getFavoritos().contains(prestador));
    }

    @Dado("o cliente possui {int} prestadores na lista de favoritos")
    public void cliente_possui_n_favoritos(int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            Endereco endereco = new Endereco("Rua " + i, "Bairro " + i, "Cidade", "Estado");
            PrestacaoServico servico = new PrestacaoServico(i, "Serviço " + i, 100 + i, "Local", "Descrição", "Consultor");
            Prestador p = new Prestador(i, "Prestador " + i, List.of(servico), "email" + i + "@teste.com", "123", endereco);
            servicoDeFavoritos.adicionarFavorito(p);
        }
        assertEquals(quantidade, servicoDeFavoritos.getFavoritos().size());
    }

    @Dado("o limite máximo de favoritos por cliente é {int}")
    public void cliente_possui_limite_maximo(int limite) {
        assertEquals(limite, servicoDeFavoritos.getFavoritos().size());
    }

    @Quando("o cliente solicita a inclusão de {string} nos seus favoritos")
    public void cliente_solicita_inclusao(String nome) {
        mensagemResposta = servicoDeFavoritos.adicionarFavorito(prestador);
    }

    @Quando("o cliente tenta adicioná-lo novamente")
    public void cliente_tenta_adicionar_novamente() {
        mensagemResposta = servicoDeFavoritos.adicionarFavorito(prestador);
    }

    @Quando("o cliente tenta adicionar um novo prestador à lista")
    public void cliente_tenta_adicionar_novo_prestador() {
        Endereco endereco = new Endereco("Rua Extra", "Bairro Extra", "Cidade", "Estado");
        PrestacaoServico servico = new PrestacaoServico(999, "Serviço Extra", 150, "Local", "Descrição", "Consultor");
        Prestador novoPrestador = new Prestador(999, "Novo Prestador", List.of(servico), "novo@email.com", "999", endereco);
        mensagemResposta = servicoDeFavoritos.adicionarFavorito(novoPrestador);
    }

    @Então("o sistema adiciona o prestador à lista de favoritos")
    public void sistema_adiciona_prestador() {
        assertTrue(servicoDeFavoritos.getFavoritos().contains(prestador));
    }

    @Então("confirma a operação")
    public void confirma_operacao() {
        assertEquals("Prestador adicionado com sucesso!", mensagemResposta);
    }

    @Então("o sistema não duplica o prestador na lista")
    public void sistema_nao_duplica_prestador() {
        long count = servicoDeFavoritos.getFavoritos().stream()
                .filter(p -> p.getNome().equals(prestador.getNome())).count();
        assertEquals(1, count);
    }

    @Então("informa que ele já está favoritado")
    public void informa_ja_favoritado() {
        assertEquals("Prestador já está nos favoritos.", mensagemResposta);
    }

    @Então("o sistema rejeita a inclusão")
    public void sistema_rejeita_inclusao() {
        assertFalse(mensagemResposta.contains("sucesso"));
    }

    @Então("informa que o limite foi atingido")
    public void informa_limite_atingido() {
        assertEquals("Limite de favoritos atingido.", mensagemResposta);
    }

    private Prestador criarPrestador(String nome) {
        Endereco endereco = new Endereco("Rua", "Bairro", "Cidade", "Estado");
        PrestacaoServico servico = new PrestacaoServico(1, "Consultoria", 200, "Local", "Descrição", "Consultor");
        return new Prestador(1, nome, List.of(servico), "email@teste.com", "123", endereco);
    }
}
