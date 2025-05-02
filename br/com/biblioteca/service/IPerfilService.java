package br.com.biblioteca.service;

import br.com.biblioteca.base.service.IEnumService;
import br.com.biblioteca.enums.EPerfil;

import java.util.List;
import java.util.Scanner;

public interface IPerfilService extends IEnumService<EPerfil> {
    List<EPerfil> criarPefil(Scanner scanner);
    List<EPerfil> getHierarquiaByPerfil(EPerfil perfil);
}
