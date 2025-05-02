package br.com.biblioteca.service.impl;

import br.com.biblioteca.enums.EStatus;
import br.com.biblioteca.model.Emprestimo;
import br.com.biblioteca.model.Livro;
import br.com.biblioteca.model.Usuario;
import br.com.biblioteca.repository.IEmprestimoRepository;
import br.com.biblioteca.service.IEmprestimoService;
import br.com.biblioteca.shared.model.CommandGenericResult;

import java.util.Date;
import java.util.List;

public class EmprestimoServiceImpl implements IEmprestimoService {

    private final IEmprestimoRepository emprestimoRepository;

    public EmprestimoServiceImpl(IEmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    //observação todos os listar By, podem ser vazios...
    @Override
    public List<Emprestimo> listarEmprestimoByStatus(EStatus status) {
        var emprestimos = emprestimoRepository.obterEmprestimoByStatus(status);

        if(emprestimos == null)
            throw new IllegalArgumentException("Empréstimos não encontrado.");

        return emprestimos;
    }

    @Override
    public List<Emprestimo> listarEmprestimoByUsuario(Usuario usuario) {
        var emprestimos = emprestimoRepository.obterEmprestimoByUsuario(usuario);

        if(emprestimos == null)
            throw new IllegalArgumentException("Empréstimos não encontrado.");

        return emprestimos;
    }

    @Override
    public List<Emprestimo> listarEmprestimoByLivro(Livro livro) {
        var emprestimos = emprestimoRepository.obterEmprestimoByLivro(livro);

        if(emprestimos == null)
            throw new IllegalArgumentException("Empréstimos não encontrado.");

        return emprestimos;
    }

    @Override
    public CommandGenericResult<Emprestimo> cadastrar(Emprestimo entity) {
        if (!this.listarEmprestimoByUsuario(entity.getUsuario())
                .stream().filter(em -> em.getStatus() == EStatus.Ativo).toList().isEmpty()) {
            throw new IllegalArgumentException("Este usuário já possui um empréstimo ativo!");
        }

        if(!emprestimoRepository.salvar(entity))
            return new CommandGenericResult<Emprestimo>(false,"Empréstimo não cadastrado.");

        return new CommandGenericResult<Emprestimo>(true,"Empréstimo cadastrado!", entity);
    }

    @Override
    public List<Emprestimo> listar() {
        return emprestimoRepository.listar();
    }

    @Override
    public boolean deletar(Emprestimo entity) {
        return emprestimoRepository.remover(entity);
    }
}
