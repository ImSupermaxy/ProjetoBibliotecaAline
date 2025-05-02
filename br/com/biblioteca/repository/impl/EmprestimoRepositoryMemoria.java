package br.com.biblioteca.repository.impl;

import br.com.biblioteca.enums.EStatus;
import br.com.biblioteca.model.Emprestimo;
import br.com.biblioteca.model.Livro;
import br.com.biblioteca.model.Usuario;
import br.com.biblioteca.repository.IEmprestimoRepository;

import java.util.ArrayList;
import java.util.List;

public class EmprestimoRepositoryMemoria implements IEmprestimoRepository {


    private final List<Emprestimo> emprestimos = new ArrayList<>();

    public EmprestimoRepositoryMemoria(boolean createDateBaseWithData)
    {
        if(createDateBaseWithData)
        {
//            emprestimos.add(new Emprestimo("Duna 2", 12, "Frank Herbert"));
//            emprestimos.add(new Emprestimo("O Morro dos Ventos Uivantes", 5, "Emily Brontë"));
//            emprestimos.add(new Emprestimo("Água Viva", 3, "Clarice Lispector"));
//            emprestimos.add(new Emprestimo("A Biblioteca da Meia-Noite", 7, "Matt Haig"));
//            emprestimos.add(new Emprestimo("Hamlet", 10, "William Shakespeare"));
//            emprestimos.add(new Emprestimo("Romeu e Julieta", 6, "William Shakespeare"));
//            emprestimos.add(new Emprestimo("A revolução dos bichos", 8, "George Orwell"));
//            emprestimos.add(new Emprestimo("Call of Cthulhu", 5, "Howard Phillips Lovecraft"));
        }
    }

    @Override
    public List<Emprestimo> obterEmprestimoByStatus(EStatus status) {
        return emprestimos.stream()
                .filter(e -> e.getStatus().equals(status)).toList();
    }

    @Override
    public List<Emprestimo> obterEmprestimoByUsuario(Usuario usuario) {
        return emprestimos.stream()
                .filter(e -> e.getUsuario().equals(usuario)).toList();
    }

    @Override
    public List<Emprestimo> obterEmprestimoByLivro(Livro livro) {
        return emprestimos.stream()
                .filter(e -> e.getLivro().equals(livro)).toList();
    }

    @Override
    public boolean salvar(Emprestimo entity) {
        if (entity == null) throw new IllegalArgumentException("O empréstimo não pode ser nulo.");
        emprestimos.add(entity);
        return true;
    }

    @Override
    public List<Emprestimo> listar() {
        return emprestimos;
    }

    @Override
    public boolean remover(Emprestimo entity) {
        if (entity == null) throw new IllegalArgumentException("O empréstimo não pode ser nulo.");

        emprestimos.stream().filter(c -> c.equals(entity))
                .toList().getFirst().updateStatus(EStatus.Inativo);

        return true;
    }
}
