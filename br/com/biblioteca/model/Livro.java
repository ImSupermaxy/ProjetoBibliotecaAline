package br.com.biblioteca.model;

import br.com.biblioteca.base.model.Entity;
import br.com.biblioteca.enums.EStatus;

import java.util.UUID;

public class Livro extends Entity {
    private final UUID id;
    private final String nome;
    private final int quantidade;
    private int disponiveis;
    private final String autor;
    private EStatus status;

    public Livro(String nome, int quantidade, String autor) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.quantidade = quantidade;
        this.disponiveis = quantidade;
        this.autor = autor;
        this.status = EStatus.Ativo;
    }

    public UUID getId(){
        return this.id;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade(){
        return this.quantidade;
    }

    public int getDisponiveis(){
        return this.disponiveis;
    }

    public String getAutor(){
        return this.autor;
    }

    public void atualizaDisponivel(int quantidade){
        this.disponiveis += quantidade;
    }

    public void inativaUsuario() { this.status = EStatus.Inativo; }

    public void ativaUsuario() { this.status = EStatus.Ativo; }

    @Override
    public String toString() {
        return String.format("Livro: \nId: %s\n", id)
                + String.format("Nome: %s\n", nome)
                + String.format("Quantidade em Estoque: %s\n", quantidade)
                + String.format("Quantidade disponível: %s\n", disponiveis)
                + String.format("Autor: %s\n", autor)
                + String.format("Status: %s", status.name());
    }
}
