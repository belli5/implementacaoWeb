package com.exemple.backend.steps;

import io.cucumber.java.pt.*;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FavoritosSteps {

    private boolean clienteLogado = false;
    private Set<String> listaFavoritos = new HashSet<>();
    private String mensagemSistema = "";
    private final int LIMITE = 50;

    @Dado("que o cliente está logado no sistema")
    public void cliente_logado() {
        clienteLogado = true;
    }

    @E("está visualizando o perfil de um prestador que ainda não está na sua lista de favoritos")
    public void prestador_nao_favoritado() {
        listaFavoritos.remove("João Silva"); // garantir que ainda não está
    }

    @Quando("o cliente favoritar o perfil de “João Silva”")
    public void cliente_favorita_joao_silva() {
        if (!listaFavoritos.contains("João Silva")) {
            listaFavoritos.add("João Silva");
            mensagemSistema = "Prestador favoritado com sucesso";
        }
    }

    @Então("o sistema adiciona o prestador “João Silva” à lista de favoritos do cliente")
    public void sistema_adiciona_favorito() {
        assertTrue(listaFavoritos.contains("João Silva"));
    }

    @E("exibe uma mensagem confirmando que o prestador foi favoritado com sucesso")
    public void exibe_mensagem_sucesso() {
        assertEquals("Prestador favoritado com sucesso", mensagemSistema);
    }

    @E("está visualizando o perfil de um prestador que já está na lista de favoritos do cliente")
    public void prestador_ja_favoritado() {
        listaFavoritos.add("João Silva");
    }

    @Quando("ele favoritar o colaborador")
    public void tentar_favoritar_novamente() {
        if (listaFavoritos.contains("João Silva")) {
            mensagemSistema = "Prestador já está favoritado";
        }
    }

    @Então("o sistema não o adiciona novamente à lista")
    public void nao_adiciona_novamente() {
        int count = 0;
        for (String favorito : listaFavoritos) {
            if (favorito.equals("João Silva")) count++;
        }
        assertEquals(1, count);
    }

    @E("exibe uma mensagem informando que o prestador já está favoritado")
    public void exibe_mensagem_ja_favoritado() {
        assertEquals("Prestador já está favoritado", mensagemSistema);
    }

    @E("já possui {int} prestadores na sua lista de favoritos")
    public void ja_possui_limite_de_favoritos(Integer limite) {
        listaFavoritos.clear();
        for (int i = 1; i <= limite; i++) {
            listaFavoritos.add("Prestador " + i);
        }
        assertEquals(limite.intValue(), listaFavoritos.size());
    }

    @Quando("tenta favoritar um novo prestador")
    public void tenta_favoritar_novo() {
        if (listaFavoritos.size() >= LIMITE) {
            mensagemSistema = "Limite de prestadores favoritados atingido";
        }
    }

    @Então("o sistema exibe uma mensagem de erro informando que o limite foi atingido")
    public void exibe_mensagem_limite() {
        assertEquals("Limite de prestadores favoritados atingido", mensagemSistema);
    }
}
