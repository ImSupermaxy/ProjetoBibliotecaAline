package br.com.biblioteca.service;

import br.com.biblioteca.base.service.IEntityService;
import br.com.biblioteca.enums.EStatus;
import br.com.biblioteca.model.Emprestimo;
import br.com.biblioteca.model.Livro;
import br.com.biblioteca.model.Usuario;

import java.util.Date;
import java.util.List;

public interface IEmprestimoService extends IEntityService<Emprestimo> {
    List<Emprestimo> listarEmprestimoByStatus(EStatus status);
    List<Emprestimo> listarEmprestimoByUsuario(Usuario usuario);
    List<Emprestimo> listarEmprestimoByLivro(Livro livro);
}
