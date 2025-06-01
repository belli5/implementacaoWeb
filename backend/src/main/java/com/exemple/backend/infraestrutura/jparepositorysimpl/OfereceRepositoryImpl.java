package com.exemple.backend.infraestrutura.jparepositorysimpl;

import com.exemple.backend.dominio.models.Oferece;
import com.exemple.backend.dominio.repositorys.OfereceRepository;
import com.exemple.backend.infraestrutura.jpamodels.OfereceJpa;
import com.exemple.backend.infraestrutura.jparepositorys.OfereceJpaRepository;
import com.exemple.backend.infraestrutura.mappers.OfereceMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.Assert.notNull;

@Repository
public class OfereceRepositoryImpl implements OfereceRepository {

    private final OfereceJpaRepository ofereceJpaRepository;

    public OfereceRepositoryImpl(OfereceJpaRepository ofereceJpaRepository) {
        this.ofereceJpaRepository = ofereceJpaRepository;
    }

    @Override
    public Optional<Oferece> findById(int id){

        return ofereceJpaRepository.findById(id)
                .map(OfereceMapper::toOferece);
    }

    @Override
    public List<Oferece> findAll(){
        return ofereceJpaRepository.findAll()
                .stream()
                .map(OfereceMapper::toOferece)
                .toList();
    }

    @Override
    public List<Oferece> findByPrestadorId(int prestador_Id){
        return ofereceJpaRepository.findByPrestadorId(prestador_Id)
                .stream()
                .map(OfereceMapper::toOferece)
                .toList();
    }

    @Override
    public List<Oferece> findByServicoNome(String servico_Nome){
        notNull(servico_Nome, "Servico_Nome não deve ser nulo");

        return ofereceJpaRepository.findByServicoNome(servico_Nome)
                .stream()
                .map(OfereceMapper::toOferece)
                .toList();
    }

    @Override
    public Oferece save(Oferece oferece){
        notNull(oferece, "Oferece não deve ser nulo");

        OfereceJpa ofereceJpa = OfereceMapper.toOfereceJpa(oferece);
        return OfereceMapper.toOferece(ofereceJpaRepository.save(ofereceJpa));
    }

    @Override
    public void delete(int id){
        ofereceJpaRepository.deleteById(id);
    }
}
