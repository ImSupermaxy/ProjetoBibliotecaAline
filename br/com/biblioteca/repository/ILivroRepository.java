package br.com.biblioteca.repository;

import br.com.biblioteca.base.repository.IBaseRepository;
import br.com.biblioteca.model.Livro;

import java.util.UUID;

public interface ILivroRepository extends IBaseRepository<Livro> {
    Livro obterLivroById(UUID id);
    Livro obterLivroByNomeAndAutor(String nome, String autor);
    boolean atualizaEstoque(UUID id, int quantidade);
}
