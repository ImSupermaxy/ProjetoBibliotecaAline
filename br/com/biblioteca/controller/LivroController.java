package br.com.biblioteca.controller;

import br.com.biblioteca.base.controller.IEntityController;
import br.com.biblioteca.model.Livro;
import br.com.biblioteca.service.ILivroService;
import br.com.biblioteca.shared.BibliotecaUtils;

import java.util.Scanner;
import java.util.UUID;

import static br.com.biblioteca.shared.ConsoleUtils.*;
import static br.com.biblioteca.shared.ConsoleUtils.lerLinha;

public class LivroController implements IEntityController {
    private final ILivroService livroService;

    public LivroController(ILivroService livroService) {
        this.livroService = livroService;
    }

    public void menu(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n--- Menu Livro ---");
            System.out.println("1 - Cadastrar livro");
            System.out.println("2 - Listar livros");
            System.out.println("3 - Buscar um livro");
            System.out.println("4 - Remover livro");
            System.out.println("0 - Voltar");

            opcao = lerInt(scanner, "Escolha uma opção");

            switch (opcao) {
                case 1 -> cadastrar(scanner);
                case 2 -> listar(scanner);
                case 3 -> obterLivroById(scanner);
                case 4 -> remover(scanner);
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    public void cadastrar(Scanner scanner) {
        var nome = lerLinha(scanner, "Nome", BibliotecaUtils::ValidateNome);
        var quantidade = lerInt(scanner, "Quantidade em estoque", q -> q > 0 && q <= 50000);
        var autor = lerLinha(scanner, "Nome do autor");

        var livro = new Livro(nome, quantidade, autor);

        var commandResult = livroService.cadastrar(livro);

        if(!commandResult.getSuccess()) {
            System.out.println(commandResult.getMessage());
            return;
        }

        System.out.println(commandResult.getMessage());
        System.out.println("Id do livro: " + commandResult.getData().getId().toString());
    }

    public void listar(Scanner scanner) {
        var livro = livroService.listar();

        System.out.println("============================================== Livros ==============================================");

        livro.forEach(c -> System.out.println("\n" + c.toString() + "\n"));

        System.out.println("====================================================================================================\n");
    }

    public void obterLivroById(Scanner scanner){
        var Id = lerLinha(scanner, "Id do livro");

        var livro = livroService.listarLivroById(UUID.fromString(Id));

        System.out.println("============================================== Livro: " + livro.getNome() + " ==============================================");

        System.out.println("\n" + livro.toString() + "\n");

        System.out.println("========================================================================================================================================\n");
    }

    public void remover(Scanner scanner) {
        String Id = lerLinha(scanner, "Id do livro");

        var livro = livroService.listarLivroById(UUID.fromString(Id));

        boolean removido = livroService.deletar(livro);
        System.out.println(removido ? "Livro removido." : "Livro não removido.");
    }

}
