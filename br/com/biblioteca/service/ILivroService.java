package br.com.biblioteca.service;

import br.com.biblioteca.base.service.IEntityService;
import br.com.biblioteca.base.service.IService;
import br.com.biblioteca.model.Funcionario;
import br.com.biblioteca.model.Livro;

import java.util.UUID;

public interface ILivroService extends IEntityService<Livro> {
    Livro listarLivroById(UUID Id);
    Livro listarLivroByNomeAndAutor(String nome, String autor);
    boolean validarEstoque(Livro livro);
    void atualizarEstoque(Livro livro, int quantidade);
}
