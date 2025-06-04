package com.exemple.backend.dominio.services.template;

public abstract class CadastroTemplate<T> {

    public final T cadastrar(T objeto) {
        validar(objeto);
        T pronto = preparar(objeto);
        salvar(pronto);
        posCadastro(pronto);
        return pronto;
    }

    protected abstract void validar(T objeto);
    protected abstract T preparar(T objeto);
    protected abstract void salvar(T objeto);

    protected void posCadastro(T objeto) {
        // gancho opcional
    }
}
