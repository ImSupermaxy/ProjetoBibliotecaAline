package br.com.biblioteca.controller;

import br.com.biblioteca.base.controller.IEntityController;
import br.com.biblioteca.service.IPerfilService;
import br.com.biblioteca.model.Funcionario;
import br.com.biblioteca.service.IFuncionarioService;
import br.com.biblioteca.shared.BibliotecaUtils;

import java.util.Scanner;

import static br.com.biblioteca.shared.ConsoleUtils.*;
import static br.com.biblioteca.shared.ConsoleUtils.lerLinha;

public class FuncionarioController implements IEntityController {
    private final IFuncionarioService funcionarioService;
    private final IPerfilService perfilService;

    public FuncionarioController(IFuncionarioService funcionarioService, IPerfilService perfilService) {
        this.funcionarioService = funcionarioService;
        this.perfilService = perfilService;
    }

    public void menu(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n--- Menu Funcionário ---");
            System.out.println("1 - Cadastrar funcionário");
            System.out.println("2 - Listar funcionário");
            System.out.println("3 - Buscar um funcionário");
            System.out.println("4 - Remover funcionário");
            System.out.println("0 - Voltar");

            opcao = lerInt(scanner, "Escolha uma opção");

            switch (opcao) {
                case 1 -> cadastrar(scanner);
                case 2 -> listar(scanner);
                case 3 -> obterByCpf(scanner);
                case 4 -> remover(scanner);
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    public void cadastrar(Scanner scanner) {
        var nome = lerLinha(scanner, "Nome completo", BibliotecaUtils::ValidateNome);
        var documento = lerLinha(scanner, "Documento (CPF: 000.000.000-00)", BibliotecaUtils::ValidateCPF);
        var email = lerLinha(scanner, "E-mail: exemplo@gmail.com", e -> e.contains("@"));
        var senha = lerLinha(scanner, "Senha");

        var perfis = perfilService.criarPefil(scanner);

        var usuairo = new Funcionario(nome, documento, email, senha, perfis);

        var commandResult = funcionarioService.cadastrar(usuairo);

        if(!commandResult.getSuccess()) {
            System.out.println(commandResult.getMessage());
            return;
        }

        System.out.println(commandResult.getMessage());
    }

    public void listar(Scanner scanner) {
        var funcionarios = funcionarioService.listar();

        if(funcionarios == null || funcionarios.isEmpty())
            throw new IllegalArgumentException("Nenhum funcionário cadastrado");

        System.out.println("============================================== Funcionários ==============================================");

        funcionarios.forEach(c -> System.out.println("\n" + c.toString() + "\n"));

        System.out.println("=========================================================================================================\n");
    }

    public void obterByCpf(Scanner scanner){
        var documento = lerLinha(scanner, "Documento (CPF: 000.000.000-00)", BibliotecaUtils::ValidateCPF);
        var usuario = funcionarioService.listarFuncionarioByCpf(documento);

        System.out.println("============================================== Funcionário: " + usuario.getCpf() + " ==============================================");

        System.out.println("\n" + usuario.toString() + "\n");

        System.out.println("=========================================================================================================================\n");
    }

    public void remover(Scanner scanner) {
        String doc = lerLinha(scanner, "Documento (CPF: 000.000.000-00)", BibliotecaUtils::ValidateCPF);

        var usuario = funcionarioService.listarFuncionarioByCpf(doc);

        boolean removido = funcionarioService.deletar(usuario);
        System.out.println(removido ? "Funcionário removido." : "Funcionário não removido.");
    }

}
