package br.com.biblioteca.service.impl;

import br.com.biblioteca.enums.ERetirada;
import br.com.biblioteca.model.Endereco;
import br.com.biblioteca.model.Retirada;
import br.com.biblioteca.service.IEnderecoService;
import br.com.biblioteca.service.IRetiradaService;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static br.com.biblioteca.shared.ConsoleUtils.lerInt;
import static br.com.biblioteca.shared.ConsoleUtils.lerLinha;

public class RetiradaServiceImpl implements IRetiradaService {

    private final Endereco enderecoBiblioteca = new Endereco("00000-020", "Rua josé Antônio", "Livraria Almeida",
            "", "s/n", "SP", "Mogi das Cruzes");
    private final IEnderecoService enderecoService;

    public RetiradaServiceImpl(IEnderecoService enderecoService){
        this.enderecoService = enderecoService;
    }

    @Override
    public Retirada criarRetirada(Scanner scanner) {
        var tiposRetirada = this.getAllNome().toArray(new String[0]);

        System.out.println("Opção de retirada: ");
        for (int i = 0; i < tiposRetirada.length; i++)
        {
            System.out.println((i + 1) + " - " + tiposRetirada[i]);
        }
        var tipo = lerInt(scanner, "Retirada", r -> r > 0 && r <= 2);

        if(tipo < 1 || tipo > tiposRetirada.length)
            throw new IllegalArgumentException("Retirada inválido.");

        var endereco = this.enderecoBiblioteca;
        var retirada = ERetirada.valueOf(tiposRetirada[tipo - 1]);

        if(retirada == ERetirada.Entrega)
            endereco = enderecoService.criarEndereco(scanner);

        return new Retirada(retirada, endereco);
    }

    @Override
    public List<ERetirada> getAll() {
        return Arrays.stream(ERetirada.values())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllNome() {
        return Arrays.stream(ERetirada.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
