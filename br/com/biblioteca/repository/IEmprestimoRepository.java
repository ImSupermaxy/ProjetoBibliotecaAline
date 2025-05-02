package br.com.biblioteca.repository;

import br.com.biblioteca.base.repository.IBaseRepository;
import br.com.biblioteca.enums.EStatus;
import br.com.biblioteca.model.Emprestimo;
import br.com.biblioteca.model.Livro;
import br.com.biblioteca.model.Usuario;

import java.util.List;

public interface IEmprestimoRepository extends IBaseRepository<Emprestimo> {
    List<Emprestimo> obterEmprestimoByStatus(EStatus status);
    List<Emprestimo> obterEmprestimoByUsuario(Usuario usuario);
    List<Emprestimo> obterEmprestimoByLivro(Livro livro);
}