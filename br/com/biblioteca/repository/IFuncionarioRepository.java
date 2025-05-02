package br.com.biblioteca.repository;

import br.com.biblioteca.base.repository.IBaseRepository;
import br.com.biblioteca.model.Funcionario;

public interface IFuncionarioRepository extends IBaseRepository<Funcionario> {
    Funcionario obterFuncionarioByCpf(String cpf);
}
