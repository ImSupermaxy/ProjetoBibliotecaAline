package br.com.biblioteca.repository;

import br.com.biblioteca.base.repository.IBaseRepository;
import br.com.biblioteca.model.Usuario;

import java.util.List;

public interface IUsuarioRepository extends IBaseRepository<Usuario> {
    Usuario obterUsuarioByCpf(String cpf);
}
