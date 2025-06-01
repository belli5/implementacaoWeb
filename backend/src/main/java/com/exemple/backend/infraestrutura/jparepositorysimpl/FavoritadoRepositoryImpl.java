package com.exemple.backend.infraestrutura.jparepositorysimpl;

import com.exemple.backend.dominio.models.Cliente;
import com.exemple.backend.dominio.models.Favoritado;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.repositorys.FavoritadoRepository;
import com.exemple.backend.infraestrutura.jpamodels.ClienteJpa;
import com.exemple.backend.infraestrutura.jpamodels.FavoritadoJpa;
import com.exemple.backend.infraestrutura.jpamodels.PrestadorJpa;
import com.exemple.backend.infraestrutura.jparepositorys.ClienteJpaRepository;
import com.exemple.backend.infraestrutura.jparepositorys.FavoritadoJpaRepository;
import com.exemple.backend.infraestrutura.jparepositorys.PrestadorJpaRepository;
import com.exemple.backend.infraestrutura.mappers.ClienteMapper;
import com.exemple.backend.infraestrutura.mappers.FavoritadoMapper;
import com.exemple.backend.infraestrutura.mappers.PrestadorMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FavoritadoRepositoryImpl implements FavoritadoRepository {

    private final FavoritadoJpaRepository favoritadoJpaRepository;
    private final PrestadorJpaRepository prestadorJpaRepository;
    private final ClienteJpaRepository clienteJpaRepository;

    public FavoritadoRepositoryImpl(FavoritadoJpaRepository favoritadoJpaRepository,
                                    PrestadorJpaRepository prestadorJpaRepository,
                                    ClienteJpaRepository clienteJpaRepository) {
        this.favoritadoJpaRepository = favoritadoJpaRepository;
        this.prestadorJpaRepository = prestadorJpaRepository;
        this.clienteJpaRepository = clienteJpaRepository;
    }

    @Override
    public Optional<Favoritado> findById(int id) {
        return favoritadoJpaRepository.findById(id)
                .map(FavoritadoMapper::toFavoritado);
    }

    @Override
    public List<Favoritado> findAll() {
        return favoritadoJpaRepository.findAll().stream()
                .map(FavoritadoMapper::toFavoritado)
                .toList();
    }

    @Override
    public List<Cliente> findClientesQueFavoritaramPrestadorByPrestadorId(int prestadorId) {
        return favoritadoJpaRepository.findByPrestadorId(prestadorId)
                .stream()
                .map(fav -> ClienteMapper.toCliente(fav.getCliente()))
                .toList();
    }


    @Override
    public List<Prestador> findPrestadoresFavoritadosByClienteId(int clienteId){
        return favoritadoJpaRepository.findByClienteId(clienteId)
                .stream()
                .map(fav-> PrestadorMapper.toPrestador(fav.getPrestador()))
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

        return FavoritadoMapper.toFavoritado(favoritadoJpaRepository.save(entity));
    }

    @Override
    public void delete(int id) {
        favoritadoJpaRepository.deleteById(id);
    }
}
