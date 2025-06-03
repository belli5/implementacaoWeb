package com.exemple.backend.infraestrutura.jparepositorysimpl;

import com.exemple.backend.dominio.models.Oferece;
import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.models.Servico;
import com.exemple.backend.dominio.repositorys.OfereceRepository;
import com.exemple.backend.infraestrutura.jpamodels.OfereceJpa;
import com.exemple.backend.infraestrutura.jparepositorys.OfereceJpaRepository;
import com.exemple.backend.infraestrutura.jparepositorys.PrestadorJpaRepository;
import com.exemple.backend.infraestrutura.jparepositorys.ServicoJpaRepository;
import com.exemple.backend.infraestrutura.mappers.OfereceMapper;
import com.exemple.backend.infraestrutura.mappers.PrestadorMapper;
import com.exemple.backend.infraestrutura.mappers.ServicoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.Assert.notNull;

@Repository
public class OfereceRepositoryImpl implements OfereceRepository {

    private final OfereceJpaRepository ofereceJpaRepository;
    private final ServicoJpaRepository servicoJpaRepository;
    private final PrestadorJpaRepository prestadorJpaRepository;

    public OfereceRepositoryImpl(OfereceJpaRepository ofereceJpaRepository, ServicoJpaRepository servicoJpaRepository, PrestadorJpaRepository prestadorJpaRepository) {
        this.ofereceJpaRepository = ofereceJpaRepository;
        this.servicoJpaRepository = servicoJpaRepository;
        this.prestadorJpaRepository = prestadorJpaRepository;
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
    public List<Servico> findByPrestadorId(int prestador_Id){
        return ofereceJpaRepository.findByPrestadorId(prestador_Id)
                .stream()
                .map(fav -> ServicoMapper.toServico(fav.getServico()))
                .toList();
    }

    @Override
    public List<Prestador> findByServicoNome(String servico_Nome){
        notNull(servico_Nome, "Servico_Nome não deve ser nulo");

        return ofereceJpaRepository.findByServicoNome(servico_Nome)
                .stream()
                .map(fav -> PrestadorMapper.toPrestador(fav.getPrestador()))
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
