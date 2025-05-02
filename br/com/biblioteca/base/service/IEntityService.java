package br.com.biblioteca.base.service;
import br.com.biblioteca.base.model.Entity;
import br.com.biblioteca.shared.model.CommandGenericResult;

import java.util.List;

public interface IEntityService<T extends Entity> extends IService {
    CommandGenericResult<T> cadastrar(T entity);
    List<T> listar();
    boolean deletar(T entity);
}
