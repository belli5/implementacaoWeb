package com.exemple.backend.infraestrutura.jparepositorysimpl;

import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.repositorys.PrestadorRepository;
import com.exemple.backend.infraestrutura.jpamodels.PrestadorJpa;
import com.exemple.backend.infraestrutura.jparepositorys.PrestadorJpaRepository;
import com.exemple.backend.infraestrutura.mappers.PrestadorMapper;
import org.springframework.stereotype.Repository;
import static org.springframework.util.Assert.notNull;
import java.util.List;
import java.util.Optional;

@Repository
public class PrestadorRepositoryImpl implements PrestadorRepository {

    private final PrestadorJpaRepository prestadorJpaRepository;

    public PrestadorRepositoryImpl(PrestadorJpaRepository prestadorJpaRepository) {
        this.prestadorJpaRepository = prestadorJpaRepository;
    }

    @Override
    public Optional<Prestador> findById(int id){

        return prestadorJpaRepository.findById(id)
                .map(PrestadorMapper::toPrestador);
    }

    @Override
    public List<Prestador> findAll(){
        return prestadorJpaRepository.findAll()
                .stream()
                .map(PrestadorMapper::toPrestador)
                .toList();
    }

    @Override
    public Prestador save(Prestador prestador){
        notNull(prestador, "Prestador não deve ser nulo");

        PrestadorJpa prestadorJpa = PrestadorMapper.toPrestadorJpa(prestador);
        return PrestadorMapper.toPrestador(prestadorJpaRepository.save(prestadorJpa));
    }

    @Override
    public Prestador update(Prestador prestador) {
        notNull(prestador, "Prestador não deve ser nulo");

        PrestadorJpa prestadorJpa = PrestadorMapper.toPrestadorJpa(prestador);
        return PrestadorMapper.toPrestador(prestadorJpaRepository.save(prestadorJpa));
    }



    @Override
    public void delete(int id){
        prestadorJpaRepository.deleteById(id);

    }
}
