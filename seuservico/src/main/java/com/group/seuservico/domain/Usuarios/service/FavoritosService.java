package com.group.seuservico.domain.Usuarios.service;

import com.group.seuservico.domain.Usuarios.model.Prestador;
import java.util.ArrayList;
import java.util.List;

public class FavoritosService {

    private List<Prestador> favoritos = new ArrayList<>();

    public void adicionarFavorito(Prestador prestador) {
        if (!favoritos.contains(prestador)) {
            favoritos.add(prestador);
        }
    }

    public void removerFavorito(Prestador prestador) {
        favoritos.remove(prestador);
    }

    public List<Prestador> getFavoritos() {
        return new ArrayList<>(favoritos);
    }
}
