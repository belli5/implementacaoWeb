package com.exemple.backend.infraestrutura.jpamodels.compartilhados;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class EnderecoJpa implements Serializable {

    @Column
    private String rua;

    @Column
    private String bairro;

    @Column
    private String cidade;

    @Column
    private String estado;

    public EnderecoJpa(){

    }

    public EnderecoJpa(String rua, String bairro, String cidade, String estado) {
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public String getRua() {
        return rua;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Endereço: rua=" + rua + ", bairro=" + bairro + ", cidade=" + cidade + ", estado=" + estado;
    }
}
