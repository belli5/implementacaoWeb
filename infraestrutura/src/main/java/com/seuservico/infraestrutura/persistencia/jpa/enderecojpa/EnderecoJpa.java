package com.seuservico.infraestrutura.persistencia.jpa.enderecojpa;

import jakarta.persistence.Embeddable;

@Embeddable
public class EnderecoJpa {

    private String rua;
    private String bairro;
    private String cidade;
    private String estado;

    public EnderecoJpa(String rua, String bairro, String cidade, String estado) {
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public EnderecoJpa() {

    }

    public String getRua(){
        return rua;
    }

    public String getBairro(){
        return bairro;
    }

    public String getCidade(){
        return cidade;
    }

    public String getEstado(){
        return estado;
    }

    @Override
    public String toString() {
        return "Endereço:" +
                "rua:" + rua +
                ", bairro:"+ bairro +
                ", cidade:" + cidade +
                ", estado:" + estado;
    }

}