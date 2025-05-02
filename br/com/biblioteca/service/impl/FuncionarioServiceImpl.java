package br.com.biblioteca.service.impl;

import br.com.biblioteca.model.Funcionario;
import br.com.biblioteca.repository.IFuncionarioRepository;
import br.com.biblioteca.service.IFuncionarioService;
import br.com.biblioteca.shared.model.CommandGenericResult;

import java.util.List;

public class FuncionarioServiceImpl implements IFuncionarioService {
    private final IFuncionarioRepository funcionarioRepository;

    public FuncionarioServiceImpl(IFuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @Override
    public CommandGenericResult<Funcionario> cadastrar(Funcionario entity) {
        if (funcionarioRepository.obterFuncionarioByCpf(entity.getCpf()) != null) {
            throw new IllegalArgumentException("Funcionário já cadastrado com o documento: " + entity.getCpf());
        }

        if(!funcionarioRepository.salvar(entity))
            return new CommandGenericResult<Funcionario>(false, "Funcionário não cadastrado.");

        return new CommandGenericResult<Funcionario>(true, "Funcionário cadastrado!", entity);
    }

    @Override
    public Funcionario listarFuncionarioByCpf(String cpf) {
        var funcionario = funcionarioRepository.obterFuncionarioByCpf(cpf);

        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário com o CPF: " + cpf + " não foi encontrado. ");
        }

        return funcionario;
    }

    @Override
    public List<Funcionario> listar() {
        return funcionarioRepository.listar();
    }

    @Override
    public boolean deletar(Funcionario funcionario) {
        return funcionarioRepository.remover(funcionario);
    }
}
