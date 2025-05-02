package br.com.biblioteca;

import br.com.biblioteca.repository.impl.EmprestimoRepositoryMemoria;
import br.com.biblioteca.repository.impl.LivroRepositoryMemoria;
import br.com.biblioteca.service.impl.*;
import br.com.biblioteca.controller.*;
import br.com.biblioteca.repository.impl.FuncionarioRepositoryMemoria;
import br.com.biblioteca.repository.impl.UsuarioRepositoryMemoria;
import br.com.biblioteca.shared.MenuUI;
//import br.com.biblioteca.repository.impl.*;
//import br.com.biblioteca.service.impl.*;
//import br.com.biblioteca.shared.MenuUI;

public class Main {

    public static void main(String[] args) {
        //TesteConsole.TesteBiblioteca();

        var createDateBaseWithData = false;
        var createDataDevolucaoEmprestimoToMulta = false;

        //Services sem dependência a repositórios (Enums, e outras entidades)
        var enderecoService = new EnderecoServiceImpl();
        var perfilService = new PerfilServiceImpl();
        var statusService = new StatusServiceImpl();
        var retiradaService = new RetiradaServiceImpl(enderecoService);

        //Services com dependência a repositórios
        var usuarioService = new UsuarioServiceImpl(new UsuarioRepositoryMemoria(createDateBaseWithData));
        var funcionarioService = new FuncionarioServiceImpl(new FuncionarioRepositoryMemoria(createDateBaseWithData));
        var livroService = new LivroServiceImpl(new LivroRepositoryMemoria(createDateBaseWithData));
        var multaService = new MultaServiceImpl();
        var emprestimoService = new EmprestimoServiceImpl(new EmprestimoRepositoryMemoria(createDateBaseWithData));

        //injeção de dependência das Controller
        var usuarioController = new UsuarioController(usuarioService, enderecoService, perfilService);
        var funcionarioController = new FuncionarioController(funcionarioService, perfilService);
        var livroController = new LivroController(livroService);
        var emprestimoController = new EmprestimoController(emprestimoService, usuarioService, livroService, retiradaService);
        var devolucaoController = new DevolucaoController(createDataDevolucaoEmprestimoToMulta, emprestimoService, usuarioService, multaService, statusService, livroService);

        var menuUI = new MenuUI(usuarioController, funcionarioController, livroController, emprestimoController, devolucaoController, createDateBaseWithData, createDataDevolucaoEmprestimoToMulta);
        menuUI.exibirMenu();
    }
}
