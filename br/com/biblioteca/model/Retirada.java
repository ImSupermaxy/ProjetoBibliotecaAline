package br.com.biblioteca.model;

import br.com.biblioteca.enums.ERetirada;

public class Retirada {
    private final ERetirada retirada;
    private final Endereco endereco;

    public Retirada(ERetirada retirada, Endereco endereco){
        this.retirada = retirada;
        this.endereco = endereco;
    }

    public ERetirada getRetirada(){
        return this.retirada;
    }

    public Endereco getEndereco(){
        return this.endereco;
    }
}
