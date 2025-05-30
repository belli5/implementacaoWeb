package com.exemple.backend.dominio.models.compartilhados;

public class Endereco {

    private final String rua;
    private final String bairro;
    private final String cidade;
    private final String estado;

    public Endereco(String rua, String bairro, String cidade, String estado) {
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
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