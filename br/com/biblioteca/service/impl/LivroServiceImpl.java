package br.com.biblioteca.service.impl;

import br.com.biblioteca.model.Livro;
import br.com.biblioteca.repository.ILivroRepository;
import br.com.biblioteca.service.ILivroService;
import br.com.biblioteca.shared.model.CommandGenericResult;

import java.util.List;
import java.util.UUID;

public class LivroServiceImpl implements ILivroService {
    private final ILivroRepository livroRepository;

    public LivroServiceImpl(ILivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    @Override
    public Livro listarLivroById(UUID Id) {
        var livro = livroRepository.obterLivroById(Id);

        if (livro == null) {
            throw new IllegalArgumentException("Livro com o Id: " + Id + " não foi encontrado. ");
        }

        return livro;
    }

    @Override
    public Livro listarLivroByNomeAndAutor(String nome, String autor) {
        var livro = livroRepository.obterLivroByNomeAndAutor(nome, autor);

        if (livro == null) {
            throw new IllegalArgumentException("Livro com o nome: " + nome + " não foi encontrado. ");
        }

        return livro;
    }

    @Override
    public boolean validarEstoque(Livro livro) {
        var disponiveis = livro.getDisponiveis();

        return disponiveis > 0;
    }

    @Override
    public void atualizarEstoque(Livro livro, int quantidade) {
        var success = livroRepository.atualizaEstoque(livro.getId(), quantidade);
        if(!success)
            throw new IllegalArgumentException("Estoque não atualizado");
    }

    @Override
    public CommandGenericResult<Livro> cadastrar(Livro entity) {
        if (livroRepository.obterLivroByNomeAndAutor(entity.getNome(), entity.getAutor()) != null) {
            throw new IllegalArgumentException("Livro já cadastrado com este nome: " + entity.getNome() + "e autor: " + entity.getAutor());
        }

        if(!livroRepository.salvar(entity))
            return new CommandGenericResult<Livro>(false, "Livro não cadastrado.");

        return new CommandGenericResult<Livro>(true, "Livro cadastrado!", entity);
    }

    @Override
    public List<Livro> listar() {
        return livroRepository.listar();
    }

    @Override
    public boolean deletar(Livro entity) {
        return livroRepository.remover(entity);
    }
}
