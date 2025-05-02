package br.com.biblioteca.model;

import br.com.biblioteca.shared.BibliotecaUtils;

public class Endereco {
    private final String cep;
    private final String logradouro;
    private final String complemento;
    private final String bairro;
    private final String numero;
    private final String uf;
    private final String cidade;

    public Endereco(String cep, String logradouro, String bairro, String complemento, String numero, String uf, String cidade){
        if(cep == null || cep.isBlank())
            throw new IllegalArgumentException("CEP inválido.");
        if(!BibliotecaUtils.ValidateCEP(cep))
            throw new IllegalArgumentException("O CEP deve ser no formato 00000-000.");
        if(logradouro == null || logradouro.isBlank())
            throw new IllegalArgumentException("Logradouro inválido.");
        if(bairro == null || bairro.isBlank())
            throw new IllegalArgumentException("Bairro inválido.");
        if(uf == null || uf.isBlank() || uf.length() != 2)
            throw new IllegalArgumentException("Uf inválida! Digite a sigla do estado.");
        if(cidade == null || cidade.isBlank())
            throw new IllegalArgumentException("Cidade inválida.");

        this.cep = cep;
        this.logradouro = logradouro.trim();
        this.complemento = complemento != null ? complemento.trim() : null;
        this.bairro = bairro.trim();
        this.numero = numero.trim();
        this.uf = uf.trim().toUpperCase();
        this.cidade = cidade.trim();
    }

    public String getCep() {
        return cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public String getNumero() {
        return numero;
    }

    public String getUf() {
        return uf;
    }

    public String getCidade() {
        return cidade;
    }

    @Override
    public String toString(){
        return String.format("%s, ", logradouro)
                + String.format("%s - ", numero)
                + String.format("%s, ", bairro)
                + String.format("%s - ", cidade)
                + String.format("%s, ", uf)
                + String.format("%s ", cep);
    }
}