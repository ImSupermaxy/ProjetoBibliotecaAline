package br.com.biblioteca.repository.impl;

import br.com.biblioteca.enums.EPerfil;
import br.com.biblioteca.model.Endereco;
import br.com.biblioteca.model.Usuario;
import br.com.biblioteca.repository.IUsuarioRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UsuarioRepositoryMemoria implements IUsuarioRepository {

    private final List<Usuario> usuarios = new ArrayList<>();

    public UsuarioRepositoryMemoria(boolean createDateBaseWithData)
    {
        if(createDateBaseWithData)
        {
            var perfis = new ArrayList<EPerfil>();
            perfis.add(EPerfil.Cliente);
            usuarios.add(new Usuario("Matheus Morais", "000.000.000-00", "mahteus@gmail.com", "testeSenha@", new Date(),
                    "(11) 99999-9999", new Endereco("00000-000", "Rua teste 1", "Vila Teste",
                    null, "s/n", "SP", "São Paulo"), perfis));

            usuarios.add(new Usuario("Usuário 1", "000.000.000-01", "usuario1@gmail.com", "senhaUsuario1@", new Date(),
                    "(00) 90000-9999", new Endereco("00000-000", "Rua teste 2", "Vila Teste",
                    null, "s/n", "SP", "São Paulo"), perfis));

            usuarios.add(new Usuario("Usuário 2", "000.000.000-02", "usuario2@gmail.com", "senhaUsuario2@", new Date(),
                    "(00) 90000-0000", new Endereco("00000-000", "Rua teste 2", "Vila Teste",
                    null, "s/n", "SP", "São Paulo"), perfis));
        }
    }

    @Override
    public boolean salvar(Usuario entity) {
        if (entity == null) throw new IllegalArgumentException("O Usuário não pode ser nulo.");
        usuarios.add(entity);
        return true;
    }

    @Override
    public Usuario obterUsuarioByCpf(String cpf) {
        return usuarios.stream()
                .filter(c -> c.getCpf().equalsIgnoreCase(cpf))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Usuario> listar() {
        return usuarios;
    }

    @Override
    public boolean remover(Usuario entity) {
        if (entity == null) throw new IllegalArgumentException("O Usuário não pode ser nulo.");
//        return usuarios.removeIf(c -> c.getCpf().equalsIgnoreCase(entity.getCpf()));
        entity.inativaUsuario();
        return true;
    }
}
