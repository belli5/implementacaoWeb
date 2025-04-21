package org.example.service.usuario;

import com.group.seuservico.domain.Usuarios.model.Prestador;
import java.util.ArrayList;
import java.util.List;

public class FavoritosService {

    private static final int LIMITE_FAVORITOS = 50;
    private List<Prestador> favoritos = new ArrayList<>();

    public String adicionarFavorito(Prestador prestador) {
        if (favoritos.size() >= LIMITE_FAVORITOS) {
            return "Limite de favoritos atingido.";
        } else if (!favoritos.contains(prestador)) {
            favoritos.add(prestador);
            return "Prestador adicionado com sucesso!";
        } else {
            return "Não é possível adicionar o prestador duas vezes.";
        }
    }


    public void removerFavorito(Prestador prestador) {
        favoritos.remove(prestador);
    }

    public List<Prestador> getFavoritos() {
        return new ArrayList<>(favoritos);
    }
}
