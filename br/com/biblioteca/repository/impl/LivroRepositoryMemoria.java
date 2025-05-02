package br.com.biblioteca.repository.impl;

import br.com.biblioteca.model.Livro;
import br.com.biblioteca.repository.ILivroRepository;

import java.util.*;

public class LivroRepositoryMemoria implements ILivroRepository {

    private final List<Livro> livros = new ArrayList<>();

    public LivroRepositoryMemoria(boolean createDateBaseWithData)
    {
        if(createDateBaseWithData)
        {
            livros.add(new Livro("Duna 2", 12, "Frank Herbert"));
            livros.add(new Livro("O Morro dos Ventos Uivantes", 5, "Emily Brontë"));
            livros.add(new Livro("Água Viva", 3, "Clarice Lispector"));
            livros.add(new Livro("A Biblioteca da Meia-Noite", 7, "Matt Haig"));
            livros.add(new Livro("Hamlet", 10, "William Shakespeare"));
            livros.add(new Livro("Romeu e Julieta", 6, "William Shakespeare"));
            livros.add(new Livro("A revolução dos bichos", 8, "George Orwell"));
            livros.add(new Livro("Call of Cthulhu", 5, "Howard Phillips Lovecraft"));
        }
    }

    @Override
    public Livro obterLivroById(UUID id) {
        return livros.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Livro obterLivroByNomeAndAutor(String nome, String autor) {
        return livros.stream()
                .filter(c -> c.getNome().equalsIgnoreCase(nome)
                        && c.getAutor().equalsIgnoreCase(autor))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean atualizaEstoque(UUID id, int quantidade) {
        Objects.requireNonNull(livros.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null)).atualizaDisponivel(quantidade);

        return true;
    }

    @Override
    public boolean salvar(Livro entity) {
        if (entity == null) throw new IllegalArgumentException("O Livro não pode ser nulo.");
        livros.add(entity);
        return true;
    }

    @Override
    public List<Livro> listar() {
        return livros;
    }

    @Override
    public boolean remover(Livro entity) {
        if (entity == null) throw new IllegalArgumentException("O Livro não pode ser nulo.");
        //return livros.removeIf(c -> c.getId().equals(entity.getId()));
        entity.inativaUsuario();
        return true;
    }
}
