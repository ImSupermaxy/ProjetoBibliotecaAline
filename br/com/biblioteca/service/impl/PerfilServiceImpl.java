package br.com.biblioteca.service.impl;

import br.com.biblioteca.service.IPerfilService;
import br.com.biblioteca.enums.EPerfil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static br.com.biblioteca.shared.ConsoleUtils.lerInt;

public class PerfilServiceImpl implements IPerfilService {

    @Override
    public List<EPerfil> criarPefil(Scanner scanner) {
        var perfis = this.getAllNome().toArray(new String[0]);

        System.out.println("Perfís do sistema: ");
        for (int i = 0; i < perfis.length; i++)
        {
            System.out.println((i + 1) + " - " + perfis[i]);
        }

        var perfil = lerInt(scanner, "Perfil", p -> p > 0 && p <= 4);

        if(perfil < 1 || perfil > perfis.length)
            throw new IllegalArgumentException("Perfíl inválido.");

        return this.getHierarquiaByPerfil(EPerfil.valueOf(perfis[perfil - 1]));
    }

    @Override
    public List<EPerfil> getHierarquiaByPerfil(EPerfil perfil) {
        var arrayPerfil = new ArrayList<EPerfil>();

        switch (perfil){
            case EPerfil.Master -> {
                arrayPerfil.add(perfil);
                arrayPerfil.add(EPerfil.Admin);
                arrayPerfil.add(EPerfil.Bibliotecario);
                arrayPerfil.add(EPerfil.Cliente);
                break;
            }
            case EPerfil.Admin -> {
                arrayPerfil.add(perfil);
                arrayPerfil.add(EPerfil.Bibliotecario);
                arrayPerfil.add(EPerfil.Cliente);
                break;
            }
            case EPerfil.Bibliotecario, EPerfil.Cliente -> {
                arrayPerfil.add(perfil);
                break;
            }
        }

        return arrayPerfil;
    }

    @Override
    public List<EPerfil> getAll() {
        return Arrays.stream(EPerfil.values())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllNome() {
        return Arrays.stream(EPerfil.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
