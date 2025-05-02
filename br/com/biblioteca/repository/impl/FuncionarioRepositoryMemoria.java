package br.com.biblioteca.repository.impl;

import br.com.biblioteca.enums.EPerfil;
import br.com.biblioteca.model.Funcionario;
import br.com.biblioteca.model.Usuario;
import br.com.biblioteca.repository.IFuncionarioRepository;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioRepositoryMemoria implements IFuncionarioRepository {

    private final List<Funcionario> funcionarios = new ArrayList<>();

    public FuncionarioRepositoryMemoria(boolean createDataBaseWithData)
    {
        if(createDataBaseWithData)
        {
            var perfis = new ArrayList<EPerfil>();
            perfis.add(EPerfil.Bibliotecario);
            this.funcionarios.add(new Funcionario("Marina Santos Morais", "000.000.000-03", "marina@testeemail.com", "marina@senha", perfis));

            perfis = new ArrayList<EPerfil>();
            perfis.add(EPerfil.Bibliotecario);
            perfis.add(EPerfil.Master);
            this.funcionarios.add(new Funcionario("Matheus Santos Morais", "000.000.000-04", "matheus@testeemail.com", "mathues@senha", perfis));

            this.funcionarios.add(new Funcionario("Lucas Antônio Dias", "000.000.000-05", "lucas@lucas.com", "lucas@senha", perfis));

            perfis = new ArrayList<EPerfil>();
            perfis.add(EPerfil.Bibliotecario);
            perfis.add(EPerfil.Master);
            perfis.add(EPerfil.Admin);
            this.funcionarios.add(new Funcionario("Aline dos Santos", "000.000.000-06", "aline@testeemail.com", "aline@senha", perfis));
        }
    }

    @Override
    public Funcionario obterFuncionarioByCpf(String cpf) {
        return funcionarios.stream()
                .filter(c -> c.getCpf().equalsIgnoreCase(cpf))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean salvar(Funcionario entity) {
        if (entity == null) throw new IllegalArgumentException("O Funcionário não pode ser nulo.");
        funcionarios.add(entity);
        return true;
    }

    @Override
    public List<Funcionario> listar() {
        return funcionarios;
    }

    @Override
    public boolean remover(Funcionario entity) {
        if (entity == null) throw new IllegalArgumentException("O Funcionário não pode ser nulo.");
        //return funcionarios.removeIf(c -> c.getCpf().equalsIgnoreCase(entity.getCpf()));
        entity.inativaUsuario();
        return true;
    }
}
