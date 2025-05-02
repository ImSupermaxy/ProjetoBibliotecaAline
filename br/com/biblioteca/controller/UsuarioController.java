package br.com.biblioteca.controller;

import br.com.biblioteca.base.controller.IEntityController;
import br.com.biblioteca.service.IPerfilService;
import br.com.biblioteca.enums.EPerfil;
import br.com.biblioteca.model.Usuario;
import br.com.biblioteca.service.IEnderecoService;
import br.com.biblioteca.service.IUsuarioService;
import br.com.biblioteca.shared.BibliotecaUtils;

import java.util.Scanner;
import java.util.function.Predicate;

import static br.com.biblioteca.shared.ConsoleUtils.*;

public class UsuarioController implements IEntityController {
    private final IUsuarioService usuarioService;
    private final IEnderecoService enderecoService;
    private final IPerfilService perfilService;

    public UsuarioController(IUsuarioService usuarioService, IEnderecoService enderecoService, IPerfilService perfilService) {
        this.usuarioService = usuarioService;
        this.enderecoService = enderecoService;
        this.perfilService = perfilService;
    }

    public void menu(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n--- Menu Usuário ---");
            System.out.println("1 - Cadastrar usuário");
            System.out.println("2 - Listar usuários");
            System.out.println("3 - Buscar um usuário");
            System.out.println("4 - Remover usuário");
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
        var documento = lerLinha(scanner, "Documento (formato 000.000.000-00)", BibliotecaUtils::ValidateCPF);
        var telefone = lerLinha(scanner, "Telefone (formato (00) 90000-0000)", BibliotecaUtils::ValidateTelefone);
        var email = lerLinha(scanner, "E-mail  (formato exemplo@gmail.com)", e -> e.contains("@"));
        var senha = lerLinha(scanner, "Senha");
        var nascimento = lerData(scanner, "Data de nascimento");

        var endereco = enderecoService.criarEndereco(scanner);

        var perfis = perfilService.getHierarquiaByPerfil(EPerfil.Cliente);

        var usuairo = new Usuario(nome, documento, email, senha, nascimento, telefone, endereco, perfis);

        var commandResult = usuarioService.cadastrar(usuairo);

        if(!commandResult.getSuccess()) {
            System.out.println(commandResult.getMessage());
            return;
        }

        System.out.println(commandResult.getMessage());
    }

    public void listar(Scanner scanner) {
        var usuarios = usuarioService.listar();

        if(usuarios == null || usuarios.isEmpty())
            throw new IllegalArgumentException("Nenhum usuário cadastrado");

        System.out.println("============================================== Usuários ==============================================");

        usuarios.forEach(c -> System.out.println("\n" + c.toString() + "\n"));

        System.out.println("======================================================================================================\n");
    }

    public void obterByCpf(Scanner scanner){
        var documento = lerLinha(scanner, "Documento (CPF: 000.000.000-00)", BibliotecaUtils::ValidateCPF);
        var usuario = usuarioService.listarUsuarioByCpf(documento);

        System.out.println("============================================== Usuário: " + usuario.getCpf() + " ==============================================");

        System.out.println("\n" + usuario.toString() + "\n");

        System.out.println("=====================================================================================================================\n");
    }

    public void remover(Scanner scanner) {
        String doc = lerLinha(scanner, "Documento (CPF: 000.000.000-00)", BibliotecaUtils::ValidateCPF);

        var usuario = usuarioService.listarUsuarioByCpf(doc);

        boolean removido = usuarioService.deletar(usuario);
        System.out.println(removido ? "Usuário removido." : "Usuário não removido.");
    }
}
