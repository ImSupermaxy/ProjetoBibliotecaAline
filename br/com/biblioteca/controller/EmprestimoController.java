package br.com.biblioteca.controller;

import br.com.biblioteca.base.controller.IEntityController;
import br.com.biblioteca.enums.EStatus;
import br.com.biblioteca.model.Emprestimo;
import br.com.biblioteca.service.*;
import br.com.biblioteca.shared.BibliotecaUtils;

import java.util.Scanner;
import java.util.UUID;

import static br.com.biblioteca.shared.ConsoleUtils.lerInt;
import static br.com.biblioteca.shared.ConsoleUtils.lerLinha;

public class EmprestimoController implements IEntityController {

    private final IEmprestimoService emprestimoService;
    private final IUsuarioService usuarioService;
    private final ILivroService livroService;
    private final IRetiradaService retiradaService;

    public EmprestimoController(IEmprestimoService emprestimoService, IUsuarioService usuarioService, ILivroService livroService, IRetiradaService retiradaService) {
        this.emprestimoService = emprestimoService;
        this.usuarioService = usuarioService;
        this.livroService = livroService;
        this.retiradaService = retiradaService;
    }

    @Override
    public void menu(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n--- Menu Empréstimos ---");
            System.out.println("1 - Cadastrar empréstimo");
            System.out.println("2 - Listar empréstimo");
            System.out.println("3 - Buscar um empréstimo por usuário");
            System.out.println("4 - Buscar um empréstimo por livro");
            System.out.println("5 - Cancelar empréstimo");
            System.out.println("0 - Voltar");

            opcao = lerInt(scanner, "Escolha uma opção");

            switch (opcao) {
                case 1 -> cadastrar(scanner);
                case 2 -> listar(scanner);
                case 3 -> obterByUsuario(scanner);
                case 4 -> obterByLivro(scanner);
                case 5 -> remover(scanner);
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    @Override
    public void cadastrar(Scanner scanner) {
        var cpf = lerLinha(scanner, "Documento (CPF: 000.000.000-00)", BibliotecaUtils::ValidateCPF);
        var usuario = this.usuarioService.listarUsuarioByCpf(cpf);

        var idLivro = lerLinha(scanner, "Id do Livro");
        var livro = this.livroService.listarLivroById(UUID.fromString(idLivro));

        var retirada = this.retiradaService.criarRetirada(scanner);

        //Validar estoque
        if(!livroService.validarEstoque(livro))
            throw new IllegalArgumentException("Este livro não possui mais em estoque");

        if(emprestimoService.listarEmprestimoByUsuario(usuario).stream()
                .anyMatch(em -> em.getStatus().equals(EStatus.Ativo)))
            throw new IllegalArgumentException("Este usuário já possui um empréstimo ativo!");

        var emprestimo = new Emprestimo(usuario, livro, retirada);
        var commandResult = emprestimoService.cadastrar(emprestimo);

        if(!commandResult.getSuccess()) {
            System.out.println(commandResult.getMessage());
            return;
        }

        //Atualiza o estroque do livro
        livroService.atualizarEstoque(livro, -1);

        System.out.println(commandResult.getMessage());
    }

    @Override
    public void listar(Scanner scanner) {
        var emprestimos = emprestimoService.listar();

        if(emprestimos.isEmpty())
            throw new IllegalArgumentException("Nenhum empréstimo cadastrado.");

        System.out.println("============================================== Empréstimos ==============================================");

        emprestimos.forEach(c -> System.out.println("\n" + c.toString() + "\n"));

        System.out.println("=========================================================================================================\n");
    }

    @Override
    public void remover(Scanner scanner) {
        var doc = lerLinha(scanner, "Documento (CPF: 000.000.000-00)", BibliotecaUtils::ValidateCPF);

        var usuario = usuarioService.listarUsuarioByCpf(doc);

        var emprestimos = emprestimoService.listarEmprestimoByUsuario(usuario)
                            .stream().filter(e -> e.getStatus() == EStatus.Ativo)
                            .toList();

        if(emprestimos.isEmpty())
            throw new IllegalArgumentException("Este usuário não possui nenhum empréstimo ativo!");

        var emprestimo = emprestimos.getFirst();
        if(emprestimo.getMultas().stream()
                .anyMatch(m -> m.getStatus().equals(EStatus.Ativo)))
            throw new IllegalArgumentException("Não se pode cancelar esse empréstimo, pois ainda existe uma multa ativa!");

        boolean removido = emprestimoService.deletar(emprestimo);

        livroService.atualizarEstoque(emprestimo.getLivro(), 1);

        System.out.println(removido ? "Empréstimo cancelado." : "Empréstimo não removido.");
    }

    public void obterByUsuario(Scanner scanner){
        var documento = lerLinha(scanner, "Documento (CPF: 000.000.000-00)", BibliotecaUtils::ValidateCPF);
        var usuario = usuarioService.listarUsuarioByCpf(documento);

        var emprestimos = emprestimoService.listarEmprestimoByUsuario(usuario);
        if(emprestimos == null || emprestimos.isEmpty())
            throw new IllegalArgumentException("Nenhum empréstimo encontrado.");

        System.out.println("============================================== Empréstimo do Usuário: " + usuario.getCpf() + " ==============================================");

        emprestimos.forEach(e -> System.out.println("\n" + e.toString() + "\n"));

        System.out.println("================================================================================================================================\n");
    }

    public void obterByLivro(Scanner scanner){
        String Id = lerLinha(scanner, "Id do livro");
        var livro = livroService.listarLivroById(UUID.fromString(Id));

        var emprestimos = emprestimoService.listarEmprestimoByLivro(livro);
        if(emprestimos.isEmpty())
            throw new IllegalArgumentException("Nenhum empréstimo encontrado.");

        emprestimos.forEach(e -> System.out.println("\n" + e.toString() + "\n"));
    }
}
