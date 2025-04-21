package org.example.persistence.repository.usuarios;

import com.group.seuservico.domain.Usuarios.model.Prestador;

public interface PrestadorRepository {

    Prestador save(Prestador cliente);
    void delete(int id);
    void update (int id);
    
}
