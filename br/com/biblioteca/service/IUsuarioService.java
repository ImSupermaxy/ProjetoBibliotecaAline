package br.com.biblioteca.service;

import br.com.biblioteca.base.service.IEntityService;
import br.com.biblioteca.model.Usuario;

public interface IUsuarioService extends IEntityService<Usuario> {
    
    Usuario listarUsuarioByCpf(String cpf);

}
