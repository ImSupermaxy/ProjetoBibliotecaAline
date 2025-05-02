package br.com.biblioteca.service.impl;

import br.com.biblioteca.model.Usuario;
import br.com.biblioteca.repository.IUsuarioRepository;
import br.com.biblioteca.service.IUsuarioService;
import br.com.biblioteca.shared.model.CommandGenericResult;

import java.util.List;

public class UsuarioServiceImpl implements IUsuarioService {
    private final IUsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public CommandGenericResult<Usuario> cadastrar(Usuario entity) {
        if (usuarioRepository.obterUsuarioByCpf(entity.getCpf()) != null) {
            throw new IllegalArgumentException("Usuário já cadastrado com o documento: " + entity.getCpf());
        }

        if(!usuarioRepository.salvar(entity))
            return new CommandGenericResult<Usuario>(false, "Usuário não cadastrado.");

        return new CommandGenericResult<Usuario>(true, "Usuário cadastrado!", entity);
    }

    @Override
    public Usuario listarUsuarioByCpf(String cpf) {
        var usuario = usuarioRepository.obterUsuarioByCpf(cpf);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuário com o CPF: " + cpf + " não foi encontrado. ");
        }

        return usuario;
    }

    @Override
    public List<Usuario> listar() {
        return usuarioRepository.listar();
    }

    @Override
    public boolean deletar(Usuario usuario) {
        return usuarioRepository.remover(usuario);
    }
}
