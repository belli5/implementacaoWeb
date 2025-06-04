package com.exemple.backend.dominio.services.template;

import com.exemple.backend.dominio.models.Prestador;
import com.exemple.backend.dominio.repositorys.PrestadorRepository;
import com.exemple.backend.dominio.strategies.prestador.PrestadorValidationStrategy;
import org.springframework.stereotype.Component;

@Component
public class CadastroPrestador extends CadastroTemplate<Prestador> {

    private final PrestadorRepository prestadorRepository;
    private final PrestadorValidationStrategy validacao;

    public CadastroPrestador(PrestadorRepository prestadorRepository, PrestadorValidationStrategy validacao) {
        this.prestadorRepository = prestadorRepository;
        this.validacao = validacao;
    }

    @Override
    protected void validar(Prestador prestador) {
        validacao.validar(prestador);
    }

    @Override
    protected Prestador preparar(Prestador prestador) {
        prestador.setNome(prestador.getNome().trim());
        return prestador;
    }

    @Override
    protected void salvar(Prestador prestador) {
        prestadorRepository.save(prestador);
    }

    @Override
    protected void posCadastro(Prestador prestador) {
        System.out.println("Prestador cadastrado: " + prestador.getNome());
    }
}

