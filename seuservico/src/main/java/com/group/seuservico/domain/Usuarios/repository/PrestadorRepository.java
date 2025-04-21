package com.group.seuservico.domain.Usuarios.repository;

import com.group.seuservico.domain.Usuarios.model.Prestador;

public interface PrestadorRepository {

    Prestador save(Prestador cliente);
    void delete(int id);
    void update (int id);
    
}
