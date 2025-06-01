package com.exemple.backend.infraestrutura.jparepositorysimpl;

import com.exemple.backend.dominio.models.Favoritado;
import com.exemple.backend.dominio.repositorys.FavoritadoRepository;
import com.exemple.backend.infraestrutura.jpamodels.ClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.FavoritadoJpa;
import com.exemple.backend.infraestrutura.jpamodels.PrestadorJpa;
import com.exemple.backend.infraestrutura.jparepositorys.ClienteJpaRepository;
import com.exemple.backend.infraestrutura.jparepositorys.FavoritadoJpaRepository;
import com.exemple.backend.infraestrutura.jparepositorys.PrestadorJpaRepository;
import com.exemple.backend.infraestrutura.mappers.FavoritadoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class FavoritadoJpaRepositoryImpl implements FavoritadoRepository {

    private final FavoritadoJpaRepository jpaRepository;
    private final PrestadorJpaRepository prestadorJpaRepository;
    private final ClienteJpaRepository clienteJpaRepository;

    public FavoritadoJpaRepositoryImpl(FavoritadoJpaRepository jpaRepository,
                                       PrestadorJpaRepository prestadorJpaRepository,
                                       ClienteJpaRepository clienteJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.prestadorJpaRepository = prestadorJpaRepository;
        this.clienteJpaRepository = clienteJpaRepository;
    }

    @Override
    public Optional<Favoritado> findById(int id) {
        return jpaRepository.findById(id)
                .map(FavoritadoMapper::toFavoritado);
    }

    @Override
    public List<Favoritado> findAll() {
        return jpaRepository.findAll().stream()
                .map(FavoritadoMapper::toFavoritado)
                .toList();
    }

    @Override
    public List<Favoritado> findByPrestadorId(int prestador_Id) {
        return jpaRepository.findByPrestador_Id(prestador_Id).stream()
                .map(FavoritadoMapper::toFavoritado)
                .toList();
    }

    @Override
    public List<Favoritado> findbyClienteId(int cliente_Id) {
        return jpaRepository.findByCliente_Id(cliente_Id).stream()
                .map(FavoritadoMapper::toFavoritado)
                .toList();
    }

    @Override
    public Favoritado save(Favoritado favoritado) {
        // Garante que as entidades JPA associadas existem antes de salvar
        PrestadorJpa prestadorJpa = prestadorJpaRepository.findById(favoritado.getPrestador().getId())
                .orElseThrow(() -> new IllegalArgumentException("Prestador não encontrado"));

        ClienteJpa clienteJpa = clienteJpaRepository.findById(favoritado.getCliente().getId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        // Usa o mapper para montar a entidade
        FavoritadoJpa entity = FavoritadoMapper.toFavoritadoJpa(favoritado);
        entity.setPrestador(prestadorJpa); // garante referência gerenciada
        entity.setCliente(clienteJpa);

        return FavoritadoMapper.toFavoritado(jpaRepository.save(entity));
    }

    @Override
    public void delete(int id) {
        jpaRepository.deleteById(id);
    }
}
