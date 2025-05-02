// simular a interface web/desktop
package br.com.biblioteca.shared;

import br.com.biblioteca.controller.*;

import java.util.Scanner;

public class MenuUI {

    private boolean isFirstCallMenu = true;
    public static String vermelho = "\u001B[31m";
    public static String ciano = "\u001B[36m";
    public static String reset = "\u001B[0m";
    private final boolean isCreatedDataInDataBase;
    private final boolean createDataDevolucaoEmprestimoToMulta;
    private final UsuarioController usuarioController;
    private final FuncionarioController funcionarioController;
    private final LivroController livroController;
    private final EmprestimoController emprestimoController;
    private final DevolucaoController devolucaoController;

    public MenuUI(
            UsuarioController usuarioController,
            FuncionarioController funcionarioController,
            LivroController livroController,
            EmprestimoController emprestimoController,
            DevolucaoController devolucaoController,
            boolean isCreatedDataInDataBase,
            boolean createDataDevolucaoEmprestimoToMulta
    ) {
        this.usuarioController = usuarioController;
        this.funcionarioController = funcionarioController;
        this.livroController = livroController;
        this.emprestimoController = emprestimoController;
        this.devolucaoController = devolucaoController;
        this.isCreatedDataInDataBase = isCreatedDataInDataBase;
        this.createDataDevolucaoEmprestimoToMulta = createDataDevolucaoEmprestimoToMulta;
    }

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n" + vermelho + "Observação: " + reset + "caso queira já começar com um banco com registros para testar as outras funcionalidades / opções \ne lógicas do sistema, tem uma flag na main, que é passada ao repositório de algumas entidade com possível cadastro, \né só alterar a variável \"createDateBaseWithData\" antes da injeção de dependência dos repositórios" + reset);
        System.out.println(vermelho + "Status... " + reset + "foi criado com dados? " + ciano +  isCreatedDataInDataBase + reset);
        System.out.println(vermelho + "Observação 2: " + reset + "Caso queria que as datas das devoluções sejam feitas após a data limite de devolução do empréstimo, é só atualizar a flag no main, como para a criação do banco com as informações.");
        System.out.println(vermelho + "Status... " + reset + "As devoluções serão criadas com multas? " + ciano +  createDataDevolucaoEmprestimoToMulta + reset);

        var dias = 0;
        if(createDataDevolucaoEmprestimoToMulta && isFirstCallMenu)
        {
            dias = ConsoleUtils.lerInt(scanner, "quantos dias após a data limite de devolução? (em até um ano)", d -> d > 0 && d <= 366);
            isFirstCallMenu = false;
        }

        try
        {
            int opcao = -1;

            while (opcao != 0) {
                System.out.println("\n====== MENU BIBLIOTECA ======");
                System.out.println("1 - Gerenciar Usuários");
                System.out.println("2 - Gerenciar Funcionários");
                System.out.println("3 - Gerenciar Livros");
                System.out.println("4 - Gerenciar Empréstimos");
                System.out.println("5 - Gerenciar Devoluções");
                System.out.println("0 - Sair");
                System.out.print("Escolha uma opção: ");

                opcao = scanner.nextInt();
                // consome quebra de linha
                scanner.nextLine();

                switch (opcao) {
                    case 1 -> usuarioController.menu(scanner);
                    case 2 -> funcionarioController.menu(scanner);
                    case 3 -> livroController.menu(scanner);
                    case 4 -> emprestimoController.menu(scanner);
                    case 5 -> devolucaoController.menu(scanner, dias);
                    case 0 -> System.out.println("Encerrando...");
                    default -> System.out.println("Opção inválida.");
                }
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + "\n");
            this.exibirMenu();
        }
    }
}
