package br.com.biblioteca.controller;

import br.com.biblioteca.base.controller.IBaseController;
import br.com.biblioteca.enums.EStatus;
import br.com.biblioteca.model.Emprestimo;
import br.com.biblioteca.model.Multa;
import br.com.biblioteca.service.*;
import br.com.biblioteca.shared.BibliotecaUtils;
import br.com.biblioteca.shared.MenuUI;

import java.util.Date;
import java.util.Scanner;

import static br.com.biblioteca.shared.ConsoleUtils.*;

public class DevolucaoController implements IBaseController {

    private int diasToGenerateMulta;
    private final boolean createDataDevolucaoEmprestimoToMulta;
    private final IEmprestimoService emprestimoService;
    private final IUsuarioService usuarioService;
    private final IMultaService multaService;
    private final IStatusService statusService;
    private final ILivroService livroService;

    public DevolucaoController(boolean createDataDevolucaoEmprestimoToMulta, IEmprestimoService emprestimoService, IUsuarioService usuarioService, IMultaService multaService, IStatusService statusService, ILivroService livroService){
        this.emprestimoService = emprestimoService;
        this.usuarioService = usuarioService;
        this.multaService = multaService;
        this.statusService = statusService;
        this.livroService = livroService;
        this.createDataDevolucaoEmprestimoToMulta = createDataDevolucaoEmprestimoToMulta;
    }

    @Override
    public void menu(Scanner scanner) {
    }

    public void menu(Scanner scanner, int diasParaDevolucao) {
        diasToGenerateMulta = diasParaDevolucao;
        int opcao;
        do {
            System.out.println("\n--- Menu Devolução ---");
            System.out.println("1 - Realizar Devolução");
//            System.out.println("2 - Listar Devolução");
            System.out.println("2 - Buscar Devolução por usuário");
            System.out.println("3 - Cadastrar Multa");
            System.out.println("4 - Atualizar status da Multa de um usuário");
            System.out.println("0 - Voltar");

            opcao = lerInt(scanner, "Escolha uma opção");

            switch (opcao) {
                case 1 -> realizar(scanner);
                case 2 -> listarByUsuario(scanner);
                case 3 -> criarMulta(scanner);
                case 4 -> atualizarStatusMulta(scanner);
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    public void realizar(Scanner scanner) {
        var documento = lerLinha(scanner, "Documento (CPF: 000.000.000-00)", BibliotecaUtils::ValidateCPF);
        var usuario = usuarioService.listarUsuarioByCpf(documento);

        var emprestimos = emprestimoService.listarEmprestimoByUsuario(usuario)
                .stream().filter(em -> em.getStatus().equals(EStatus.Ativo)).toList();

        if(emprestimos.isEmpty())
            throw new IllegalArgumentException("Nenhum empréstimo encontrado.");

        var emprestimo = emprestimos.getFirst();

        if(emprestimo.getMultas().stream()
                .anyMatch(m -> m.getStatus().equals(EStatus.Ativo)))
            throw new IllegalArgumentException("O usuário ainda possui um empréstimo com multas ativas!");


        var dataDevolucao = new Date();

        if(createDataDevolucaoEmprestimoToMulta)
            dataDevolucao = BibliotecaUtils.addDaysToDate(BibliotecaUtils.addMonthsToDate(new Date(), 1), diasToGenerateMulta);

        var canCreateDevolucao = emprestimo.getDataADevolver().compareTo(dataDevolucao);
        if(canCreateDevolucao < 0)
        {
            System.out.println("\n" + MenuUI.vermelho + "Aviso: " + MenuUI.reset + " a data de devolução do livro está atrasada, será gerado uma nova multa, e o valor dela. " +
                    "\nA multa deve ser paga agora, caso não seja, na próxima vez que for fazer a devolução, será adicionado uma nova multa, com seu valor recalculado. \n");
            var multa = multaService.criarMulta(emprestimo.getDataADevolver(), dataDevolucao);
            atualizaUltimaMultaEmprestimo(emprestimo, multa);
            System.out.println("Multa no valor de: " + multa.getValor() + " R$");

            var confirmacao = lerConfirmacao(scanner, "A multa já foi paga? ");

            if(confirmacao)
            {
                multa.atualizaDataPagamento();

                //Atualiza a data de devolução e inativa o empréstimo...
                emprestimo.updateDataDevolvolucao(dataDevolucao);

                //atualiza o estoque do livro
                livroService.atualizarEstoque(emprestimo.getLivro(), 1);
                System.out.println("Devolução realizada com sucesso!");
            }
        }
        else
        {
            //Atualiza a data de devolução e inativa o empréstimo...
            emprestimo.updateDataDevolvolucao(dataDevolucao);

            //atualiza o estoque do livro
            livroService.atualizarEstoque(emprestimo.getLivro(), 1);
            System.out.println("Devolução realizada com sucesso!");
        }
    }

    public void listarByUsuario(Scanner scanner){
        var documento = lerLinha(scanner, "Documento (CPF: 000.000.000-00)", BibliotecaUtils::ValidateCPF);
        var usuario = usuarioService.listarUsuarioByCpf(documento);

        var emprestimos = emprestimoService.listarEmprestimoByUsuario(usuario).stream()
                .filter(em -> em.getDataDevolvolucao() != null).toList();
        if(emprestimos.isEmpty())
            throw new IllegalArgumentException("Nenhuma devolução encontrada.");

        System.out.println("============================================== Devoluções do Usuário: " + usuario.getCpf() + " ==============================================");

        emprestimos.forEach(e -> System.out.println("\n" + e.toString() + "\n"));

        System.out.println("===================================================================================================================================\n");


    }

    public void criarMulta(Scanner scanner){
        var documento = lerLinha(scanner, "Documento (CPF: 000.000.000-00)", BibliotecaUtils::ValidateCPF);
        var usuario = usuarioService.listarUsuarioByCpf(documento);

        var emprestimos = emprestimoService.listarEmprestimoByUsuario(usuario)
                .stream().filter(em -> em.getStatus().equals(EStatus.Ativo)).toList();

        if(emprestimos.isEmpty())
            throw new IllegalArgumentException("Nenhum empréstimo encontrado.");

        var emprestimo = emprestimos.getFirst();

        var dataDevolucao = new Date();
        if(createDataDevolucaoEmprestimoToMulta)
            dataDevolucao = BibliotecaUtils.addDaysToDate(BibliotecaUtils.addMonthsToDate(new Date(), 1), diasToGenerateMulta);

        var multa = multaService.criarMulta(emprestimo.getDataADevolver(), dataDevolucao);

        atualizaUltimaMultaEmprestimo(emprestimo, multa);

        System.out.println("Multa Criada Com sucesso!");
    }

    public void atualizarStatusMulta(Scanner scanner){
        var documento = lerLinha(scanner, "Documento (CPF: 000.000.000-00)", BibliotecaUtils::ValidateCPF);
        var usuario = usuarioService.listarUsuarioByCpf(documento);

        var emprestimos = emprestimoService.listarEmprestimoByUsuario(usuario)
                .stream().filter(em -> em.getStatus().equals(EStatus.Ativo)).toList();

        if(emprestimos.isEmpty())
            throw new IllegalArgumentException("Nenhum empréstimo encontrado.");

        var emprestimo = emprestimos.getFirst();

        if(emprestimo.getMultas().stream()
                .noneMatch(m -> m.getStatus().equals(EStatus.Ativo)))
            throw new IllegalArgumentException("Este usuário não possui nenhum empréstimo com multas ativas!");

        var multa = emprestimo.getMultas().stream().filter(m -> m.getStatus().equals(EStatus.Ativo)).toList().getFirst();

        multa.atualizaStatus(statusService.criarStatus(scanner));

        System.out.println("Status da multa atualizado com sucesso!");
    }

    private void atualizaUltimaMultaEmprestimo(Emprestimo emprestimo, Multa novaMulta){
        var multasAnteriores = emprestimo.getMultas().stream().filter(m ->
                m.getStatus().equals(EStatus.Ativo))
                .toList();

        multasAnteriores.forEach(m -> m.atualizaStatus(EStatus.Inativo));

        emprestimo.addMulta(novaMulta);
    }
}
