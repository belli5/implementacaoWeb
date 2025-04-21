package com.exemple.implementacaoweb2.cliente;

import com.exemple.implementacaoweb2.prestador.Prestador;

import java.util.ArrayList;
import java.util.List;

public class FavoritosService {

    private final List<Prestador> favoritos = new ArrayList<>();
    private static final int LIMITE_FAVORITOS = 50;

    public String adicionarFavorito(Prestador prestador) {
        if (favoritos.size() >= LIMITE_FAVORITOS) {
            return "Limite de favoritos atingido.";
        }

        if (!favoritos.contains(prestador)) {
            favoritos.add(prestador);
            return "Prestador adicionado com sucesso!";
        } else {
            return "Prestador já está nos favoritos.";
        }
    }

    public List<Prestador> getFavoritos() {
        return new ArrayList<>(favoritos);
    }
}