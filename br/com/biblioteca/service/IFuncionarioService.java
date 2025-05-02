package br.com.biblioteca.service;

import br.com.biblioteca.base.service.IEntityService;
import br.com.biblioteca.model.Funcionario;

public interface IFuncionarioService extends IEntityService<Funcionario> {
    Funcionario listarFuncionarioByCpf(String cpf);
}
