package br.com.biblioteca.base.repository;

import br.com.biblioteca.base.model.Entity;

import java.util.List;

public interface IBaseRepository<T extends Entity> {
    boolean salvar(T entity);
    List<T> listar();
    boolean remover(T entity);
}
