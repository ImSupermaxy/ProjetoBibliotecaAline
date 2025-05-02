package br.com.biblioteca.base.service;

import java.util.List;

public interface IEnumService<T extends Enum<T>> extends IService{
    List<T> getAll();
    List<String> getAllNome();
}
